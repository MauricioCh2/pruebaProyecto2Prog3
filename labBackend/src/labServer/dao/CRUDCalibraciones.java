package labServer.dao;

import Protocol.Calibraciones;
import Protocol.Listas.TiposInstrumentosList;
import Protocol.TipoInstrumentoObj;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CRUDCalibraciones {

    public boolean create(Calibraciones cali) throws Exception {
        Connection connection = new DataBaseConn().connection();
        String sql = "INSERT INTO calibraciones (fecha, mediciones, id_instrumento) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        // Convertir la fecha de String a java.sql.Timestamp
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parsedDate = dateFormat.parse(cali.getFecha() + " 00:00:00"); // Asegúrate de agregar las horas, minutos y segundos si están disponibles en tu base de datos.
        Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());

        statement.setTimestamp(1, timestamp);
        statement.setInt(2, cali.getNumeroMediciones());
        statement.setInt(3, Integer.parseInt(cali.getNo_SerieIns()));

        statement.executeUpdate();
        System.out.println("Crea calibracion.. " + cali.getNumeroCalibracion() + " de instrumento: " + cali.getNo_SerieIns() + "\n");
        return true;
    }

    public List<Calibraciones> read(String idInstrumento) throws Exception {
        ArrayList<Calibraciones> lista = new ArrayList<>();
        Connection connection =  new DataBaseConn().connection();
        String sql = "SELECT * FROM calibraciones WHERE id_instrumento = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, Integer.parseInt(idInstrumento));
        ResultSet result = statement.executeQuery(); // Aquí deberías llamar solo a executeQuery()
        while (result.next()) {
            Calibraciones calib = new Calibraciones(result.getInt(1), result.getString(2), result.getInt(3), result.getInt(4));
            lista.add(calib);
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
