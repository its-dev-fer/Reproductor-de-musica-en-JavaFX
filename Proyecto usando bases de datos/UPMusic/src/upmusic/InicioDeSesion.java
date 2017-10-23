/*
 * Programa creado por Luis Fernando Hernández Morales
 * Ingeniería en desarrollo de software IV-A
 * Contacto: 163189@ids.upchiapas.edu.mx
 */
package upmusic;


import java.sql.Connection;
import java.sql.ResultSet;
import javafx.scene.control.TextField;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/*
Clase que leerá el archivo de texto donde se aloja la informacion de usuarios

Recuerda poner en un comentario qué hiciste y cuándo, por ejemplo:

Fernando: Creé la clase - 25/09/17
Fernando: Agregué una pila para almacenar a los usuarios ahí
*/
public class InicioDeSesion {
    static int cantidad_de_usuarios = 0;
    
    public Conexion conexionBD;
    private ArrayList<String> listaUsuarios;
    private ArrayList<String> listaUsuariosPassword;
    
    private Conexion c;
    
    public InicioDeSesion(Conexion co){
        //conexionBD = new Conexion();
        c = co;
        this.listaUsuarios = new ArrayList<>();
        this.listaUsuariosPassword = new ArrayList<>();
    }
    
    public void consultarUsuario(String usr, String pass){
        try {
            String consulta = "SELECT * FROM usuarios WHERE Nombre_usuario ='" + usr + "' AND Contrasenia = '" + pass +"'";
            c.comando = c.conexion.createStatement();
            ResultSet results = c.comando.executeQuery(consulta);
            
            while(results.next()){
                listaUsuarios.add(results.getString("Nombre_usuario"));
                listaUsuariosPassword.add(results.getString("Contrasenia"));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public String ObtenerUsuario(TextField campo_usuario){//Este método recibirá el objeto para escribir el nombre del usuario y devolverá su valor como string
        return campo_usuario.getText();
    }
    
    public String ObtenerContraseniaUsuario(TextField campo_contrasenia){//Este método recibirá el objeto para escribir la contraseña y devolverá su valor como string
        return campo_contrasenia.getText();
    }
    
    public ArrayList obtenerUsuariosQueCoinciden(){
        return this.listaUsuarios;
    }
    
    public ArrayList obtenerPasswordsQueCoinciden(){
        return this.listaUsuariosPassword;
    }
}
