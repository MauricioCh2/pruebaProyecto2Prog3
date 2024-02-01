package Protocol.Listas;

import Protocol.UnidadMedida;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UnidadMedList implements Serializable {

    private static List<UnidadMedida> list;
    public UnidadMedList(){
        if(list==null){
            list = new ArrayList<>();
        }

    }
    public  static List<UnidadMedida> getList() {
            if (list == null){
                list = new ArrayList<>();
            }
            return list;
    }
    public static void updateList(List<UnidadMedida> newlist){
        list = newlist;
    }
    public void setList(List<UnidadMedida> newLis){
            list = newLis;
    }

    public static  void addObj (UnidadMedida ins){
        if(list==null){
            list = new ArrayList<>();
        }
        list.add(ins);
    }

    public UnidadMedida obtener(int i) {
        for (UnidadMedida object : list) {
            if (object.getIdUnidadMedida() == i ) {
                return object;
            }
        }
        return null;
    }
}
