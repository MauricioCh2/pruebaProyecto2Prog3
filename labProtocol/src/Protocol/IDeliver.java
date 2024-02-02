package Protocol;

public interface IDeliver {
     void deliver(Message message, int numeroWorker);

     void set_numero_worker(int numeroWorker);

}
