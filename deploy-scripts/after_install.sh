#!/bin/bash
set -euo pipefail
. $(dirname $0)/constants.sh
#Copy source to our depoyment group folder.
cp -rp $code_deploy_path $source_path/$DEPLOYMENT_GROUP_NAME
#Copy all the gitignored files(This is basically conf and related files)
cd $source_path/$DEPLOYMENT_GROUP_NAME
#get required modules for the app.
npm install
#node generate.config.script.js $DEPLOYMENT_GROUP_NAME
npm run build
sudo forever-service install $application_name-$DEPLOYMENT_GROUP_NAME --script app.js