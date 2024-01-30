package labServer;

import Protocol.IService;
import Protocol.Message;
import Protocol.Protocol;
import Protocol.TipoInstrumentoObj;
import Protocol.Calibraciones;
import Protocol.Instrumento;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;


public class Worker { // es cada socket
    Server srv; // quien es su server
    ObjectInputStream in; // por donde transmte
    ObjectOutputStream out; // por quien transmite
    IService service;

    boolean continuar; //importante para saber cuando termina el servicio de este socket
    public Worker(Server srv, ObjectInputStream in, ObjectOutputStream out, IService service) {
        this.srv=srv;
        this.in=in;
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
        Message message = new Message();
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
                        try{
                            TipoInstrumentoObj e = (TipoInstrumentoObj) in.readObject();
                            service.create(e);
                            out.writeInt(Protocol.CREATETIPO);
                            out.flush();

                            //System.out.println("Envio error no error en CreateTipo de listen del worker");
                            //System.out.println("se ejecuta error no error\n");

                           message = new Message( Message.CREATE, "TI", e.getNombre());
                         //   System.out.println("voy a entrar al deliver en worker\n");
                         //   System.out.println(message.getMessage());
                            srv.deliver(message);
                           // srv.update();
                        }catch (Exception ex){
                            System.out.println("Catch del create tipos");
                            continuar = false;
                            //out.writeInt(Protocol.ERROR_ERROR);
                           // out.flush();
                        }
                        break;
                    case Protocol.READTIPO:
                        try{
                            System.out.println("Estoy en readTipo de worker");
                            List<TipoInstrumentoObj> lisT = (List<TipoInstrumentoObj>) in.readObject();

                            out.writeInt(Protocol.READTIPO);
                            out.writeObject(service.read(lisT));
                            System.out.println("Ya le mande la vaina a service proxy de vuelta ");
                            out.flush();

                            message = new Message( Message.READ, "TI", "Lista Tipos");
                            srv.deliver(message);
                        }catch (Exception ex){
                            System.out.println("Catch del read tipo");
                            continuar = false;
                        }
                        break;
                    case Protocol.UPDATETIPO:
                        try{
                            System.out.println("Estoy en updateTipo de worker");

                            TipoInstrumentoObj e = (TipoInstrumentoObj) in.readObject();


                            out.writeInt(Protocol.UPDATETIPO);
                            out.writeObject(service.update(e));
                            System.out.println("Ya le mande la vaina a service proxy de vuelta ");
                            out.flush();

                            message = new Message( Message.UPDATE, "TI", "Lista Tipos");
                            srv.deliver(message);

                        }catch(Exception ex){
                            System.out.println("Catch del update tipo");
                            continuar = false;
                        }
                        break;
                    case Protocol.DELETETIPO:
                        try{
                            System.out.println("Estoy en deleteTipo de worker");
                            String tipoId = (String) in.readObject();

                            out.writeInt(Protocol.DELETETIPO);
                            out.writeObject(service.delete(tipoId));
                            System.out.println("Ya le mande la vaina a service proxy de vuelta ");
                            out.flush();

                            message = new Message( Message.DELETE, "TI", "Lista Tipos");
                            srv.deliver(message);

                        }catch(Exception ex){
                            System.out.println("Catch del update tipo");
                            continuar = false;
                        }
                        break;
                    case Protocol.CREATEINSTRUMENTO:
                        try{
                            Instrumento e = (Instrumento) in.readObject();
                            service.create(e);
                            out.writeInt(Protocol.CREATEINSTRUMENTO);
                            out.flush();

                            //System.out.println("Envio error no error en CreateTipo de listen del worker");
                            //System.out.println("se ejecuta error no error\n");

                            message = new Message( Message.CREATE, "TI", e.getDescripcion());
                            //   System.out.println("voy a entrar al deliver en worker\n");
                            //   System.out.println(message.getMessage());
                            srv.deliver(message);
                            // srv.update();
                        }catch (Exception ex){
                            System.out.println("Catch del create tipos");
                            continuar = false;
                            //out.writeInt(Protocol.ERROR_ERROR);
                            // out.flush();
                        }
                        break;
                    case Protocol.READINSTRUMENTO:
                        try{
                            System.out.println("Estoy en readTipo de worker");
                            List<Instrumento> lisT = (List<Instrumento>) in.readObject();

                            out.writeInt(Protocol.READINSTRUMENTO);
                            out.writeObject(service.read_instrumentos(lisT)); // debe ser un read con name distinto
                            System.out.println("Ya le mande la vaina a service proxy de vuelta ");
                            out.flush();

                            message = new Message( Message.READ, "TI", "Lista Instrumento");
                            srv.deliver(message);
                        }catch (Exception ex){
                            System.out.println("Catch del read instrumento");
                            continuar = false;
                        }
                        break;
                    case Protocol.UPDATEINSTRUMENTO:
                        try{
                            System.out.println("Estoy en update Instrumento de worker");

                            Instrumento e = (Instrumento) in.readObject();


                            out.writeInt(Protocol.UPDATEINSTRUMENTO);
                            out.writeObject(service.update(e));
                            System.out.println("Ya le mande la vaina a service proxy de vuelta ");
                            out.flush();

                            message = new Message( Message.UPDATE, "TI", "Lista Instrumentos");
                            srv.deliver(message);

                        }catch(Exception ex){
                            System.out.println("Catch del update instrumentos");
                            continuar = false;
                        }
                        break;
                    case Protocol.DELETEINSTRUMENTO:
                        try{
                            System.out.println("Estoy en deleteTipo de worker");
                            String tipoId = (String) in.readObject();

                            out.writeInt(Protocol.DELETEINSTRUMENTO);
                            out.writeObject(service.delete_instrumento_id(tipoId));
                            System.out.println("Ya le mande la vaina a service proxy de vuelta ");
                            out.flush();

                            message = new Message( Message.DELETE, "TI", "Lista instrumentos");
                            srv.deliver(message);

                        }catch(Exception ex){
                            System.out.println("Catch del delete instrumentos");
                            continuar = false;
                        }
                        break;
                    //--------------------------------------------------INSTRUMENTOS--------------------------------------------------
                    case Protocol.CREATEINSTRUMENTO:
                        try{
                            Instrumento e = (Instrumento) in.readObject();
                            service.create(e);
                            out.writeInt(Protocol.CREATEINSTRUMENTO);
                            out.flush();

                            //System.out.println("Envio error no error en CreateTipo de listen del worker");
                            //System.out.println("se ejecuta error no error\n");

                            message = new Message( Message.CREATE, "TI", e.getDescripcion());
                            //   System.out.println("voy a entrar al deliver en worker\n");
                            //   System.out.println(message.getMessage());
                            srv.deliver(message);
                            // srv.update();
                        }catch (Exception ex){
                            System.out.println("Catch del create tipos");
                            continuar = false;
                            //out.writeInt(Protocol.ERROR_ERROR);
                            // out.flush();
                        }
                        break;
                    case Protocol.READINSTRUMENTO:
                        try{
                            System.out.println("Estoy en readTipo de worker");
                            List<Instrumento> lisT = (List<Instrumento>) in.readObject();

                            out.writeInt(Protocol.READINSTRUMENTO);
                            out.writeObject(service.read_instrumentos(lisT)); // debe ser un read con name distinto
                            System.out.println("Ya le mande la vaina a service proxy de vuelta ");
                            out.flush();

                            message = new Message( Message.READ, "TI", "Lista Instrumento");
                            srv.deliver(message);
                        }catch (Exception ex){
                            System.out.println("Catch del read instrumento");
                            continuar = false;
                        }
                        break;
                    case Protocol.UPDATEINSTRUMENTO:
                        try{
                            System.out.println("Estoy en update Instrumento de worker");

                            Instrumento e = (Instrumento) in.readObject();


                            out.writeInt(Protocol.UPDATEINSTRUMENTO);
                            out.writeObject(service.update(e));
                            System.out.println("Ya le mande la vaina a service proxy de vuelta ");
                            out.flush();

                            message = new Message( Message.UPDATE, "TI", "Lista Instrumentos");
                            srv.deliver(message);

                        }catch(Exception ex){
                            System.out.println("Catch del update instrumentos");
                            continuar = false;
                        }
                        break;
                    case Protocol.DELETEINSTRUMENTO:
                        try{
                            System.out.println("Estoy en deleteTipo de worker");
                            String tipoId = (String) in.readObject();

                            out.writeInt(Protocol.DELETEINSTRUMENTO);
                            out.writeObject(service.delete_instrumento_id(tipoId));
                            System.out.println("Ya le mande la vaina a service proxy de vuelta ");
                            out.flush();

                            message = new Message( Message.DELETE, "TI", "Lista instrumentos");
                            srv.deliver(message);

                        }catch(Exception ex){
                            System.out.println("Catch del delete instrumentos");
                            continuar = false;
                        }
                        break;
                    //--------------------------------------------------CALIBRACIONES--------------------------------------------------
                    case Protocol.CREATECALIBRACION:
                        System.out.println("Estoy en CREATE CALIBRACIONES de worker");
                        Calibraciones cal = (Calibraciones) in.readObject();
                        service.create(cal);

                        out.writeInt(Protocol.CREATECALIBRACION);
                        out.writeObject(cal);

                        out.flush();

                        message = new Message( Message.CREATE, "CA", String.valueOf(cal.getNumeroCalibracion()));
                        srv.deliver(message);
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
    public void deliver(Message message){
        try {
            System.out.println("Se entro al deliver del worker");
            out.writeInt(Protocol.DELIVER);//oiga estoyenviando un deliver
            out.writeObject(message);
            out.flush();
            //aqui entrega solo a su propio cliente sin tener que propagar a todos
        } catch (IOException ex) {
        }
    }


}