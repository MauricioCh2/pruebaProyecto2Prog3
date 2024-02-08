package data;

import Protocol.Calibraciones;
import Protocol.Instrumento;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CRUDCalibraciones {

    public boolean create(Calibraciones cali) throws Exception {
        Connection connection = new DataBaseConn().connection();


        String sql = "INSERT INTO calibraciones (fecha, mediciones, id_instrumento, num_calibracion) VALUES (?, ?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, cali.getFecha());
        statement.setInt(2, cali.getNumeroMediciones());
        statement.setInt(3, Integer.parseInt(cali.getNo_SerieIns()));
        statement.setInt(4, cali.getNumeroCalibracion());

        int affectedRows = statement.executeUpdate();

        if(affectedRows > 0) {

            ResultSet rs = statement.getGeneratedKeys();
            if(rs.next()){
                int idCalibracion = rs.getInt(1);
                cali.setIdCalibracion(idCalibracion);
            }
        }

        return true;
    }

    public List<Calibraciones> read(String idInstrumento) throws Exception {
        ArrayList<Calibraciones> lista = new ArrayList<>();
        Connection connection = new DataBaseConn().connection();
        String sql = "SELECT c.id_calibraciones, c.fecha, c.mediciones, c.num_calibracion, \n" +
                "i.id_instrumentos, i.id_tipo_instrumento, \n" +
                "i.descripcion, i.min, i.max, i.tolerancia\n" +
                "FROM calibraciones c \n" +
                "INNER JOIN instrumentos i ON c.id_instrumento = i.id_instrumentos\n" +
                "WHERE c.id_instrumento = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, Integer.parseInt(idInstrumento));
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            // Obtener los datos de la calibración
            int numCalibracion = result.getInt("num_calibracion");
            int idCalibracion = result.getInt("id_calibraciones");
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
            calibracion.setIdCalibracion(idCalibracion);
            // Agregar la calibración a la lista
            lista.add(calibracion);
        }
        return lista;
    }

    public boolean update(Calibraciones cali) throws Exception {
        Connection connection = new DataBaseConn().connection();
        String sql = "UPDATE calibraciones SET fecha = ?, mediciones = ?, id_instrumento = ? WHERE id_calibraciones = ? AND num_calibracion = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        // Establecer los nuevos valores para los campos de la calibración
        statement.setString(1, cali.getFecha());
        statement.setInt(2, cali.getNumeroMediciones());
        statement.setInt(3, Integer.parseInt(cali.getNo_SerieIns()));
        statement.setInt(4, cali.getIdCalibraciones());
        statement.setInt(5, cali.getNumeroCalibracion()); // Suponiendo que tienes un método para obtener el ID de calibraciones

        statement.executeUpdate();

        System.out.println("Actualiza  calibracion.. "+cali.getNumeroCalibracion()+"de instrumento: "+cali.getNo_SerieIns()+"\n");
        return true;
    }

    public boolean delete(Calibraciones cali) throws Exception {
        Connection connection =  new DataBaseConn().connection();

        String sql = "DELETE FROM calibraciones WHERE id_calibraciones = ? AND num_calibracion = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,cali.getIdCalibraciones());
        statement.setInt(2,cali.getNumeroCalibracion());
        statement.executeUpdate();


        System.out.println("Elimina  calibracion.. "+cali.getNumeroCalibracion()+"de instrumento: "+cali.getNo_SerieIns()+"\n");

        return true;
    }

}
