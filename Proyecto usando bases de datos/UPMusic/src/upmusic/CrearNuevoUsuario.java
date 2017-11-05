/*
 * Programa creado por Luis Fernando Hernández Morales
 * Ingeniería en desarrollo de software IV-A
 * Contacto: 163189@ids.upchiapas.edu.mx
 */
package upmusic;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


/**
 *
 * Clase que será de utilidad para crear un nuevo usuario y su estado
 */

public class CrearNuevoUsuario {
    
    private String nombre_de_usuario = null;
    private String contrasenia_de_usuario = null;
    private String email_de_usuario = null;
    
   public Conexion conexionBD;
   
   public CrearNuevoUsuario(){
       conexionBD = new Conexion();
       conexionBD.conectarBD();
   }
    
    public void registrarUsuario(String nombreUsuario, String password, String correo, boolean premium){
        boolean sePudoRegistrar = true;
        password = password.toString();
        correo = String.valueOf(correo);
        try {
            //String consulta = "INSERT INTO usuarios (Nombre_usuario,Contrasenia,Correo,Premium) VALUES(" + nombreUsuario + "," + password + "," + correo + "," + premium + ")";
            //conexionBD.comando = conexionBD.conexion.createStatement();
            //conexionBD.registro = conexionBD.comando.executeQuery(consulta);
            //String consulta = "INSERT INTO usuarios ('Nombre_usuario','Contrasenia','Correo','Premium') VALUES('" + nombreUsuario + "','" + password + "','" + correo + "','" + premium + "')";
            String consulta = "INSERT INTO `usuarios`(`Nombre_usuario`, `Contrasenia`, `Correo`, `Premium`) VALUES ('" + nombreUsuario + "','" + password + "','" + correo + "'," + premium + ")";
            conexionBD.comando = conexionBD.conexion.createStatement();
            //conexionBD.comando.executeUpdate("INSERT INTO usuarios " + "(Nombre_usuario, Contrasenia, Correo, Premium) " + "VALUES" + "(" + nombreUsuario + password + correo + premium + ")");
            conexionBD.comando.execute(consulta);
            System.out.println("=== Consulta: " + consulta + " ===");
        } catch (SQLException ex) {
            sePudoRegistrar = false;
            //System.out.println("!!! No se pudo crear el registro !!!");
            System.out.println(ex);
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("UP Music error :c");
            alert.setContentText(String.valueOf(ex));
            alert.showAndWait();
        }
        
        if(sePudoRegistrar){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Registro exitoso");
            alert.setContentText("Se ha registrado al usuario " + nombreUsuario);
            alert.showAndWait();
        }else{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Algo salió mal");
            alert.setContentText("No se pudo registrar el usuario, intente lo siguiente:\n1.- Verifique su conexión a Internet.\n2.- Si lo anterior no funcionó, contacte al administrador.");
            alert.showAndWait();
        }
    }
    
    public String  ObtenerNombreDeUsuario(TextField campo_usuario){
        return this.nombre_de_usuario = campo_usuario.getText();
    }
    
    public String ObtenerContraseniaDeUsuario(PasswordField campo_contrasenia){
        return this.contrasenia_de_usuario = campo_contrasenia.getText();
    }
    
    public String ObtenerEmailDeUsuario(TextField campo_email){
        return this.email_de_usuario = campo_email.getText();
    }
    
    public boolean EstaVacioElCampo(TextField campo){
        boolean resultado;
        if(campo.getText().length() > 0){
            resultado = false;
        }else{
            resultado = true;
        }
        
        return resultado;
    }
    
    public boolean EstaVacioElCampo(PasswordField campo){
        boolean resultado;
        if(campo.getText().length() > 0){
            resultado = false;
        }else{
            resultado = true;
        }
        
        return resultado;
    }    
    
    public boolean ValidarLongitudDelCampo(TextField c){
        return c.getText().toString().length() >= 6;
    }
    
    public boolean ValidarLongitudDelCampo(PasswordField c){
        return c.getText().toString().length() >= 6;
    }
    
    public boolean ValidarFormatoDeEmail(TextField t){
        boolean ok = false;
        if(t.getText().contains("@") && t.getText().contains(".")){
            ok = true;
        }
        return ok;
    }
}
