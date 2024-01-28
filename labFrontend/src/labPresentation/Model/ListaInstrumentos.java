package labPresentation.Model;

import Protocol.TipoInstrumentoObj;

import java.util.ArrayList;
import java.util.List;

public class ListaInstrumentos {
    static List<TipoInstrumentoObj> list = new ArrayList<>();
    //Setters y getters------------------------------------------------------------------------------------------------
    public List<TipoInstrumentoObj> getList() {
        return list;
    }
    public void setList(List<TipoInstrumentoObj> list){
        list = list;
    }
    //Metodos----------------------------------------------------------------------------------------------------------
    public void agregar (TipoInstrumentoObj ins){list.add(ins);}
    public TipoInstrumentoObj obtener(String str) {
        for (TipoInstrumentoObj object : list) {
            if (object.getCodigo().equals(str)) {
                return object;
            }
        }
        return null;
    }
    public void eliminar(String str) {
        TipoInstrumentoObj ins = obtener(str);
        if(ins != null){
            list.remove(ins);
        }

    }
}
