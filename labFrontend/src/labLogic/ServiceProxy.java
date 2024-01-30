package labLogic;

import Protocol.*;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ServiceProxy implements IService {
    private static ServiceProxy theInstance;
    public static ServiceProxy instance() {
        if (theInstance == null){
            theInstance = new ServiceProxy();
        }
        return theInstance;
    }
    ObjectInputStream in; // obj entrada
    ObjectOutputStream out; // obj salida

    IDeliver deliver;
    IController controller;

    public void register() throws Exception {
        connect();
        out.writeInt(Protocol.REGISTER);//le estoy diciendo que hacer

        out.flush();//una vez transmito limpio y cierro
        int response = in.readInt();//automaticamente ahora espero la respuesta
        if (response==Protocol.ERROR_NO_ERROR){
            System.out.println("Voy a ejecutar start");
            this.start();//una vez nos coencatmos bien a todo
        }else{
            System.out.println("Error al conectar con el servidor");
        }
    }


    Socket skt;
    private void connect() throws Exception{
        skt = new Socket(Protocol.SERVER,Protocol.PORT);
        out = new ObjectOutputStream(skt.getOutputStream() );
        out.flush();//deja de transmitir, es como un break, quedaria abierto, limpia elruido dl socket
        in = new ObjectInputStream(skt.getInputStream());
    }

    private void disconnect() throws Exception{
        skt.shutdownOutput();//manda una señal de que la transmcion termino
        skt.close();
    }

    public void setIDeliver(IDeliver d){
        deliver = d;
    }
    public void setTController(IController c){controller = c;}
    //Listening funtions----------------------------------------------
    boolean continuar = true;
    public void start(){
        System.out.println("Client worker atendiendo peticiones...");
        Thread t = new Thread(new Runnable(){
            public void run(){
                listen();
            }//aca se encicla para que el hilo no se acaba
        });
        continuar = true;
        t.start();
    }
    public void stop(){
        continuar=false;
    }

    public void listen(){
        System.out.println("Ejecutando listen de front end");
        int method;
        while (continuar) {
            try {
                method = in.readInt();

                System.out.println("DELIVERY del listen del worker ");
                System.out.println("Operacion: "+method);

                switch(method){
                    case Protocol.DELIVER://en particular este solo hace el deliveri por que el cliente solo necesita escuhar esto
                        try {
                            System.out.println("Se entro al deliver en service proxy");
                            Message message=(Message)in.readObject();
                            System.out.println("Mensaje de message en Service Proxy: /Protocol deliver "+message.getMessage());
                            deliver(message);
                        } catch (ClassNotFoundException ex) {}
                        break;
                    case Protocol.ERROR_NO_ERROR:
                        System.out.println("Error_no_error");break;
                    case Protocol.READTIPO:
                        List<TipoInstrumentoObj> lis = (List<TipoInstrumentoObj>) in.readObject();
                        System.out.println("Me llego la lista perfectamente a Service!!\n");
                        break;
                    case Protocol.UPDATETIPO:
                        boolean p = (boolean) in.readObject();
                        System.out.println("Me llego la notificacion de que actualizo!!\n");
                        break;
                    case Protocol.DELETETIPO:
                        boolean del = (boolean) in.readObject();
                        System.out.println("Me llego la notificacion de que elimino!!\n");
                        break;

                    //--------------------------------------------------CALIBRACIONES--------------------------------------------------
                    case Protocol.CREATECALIBRACION:
                        System.out.println("La calibracion se creo con existo ");
                        Calibraciones cal = (Calibraciones) in.readObject();
                        break;
                }
                out.flush();
            } catch (IOException ex) {
                continuar = false;
                System.out.println("Se detuvo en el catch de listen de service proxy");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            disconnect();
        } catch (IOException e) {
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void deliver( final Message message ){
        SwingUtilities.invokeLater(new Runnable(){//crea un hilo temporal que se destrulle cuando termina
            // se cierran solos cuando termina de pocesar (no esta en un while)
               public void run(){
                   deliver.deliver(message);
               }
           }
        );
    }

    public void exit() throws Exception{ // como de deslogueo
        out.writeInt(Protocol.DISCONNECT);
        //out.writeObject(u);
        out.flush();
        System.out.println("le dije al server que se cerrara");
        this.stop();
        this.disconnect();
    }



    //-------------------------------------------------------CRUD-------------------------------------------------------

    //---------------------------------------------Tipos Instrumento----------------------------------------------

    @Override
    public void create(TipoInstrumentoObj tipo) throws Exception {
        out.writeInt(Protocol.CREATETIPO);
        out.writeObject(tipo);
        out.flush();
        System.out.println("Mande el mensaje de Crear a el server");

    }

    @Override
    public List<TipoInstrumentoObj> read(List<TipoInstrumentoObj> Listipo) throws Exception {
        out.writeInt(Protocol.READTIPO);
        out.writeObject(Listipo);
        out.flush();
        System.out.println("Le estoy pasando la lista de tipos a server desde serviceProxy");
        return Listipo;
    }

    @Override
    public boolean update(TipoInstrumentoObj tipo) throws Exception {
        out.writeInt(Protocol.UPDATETIPO);
        out.writeObject(tipo);
        out.flush();
        System.out.println("Mande el mensaje de update a el server");
        return false;
    }

    @Override
    public boolean delete(TipoInstrumentoObj tipo) throws Exception {
        out.writeInt(Protocol.DELETETIPO);
        out.writeObject(tipo);
        out.flush();
        System.out.println("Mande el mensaje de delete a el server");

        return false;
    }
    @Override
    public boolean delete(String tipoID) throws Exception {
        out.writeInt(Protocol.DELETETIPO);
        out.writeObject(tipoID);
        out.flush();
        System.out.println("Mande el mensaje de delete a el server");

        return false;
    }

    //------------------------------------------------Instrumentos------------------------------------------------

    @Override
    public void create(Instrumento instrumento) throws Exception {

    }

    @Override
    public Instrumento read(Instrumento instrumento) throws Exception {
        return null;
    }

    @Override
    public void update(Instrumento instrumento) throws Exception {

    }

    @Override
    public void delete(Instrumento instrumento) throws Exception {

    }
    //-----------------------------------------------Calibraciones------------------------------------------------
    @Override
    public boolean create(Calibraciones calibracion) throws Exception {
        out.writeInt(Protocol.CREATECALIBRACION);
        out.writeObject(calibracion);
        out.flush();
        System.out.println("Mande el mensaje de Crear a el server");
        return false;
    }

    @Override
    public Calibraciones read(Calibraciones calibracion) throws Exception {
        return null;
    }

    @Override
    public void update(Calibraciones calibracion) throws Exception {

    }

    @Override
    public void delete(Calibraciones calibracion) throws Exception {

    }

    //-------------------------------------------------Mediciones-------------------------------------------------
    @Override
    public void create(Mediciones medida) throws Exception {

    }


    public void delete(Mediciones medida) throws Exception {

    }


    public void update(Mediciones medida) throws Exception {

    }


    public Mediciones read(Mediciones medida) throws Exception {
        return null;
    }

    //--------------------------------------------Setters controllers---------------------------------------------


//    public void setTipoinscontroller(TiposInstrumentoController tipoinscontroller) {
//        this.tipoinscontroller = tipoinscontroller;
//    }
//
//    public void setInstrumentoController(Instrumentos_Controller instrumentoController) {
//        this.instrumentoController = instrumentoController;
//    }
//
//    public void setCalibracionesController(CalibracionesController calibracionesController) {
//        this.calibracionesController = calibracionesController;
//    }
}

