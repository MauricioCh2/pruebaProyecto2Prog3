package Protocol;

public class Protocol {
    public static final String SERVER = "localhost";
    public static final int PORT = 1235;

    public static final int REGISTER = 1;

    public static final int FORCE_UPDATE = 42;
    //protocolo de listas-------------------------------
    public static final int RELOAD_UM = 21;
    public static final int RELOAD_TIP_INS = 22;
    public static final int RELOAD_INSTRUMENTO = 23;
    public static final int RELOAD_CALIBRACION = 24;
    public static final int  RELOAD_MEDICIONES = 25;
    public static final int tellRELOAD_CALIBRACION = 244;

    //Respuestas del server-------------------------------------------------------------------------------------------------
    public static final int ERROR_NO_ERROR = 0;
    public static final int ERROR_LOGIN=1;

    public static final int DELIVER = 12;
    public static final int DISCONNECT = 13;

    //-------------------------------------------------------CRUD-------------------------------------------------------
    //---------------UNIDAD DE MEDIDA-----------------
    public static final int READUNIDAD = 91;
    public static final int FINDIDUNIDAD = 94;
    //--------------TIPOS DE INTRUMENTO---------------
    public static final int CREATETIPO = 100;
    public static final int READTIPO = 101;
    public static final int UPDATETIPO = 102;
    public static final int DELETETIPO = 103;

    //--------------INTRUMENTO---------------
    public static final int CREATEINSTRUMENTO = 200;
    public static final int READINSTRUMENTO = 201;
    public static final int UPDATEINSTRUMENTO = 202;
    public static final int DELETEINSTRUMENTO = 203;
    //--------------CALIBRACIONES---------------
    public static final int CREATECALIBRACION = 300;
    public static final int READCALIBRACION = 301;
    public static final int UPDATECALIBRACION = 302;
    public static final int DELETECALIBRACION = 303;

    public static final int INIT_LISTA_TIPO_INSTRUMENTOS = 401;

    //--------------MEDICIONES---------------
    public static final int CREATEMEDICIONES = 500;
    public static final int CREATEMEDICIONESVECTOR = 5001;
    public static final int READMEDICIONES = 501;
    public static final int UPDATEMEDICIONES = 502;
    public static final int DELETEMEDICIONES = 503;
    public static final int DELETEALLMEDICIONES = 5003;
    public static final int GETLISTAMEDICIONES = 504;


    //-----------------------JDOM-------------------------------
    public static final int SEND_NUMERO_WORKER = 1001;
    public static final int REQUEST_NUMERO_WORKER = 1002;
    public static final int CHANGE_MADE = 2001;

}
