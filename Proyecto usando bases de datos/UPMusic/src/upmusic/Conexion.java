/*
    Clase que permitirá establecer una conexión con la BD
*/
package upmusic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;


/**
 *
 * @author fer_i
 */
public class Conexion {
    public Connection conexion = null;
    public Statement comando = null;
    public ResultSet registro;
    
    public Connection conectarBD(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String servidor = "jdbc:mysql://localhost:3306/upmusic";
            String BDUser = "UPMUSIC_4B";
            String BDPass = "PV4B.E1.2017";
            conexion = DriverManager.getConnection(servidor,BDUser,BDPass);
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("UP Music");
            alert.setContentText("No podemos conectarnos a nuestras bases de datos :c\n1.- Verifica tu conexión a Internet\n2.- Es probable que los servidores se encuentren en mantenimiento.");
            alert.showAndWait();
        }finally{
            System.out.println("*** Conexion exitosa ***");
            return conexion;
        }
    }
    
    public void close(){
        conexion = null;
        comando = null;
        registro = null;
        System.out.println("*** Se ha cerrado la conexion ***");
    }
}
