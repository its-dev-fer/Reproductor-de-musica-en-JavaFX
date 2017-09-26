/*
 * Programa creado por Luis Fernando Hernández Morales
 * Ingeniería en desarrollo de software IV-A
 * Contacto: 163189@ids.upchiapas.edu.mx
 */
package protologuin;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author fer_i
 */
public class CrearNuevoUsuarioController implements Initializable {

    Alert alert;
    @FXML
    TextField INPUT_TEXT_Usuario,INPUT_TEXT_Contrasenia;
    @FXML
    CheckBox CHECKBOX_Invitado;
    @FXML
    TextField INPUT_TEXT_Email;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }   

    public void CerrarVentana(ActionEvent e) throws IOException, Exception{
        FDGUI NuevoUsuarioGUI = new FDGUI();
        //Abrir la ventana de iniciar sesión
        
        FXMLLoader lanzadorDeVentana = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
        
        Parent root  = (Parent) lanzadorDeVentana.load();
        
        NuevoUsuarioGUI.PrepararEscena(null, 0, 0, "FXMLDocument.fxml", "loguicss.css");
    }
    
    public void CrearUsuario(ActionEvent e){
        ButtonType si = new ButtonType("Si");
        ButtonType no = new ButtonType("No");
        CrearNuevoUsuario nuevo_usuario = new CrearNuevoUsuario();
        String nombreDeUsuario,contrasenia,email;
        
        nombreDeUsuario = nuevo_usuario.ObtenerNombreDeUsuario(INPUT_TEXT_Usuario);
        contrasenia = nuevo_usuario.ObtenerContraseniaDeUsuario(INPUT_TEXT_Contrasenia);
        email = nuevo_usuario.ObtenerEmailDeUsuario(INPUT_TEXT_Email);
        
        if(CHECKBOX_Invitado.isSelected()){
            INPUT_TEXT_Usuario.setText("");
            INPUT_TEXT_Usuario.setEditable(false);
            INPUT_TEXT_Usuario.setDisable(true);
            
            INPUT_TEXT_Contrasenia.setText("");
            INPUT_TEXT_Contrasenia.setEditable(false);
            INPUT_TEXT_Contrasenia.setDisable(true);
            
            INPUT_TEXT_Email.setText("");
            INPUT_TEXT_Email.setEditable(false);
            INPUT_TEXT_Email.setDisable(true);
            
            alert = new Alert(AlertType.CONFIRMATION,"",si,no);
            alert.setTitle("¿Esta seguro de entrar en modo de prueba?");
            alert.setHeaderText("¿Qué es el modo de prueba?");
            alert.setContentText("El modo de prueba le permite usar las características gratuitas sin necesidad de registrarse.");
            alert.showAndWait().ifPresent(response ->{  //response va a guardar la opción que el usuario eligió
                if(response == si){
                    //Entrar a la GUI del reproductor SIN ESCRIBIR EN EL ARCHIVO DE USUARIOS
                }
            });
        }else{
            INPUT_TEXT_Usuario.setEditable(true);
            INPUT_TEXT_Usuario.setDisable(false);
            
            INPUT_TEXT_Contrasenia.setEditable(true);
            INPUT_TEXT_Contrasenia.setDisable(false);
            
            INPUT_TEXT_Email.setEditable(true);
            INPUT_TEXT_Email.setDisable(false);
            
            if(nuevo_usuario.EstaVacioElCampo(INPUT_TEXT_Usuario)){
                INPUT_TEXT_Usuario.setStyle("-fx-background: rgb(244, 95, 66)");
            }else{
                INPUT_TEXT_Usuario.setStyle("-fx-background: rgb(66, 163, 79)");
            }
            
            if(nuevo_usuario.EstaVacioElCampo(INPUT_TEXT_Contrasenia)){
                INPUT_TEXT_Contrasenia.setStyle("-fx-background: rgb(244, 95, 66)");
            }else{
                INPUT_TEXT_Contrasenia.setStyle("-fx-background: rgb(66, 163, 79)");
            }
            
            if(nuevo_usuario.EstaVacioElCampo(INPUT_TEXT_Email)){
                INPUT_TEXT_Email.setStyle("-fx-background: rgb(244, 95, 66)");
            }else{
                INPUT_TEXT_Email.setStyle("-fx-background: rgb(66, 163, 79)");
            }
            
            if(!email.contains("@")){
                INPUT_TEXT_Email.setStyle("-fx-background: rgb(244, 95, 66)");
            }else{
                INPUT_TEXT_Email.setStyle("-fx-background: rgb(66, 163, 79)");
                alert = new Alert(AlertType.INFORMATION,"",si,no);
                alert.setTitle("Aviso");
                alert.setHeaderText("Verifique sus datos");
                alert.setContentText("Nombre de usuario: " + nombreDeUsuario + "\nCorreo: " + email);
                alert.showAndWait().ifPresent(response ->{
                    if(response == si){
                        try {
                            //Procede a registrarlo en el archivo y lanzar la interfaz del usuario en modo gratuito
                            nuevo_usuario.EscribirArchivoDeUsuarios("Usuarios.txt", nombreDeUsuario, contrasenia, email);//Ejemplo
                        } catch (IOException ex) {
                            alert.setTitle("Algo salió mal :c");
                            alert.setContentText("Ha ocurrido un error al obtener los datos de inicio de sesión, quizá el archivo está corrupto.\nContacte con el administrador.");
                            alert.showAndWait();
                        }
                    }
                });   
            }
        }
    }
}
