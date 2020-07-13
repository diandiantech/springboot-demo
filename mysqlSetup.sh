#!/bin/bash

CUR_HOME=$(cd $(dirname ${BASH_SOURCE[0]}); pwd)
TABLE_INIT_FILE=${CUR_HOME}/springboot-demo-dal/src/main/resources/sql/table-init.sql

mysql -h127.0.0.1 -P13306 -uspringboot-demo -pspringboot-demo -Dspringboot-demo < "${TABLE_INIT_FILE}"