package consulting.sendfile.config;

import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.log4j.Logger;


public class ConfiguracionImlp implements Configuracion {
    private final static Logger log = Logger.getLogger(ConfiguracionImlp.class);
    Configurations configs = new Configurations();
    XMLConfiguration config;

    @Override
    public void setRutaArchivoXml(String archivoXml) {
        try {
            config = configs.xml(archivoXml);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        }
    }

    @Override
    public String getString(String atrivuto) {
        return config.getString(atrivuto);
    }

    @Override
    public int getInt(String atrivuto) {
        return config.getInt(atrivuto);
    }
}
