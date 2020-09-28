#!/bin/bash
# Application constants
application_name="spring-demo" # application-name specified in build.sbt
code_deploy_path="/home/ubuntu/$application_name-code-deploy-source"
source_path="/home/ubuntu/$application_name-source"
backup_path="/home/ubuntu/$application_name-backup"
#---------------------------------------------------------------#
case $DEPLOYMENT_GROUP_NAME in
  dev)
    server_port=8080
      ;;
  staging)
    server_port=9090
      ;;
  production)
    server_port=8080
      ;;
esac
