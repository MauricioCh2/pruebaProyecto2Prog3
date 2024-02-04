package data;

import Protocol.Mediciones;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CRUDMediciones {
    //Database db;
    public void create(Mediciones med) throws Exception {
        Connection connection =  new DataBaseConn().connection();
        String sql = "INSERT INTO mediciones(`referencia`,`lectura`,`id_calibracion`) VALUES (?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        //statement.setInt(1,med.getNumMedicion());
        statement.setDouble(1,med.getValorReferencia());
        statement.setDouble(2,med.getValorMarcado());
        statement.setInt(3,med.getIdCalibracion());

        statement.executeUpdate();

        System.out.println("Crea medicion.. "+med.getNumMedicion()+"\n");
    }

    public List<Mediciones> read(Mediciones med) throws Exception {
        ArrayList<Mediciones> lista = new ArrayList<>();
        Connection connection =  new DataBaseConn().connection();
        String sql = "select * from mediciones";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        while (result.next()) {

            Mediciones medi = new Mediciones( result.getDouble(1), result.getDouble(2), result.getInt(3));
            lista.add(medi);
            //TiposInstrumentosList.list.add(tipoIns);
        }
        System.out.println("/n ----Lee la lista de tipos de instrumentos ----  \n");
        //return lista;

        return null;
    }

    public void update(Mediciones med) throws Exception {
        System.out.println("Actualizo  medicion.. "+med.getNumMedicion()+"\n");
    }

    public void delete(Mediciones med) throws Exception {
        Connection connection =  new DataBaseConn().connection();
        String sql = "delete from   tipos_instrumento  where id_tipo_instrumento = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,med.getIdCalibracion());
        statement.executeUpdate();


        System.out.println("Se elimino medicion.. "+med.getNumMedicion()+"\n");

    }

}
