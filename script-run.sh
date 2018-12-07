#!/bin/bash

source /lib/lsb/init-functions;

DIRNAME=$(dirname $0);
DEMON_HOME=$(cd $DIRNAME/..;pwd -LP);
PROCNAME=$(echo $DEMON_HOME | awk -F"/" '{print $NF}');

respuesta=0;

lib="$DEMON_HOME/lib";
etc="$DEMON_HOME/etc";
log="$DEMON_HOME/var/log";
pid="$DEMON_HOME/var/run";

dlog4j="$etc/log4j.properties";
dconfig="$etc/send-file-audio.xml";
drutalog="$log/$PROCNAME.log";

exe="$(which jsvc)";
name="";

USER="root";

JAVA_HOME="/usr/lib/jvm/jdk-8-oracle-arm32-vfp-hflt";
CLASS_PATH="$lib/daemons-send-file-audio-0.1.0.jar:$lib/*";
CLASS="consulting.sendfile.daemons.DaemonApp";
ARGS="";

function jvsc_exec(){
  local flag="$1";
  $exe -procname $PROCNAME $flag -Dlog4j=$dlog4j -Dconfig=$dconfig -Drutalog=$drutalog -cp $CLASS_PATH -java-home $JAVA_HOME -user $USER -pidfile $pid/$PROCNAME.pid -outfile $log/$PROCNAME.out -errfile $log/$PROCNAME.err $CLASS $ARGS;
  echo $?;
}

function iniciando_proceso(){

  if [[ -f "$pid/$PROCNAME.pid" ]]; then

    local mens=$"se encontro un archivo pid: ";
    log_failure_msg "$mens";
    respuesta=1;

  else

    respuesta=$(jvsc_exec);

    if [[ $respuesta == 1 ]]; then

      local mens=$"se encontro problemas al iniciar: ";
      log_failure_msg "$mens";
      respuesta=1;
    else

      local mens=$"iniciando servicio $PROCNAME: ";
      log_success_msg "$mens";

    fi

  fi

}

function deteniendo_proceso(){

  local ret=$(jvsc_exec "-stop");

  if [[ $ret -eq 0 ]]; then
    log_success_msg "se tenio el proceso $PROCNAME..";
  else
    log_failure_msg "hubo un error al detener el proceso $PROCNAME.";
    respuesta=1;
  fi
}

function estado_proceso(){
  status_of_proc -p $pid/$PROCNAME.pid $PROCNAME;
  respuesta=$?;
}

function main(){

  local flag="$1";

  case $flag in
    start )
      iniciando_proceso;
    ;;
    stop )
      deteniendo_proceso
    ;;
    status )
        estado_proceso
    ;;
    * )
        echo "usar: $DEMON_HOME/bin/script-run.sh {start|stop|status}" >&2;
      ;;
  esac
}

main $1;

exit $respuesta;