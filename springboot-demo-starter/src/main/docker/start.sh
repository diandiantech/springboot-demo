#!/bin/bash

log_info() {
    now=$(date "+%Y-%m-%d %H:%M:%S")
    echo "[${now}] INFO: " "$@"
}

log_error() {
    now=$(date "+%Y-%m-%d %H:%M:%S")
    echo "[${now}] ERROR: " "$@"
}

exit1() {
    log_error "$@"
    exit 1
}


prepare_env() {
    mkdir -p "$APP_HOME"/.default

    export JAVA_FILE_ENCODING=UTF-8
    export APP_TGZ_FILE=${APP_NAME}.tgz

    export SERVICE_TMPDIR=${APP_HOME}/tmp
    export SERVICE_PID=${APP_HOME}/.default/${APP_NAME}.pid
    export SERVICE_OUT=${APP_HOME}/logs/service_stdout.log
}

prepare_jvm_opts() {
    SERVICE_OPTS="${SERVICE_OPTS} -server"
    SERVICE_OPTS="${SERVICE_OPTS} -Xms128m -Xmx1024m"
    SERVICE_OPTS="${SERVICE_OPTS} -Xmn512m"
    SERVICE_OPTS="${SERVICE_OPTS} -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m"
    SERVICE_OPTS="${SERVICE_OPTS} -XX:MaxDirectMemorySize=512m"
    SERVICE_OPTS="${SERVICE_OPTS} -XX:+UseG1GC"
    SERVICE_OPTS="${SERVICE_OPTS} -XX:MaxGCPauseMillis=200 -XX:ParallelGCThreads=20 -XX:ConcGCThreads=5 -XX:InitiatingHeapOccupancyPercent=70"
    SERVICE_OPTS="${SERVICE_OPTS} -XX:SurvivorRatio=10"
    SERVICE_OPTS="${SERVICE_OPTS} -XX:+ExplicitGCInvokesConcurrent -Dsun.rmi.dgc.server.gcInterval=2592000000 -Dsun.rmi.dgc.client.gcInterval=2592000000"
    SERVICE_OPTS="${SERVICE_OPTS} -Xloggc:${APP_HOME}/logs/gc.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps"
    SERVICE_OPTS="${SERVICE_OPTS} -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${APP_HOME}/logs/java.hprof"
    SERVICE_OPTS="${SERVICE_OPTS} -Djava.awt.headless=true"
    SERVICE_OPTS="${SERVICE_OPTS} -Dsun.net.client.defaultConnectTimeout=10000"
    SERVICE_OPTS="${SERVICE_OPTS} -Dsun.net.client.defaultReadTimeout=30000"
    SERVICE_OPTS="${SERVICE_OPTS} -Dfile.encoding=${JAVA_FILE_ENCODING}"
    SERVICE_OPTS="${SERVICE_OPTS} -Dmanagement.port=9000 -Dmanagement.server.port=9001"
}

extract_tgz() {
    local tgz_path="$1"
    local dir_path="$2"

    log_info "extract ${tgz_path} to ${dir_path}"
    rm -rf "${dir_path}" || exit1 "remove ${dir_path} failed"
    mkdir "${dir_path}" || exit1 "mkdir ${dir_path} failed"
    tar xzf "${tgz_path}" -C "${dir_path}" || exit1 "unzip ${tgz_path} failed"
    test -d "${dir_path}" || exit1 "no directory: ${dir_path}"
    touch --reference "${tgz_path}" "${tgz_path}.timestamp" || exit1 "touch reference failed"
}

