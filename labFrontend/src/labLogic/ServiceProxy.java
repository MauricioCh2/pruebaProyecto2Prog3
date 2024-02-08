package labLogic;

import Protocol.*;
import labPresentation.Controller.MainController;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ServiceProxy implements IService {
    private static ServiceProxy theInstance;

    public static ServiceProxy instance() {
        if (theInstance == null) {
            theInstance = new ServiceProxy();
        }
        return theInstance;
    }
    ObjectInputStream in; // obj entrada
    ObjectOutputStream out; // obj salida

    IDeliver deliver;
    IController controllerTipo;
    IController controllerInst;
    IController controllerCal;
    IController controllerMed;
    MainController controller;


    public void register() throws Exception {
        connect();
        out.writeInt(Protocol.REGISTER);//le estoy diciendo que hacer

        out.flush();//una vez transmito limpio y cierro
        int response = in.readInt();//automaticamente ahora espero la respuesta
        if (response == Protocol.ERROR_NO_ERROR) {
            System.out.println("Voy a ejecutar start");
            this.start();//una vez nos coencatmos bien a todo
        } else {
            System.out.println("Error al conectar con el servidor");
        }
    }


    Socket skt;

    private void connect() throws Exception {
        skt = new Socket(Protocol.SERVER, Protocol.PORT);
        out = new ObjectOutputStream(skt.getOutputStream());
        out.flush();//deja de transmitir, es como un break, quedaria abierto, limpia elruido dl socket
        in = new ObjectInputStream(skt.getInputStream());
    }

    private void disconnect() throws Exception {
        skt.shutdownOutput();//manda una señal de que la transmcion termino
        skt.close();
    }



    public void solicitar_numero_worker() {
        try {
            out.writeInt(Protocol.REQUEST_NUMERO_WORKER);
            out.flush();

        } catch (Exception ex) {
            System.out.println("Excepcion: " + ex.getMessage());
        }
    }

    public void setIDeliver(IDeliver d) {
        deliver = d;
    }

    public void setTControllerTipo(IController c) {
        controllerTipo = c;
    }

    public void setTControllerInstrumento(IController c) {
        controllerInst = c;
    }

    public void setControllerCal(IController c) {
        controllerCal = c;
    }
    public  void setController(MainController c){controller = c;}

    //Listening funtions----------------------------------------------
    boolean continuar = true;

    public void start() {
        System.out.println("Client worker atendiendo peticiones...");
        Thread t = new Thread(new Runnable() {
            public void run() {
                listen();
            }//aca se encicla para que el hilo no se acaba
        });
        continuar = true;
        t.start();
    }

    public void stop() {
        continuar = false;
    }


    public void listen() {
        System.out.println("Ejecutando listen de front end");
        int method;
        while (continuar) {
            try {
                method = in.readInt();

                System.out.println("DELIVERY del listen del worker ");
                System.out.println("Operacion: " + method);

                switch (method) {
                    case Protocol.DELIVER://en particular este solo hace el deliveri por que el cliente solo necesita escuhar esto
                        try {
                            System.out.println("Se entro al deliver en service proxy");
                            Message message = (Message) in.readObject();
                            System.out.println("Mensaje de message en Service Proxy: /Protocol deliver " + message.getMessage());
                            deliver(message);
                        } catch (ClassNotFoundException ex) {
                            System.out.println(Color.RED+"Catch de proxy recibiendo deliver " + ex.getMessage()+Color.RESET);
                        }
                        break;
                    //Reload/ refresco de listas--------------------------------------------------------------------------------------
                    case Protocol.RELOAD_UM://en particular este solo hace el deliveri por que el cliente solo necesita escuhar esto
                        System.out.println("Se entro al deliver en service proxy");
                        Object objectU = in.readObject();
                        System.out.println("RELOAD LIST en Service Proxy: /Protocol deliver ");
                        update(objectU, Protocol.RELOAD_UM);
                        break;
                    case Protocol.RELOAD_TIP_INS://en particular este solo hace el deliveri por que el cliente solo necesita escuhar esto
                        System.out.println("Se entro al deliver en service proxy");
                        Object objectT = in.readObject();
                        System.out.println("RELOAD LIST en Service Proxy: /Protocol deliver ");
                        update(objectT, Protocol.RELOAD_TIP_INS);
                        break;
                    case Protocol.RELOAD_INSTRUMENTO://en particular este solo hace el deliveri por que el cliente solo necesita escuhar esto
                        System.out.println("Se entro al deliver en service proxy");
                        Object objectI = in.readObject();
                        System.out.println("RELOAD LIST en Service Proxy: /Protocol deliver ");
                        update(objectI, Protocol.RELOAD_INSTRUMENTO);
                        break;
                    case Protocol.RELOAD_CALIBRACION:
                        System.out.println("Se entro al deliver en service proxy");
                        Object objectC = in.readObject();
                        System.out.println("RELOAD LIST en Service Proxy: /Protocol deliver ");
                        update(objectC, Protocol.RELOAD_CALIBRACION);
                        break;
                    case Protocol.tellRELOAD_CALIBRACION:
                        //System.out.println("Se entro al deliver en service proxy");
                        Message mes = (Message) in.readObject();
                        //System.out.println("RELOAD LIST en Service Proxy: /Protocol deliver ");
                        update(mes, Protocol.tellRELOAD_CALIBRACION);
                        break;
                    case Protocol.ERROR_NO_ERROR:
                        System.out.println("Error_no_error");
                        break;


                    //--------------------------------------------------Unidad Medida--------------------------------------------------
                    case Protocol.READUNIDAD:
                        List<UnidadMedida> res = (List<UnidadMedida>) in.readObject();
                        System.out.println("Me llego la lista de unidad de medida perfectamente a Service!!\n");
                        break;
                    case Protocol.FINDIDUNIDAD:
                        UnidadMedida med = (UnidadMedida) in.readObject();
                        System.out.println("Me llego el elemento buscado  de unidad de medida perfectamente a Service!!\n");
                        break;
                    //--------------------------------------------------TIPOS DE INSTRUMENTOS--------------------------------------------------

                    case Protocol.READTIPO:
                        List<TipoInstrumentoObj> lis = (List<TipoInstrumentoObj>) in.readObject();
                        System.out.println("Me llego la lista perfectamente a Service!!\n");
                        break;
                    case Protocol.UPDATETIPO:
                        //boolean p = (boolean) in.readObject();
                        System.out.println("Me llego la notificacion de que actualizo!!\n");
                        break;
                    case Protocol.DELETETIPO:
                        //boolean del = (boolean) in.readObject();
                        System.out.println("Me llego la notificacion de que elimino!!\n");
                        break;
                    case Protocol.READINSTRUMENTO:
                        List<Instrumento> lis_i = (List<Instrumento>) in.readObject();
                        System.out.println("Me llego la lista de instrumentos perfectamente a Service!!\n");
                        break;
                    case Protocol.UPDATEINSTRUMENTO:
                        // boolean p_i = (boolean) in.readObject();
                        System.out.println("Me llego la notificacion de que actualizo instrumento!!\n");
                        break;
                    case Protocol.DELETEINSTRUMENTO:
                        // boolean del_i = (boolean) in.readObject();
                        System.out.println("Me llego la notificacion de que elimino instrumento!!\n");
                        break;


                    //--------------------------------------------------CALIBRACIONES--------------------------------------------------
                    case Protocol.CREATECALIBRACION:
                        System.out.println("La calibracion se creo con existo ");
                        Calibraciones cal = (Calibraciones) in.readObject();
                        break;
                    //--------------------------------------------------INICIALIZACION---------------------------------------------------
                    case Protocol.INIT_LISTA_TIPO_INSTRUMENTOS: {
                        System.out.println("Setenado lista a mi lista propia");
                        try {
                            List<TipoInstrumentoObj> list = (List<TipoInstrumentoObj>) in.readObject();
                            iniciar_lista_tipos_instrumento(list);
                        } catch (Exception ex) {
                            System.out.println(Color.RED+"Excepcion: " + ex.getMessage()+Color.RESET);
                        }
                        break;
                    }
                    case Protocol.SEND_NUMERO_WORKER: {
                        try {
                            System.out.println("Seteando worker\n\n\n\n\n\n\n");
                            int numeroWorker = (int) in.readInt();
                            set_numero_worker(numeroWorker);
                        } catch (Exception ex) {
                            System.out.println(Color.RED+"Excepcion: " + ex.getMessage()+Color.RESET);
                        }
                        break;
                    }
                    case Protocol.CREATEMEDICIONESVECTOR:{

                    }
                }
                out.flush();
            } catch (IOException ex) {
                continuar = false;
                System.out.println(Color.RED+"Se detuvo en el catch de listen de service proxy: " + ex.getMessage()+Color.RESET);
                if(ex.getMessage() == null){
                    controller.showMessage("El servidor se a cerrado");
                    System.exit(0);
                }
            } catch (ClassNotFoundException e) {
                System.out.println(Color.RED+"Catch CLASSNOTFOUNDEXCEPTION " + e.getMessage()+Color.RESET);
                throw new RuntimeException(e);
            }
        }
        try {
            disconnect();
        } catch (IOException e) {
            System.out.println(Color.RED+"Catch De Disconnected" + e.getMessage()+Color.RESET);
        } catch (Exception e) {
            System.out.println(Color.RED+"CATCH RUNTIMEEXCEPTION "+ e.getMessage()+Color.RESET);
            throw new RuntimeException(e);
        }
    }

    private void deliver( final Message message ){
        SwingUtilities.invokeLater(new Runnable(){//crea un hilo temporal que se destrulle cuando termina
                                       // se cierran solos cuando termina de pocesar (no esta en un while)
                                       public void run(){
                                           //try {
                                           // controllerCal.recargarLista();
                                           //}catch (Exception ex){
                                           //  System.out.println(ex.getMessage());}
                                           deliver.deliver(message);
                                           //controllerTipo.changesMaked();
                                           //controllerInst.changesMaked();
                                       }
                                   }
        );
    }

    private void set_numero_worker(final int num) {
        SwingUtilities.invokeLater(new Runnable() {//crea un hilo temporal que se destrulle cuando termina
                                       // se cierran solos cuando termina de pocesar (no esta en un while)
                                       public void run() {
                                           deliver.set_numero_worker(num);
                                       }
                                   }
        );
    }

    private void update(Object ob, final int pro) {
        SwingUtilities.invokeLater(new Runnable()
                                   {//crea un hilo temporal que se destrulle cuando termina
                                       // se cierran solos cuando termina de pocesar (no esta en un while)
                                       public void run() {
                                           try {
                                               controllerTipo.update(ob, pro);
                                               controllerInst.update(ob, pro);
                                               controllerCal.update(ob, pro);
                                           } catch (Exception e) {
                                               JOptionPane.showMessageDialog(null, e.getMessage());
                                               System.out.println(Color.RED+"Error en update Service"+e.getMessage()+Color.RESET);
                                           }
                                       }
                                   }
        );
    }

    private void iniciar_lista_tipos_instrumento(final List<TipoInstrumentoObj> list) {
        SwingUtilities.invokeLater(new Runnable()
                                   {
                                       public void run() {
                                           try {
                                               //controller.cargar_datos(list);
                                           } catch (Exception e) {
                                               throw new RuntimeException(e);
                                           }
                                       }
                                   }
        );
    }

    public void exit() throws Exception { // como de deslogueo
        out.writeInt(Protocol.DISCONNECT);
        //out.writeObject(u);
        out.flush();
        System.out.println("le dije al server que se cerrara");
        this.stop();
        this.disconnect();
    }



    //-------------------------------------------------------CRUD-------------------------------------------------------
    @Override
    public List<UnidadMedida> readUnidadesMedida(List<UnidadMedida> lis) throws Exception {
        out.writeInt(Protocol.READUNIDAD);
        out.writeObject(lis);
        out.flush();
        System.out.println("Mande el mensaje de leer UM a el server");
        return lis;
    }
    @Override
    public UnidadMedida findById(int id) throws Exception {
        out.writeInt(Protocol.FINDIDUNIDAD);
        out.writeObject(id);
        out.flush();
        System.out.println("Mande el mensaje de Crear a el server");

        return null;
    }
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
    public void update(TipoInstrumentoObj tipo) throws Exception {
        out.writeInt(Protocol.UPDATETIPO);
        out.writeObject(tipo);
        out.flush();
        System.out.println("Mande el mensaje de update a el server");

    }

    @Override
    public void delete(TipoInstrumentoObj tipo) throws Exception {
        out.writeInt(Protocol.DELETETIPO);
        out.writeObject(tipo);
        out.flush();
        System.out.println("Mande el mensaje de delete a el server");


    }

    @Override
    public void delete(String tipoID) throws Exception {
        out.writeInt(Protocol.DELETETIPO);
        out.writeObject(tipoID);
        out.flush();
        System.out.println("Mande el mensaje de delete a el server");


    }

    //------------------------------------------------Instrumentos------------------------------------------------

    @Override
    public void create(Instrumento instrumento) throws Exception {
        out.writeInt(Protocol.CREATEINSTRUMENTO);
        out.writeObject(instrumento);
        out.flush();
        System.out.println("Mande el mensaje de Crear instrumento a el server");
    }

    @Override
    public List<Instrumento> read_instrumentos() throws Exception {
        out.writeInt(Protocol.READINSTRUMENTO);
        //out.writeObject(listInst);
        out.flush();
        System.out.println("Le estoy pasando la lista de instrumentos a server desde serviceProxy");
        return null;
    }


    @Override
    public void update(Instrumento instrumento) throws Exception {
        out.writeInt(Protocol.UPDATEINSTRUMENTO);
        out.writeObject(instrumento);
        out.flush();
        System.out.println("Mande el mensaje de update instrumento a el server");

    }

    @Override
    public void delete(Instrumento instrumento) throws Exception {
        out.writeInt(Protocol.DELETEINSTRUMENTO);
        out.writeObject(instrumento);
        out.flush();
        System.out.println("Mande el mensaje de delete instrumento a el server");


    }

    @Override
    public void deleteInstrumentoId(String instruID) throws Exception {
        out.writeInt(Protocol.DELETEINSTRUMENTO);
        out.writeObject(instruID);
        out.flush();
        System.out.println("Mande el mensaje de delete id instrumento a el server");

    }

    //-----------------------------------------------Calibraciones------------------------------------------------
    @Override
    public void create(Calibraciones calibracion) throws Exception {
        out.writeInt(Protocol.CREATECALIBRACION);
        out.writeObject(calibracion);
        out.flush();
        System.out.println("Mande el mensaje de Crear a el server");

    }

    @Override
    public List<Calibraciones> readCalibracion(String idIns) throws Exception {
        out.writeInt(Protocol.READCALIBRACION);
        out.writeObject(idIns);
        out.flush();
        System.out.println("Le estoy pasando la lista de instrumentos a server desde serviceProxy");
        return null;
    }

    @Override
    public void update(Calibraciones calibracion) throws Exception {
        out.writeInt(Protocol.UPDATECALIBRACION);
        out.writeObject(calibracion);
        out.flush();
        System.out.println("Mande el mensaje de update a el server");


    }

    @Override
    public void delete(Calibraciones calibracion) throws Exception {
        out.writeInt(Protocol.DELETECALIBRACION);
        out.writeObject(calibracion);
        out.flush();
        System.out.println("Mande el mensaje de delete  calebracion  a el server");


    }

    @Override
    public void deleteCalibracionId(String calibracion) throws Exception {
        out.writeInt(Protocol.DELETECALIBRACION);
        out.writeObject(calibracion);
        out.flush();
        System.out.println("Mande el mensaje de delete id calebracion  a el server");

    }

    //-------------------------------------------------Mediciones-------------------------------------------------
    @Override
    public void create(List<Mediciones> medida) throws Exception {
        out.writeInt(Protocol.CREATEMEDICIONES);
        out.writeObject(medida);
        out.flush();
        System.out.println("Mande el mensaje de Crear mediciones a el server");
    }

    @Override
    public void create(Mediciones[] medidas) throws Exception {
        out.writeInt(Protocol.CREATEMEDICIONESVECTOR);
        out.writeObject(medidas);
        out.flush();
        System.out.println("Mande el mensaje de Crear mediciones vector a el server");
    }

    @Override
    public List<Mediciones> read(Mediciones medida) throws Exception {
        out.writeInt(Protocol.READMEDICIONES);
        out.writeObject(medida);
        out.flush();
        System.out.println("Le estoy pasando la lista de mediciones a server desde serviceProxy");
        return null;
    }
    @Override
    public void update(Mediciones medida) throws Exception {
        out.writeInt(Protocol.UPDATEMEDICIONES);
        out.writeObject(medida);
        out.flush();
        System.out.println("Mande el mensaje de update medida a el server");

    }
    @Override
    public void delete(Mediciones medida) throws Exception {
        out.writeInt(Protocol.DELETEMEDICIONES);
        out.writeObject(medida);
        out.flush();
        System.out.println("Mande el mensaje de delete medicon a el server");
    }

    @Override
    public void deleteAll() throws Exception {
        out.writeInt(Protocol.DELETEALLMEDICIONES);
        //out.writeObject(medida);
        out.flush();
        System.out.println("Mande el mensaje de delete medicon a el server");
    }

    @Override
    public void forceUpdate() throws IOException {
        out.writeInt(Protocol.FORCE_UPDATE);
        out.flush();
    }
}

