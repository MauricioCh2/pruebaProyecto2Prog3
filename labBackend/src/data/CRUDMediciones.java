package data;

import Protocol.Mediciones;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CRUDMediciones {
    //Database db;
    public void create(List<Mediciones> lisM) throws Exception {
        Connection connection =  new DataBaseConn().connection();
        String sql = "INSERT INTO mediciones(`idMediciones`,`referencia`,`lectura`,`id_calibracion`) VALUES (?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        //statement.setInt(1,med.getNumMedicion());
        for(Mediciones med: lisM){
            statement.setInt(1,med.getNumMedicion());
            statement.setDouble(2,med.getValorReferencia());
            statement.setDouble(3,med.getValorMarcado());
            statement.setInt(4,med.getIdCalibracion());
        }


        statement.executeUpdate();

        //System.out.println("Crea medicion.. "+med.getNumMedicion()+"\n");
    }


    public void create(Mediciones[] medicionesArray) throws Exception {
        Connection connection = new DataBaseConn().connection();
        String sql = "INSERT INTO mediciones(`idmediciones`, `referencia`, `lectura`, `id_calibracion`) VALUES (?, ?, ?, ?)";
        Mediciones medicion;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i =0; i < medicionesArray.length;i++) {
                medicion = medicionesArray[i];
                if(medicion==null){break;}
                System.out.print("Estoy en create de mediciones en crud  \n");
                System.out.println("------------------------------------"+medicion.getIdCalibracion());
                System.out.println("------------------------------------"+medicion.getNumMedicion());
                System.out.println("------------------------------------"+medicion.getValorReferencia());
                System.out.println("------------------------------------"+medicion.getValorMarcado());
                statement.setInt(1, i+1);
                statement.setDouble(2, medicion.getValorReferencia());
                statement.setDouble(3, medicion.getValorMarcado());
                statement.setInt(4, medicion.getIdCalibracion());

                statement.executeUpdate();

                System.out.println("Crea medicion.. " + medicion.getNumMedicion() + "\n");
            }
        } catch (SQLException e ) {
            System.out.println(e.getMessage());
        }
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

    public void deleteAll() throws Exception {
        Connection connection = new DataBaseConn().connection();
        String sql = "DELETE FROM mediciones";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            int filasEliminadas = statement.executeUpdate();
            System.out.println("Se eliminaron " + filasEliminadas + " filas de la tabla mediciones.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
