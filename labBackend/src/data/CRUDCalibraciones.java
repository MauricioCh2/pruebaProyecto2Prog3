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


        statement.setString(1, cali.getFecha());
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
            String fecha= result.getString("fecha");
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
            Calibraciones calibracion = new Calibraciones(numCalibracion, fecha, mediciones, instrumento);

            // Agregar la calibración a la lista
            lista.add(calibracion);
        }
        return lista;
    }

    public boolean update(Calibraciones cali) throws Exception {
        Connection connection = new DataBaseConn().connection();
        String sql = "UPDATE calibraciones SET fecha = ?, mediciones = ?, id_instrumento = ? WHERE id_calibraciones = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        // Establecer los nuevos valores para los campos de la calibración
        statement.setString(1, cali.getFecha());
        statement.setInt(2, cali.getNumeroMediciones());
        statement.setInt(3, Integer.parseInt(cali.getNo_SerieIns()));
        statement.setInt(4, cali.getNumeroCalibracion()); // Suponiendo que tienes un método para obtener el ID de calibraciones

        statement.executeUpdate();

        System.out.println("Actualiza  calibracion.. "+cali.getNumeroCalibracion()+"de instrumento: "+cali.getNo_SerieIns()+"\n");
        return true;
    }

    public boolean delete(Calibraciones cali) throws Exception {
        Connection connection =  new DataBaseConn().connection();
        String sql = "delete from   calibraciones  where id_calibraciones = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,cali.getNumeroCalibracion());
        statement.executeUpdate();


        System.out.println("Elimina  calibracion.. "+cali.getNumeroCalibracion()+"de instrumento: "+cali.getNo_SerieIns()+"\n");

        return true;
    }

}
