package labServer.dao;

import Protocol.Calibraciones;
import Protocol.Listas.TiposInstrumentosList;
import Protocol.TipoInstrumentoObj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CRUDCalibraciones {

    public boolean create(Calibraciones cali) throws Exception {
        Connection connection =  new DataBaseConn().connection();
        String sql = "insert into calibraciones values (?,?, ?, ? )";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,cali.getNumeroCalibracion());
        statement.setString(2,cali.getFecha());// no se si es necesario pasar el getFecha a DATE time de sql
        statement.setInt(3, cali.getNumeroMediciones());
        statement.setInt(4, cali.getNumeroMediciones());

        statement.executeUpdate();
        System.out.println("Crea  calibracion.. "+cali.getNumeroCalibracion()+"de instrumento: "+cali.getNo_SerieIns()+"\n");
        return true;
    }

    public List<Calibraciones> read(Calibraciones cali) throws Exception {
        ArrayList<Calibraciones> lista = new ArrayList<>();
        Connection connection =  new DataBaseConn().connection();
        String sql = "select * from calibraciones";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        while (result.next()) {

            Calibraciones calib = new Calibraciones( result.getInt(1), result.getString(2), result.getInt(3), result.getInt(4));
            lista.add(calib);
            //TiposInstrumentosList.list.add(tipoIns);
        }
        return lista;
    }

    public boolean update(Calibraciones cali) throws Exception {
//        Connection connection =  new DataBaseConn().connection();
//        String sql = "update  tipos_instrumento set nombre = ?  where id_calibraciones = ?";
//        PreparedStatement statement = connection.prepareStatement(sql);
//        statement.setString(1,cali.get());
//        statement.setString(2,cali.getCodigo());
//        statement.executeUpdate();
//

        System.out.println("Actualiza  calibracion.. "+cali.getNumeroCalibracion()+"de instrumento: "+cali.getNo_SerieIns()+"\n");

        return true;
    }

    public boolean delete(Calibraciones cali) throws Exception {
        Connection connection =  new DataBaseConn().connection();
        String sql = "delete from   calibraciones  where id_calibraciones = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,cali.getNumeroCalibracion());
        statement.executeUpdate();

        System.out.println("Se elimino una calibracion .. "+cali.getNumeroCalibracion()+"\n");

        System.out.println("Elimina  calibracion.. "+cali.getNumeroCalibracion()+"de instrumento: "+cali.getNo_SerieIns()+"\n");

        return true;
    }

}
