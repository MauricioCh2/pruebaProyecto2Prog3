package labPresentation.Model;


import Protocol.Instrumento;

import java.util.ArrayList;
import java.util.List;

public class ListaInstrumentos_E {
    static List<Instrumento> list = new ArrayList<>();
    public List<Instrumento> getList() {
        return list;
    }
    public void setList(List<Instrumento> list){
        list = list;
    }
    public void agregar (Instrumento ins){list.add(ins);}

    public Instrumento obtener(String str) {
        for (Instrumento object : list) {
            if (object.getSerie().equals(str)) {
                return object;
            }
        }
        return null;
    }
}