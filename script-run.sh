#!bin/bash

source /lib/lsb/init-functions;

DIRNAME=$(dirname $0);
DEMON_HOME=$(cd $DIRNAME/..;pwd -LP);
PROCNAME=$(echo $DEMON_HOME | awk -F"/" '{print $NF}');

respuesta=0;

lib="$DEMON_HOME/lib";
etc="$DEMON_HOME/etc";
log="$DEMON_HOME/var/log";
pid="$DEMON_HOME/var/run";
exe="/usr/bin/jsvc";
name="";
