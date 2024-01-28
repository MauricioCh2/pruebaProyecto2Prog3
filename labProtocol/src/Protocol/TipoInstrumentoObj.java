package Protocol;

import java.io.Serializable;

public class TipoInstrumentoObj implements Serializable {
    private String codigo;
    private String nombre;
    private String unidad;

    public TipoInstrumentoObj(String codigo, String nombre, String unidad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.unidad = unidad;
    }
    //setters y getters------------------------------------------------------------------------------------------------
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }
}
