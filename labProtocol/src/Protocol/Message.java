package Protocol;

import java.io.Serializable;

public class Message implements Serializable {
    private int tipo;
    private String entidad;
    private String texto;
    String message;
    int numwork;

    public static final int CREATE = 1;
    //public static final int READ = 2;
    public static final int UPDATE = 2;
    public static final int DELETE = 3;
    public Message() {
    }

    public Message( String message) { //sedner es server jaja

        this.message = message;
    }
    public Message(int tipo, String entidad, String texto, int num) {
        this.tipo = tipo;
        this.entidad = entidad;
        this.texto = texto;
        this.numwork = num;
        createMessage(tipo,entidad,texto,num);
        //message = "Tipo: " + tipo +", Entidad:  " + entidad + " Texto: "+ texto;
    }

    public Message(int tipo, String entidad, String texto,int num, String noSerieIns) {
        this.tipo = tipo;
        this.entidad = entidad;
        this.texto = texto;
        this.numwork = num;
        createMessage(tipo,entidad,texto,num, noSerieIns);
    }

    private void createMessage(int tipo, String entidad, String texto, int num){
        switch (tipo){
            case CREATE :
                message = "El cliente num: "+ num + " a creado  un"+ entidad+ ": "+ texto;
                break;
            case DELETE:
                message = "El cliente num: "+ num + " a eliminado  un"+ entidad+ ": "+ texto;

                break;
            case UPDATE:
                message = "El cliente num: "+ num + " a actualizado un"+ entidad+ ": "+ texto;

                break;
        }
    }
    private void createMessage(int tipo, String entidad, String texto, int num, String noSerie){
        switch (tipo){
            case CREATE :
                message = "El cliente num: "+ num + " a creado  un"+ entidad+ ": "+ texto+ " del instrumento: "+ noSerie;
                break;
            case DELETE:
                message = "El cliente num: "+ num + " a eliminado  un"+ entidad+ ": "+ texto+ " del instrumento: "+ noSerie;

                break;
            case UPDATE:
                message = "El cliente num: "+ num + " a actualizado un"+ entidad+ ": "+ texto+ " del instrumento: "+ noSerie;

                break;
        }
    }
    public String getMessage() {
        return message;
    }
    
}