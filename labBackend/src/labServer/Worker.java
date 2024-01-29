package labServer;

import Protocol.IService;
import Protocol.Message;
import Protocol.Protocol;
import Protocol.TipoInstrumentoObj;
import Protocol.SocketObject;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class Worker { // es cada socket
    Server srv; // quien es su server
    ObjectInputStream in; // por donde transmte
    ObjectOutputStream out; // por quien transmite
    IService service;
    SocketObject os; //Synchronous socket
    SocketObject as;
    boolean continuar; //importante para saber cuando termina el servicio de este socket
    public Worker(Server srv, ObjectInputStream in, ObjectOutputStream out, IService service) {
        this.srv=srv;
        this.in=in;//creo que esto no hace falta
        this.out=out;
        this.service=service;
    }
    public void start(){
        try {
            System.out.println("Worker atendiendo peticiones...");
            Thread t = new Thread(new Runnable(){
                public void run(){
                    listen();
                }// este socke va aestar escuchando a estar escuchando
            });
            continuar = true; // no hay errors asi que el ciclo debe enciclarse
            t.start(); // e proceso del hilo empieza
        } catch (Exception ex) {
        }
    }
    public void stop() {
        continuar=false;
        System.out.println("Conexion cerrada...");
    }

    public void listen(){
        System.out.println("Ejecuntando listen de backend");
        int method;
        while (continuar) {
            try{
                method = in.readInt();// agarra el int del socket
                System.out.println("Operacion: "+method);// imprime recivi tal operacion
                switch(method) {
                    case Protocol.DISCONNECT:
                        try {
                            this.stop();
                            srv.remove(this);
                        } catch (Exception ex) {
                        }
                        break;
                    case Protocol.CREATETIPO:
                        Message message = null;
                        try{
                            TipoInstrumentoObj e = (TipoInstrumentoObj) in.readObject();
                            service.create(e);
                            //out.writeInt(Protocol.ERROR_NO_ERROR);
                            out.flush();
                            //srv.deliver("mensaje");
                            //srv.deliver(  message = new Message( Message.CREATE, "TI", e.getNombre()));
                            //System.out.println("Se acaba de crear un uusario: "+ message.getMessage());
                        }catch (Exception ex){
                            System.out.println("erorr no error de excepcion");
                            out.writeInt(Protocol.ERROR_ERROR);
                            out.flush();
                        }
                        break;

                }
                out.flush();
            }catch (IOException  ex) {
                srv.remove(this);
                System.out.println(ex);
                continuar = false;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        //aca basicamente estara todo
    }
    public void deliver(String message){
        try {
            System.out.println("Se entro al deliver");
            out.writeInt(Protocol.DELIVER);//oiga estoyenviando un deliver
            out.writeObject(message);
            //out.flush();
            //aqui entrega solo a su propio cliente sin tener que propagar a todos
        } catch (IOException ex) {
        }
    }
}