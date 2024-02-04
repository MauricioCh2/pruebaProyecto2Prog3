package data;

import Protocol.Calibraciones;
import Protocol.Instrumento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
        Connection connection = new DataBaseConn().connection();
        String sql = "SELECT c.id_calibraciones, c.fecha, c.mediciones, i.id_instrumentos, i.id_tipo_instrumento, i.descripcion, i.min, i.max, i.tolerancia " +
                "FROM calibraciones c " +
                "INNER JOIN instrumentos i ON c.id_instrumento = i.id_instrumentos " +
                "WHERE c.id_instrumento = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, Integer.parseInt(idInstrumento));
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            // Obtener los datos de la calibración
            int numCalibracion = result.getInt("id_calibraciones");
            LocalDateTime fecha = result.getTimestamp("fecha").toLocalDateTime();
            int mediciones = result.getInt("mediciones");

            // Obtener los datos del instrumento asociado a la calibración
            //int serInstrumento = result.getInt("id_instrumentos");
            String idTipoInstrumento = result.getString("id_tipo_instrumento");
            String descripcion = result.getString("descripcion");
            int min = result.getInt("min");
            int max = result.getInt("max");
            double tolerancia = result.getDouble("tolerancia");

            // Crear el objeto Instrumento
            Instrumento instrumento = new Instrumento(String.valueOf(idInstrumento), idTipoInstrumento, descripcion, min, max, tolerancia);

            // Crear el objeto Calibraciones y asociarlo con el objeto Instrumento
            Calibraciones calibracion = new Calibraciones(numCalibracion, fecha.toString(), mediciones, instrumento);

            // Agregar la calibración a la lista
            lista.add(calibracion);
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
