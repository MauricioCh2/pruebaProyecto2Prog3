package Protocol;

import java.io.Serializable;
import java.util.List;

public class TipoInstrumentoObj implements Serializable {
    private String codigo;
    private String nombre;
    private UnidadMedida unidadM;
    private String unidad;
    private  int unidadId;


    private List<Instrumento> listaInstrumentos;

    public TipoInstrumentoObj(String codigo, String nombre, String unidad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.unidad = unidad;
    }
    public TipoInstrumentoObj(String codigo, String nombre, UnidadMedida unidad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.unidadM = unidad;
    }
    public TipoInstrumentoObj(String codigo, String nombre, int unidad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.unidadId = unidad;
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

    public UnidadMedida getUnidadM() {
        return unidadM;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidadM(UnidadMedida unidadM) {
        this.unidadM = unidadM;
    }
    public int getUnidadId() {
        return unidadId;
    }

}
