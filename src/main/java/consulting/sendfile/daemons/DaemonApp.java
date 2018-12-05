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
        PropertyConfigurator.configure(System.getProperty("configSendFile"));
        log.info("se guardo cargo el archivo de configuracion correctamente...");
        _poolProcesos = PoolProcesos.newInstance();
        _conf = Configuracion.newInstance();
        _escuchar = EscucharRutaDirectorio.newInstance();

        _conf.setRutaArchivoXml(System.getProperty("pathConfigXml"));
        _envioDeArchivo = EnvioDeArchivo.newInstance();

        _poolProcesos
            .agregarProceso(_escuchar.setRutaArchivo(_conf.getString("observe"))
            .setOnArcihvoCreado(_envioDeArchivo::enviarArchivo));

    }

    @Override
    public void start() throws Exception {
        _poolProcesos.iniciarProceso();
    }

    @Override
    public void stop() throws Exception {

    }

    @Override
    public void destroy() {

    }
}
