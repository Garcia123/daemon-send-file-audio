Diseño de aplicacion
==================
Se montrara la arquitectura de paquetes y el diagrama de clases.

Arquitectura de paquetes
==================
<img src="recursos/arquitectura-paquetes.png" width="80%">

Diagrama de clases
==================
<img src="recursos/diagrama-de-clases.png" width="80%">

Manual de operaciones
==================
Este manual tiene como objetivo, mostrar la arquitectura de carpetas y como solucionar los problemas que puedan ocurrir

## ingresar al sistemEP
Se tiene que ingresar por el protocolo ssh. Antes de ingresar se debe configurar la red. Por otro lado, recuerda hacer el "sudo su" despues de ingresar.

Ejemplo
```
ssh pi@192.168.100.14
```
<img src="recursos/ingresar-sistemep.png" width="100%">

## Arquitectura de carpetas.
Este punto muestra como esta clasificado las carpetas en el sistemaEP. <br>
<b>bin:</b> script para ejecutar.<br>
<b>etc:</b> archivos de configuracion. <br>
<b>lib:</b> todas las librerias que se están utilisando. <br>
<b>var:</b> directorio donde esta el log y run. <br>
<b>var/log:</b> aqui se guarda los log del demonio. <br>
<b>var/run:</b> aqui se guarda el fichero pid del demonio. <br>

```
root@sistemep2:/opt# tree daemon-send-file-audio/
daemon-send-file-audio/
├── bin
│   ├── script-iniciar-sistema.sh
│   └── script-run.sh
├── etc
│   ├── log4j.properties
│   └── send-file-audio.xml
├── lib
│   ├── aspectjrt-1.8.4.jar
│   ├── commons-beanutils-1.9.3.jar
│   ├── commons-collections-3.2.2.jar
│   ├── commons-configuration2-2.3.jar
│   ├── commons-daemon-1.1.0.jar
│   ├── commons-lang3-3.7.jar
│   ├── commons-logging-1.2.jar
│   ├── commons-net-3.6.jar
│   ├── daemons-send-file-audio-0.1.0.jar
│   ├── jcabi-aspects-0.22.jar
│   ├── jcabi-log-0.17.2.jar
│   ├── log4j-1.2.17.jar
│   ├── slf4j-api-1.7.5.jar
│   └── validation-api-1.1.0.Final.jar
└── var
    ├── log
    │   ├── daemon-send-file-audio.err
    │   ├── daemon-send-file-audio.log
    │   └── daemon-send-file-audio.out
    └── run
        └── daemon-send-file-audio.pid
```
## iniciar el proceso
se tiene que ejecutar el siguiente fichero <b> script-run.sh </b>. Ejemplo:
```
root@sistemep2:/opt# /opt/daemon-send-file-audio/bin/script-run.sh start
[ ok ] iniciando servicio daemon-send-file-audio:.
root@sistemep2:/opt# 
```
## para el proceso
```
root@sistemep2:/opt# /opt/daemon-send-file-audio/bin/script-run.sh stop
[ ok ] se tenio el proceso daemon-send-file-audio...
root@sistemep2:/opt# 
```
## para ver el estado del proceso
```
root@sistemep2:/opt# /opt/daemon-send-file-audio/bin/script-run.sh status
[ ok ] is running.
```
## para ver el log del demonio
```
root@sistemep2:/opt# tail -f daemon-send-file-audio/var/log/daemon-send-file-audio.log 
2018-12-11 23:23:44.053 [INFO ]: deteniendo el proceso...
2018-12-11 23:23:55.674 [INFO ]: iniciando proceso...
2018-12-11 23:23:55.686 [INFO ]: se cargo el archivo de configuracion /opt/daemon-send-file-audio/etc/log4j.properties correctamente...
2018-12-11 23:23:57.007 [INFO ]: se cargo el archivo /opt/daemon-send-file-audio/etc/send-file-audio.xml
2018-12-11 23:23:57.010 [INFO ]: directorio a observar: /home/pi/sistemep/ui/dist/assets/records
2018-12-11 23:33:56.320 [INFO ]: deteniendo el proceso...
2018-12-11 23:35:46.553 [INFO ]: iniciando proceso...
2018-12-11 23:35:46.565 [INFO ]: se cargo el archivo de configuracion /opt/daemon-send-file-audio/etc/log4j.properties correctamente...
2018-12-11 23:35:47.823 [INFO ]: se cargo el archivo /opt/daemon-send-file-audio/etc/send-file-audio.xml
2018-12-11 23:35:47.826 [INFO ]: directorio a observar: /home/pi/sistemep/ui/dist/assets/records
```