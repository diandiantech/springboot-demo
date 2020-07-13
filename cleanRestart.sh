#!/bin/bash

CUR_HOME=$(cd $(dirname ${BASH_SOURCE[0]}); pwd)

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

cd "${CUR_HOME}"

log_info "Creating network: springboot-demo-net"
docker network create -d bridge springboot-demo-net

log_info "Creating volume: springboot-demo-mysql-data"
docker volume create springboot-demo-mysql-data

log_info "Creating volume: springboot-demo-logs"
docker volume create springboot-demo-logs

log_info "Pull latest springboot-demo code"
git pull || exit1 "Pull latest springboot-demo code failed"

log_info "Rebuild springboot-demo docker image"
mvn clean install docker:build || exit1 "Rebuild springboot-demo docker image failed"

log_info "Stop springboot-demo container"
docker-compose stop springboot-demo || exit1 "Stop springboot-demo container failed"

log_info "Remove springboot-demo container"
echo y | docker-compose rm springboot-demo || exit1 "Remove springboot-demo container failed"

log_info "springboot-demo image cleanup"
echo y | docker image prune || exit1 "springboot-demo image cleanup failed"

log_info "Restart springboot-demo"
docker-compose up -d || exit1 "Restart springboot-demo failed"

log_info "All SUCCESS"
cd -
