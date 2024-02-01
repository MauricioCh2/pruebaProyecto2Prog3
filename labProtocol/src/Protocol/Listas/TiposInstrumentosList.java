package Protocol.Listas;

import Protocol.TipoInstrumentoObj;

import java.util.ArrayList;
import java.util.List;

public class TiposInstrumentosList {
    static public List<TipoInstrumentoObj> list = new ArrayList<>();
    public static  List<TipoInstrumentoObj> getList() {
        return list;
    }
    public void setList(List<TipoInstrumentoObj> list){
        list = list;
    }
    public void addObj (TipoInstrumentoObj ins){list.add(ins);}

    public TipoInstrumentoObj obtener(String str) {
        for (TipoInstrumentoObj object : list) {
            if (object.getCodigo().equals(str)) {
                return object;
            }
        }
        return null;
    }
}
