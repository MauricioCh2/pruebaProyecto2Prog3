package labLogic;

import Protocol.*;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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
    //Controllers //estos son necesarios para las comunicaciones con el forntEnd
//    TiposInstrumentoController tipoinscontroller;
//    Instrumentos_Controller instrumentoController;
//    CalibracionesController calibracionesController;

    public void register() throws Exception {
        connect();
        out.writeInt(Protocol.REGISTER);//le estoy diciendo que hacer
        //out.writeObject(null);
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
        int object;
        int method;
        System.out.println(continuar + " switch de listen en front end");
        while (continuar) {
            System.out.println("----------------------------------------------------------------------------------");
            try {
                object = in.readInt();
                System.out.println(object);
                method = (Integer)object;
                System.out.println("DELIVERY");
                System.out.println("Operacion: "+method);
                switch(method){
                    case Protocol.DELIVER://en particular este solo hace el deliveri por que el cliente solo necesita escuhar esto
                        try {
                            System.out.println("Se entro al deliver en service proxy");
                            String message=(String) in.readObject();
                            //deliver(message);
                        } catch (ClassNotFoundException ex) {}
                        break;
                    case Protocol.ERROR_NO_ERROR:
                        System.out.println("Error_no_error");break;
                }
                out.flush();
            } catch (IOException ex) {
                continuar = false;
                System.out.println("Se detuvo");
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
                                       //se cierran solos cuando termina de pocesar (no esta en un while)
                                       public void run(){
                                           //.deliver(message);
                                       }
                                   }
        );
    }




    //-------------------------------------------------------CRUD-------------------------------------------------------

    //---------------------------------------------Tipos Instrumento----------------------------------------------

    @Override
    public void create(TipoInstrumentoObj tipo) throws Exception {
        out.writeInt(Protocol.CREATETIPO);
        out.writeObject(tipo);
        out.flush();
        if(in.readInt()==Protocol.ERROR_NO_ERROR){
            System.out.println("Pase por proxy \n");
        }
        else throw new Exception("TIPO INSTRUMENTO DUPLICADO");
        System.out.println("Termine de crear");
    }

    @Override
    public TipoInstrumentoObj read(TipoInstrumentoObj e) throws Exception {
        return null;
    }

    @Override
    public void update(TipoInstrumentoObj e) throws Exception {

    }

    @Override
    public void delete(TipoInstrumentoObj e) throws Exception {

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
    public void create(Calibraciones calibracion) throws Exception {

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

