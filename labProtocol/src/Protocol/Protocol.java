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
    //--------------TIPOS DE INTRUMENTO---------------
    public static final int CREATETIPO = 100;
    public static final int READTIPO = 101;
    public static final int UPDATETIPO = 102;
    public static final int DELETETIPO = 103;

    //--------------TIPOS DE INTRUMENTO---------------
    public static final int CREATEINSTRUMENTO = 200;
    public static final int READINSTRUMENTO = 201;
    public static final int UPDATEINSTRUMENTO = 202;
    public static final int DELETEINSTRUMENTO = 203;

}
