package labServer.dao;

import Protocol.Instrumento;
import Protocol.Listas.TiposInstrumentosList;
import Protocol.TipoInstrumentoObj;
import Protocol.UnidadMedida;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CRUDInstrumento {
    public void create(Instrumento instrumento) throws Exception {
        Connection connection =  new DataBaseConn().connection();
        String sql = "INSERT INTO instrumentos (id_instrumentos, id_tipo_instrumento, descripcion, min, max, tolerancia) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, Integer.parseInt(instrumento.getSerie()));
        statement.setString(2, instrumento.getTipo());
        statement.setString(3, instrumento.getDescripcion());
        statement.setInt(4, instrumento.getMinimo());
        statement.setInt(5, instrumento.getMaximo());
        statement.setDouble(6, instrumento.getTolerancia());

        statement.executeUpdate();
        System.out.println("Crea instrumento: " + instrumento.getDescripcion() + "\n");
    }

    public List<Instrumento> read() throws Exception {
        ArrayList<Instrumento> lista = new ArrayList<>();
        Connection connection = new DataBaseConn().connection();
        String sql = "SELECT i.id_instrumentos, i.descripcion, t.id_tipo_instrumento, t.id_unidad_medida, u.nombre AS unidad_medida_nombre, u.simbolo AS unidad_medida_simbolo, i.min, i.max, i.tolerancia " +
                "FROM instrumentos i " +
                "JOIN tipos_instrumento t ON i.id_tipo_instrumento = t.id_tipo_instrumento " +
                "JOIN unidades_medida u ON t.id_unidad_medida = u.idunidades_medida";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        while (result.next()) {
            Instrumento instrumento = new Instrumento(
                    result.getString("id_instrumentos"),
                    result.getString("descripcion"),
                    result.getString("id_tipo_instrumento"), // Modificado para obtener el ID del tipo de instrumento
                    result.getInt("max"),
                    result.getInt("min"),
                    result.getDouble("tolerancia")
            );
            // Añadir el atributo unidad de medida al objeto Instrumento
            UnidadMedida unidadMedida = new UnidadMedida(
                    result.getInt("id_unidad_medida"),
                    result.getString("unidad_medida_nombre"),
                    result.getString("unidad_medida_simbolo")
            );
            instrumento.setUnidad(unidadMedida.getNombre());
            lista.add(instrumento);
        }
        System.out.println("\n---- Lista de instrumentos leída ----\n");
        return lista;
    }

    public boolean update(Instrumento instrumento) throws Exception {
        Connection connection = new DataBaseConn().connection();
        String sql = "UPDATE instrumentos SET id_tipo_instrumento = ?, descripcion = ?, min = ?, max = ?, tolerancia = ? WHERE id_instrumentos = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, instrumento.getTipo());
        statement.setString(2, instrumento.getDescripcion());
        statement.setInt(3, instrumento.getMinimo());
        statement.setInt(4, instrumento.getMaximo());
        statement.setDouble(5, instrumento.getTolerancia());
        statement.setInt(6, Integer.parseInt(instrumento.getSerie()));
        statement.executeUpdate();

        System.out.println("Instrumento actualizado: " + instrumento.getDescripcion() + "\n");
        return true;
    }

    public boolean delete(Instrumento instrumento) throws Exception {
        Connection connection =  new DataBaseConn().connection();
        String sql = "delete from   instrumentos  where id_instrumentos = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,instrumento.getSerie());
        statement.executeUpdate();

        System.out.println("Se elimino un tipo de instrumento.. "+instrumento.getDescripcion()+"\n");
        return true;
    }

    public boolean delete(String e) throws Exception {
        Connection connection =  new DataBaseConn().connection();
        String sql = "delete from   instrumentos  where id_instrumentos = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,e);
        statement.executeUpdate();

        System.out.println("Se elimino un tipo de instrumento.. "+e+"\n");
        return true;
    }
    public Instrumento findById(String id)throws SQLException {
        Connection connection =  new DataBaseConn().connection();

        String sql = "select * from instrumentos where id_instrumentos=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, id);
        ResultSet result = statement.executeQuery();
        if (result.next()){
            return new Instrumento( result.getString(1), result.getString(2), result.getString(3), result.getInt(4),result.getInt(5), result.getDouble(6));

        }
        return null;

    }
}
