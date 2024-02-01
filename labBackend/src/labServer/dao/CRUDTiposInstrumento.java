package labServer.dao;

import Protocol.Listas.TiposInstrumentosList;
import Protocol.TipoInstrumentoObj;
import Protocol.UnidadMedida;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CRUDTiposInstrumento {

    public void create(TipoInstrumentoObj tipo) throws Exception {
        Connection connection =  new DataBaseConn().connection();
        String sql = "insert into tipos_instrumento values (?,?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,tipo.getCodigo());
        statement.setString(2,tipo.getNombre());
        statement.setInt(3, Integer.parseInt(tipo.getUnidad()));

        statement.executeUpdate();
        System.out.println("/n ----Crea tipos de instrumento----  "+tipo.getNombre()+"\n");

    }

    public List<TipoInstrumentoObj> read(List<TipoInstrumentoObj> e) throws Exception {
        ArrayList<TipoInstrumentoObj> lista = new ArrayList<>();
        Connection connection =  new DataBaseConn().connection();
        String sql = "select * from tipos_instrumento";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        while (result.next()) {

            TipoInstrumentoObj tipoIns = new TipoInstrumentoObj( result.getString(1), result.getString(2), result.getInt(3));
            lista.add(tipoIns);
            TiposInstrumentosList.list.add(tipoIns);
        }
        System.out.println("/n ----Lee la lista de tipos de instrumentos ----  \n");
        return lista;
    }


    public List<TipoInstrumentoObj> read() throws Exception {
        ArrayList<TipoInstrumentoObj> lista = new ArrayList<>();
        Connection connection =  new DataBaseConn().connection();
        String sql = "select * from tipos_instrumento";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        while (result.next()) {

            TipoInstrumentoObj tipoIns = new TipoInstrumentoObj( result.getString(1), result.getString(2), result.getInt(3));
            lista.add(tipoIns);
            //TiposInstrumentosList.list.add(tipoIns);
        }
        System.out.println("/n ----Lee la lista de tipos de instrumentos  Kata 2----  \n");
        return lista;
    }

    public boolean update(TipoInstrumentoObj tipo) throws Exception {
        Connection connection =  new DataBaseConn().connection();
        String sql = "update  tipos_instrumento set nombre = ?  where id_tipo_instrumento = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,tipo.getNombre());
        statement.setString(2,tipo.getCodigo());
        statement.executeUpdate();

//        if(){
//            String sql2 = "update  tipos_instrumento set nombre = ?  where id = ?";
//            PreparedStatement statement2 = connection.prepareStatement(sql);
//            statement2.setString(1,tipo.getNombre());
//            statement2.setString(2,tipo.getCodigo());
//            statement2.executeUpdate();
//        }



        System.out.println("Actualizo  tipos de instrumento.. "+tipo.getNombre()+"\n");
        return true;
    }

    public boolean delete(TipoInstrumentoObj tipo) throws Exception {
        Connection connection =  new DataBaseConn().connection();
        String sql = "delete from   tipos_instrumento  where id_tipo_instrumento = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,tipo.getCodigo());
        statement.executeUpdate();

        System.out.println("Se elimino un tipo de instrumento.. "+tipo.getNombre()+"\n");
        return true;
    }

    public boolean delete(String e) throws Exception {
        Connection connection =  new DataBaseConn().connection();
        String sql = "delete from   tipos_instrumento  where id_tipo_instrumento = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,e);
        statement.executeUpdate();

        System.out.println("Se elimino un tipo de instrumento.. "+e+"\n");
        return true;
    }
    public TipoInstrumentoObj findById(String id)throws SQLException{
        Connection connection =  new DataBaseConn().connection();

        String sql = "select * from tipos_instrumento where id_tipo_instrumento=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, id);
        ResultSet result = statement.executeQuery();
        if (result.next()){
            return  new TipoInstrumentoObj( result.getString(1), result.getString(2), result.getInt(3));
        }
        return null;

    }
}
