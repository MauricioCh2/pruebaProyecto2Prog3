package labServer;

import Protocol.TipoInstrumentoObj;

import java.util.ArrayList;
import java.util.List;

public class LocalData {

    private List<TipoInstrumentoObj> listaTipos;

    public List<TipoInstrumentoObj> getListaTipos() {
        return listaTipos;
    }

    public void setListaTipos(List<TipoInstrumentoObj> listaTipos) {
        this.listaTipos = listaTipos;
    }

    LocalData(){
        listaTipos = new ArrayList<>();
        //this.test();
    }

    public void test(){
        listaTipos.add(new TipoInstrumentoObj("1234", "Pepe", 2));
        listaTipos.add(new TipoInstrumentoObj("2234", "Repe", 3));
        listaTipos.add(new TipoInstrumentoObj("4234", "Oepe", 1));
    }

    public void imprimir_lista_tipos(){
        for (TipoInstrumentoObj obj:listaTipos) {
            System.out.println("Tipo desde server " + obj.getNombre());
        }
    }

}
