package Protocol;

import java.util.List;

public interface IController {
    void update(Object o, int pro) throws Exception;
    void cargar_datos(List<TipoInstrumentoObj> list) throws Exception;
}
