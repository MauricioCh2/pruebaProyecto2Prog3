package Protocol;

import java.io.Serializable;

public class UnidadMedida implements Serializable {
    private int idUnidadMedida;
    private String nombre;
    private String simbolo;

    public UnidadMedida(int id, String nom, String sim){
        idUnidadMedida = id;
        nombre = nom;
        simbolo = simbolo;
    }

    public int getIdUnidadMedida() {
        return idUnidadMedida;
    }

    public String getNombre() {
        return nombre;
    }

    public String getSimbolo() {
        return simbolo;
    }
}
