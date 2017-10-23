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
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
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
