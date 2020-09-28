#!/bin/bash
. $(dirname $0)/constants.sh

rm -rf $backup_path/$DEPLOYMENT_GROUP_NAME
rm -rf $code_deploy_path

i=0
while [ "$i" -lt 120 ]
do
  sleep 5
  pid=`lsof -t -i:$server_port`
  if [ "$pid" != "" ]
  then # Process is running
      echo "Process is running"
     exit 0
  fi
  i=$((i+5))
done

pid=`lsof -t -i:$server_port`
if [ "$pid" != "" ]
then # Process is running
    echo "Process is running"
   exit 0
else
	echo "Process does not exist(lsof -t -i:$server_port)"
  exit 1
fi
