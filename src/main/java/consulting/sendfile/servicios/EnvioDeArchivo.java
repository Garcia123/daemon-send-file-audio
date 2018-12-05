package consulting.sendfile.servicios;

public interface EnvioDeArchivo {

    static EnvioDeArchivo newInstance() {
        return null;
    }

    void enviarArchivo(String rutaArchivo);

    Build config();

    interface Build {
        Build setUsuario();

        Build setPassword();

        Build setIP();

        Build setPuerto();

        EnvioDeArchivo build();
    }
}
