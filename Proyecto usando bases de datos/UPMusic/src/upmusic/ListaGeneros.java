/*
 Esta clase se encargará de listar todos los generos que se encuentren en la base de datos
*/
package upmusic;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author fer_i
 */
public class ListaGeneros {
    private ObservableList<String> generos = FXCollections.observableArrayList();
    public ListaGeneros(Conexion con){
        String query;
        try {
            con.comando = con.conexion.createStatement();
            query = "SELECT `Descripcion` FROM `genero`";
            ResultSet results = con.comando.executeQuery(query);
            
            while(results.next()){
                generos.add(results.getString("Descripcion"));
            }
            
        } catch (SQLException ex) {
            System.out.println("Error = " + ex);
        }
    }
    
    public ObservableList getArrayDeGeneros(){
        return this.generos;
    }
}
