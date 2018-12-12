package consulting.sendfile.servicios;

public interface EnvioDeArchivo {

    static EnvioDeArchivo newInstance() {
        return new EnvioDeArchivoImpl();
    }

    void enviarArchivo(String rutaArchivo);

    Build config();

    interface Build {

        Build setDirectorioTrabajo(String directorioTrabajo);

        Build setUsuario(String usuario);

        Build setPassword(String password);

        Build setIP(String ip);

        Build setPuerto(int puerto);

        Build setTiempoEspera(int tiempoEspera);

        EnvioDeArchivo build();
    }
}
