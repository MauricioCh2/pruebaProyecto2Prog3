package Protocol;

import java.io.Serializable;

public class UnidadMedida implements Serializable {
    private final int idUnidadMedida;
    private String nombre;
    private String simbolo;

    //--------------------------------METODOS--------------------------------
    public UnidadMedida(int id, String nom, String sim){
        idUnidadMedida = id;
        nombre = nom;
        simbolo = sim;
    }
    public int getIdUnidadMedida() {
        return idUnidadMedida;
    }
    public String getNombre() {
        return nombre;
    }

}
