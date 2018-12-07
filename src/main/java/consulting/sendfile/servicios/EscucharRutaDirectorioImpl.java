package consulting.sendfile.servicios;


import org.apache.log4j.Logger;

import java.nio.file.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.file.StandardWatchEventKinds.*;

public class EscucharRutaDirectorioImpl implements EscucharRutaDirectorio {

    private final static Logger log = Logger.getLogger(EscucharRutaDirectorioImpl.class);

    private boolean stop = false;
    ArcihvoCreado arcihvoCreado;
    WatchService watcher;
    String rutaDirectorio;
    Path dir;

    public EscucharRutaDirectorioImpl() {
    }

    @Override
    public EscucharRutaDirectorio setRutaArchivo(String rutaDirectorio) {
        this.rutaDirectorio = rutaDirectorio;
        dir = Paths.get(this.rutaDirectorio);
        return this;
    }

    @Override
    public EscucharRutaDirectorio setOnArcihvoCreado(ArcihvoCreado arcihvoCreado) {
        this.arcihvoCreado = arcihvoCreado;
        return this;
    }

    @Override
    public void detener() {
        setStop(true);
        try {
            watcher.close();
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        }
    }

    @Override
    public void run() {
        try {
            watcher = FileSystems.getDefault().newWatchService();
            dir.register(watcher, ENTRY_CREATE);

            while (!isStop()) {
                Thread.sleep(1000);
                WatchKey key = watcher.take();
                key.pollEvents().stream().forEach(this::procesarEvento);
                boolean valid = key.reset();
                if (!valid) {
                    break;
                }
            }

        } catch (Exception e) {
            log.info(e.getMessage(), e);
        }
    }

    private void procesarEvento(WatchEvent<?> watchEvent) {
        WatchEvent.Kind<?> tipoEvento = watchEvent.kind();
        if (tipoEvento == ENTRY_CREATE) {
            Path fileName = (Path) watchEvent.context();
            if (isWav(fileName.toString())) {
                arcihvoCreado.onArchivoCreado(rutaDirectorio + "/" + fileName.toString());
            }
        }
    }

    private boolean isWav(String nombreArchivo) {
        String regex = ".wav$";
        Pattern patron = Pattern.compile(regex);
        Matcher emparejador = patron.matcher(nombreArchivo);
        return emparejador.find();
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean value) {
        this.stop = true;
    }
}
