#!/usr/bin/env bash

FROM ubuntu:18.04
LABEL maintainer="dinglevin@foxmail.com"

# 用户信息
ENV ADMIN_USER_GROUP=admin
ENV ADMIN_USER=admin

# 时区信息
ENV TIME_ZONE=Asia/Shanghai
ENV TZ=${TIME_ZONE}

# 创建新的group和user，并把user添加到sudo组，从而可以执行sudo命令
RUN groupadd -r ${ADMIN_USER_GROUP} \
    && useradd -r -g ${ADMIN_USER_GROUP} --create-home --no-log-init --shell /bin/bash ${ADMIN_USER} \
    && echo "${ADMIN_USER}:admin" | chpasswd \
    && usermod -aG sudo ${ADMIN_USER}

# 使用阿里云镜像，并安装需要的软件，同时更新时区
RUN sed -i 's/archive.ubuntu.com/mirrors.aliyun.com/g' /etc/apt/sources.list \
    && sed -i 's/security.ubuntu.com/mirrors.aliyun.com/g' /etc/apt/sources.list \
    && dpkgArch="$(dpkg --print-architecture | awk -F- '{ print $NF }')" \
    && apt-get clean \
    && apt-get autoclean \
    && apt-get update \
    && ln -sf /usr/share/zoneinfo/${TIME_ZONE} /etc/localtime \
    && apt-get -y install \
        apt-utils \
        tzdata \
        locales \
    && locale-gen zh_CN.UTF-8 \
    && apt-get -y install \
        vim \
        sudo \
        openjdk-8-jdk \
    && apt-get clean \
    && apt-get autoclean \
    && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*

# 设置语言
ENV LANG="zh_CN.UTF-8"

# Java环境设置
ENV JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
ENV JRE_HOME=${JAVA_HOME}/jre
ENV CLASSPATH=.:${JAVA_HOME}/lib/dt.jar:${JAVA_HOME}/lib/tools.jar:${JRE_HOME}/lib:${CLASSPATH}

ENV APP_NAME=springboot-demo
ENV APP_SPRING_ACTIVE_PROFILE=production
ENV APP_HOME=/home/${ADMIN_USER}/${APP_NAME}

RUN mkdir -p ${APP_HOME}/bootstrap ${APP_HOME}/logs ${APP_HOME}/tmp ${APP_HOME}/pkg
COPY ${APP_NAME}.tgz ${APP_HOME}/pkg/
COPY start.sh /

RUN chown ${ADMIN_USER}:${ADMIN_GROUP} -R /home/${ADMIN_USER} \
    && chmod +x /start.sh

USER ${ADMIN_USER}
WORKDIR /home/${ADMIN_USER}
RUN echo "export PS1='\n\u@\[\e[31;40m\]\h\[\e[0m\]:\[\e[36;40m\]\w\[\e[0m\] $\n$'" >> /home/${ADMIN_USER}/.bashrc

ENTRYPOINT ["bash", "/start.sh"]