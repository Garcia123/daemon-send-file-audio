package consulting.sendfile.hilos;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PoolProcesosImpl implements PoolProcesos {

    List<Proceso> procesos = new ArrayList<>();
    ExecutorService executor;

    @Override
    public void agregarProceso(Proceso proceso) {
        procesos.add(proceso);
    }

    @Override
    public void iniciarProceso() {
        executor = Executors.newFixedThreadPool(procesos.size());
        procesos.stream().forEach(p -> executor.execute(p));
    }

    @Override
    public void detenerProceso() {
        executor.shutdown();
        procesos.stream().forEach(p -> p.detener());
    }
}
