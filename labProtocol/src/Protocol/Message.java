package Protocol;

import java.io.Serializable;

public class Message implements Serializable {
    private int tipo;
    private String entidad;
    private String texto;
    String message;
    int numwork;

    //se podria mandar la estructura o algo similar
    // o un int con el protocolo que se ejecutó en el mensaje
    // como decir hey se hizo un update a este objeto, paraa que los demás workers hagan lo mismo
    //asi será más util que solo texto
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}