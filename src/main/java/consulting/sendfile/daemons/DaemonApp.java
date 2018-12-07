package consulting.sendfile.daemons;


import consulting.sendfile.config.Configuracion;
import consulting.sendfile.hilos.PoolProcesos;
import consulting.sendfile.servicios.EnvioDeArchivo;
import consulting.sendfile.servicios.EscucharRutaDirectorio;
import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class DaemonApp implements Daemon {

    private final static Logger log = Logger.getLogger(DaemonApp.class);
    private Configuracion _conf;
    EscucharRutaDirectorio _escuchar;
    PoolProcesos _poolProcesos;
    EnvioDeArchivo _envioDeArchivo;

    @Override
    public void init(DaemonContext context) throws DaemonInitException, Exception {

        PropertyConfigurator.configure(System.getProperty("log4j"));
        log.info("iniciando proceso...");
        log.info("se cargo el archivo de configuracion " + System.getProperty("log4j") + " correctamente...");
        _poolProcesos = PoolProcesos.newInstance();
        _conf = Configuracion.newInstance();
        _escuchar = EscucharRutaDirectorio.newInstance();

        _conf.setRutaArchivoXml(System.getProperty("config"));

        _envioDeArchivo = EnvioDeArchivo.newInstance().config()
            .setIP(_conf.getString("envio-archivo.ip"))
            .setPuerto(_conf.getInt("envio-archivo.puerto"))
            .setUsuario(_conf.getString("envio-archivo.user"))
            .setPassword(_conf.getString("envio-archivo.password"))
            .setTiempoEspera(_conf.getInt("envio-archivo.timesleep"))
        .build();

        log.info(String.format("se cargo el archivo %s", System.getProperty("config")));
        log.info(String.format("directorio a observar: %s", _conf.getString("observar")));

        _poolProcesos
            .agregarProceso(_escuchar.setRutaArchivo(_conf.getString("observar"))
            .setOnArcihvoCreado(_envioDeArchivo::enviarArchivo));
    }

    @Override
    public void start() throws Exception {
        _poolProcesos.iniciarProceso();
    }

    @Override
    public void stop() throws Exception {
        log.info("deteniendo el proceso...");
        _poolProcesos.detenerProceso();
    }

    @Override
    public void destroy() {
        _poolProcesos = null;
        _escuchar = null;
        _conf = null;
    }
}
