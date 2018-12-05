package consulting.sendfile.servicios;

import consulting.sendfile.hilos.Proceso;

import java.io.File;

public interface EscucharRutaDirectorio extends Proceso {

    static EscucharRutaDirectorio newInstance() {
        return new EscucharRutaDirectorioImpl();
    }

    EscucharRutaDirectorio setRutaArchivo(String rutaDirectorio);

    EscucharRutaDirectorio setOnArcihvoCreado(ArcihvoCreado arcihvoCreado);

    @FunctionalInterface
    interface ArcihvoCreado {
        void onArchivoCreado(String PathFile);
    }
}
