package consulting.sendfile.hilos;

public interface PoolProcesos {

    static PoolProcesos newInstance() {
        return new PoolProcesosImpl();
    }

    void agregarProceso(Proceso proceso);

    void iniciarProceso();

    void detenerProceso();

}
