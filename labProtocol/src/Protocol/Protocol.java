package Protocol;

public class Protocol {
    public static final String SERVER = "localhost";
    public static final int PORT = 1235;

    public static final int REGISTER = 1;
    //Respuestas del server-------------------------------------------------------------------------------------------------
    public static final int ERROR_NO_ERROR = 0;
    public static final int ERROR_LOGIN=1;
    public static final int ERROR_ERROR = 1;
    public static final int ERROR_LOGOUT=2;
    public static final int ERROR_POST=3;


    public static final int SYNC = 10;
    public static final int ASYNC = 11;
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
    //--------------MEDICIONES---------------
    public static final int CREATEMEDICIONES = 400;
    public static final int READMEDICIONES = 401;
    public static final int UPDATEMEDICIONES = 402;
    public static final int DELETEMEDICIONES = 403;

}
