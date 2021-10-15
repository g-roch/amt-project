#!/bin/bash
# Author: Gaby Roch

#
#   extract branch stable in temporary directory and
#   push it in production server
#
# Local dependencies: 
#   git
#   rsync
# Remote dependencies:
#   rsync
# default-jre-headless


# sudo apt install default-jre-headless

set -e

HOST=amt-pianorgue
BRANCH=stable

if [ ! -z "$1" -a "$1" = "github" ]; then 
  mkdir ~/.ssh 
  cp script/ssh_config ~/.ssh/config
  cp script/ssh_known_hosts ~/.ssh/known_hosts
  echo "$AMT_DMZ_PIANORGUE_PEM" > ~/.ssh/AMT-DMZ-PIANORGUE.pem
  echo "$AMT_PIANORGUE_PEM" > ~/.ssh/AMT-PIANORGUE.pem
  chmod go= ~/.ssh/AMT-DMZ-PIANORGUE.pem
  chmod go= ~/.ssh/AMT-PIANORGUE.pem
  chmod go= ~/.ssh/
fi

#pushd application/
#mvn clean package
#popd

ls -la application/target/

# Synchronise avec le serveur
#rsync --delete -Cav application/target/AMT-Test-*.jar "$HOST":pianorgue
scp application/target/AMT-Test-*.jar "$HOST":pianorgue

ssh "$HOST" sudo systemctl restart pianorgue.service

# Clean
rm -vfr ~/.ssh