update_bootstrap() {
    local tgz_name="$1"
    local dir_name="$2"

    local tgz_path="${APP_HOME}/pkg/${tgz_name}"
    local dir_path="${APP_HOME}/${dir_name}"

    local error=0
    # dir exists
    if [ -d "${dir_path}" ]; then
        # tgz exists
        if [ -f "${tgz_path}" ]; then
            local need_tar=0
            if [ ! -e "${tgz_path}.timestamp" ]; then
                need_tar=1
            else
                local tgz_time=$(stat -L -c "%Y" "${tgz_path}")
                local last_time=$(stat -L -c "%Y" "${tgz_path}.timestamp")
                if [ ${tgz_time} -gt ${last_time} ]; then
                    need_tar=1
                fi
            fi
            # tgz is new - extract_tgz
            if [ "${need_tar}" -eq 1 ]; then
                extract_tgz "${tgz_path}" "${dir_path}"
            fi
            # tgz is not new - return SUCCESS
        fi
        # tgz not exists - return SUCCESS
    # dir not exists
    else
        # tgz exists - extract_tgz
        if [ -f "${tgz_path}" ]; then
            extract_tgz "${tgz_path}" "${dir_path}"
        # tgz not exists - return FAIL
        else
            exit1 "${tgz_path} NOT EXISTS"
        fi
    fi

    return ${error}
}

start_spring_boot() {
    # 只保留5个SERVICE_OUT文件
    ls -t "$SERVICE_OUT".* 2>/dev/null | tail -n +$((5 + 1)) | xargs --no-run-if-empty rm -f
    if [ -e "$SERVICE_OUT" ]; then
        mv "$SERVICE_OUT" "$SERVICE_OUT.$(date '+%Y%m%d%H%M%S')" || exit1
    fi
    touch "$SERVICE_OUT" || exit1

    log_info "Spring boot service log: $SERVICE_OUT"

    if [ ! -z "$SERVICE_PID" ]; then
        if [ -f "$SERVICE_PID" ]; then
            if [ -s "$SERVICE_PID" ]; then
                log_info "Existing PID file found during start."
                if [ -r "$SERVICE_PID" ]; then
                    PID=`cat "$SERVICE_PID"`
                    ps -p $PID >/dev/null 2>&1
                    if [ $? -eq 0 ] ; then
                        exit1 "Service(spring boot) appears to still be running with PID $PID. Start aborted."
                    else
                        log_info "Removing/clearing stale PID file."
                        rm -f "$SERVICE_PID" >/dev/null 2>&1
                        if [ $? != 0 ]; then
                            if [ -w "$SERVICE_PID" ]; then
                                cat /dev/null > "$SERVICE_PID"
                            else
                                exit1 "Unable to remove or clear stale PID file. Start aborted."
                            fi
                        fi
                    fi
                else
                    exit1 "Unable to read PID file. Start aborted."
                fi
            else
                rm -f "$SERVICE_PID" >/dev/null 2>&1
                if [ $? != 0 ]; then
                    if [ ! -w "$SERVICE_PID" ]; then
                        exit1 "Unable to remove or write to empty PID file. Start aborted."
                    fi
                fi
            fi
        fi
    fi

    command="exec \"${JAVA_HOME}/bin/java\" $SERVICE_OPTS \
            -classpath \"${APP_HOME}/bootstrap\" \
            -Dapp.location=\"${APP_HOME}/bootstrap\" \
            -Djava.io.tmpdir=\"${SERVICE_TMPDIR}\" \
            org.springframework.boot.loader.JarLauncher \"$@\" \
            >> \"$SERVICE_OUT\" 2>&1 &"

    log_info "Run command: ${command}"
    eval ${command}

    if [ ! -z "$SERVICE_PID" ]; then
        echo $! > "$SERVICE_PID"
    fi
}

start() {
    log_info "${APP_NAME} try to start..."

    prepare_env
    prepare_jvm_opts

    log_info "[step 1] start to unzip app tgz file..."
    update_bootstrap "${APP_TGZ_FILE}" bootstrap || exit1

    log_info "[step 2] try to start spring boot..."
    start_spring_boot || exit1 "Start spring boot failed."

    log_info "${APP_NAME} start success"

    PID=`cat "$SERVICE_PID"`
    wait "${PID}"
}

__main__() {
    start
}

__main__ "$@"