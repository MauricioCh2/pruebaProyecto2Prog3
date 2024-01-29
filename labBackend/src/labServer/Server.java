package labServer;

//Protocolos---------------------------

import Protocol.IService;
import Protocol.Message;
import Protocol.Protocol;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server {
    ServerSocket srv;
    List<Worker> workers;

    public Server() {
        try {
            srv = new ServerSocket(Protocol.PORT);
            workers =  Collections.synchronizedList(new ArrayList<Worker>());
            System.out.println("Servidor iniciado..."); //el server se inicio
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    public void run(){
        IService service = new Service();
        boolean continuar = true;
        String sid;
        ObjectInputStream in;
        ObjectOutputStream out=null;
        Socket skt=null;
        while (continuar) {//se encicla a propocito hasta que alguien llega, es un cilco infinito adrede
            try {

                skt = srv.accept();//si entro algo y se acepto el socket tendra algo
                sid = skt.getRemoteSocketAddress().toString();//identificacion del socket


                in = new ObjectInputStream(skt.getInputStream());
                out = new ObjectOutputStream(skt.getOutputStream() );//limpiamos para que no se llenen de basura o ruido
                System.out.println("Conexion Establecida...");
                register(in,out, service);
                //User user=this.login(in,out,service);            //nuevo usuario
                //aqui hay que poner lo de register para que cada usuario tenga una identificacion pero no hace falta que el usuario lo sepa
               // Worker worker = new Worker(this,in,out,user, service); //crea nuevo worker
                Worker worker = new Worker(this,in,out, service); //crea nuevo worker
                workers.add(worker);             // lo agrego (aun no sirve)
                worker.start();                  //ahora si ya esta iniciando
                //out.writeObject(sid);
            }
            //catch (IOException | ClassNotFoundException ex) {}
            catch (Exception ex) {
                try {//los separo para gstionar ruido
                    out.writeInt(Protocol.ERROR_LOGIN);//
                    out.flush(); //limpiamos memoria
                    skt.close();//cerramos soket
                } catch (IOException ex1) {}
                System.out.println("Conexion cerrada...");// por si algo pasa
            }
        }
    }

    public void register(ObjectInputStream in,ObjectOutputStream out,IService service) throws Exception {
        int method = in.readInt();//lee int de la entada
        if (method!=Protocol.REGISTER) throw new Exception("Should login first");//rechaza cualquier cosa que no sea un uno, el servidor solo se encarga de dar access
        //
        //User user=(User)in.readObject();
        service.register();
        out.writeInt(Protocol.ERROR_NO_ERROR);//si si es loggin, el usuario no dio error ahora si devolvera usuario
        // simplemente es decirle al socke que transmita este objeto(usuario)
        out.flush();
    }
    public void remove(Worker w) {
        workers.remove(w);
        System.out.println("Quedan: " +
                workers.size());
    }

    //los sockets no saben lo que lo rodea, solo su padre (el server )
    public void deliver(Message message){ //el server le dice a sus workers entregen el mensaje
        for(Worker wk:workers){//NO TOCAR ESTO ESTO ES 100 POR CIEN NECESARIO, es oomo un brodcast
            wk.deliver(message);

        }
        //todos  propagan el mensaje y todos los conocen pero solo se ve reflejado en el usuario concreto
        //ejemplo, en el poyecto si un usuario elimino un tipo de instrumento otro usuario que esta n este le
        // deberia de notificar que fue modificado y preguntar si quiere seguir o aceptar los cambios

        //Una solucion es mandar a el resto cual protocolo fue ejecutado
        //asi se propaga de que forma tiene que actualizare


    } // hay que tenerle cuidado a esto

//    public void join(SocketObject as){
//        for(Worker w:workers){
//            if(w.os.sid.equals(as.sid)){
//                w.as=as;
//                break;
//            }
//        }
//    }

}
