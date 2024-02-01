package labServer;

import Protocol.IService;
import Protocol.Listas.UnidadMedList;
import Protocol.Message;
import Protocol.Protocol;
import Protocol.TipoInstrumentoObj;
import Protocol.Calibraciones;
import Protocol.Instrumento;
import Protocol.UnidadMedida;

import javax.swing.*;
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
                        //--------------------------------------------------Unidad Medida--------------------------------------------------
                    case Protocol.READUNIDAD:
                        try{
                            System.out.println("Estoy en READunidad de worker");
                            UnidadMedList lisU = (UnidadMedList) in.readObject();
                            out.writeInt(Protocol.READUNIDAD);
                            out.writeObject(service.readUnidadesMedida(lisU));
                            System.out.println("Le envio de vuelta la lista al service del ciente ");
                            out.flush();

                            message = new Message( Message.READ, "UM", "Lisra Unidad");
                            srv.deliver(message);

                        }catch(Exception ex){
                            System.out.println("Catch del update tipo");
                            System.out.println(ex);
                            continuar = false;
                        }
                        break;
                    case Protocol.FINDIDUNIDAD:
                        try{
                            System.out.println("Estoy en FindUnidad de worker");
                            String tipoId = (String) in.readObject();

                            out.writeInt(Protocol.FINDIDUNIDAD);
                           // out.writeObject(service.delete(tipoId));
                            System.out.println("Le envio de vuelta el id encontrado ");
                            out.flush();

                            message = new Message( Message.DELETE, "CA", tipoId);
                            srv.deliver(message);

                        }catch(Exception ex){
                            System.out.println("Catch del update tipo");
                            continuar = false;
                        }
                        break;
                    //--------------------------------------------------TIPOS DE INSTRUMENTOS--------------------------------------------------

                    case Protocol.CREATETIPO:
                        try{
                            TipoInstrumentoObj e = (TipoInstrumentoObj) in.readObject();
                            service.create(e);
                            out.writeInt(Protocol.CREATETIPO);
                            out.flush();


                           message = new Message( Message.CREATE, "TI", e.getNombre());

                            srv.deliver(message);

                        }catch (Exception ex){
                            System.out.println("Catch del create tipos");
                            continuar = false;
                            JOptionPane.showMessageDialog(null, ex.getMessage());

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
                            //out.writeObject(service.update(e));
                            System.out.println("Ya le mande la vaina a service proxy de vuelta ");
                            out.flush();

                            message = new Message( Message.UPDATE, "TI", "Lista Tipos");
                            srv.deliver(message);

                        }catch(Exception ex){
                            System.out.println("Catch del update tipo");
                            JOptionPane.showMessageDialog(null, ex.getMessage());
                            continuar = false;
                        }
                        break;
                    case Protocol.DELETETIPO:
                        try{
                            System.out.println("Estoy en deleteTipo de worker");
                            String tipoId = (String) in.readObject();

                            out.writeInt(Protocol.DELETETIPO);
                            //out.writeObject(service.delete(tipoId));
                            System.out.println("Ya le mande la vaina a service proxy de vuelta ");
                            out.flush();

                            message = new Message( Message.DELETE, "TI", "Lista Tipos");
                            srv.deliver(message);

                        }catch(Exception ex){
                            System.out.println("Catch del update tipo");
                            continuar = false;
                        }
                        break;

                    //--------------------------------------------------INSTRUMENTOS--------------------------------------------------
                    case Protocol.CREATEINSTRUMENTO:
                        try{
                            System.out.println("Estoy en CREATE INSTRUMENTO de worker");
                            Instrumento e = (Instrumento) in.readObject();
                            service.create(e);
                            out.writeInt(Protocol.CREATEINSTRUMENTO);
                            out.flush();


                            message = new Message( Message.CREATE, "Ins", e.getDescripcion());

                            srv.deliver(message);

                        }catch (Exception ex){
                            System.out.println("Catch del create tipos");
                            continuar = false;

                        }
                        break;
                    case Protocol.READINSTRUMENTO:
                        try{
                            System.out.println("Estoy en readTipo de worker");
                            Instrumento ins = (Instrumento) in.readObject();

                            out.writeInt(Protocol.READINSTRUMENTO);
                            out.writeObject(service.read_instrumentos(ins)); // debe ser un read con name distinto
                            System.out.println("Ya le mande la vaina a service proxy de vuelta ");
                            out.flush();

                            message = new Message( Message.READ, "Ins", "Lista Instrumento");
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
                            //out.writeObject(service.update(e));
                            System.out.println("Ya le mande la vaina a service proxy de vuelta ");
                            out.flush();

                            message = new Message( Message.UPDATE, "Ins", e.getSerie());
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
                           // out.writeObject(service.deleteInstrumentoId(tipoId));
                            System.out.println("Ya le mande la vaina a service proxy de vuelta ");
                            out.flush();

                            message = new Message( Message.DELETE, "Ins", tipoId);
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

                    case Protocol.READCALIBRACION:
                        try{
                            System.out.println("Estoy en readCalibracion de worker");
                            //List<Calibraciones> lisT = (List<Calibraciones>) in.readObject();
                            Calibraciones cali = (Calibraciones) in.readObject();

                            out.writeInt(Protocol.READCALIBRACION);
                            out.writeObject(service.read(cali));
                            System.out.println("Ya le mande de vuelta la lista de calibraciones");
                            out.flush();

                            message = new Message( Message.READ, "CA", "Lista Calibracion");
                            srv.deliver(message);
                        }catch (Exception ex){
                            System.out.println("Catch del read tipo");
                            continuar = false;
                        }
                        break;
                    case Protocol.UPDATECALIBRACION:
                        try{
                            System.out.println("Estoy en updateCalibracion de worker");

                            Calibraciones e = (Calibraciones) in.readObject();


                            out.writeInt(Protocol.UPDATECALIBRACION);
                            //out.writeObject(service.update(e));
                            System.out.println("Le mando de vuelta al proxy calibracion ");
                            out.flush();

                            message = new Message( Message.UPDATE, "CA", String.valueOf(e.getNumeroCalibracion()));
                            srv.deliver(message);

                        }catch(Exception ex){
                            System.out.println("Catch del update tipo");
                            continuar = false;
                        }
                        break;
                    case Protocol.DELETECALIBRACION:
                        try{
                            System.out.println("Estoy en deleteCalibracion de worker");
                            String tipoId = (String) in.readObject();

                            out.writeInt(Protocol.DELETECALIBRACION);
                            //out.writeObject(service.delete(tipoId));
                            System.out.println("Le envio de vuelta el id eliminado ");
                            out.flush();

                            message = new Message( Message.DELETE, "CA", tipoId);
                            srv.deliver(message);

                        }catch(Exception ex){
                            System.out.println("Catch del update tipo");
                            continuar = false;
                        }
                        break;
                    case Protocol.SEND_LISTA_TIPO_INSTRUMENTOS:
                        try {
                            List<TipoInstrumentoObj> list = service.get_lista_tipo_instrumento();
                            srv.set_lista_candidatos_clientes(list);
                        } catch (Exception ex) {}
                        break;
                    case Protocol.SEND_TIPO_INSTRUMENTOS:
                        try {
                            System.out.println("Se tiene que agregar un candidato");
                            TipoInstrumentoObj obj = (TipoInstrumentoObj) in.readObject();
                            System.out.println(obj.getNombre());
                            service.agregar_tipo_instrumento(obj);
                            List<TipoInstrumentoObj> list = service.get_lista_tipo_instrumento();
                            srv.set_lista_candidatos_clientes(list);
                        } catch (Exception ex) {}
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


    public void set_lista_candidatos_clientes(List<TipoInstrumentoObj> list){
        try {
            out.writeInt(Protocol.INIT_LISTA_TIPO_INSTRUMENTOS);
            out.writeObject(list);
            out.flush();
        }catch (Exception ex){
            System.out.println("Excepcion: " + ex.getMessage());
        }
    }


}