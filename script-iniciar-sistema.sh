#!/usr/bin/env bash

DIRNAME=$(dirname $0);
DEMON_HOME=$(cd $DIRNAME/..;pwd -LP);
PROCNAME=$(echo $DEMON_HOME | awk -F"/" '{print $NF}');

pid="$DEMON_HOME/var/run";

if [[ -f $pid/$PROCNAME.pid ]]; then
    rm -rf $pid/$PROCNAME.pid;
fi

$DEMON_HOME/bin/script-run.sh start;