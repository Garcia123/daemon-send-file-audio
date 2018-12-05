package consulting.sendfile.config;

public interface Configuracion {

    static Configuracion newInstance() {
        return new ConfiguracionImlp();
    }

    void setRutaArchivoXml(String archivoXml);

    String getString(String atrivuto);

    int getInt(String atrivuto);
}
