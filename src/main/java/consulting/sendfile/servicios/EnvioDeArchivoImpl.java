package consulting.sendfile.servicios;


import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.util.regex.Pattern;

public class EnvioDeArchivoImpl implements EnvioDeArchivo {

    private final static Logger log = Logger.getLogger(EnvioDeArchivoImpl.class);

    private String usuario;
    private String password;
    private String ip;
    private int puerto;
    private int tiempoEspera;

    @Override
    public void enviarArchivo(String rutaArchivo) {
        log.info(String.format("se creo el archivo %s", rutaArchivo));
        FTPClient ftpClient = new FTPClient();
        try {

            log.info(String.format("conectando al servicio ftp=%s", toString()));

            ftpClient.connect(InetAddress.getByName(ip));
            ftpClient.login(usuario, password);
            int reply = ftpClient.getReplyCode();
            if (FTPReply.isPositiveCompletion(reply)) {
                //ftpClient.changeWorkingDirectory("/cdrdata/incoming_parser");
                log.info("conexion ftp satisfactoria...");
                Thread.sleep(tiempoEspera);
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                File f = new File(rutaArchivo);
                BufferedInputStream buffIn = new BufferedInputStream(new FileInputStream(rutaArchivo));
                ftpClient.enterLocalPassiveMode();
                //ftpClient.storeFile(f.getName(), buffIn);
                ftpClient.storeFile("stethoscope.wav", buffIn);
                log.info(String.format("archivo %s enviado correctamente..",rutaArchivo));
            } else {
                log.error("No se pudo conectar al servicio");
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public Build config() {
        return new EnvioDeArchivoImplBuild(this);
    }

    @Override
    public String toString() {
        return "{" +
                "usuario='" + usuario + '\'' +
                ", password='" + password + '\'' +
                ", ip='" + ip + '\'' +
                ", puerto=" + puerto +
                '}';
    }

    public class EnvioDeArchivoImplBuild implements Build {
        EnvioDeArchivoImpl envArc;

        public EnvioDeArchivoImplBuild(EnvioDeArchivoImpl envArc) {
            this.envArc = envArc;
        }

        @Override
        public Build setUsuario(String usuario) {
            envArc.usuario = usuario;
            return this;
        }

        @Override
        public Build setPassword(String password) {
            envArc.password = password;
            return this;
        }

        @Override
        public Build setIP(String ip) {
            envArc.ip = ip;
            return this;
        }

        @Override
        public Build setPuerto(int puerto) {
            envArc.puerto = puerto;
            return this;
        }

        @Override
        public Build setTiempoEspera(int tiempoEspera){
            envArc.tiempoEspera = tiempoEspera;
            return this;
        }

        @Override
        public EnvioDeArchivo build() {
            return envArc;
        }
    }
}
