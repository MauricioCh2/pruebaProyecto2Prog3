package labPresentation.Model.Calibraciones;

import Protocol.Calibraciones;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListaCalibraciones {
    List<Calibraciones> listaCalibraciones;

    public ListaCalibraciones(){
        listaCalibraciones = new ArrayList<>();
    }

    public List<Calibraciones> getListaCalibraciones() {
        return listaCalibraciones;
    }

    public void setListaCalibraciones(List<Calibraciones> listaCalibraciones) {
        this.listaCalibraciones = listaCalibraciones;
    }

    @Override
    public String toString() {
        return "ListaCalibraciones{" +
                "listaCalibraciones=" + listaCalibraciones +
                '}';
    }
    public void agregar(Calibraciones cali){
        listaCalibraciones.add(cali);
    }
    public Calibraciones retornarUltimaCalibracion(){
        if(listaCalibraciones.isEmpty()){
            return null;
        }else{
            return listaCalibraciones.get(listaCalibraciones.size()-1);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListaCalibraciones that = (ListaCalibraciones) o;
        return Objects.equals(listaCalibraciones, that.listaCalibraciones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listaCalibraciones);
    }
}
