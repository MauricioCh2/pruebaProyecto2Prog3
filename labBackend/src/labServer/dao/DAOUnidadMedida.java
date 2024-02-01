package labServer.dao;

import Protocol.Listas.UnidadMedList;
import Protocol.UnidadMedida;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOUnidadMedida {
    public UnidadMedida findById(int id)throws SQLException{
        Connection connection =  new DataBaseConn().connection();

        String sql = "select * from unidad_medida where id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet result = statement.executeQuery();
        if (result.next()){
            return  new UnidadMedida( result.getInt(1), result.getString(2), result.getString(3));
        }
        return null;

    }
    public boolean readUnidadesMedida(UnidadMedList lis) throws SQLException{
        System.out.println("estoy antes de la conexion");

        ArrayList<UnidadMedida> unidadMedidas = new ArrayList<>();
        Connection connection =  new DataBaseConn().connection();
        System.out.println("estoy despues de la conexion");


        String sql = "select * from unidades_medida";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);

        //UnidadMedList list = new UnidadMedList();
        while (result.next()) {
//            System.out.println(result.getInt(1));
//            System.out.println(result.getString(2));
//            System.out.println(result.getString(3));
            UnidadMedida unidadMedida = new UnidadMedida( result.getInt(1), result.getString(2), result.getString(3));
            unidadMedidas.add(unidadMedida);

            //UnidadMedList.addObj(unidadMedida);
            UnidadMedList.addObj(unidadMedida);
        }
        //UnidadMedList.updateList(unidadMedidas);
        return true;
    }
}
