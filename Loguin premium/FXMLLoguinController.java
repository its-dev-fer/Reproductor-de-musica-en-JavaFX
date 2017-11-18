/*
 * Programa creado por Luis Fernando Hernández Morales
 * Ingeniería en desarrollo de software IV-A
 * Contacto: 163189@ids.upchiapas.edu.mx
 */
package upmusic;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author fer_i
 */
public class FXMLLoguinController implements Initializable {

    @FXML
    Button INPUT_BOTON_Ir_a_registrar;
    
    @FXML
    TextField INPUT_TEXTO_NombreDeUsuario;
    
    @FXML
    PasswordField INPUT_TEXT_Password;
    
    @FXML
    Button loguin;
    
    private String usuario;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void IniciarSesion(ActionEvent e) throws IOException{
        InicioDeSesion inicio_de_sesion = new InicioDeSesion();
        String nombreUsuario,contrasenia,cadenaRecuperada;
        String usuario_leido;
        String usuario_split[];
        
        boolean usuario_correcto, contrasenia_correcta;
        
        inicio_de_sesion.LeerArchivoDeUsuarios();
        nombreUsuario = inicio_de_sesion.ObtenerUsuario(INPUT_TEXTO_NombreDeUsuario);
        contrasenia = inicio_de_sesion.ObtenerContraseniaUsuario(INPUT_TEXT_Password);
        
        usuario_leido = inicio_de_sesion.RecuperarUsuarioLeido();
        usuario_split = usuario_leido.split("/");
        
        /*Si los campos se encuentran vacíos debe indicar con color rojo, caso contrario, color verde*/
        if(nombreUsuario.equals("")){
            INPUT_TEXTO_NombreDeUsuario.setStyle("-fx-background: rgb(244, 95, 66)");
        }else{
            INPUT_TEXTO_NombreDeUsuario.setStyle("-fx-background: rgb(66, 163, 79)");
        }
        
        if(contrasenia.equals("")){
            INPUT_TEXT_Password.setStyle("-fx-background: rgb(244, 95, 66)");
        }else{
            INPUT_TEXT_Password.setStyle("-fx-background: rgb(66, 163, 79)");
        }
        
        /*Si el usuario y la contraseña concuerdan*/
        
        if(nombreUsuario.equals(usuario_split[0])){
            usuario_correcto = true;
            INPUT_TEXTO_NombreDeUsuario.setStyle("-fx-background: rgb(66, 163, 79)");
        }else{
            usuario_correcto = false;
            INPUT_TEXTO_NombreDeUsuario.setStyle("-fx-background: rgb(244, 95, 66)");
        }
        
        if(contrasenia.equals(usuario_split[1])){
            INPUT_TEXT_Password.setStyle("-fx-background: rgb(66, 163, 79)");
            contrasenia_correcta = true;
        }else{
            INPUT_TEXT_Password.setStyle("-fx-background: rgb(244, 95, 66)");
            contrasenia_correcta = false;
        }
        
        /*Validar las banderas*/
        if(usuario_correcto && contrasenia_correcta){
            this.usuario = nombreUsuario;
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Inicio de sesión");
            alert.setContentText("Éxito");
            alert.showAndWait();
            
            Stage actual = (Stage)loguin.getScene().getWindow();
            Stage nuevo = new Stage();
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("FXMLCaratula.fxml"));
            Parent root = (Parent) fxml.load();
            
            nuevo.getIcons().add(new Image(UPMusic.class.getResourceAsStream("app_icon.png")));
            nuevo.setTitle("UP Music - en sesión [ " + this.usuario +" ]");
            nuevo.setScene(new Scene(root));
            nuevo.setMinWidth(800);
            nuevo.setMinHeight(600);
            nuevo.show();
            actual.close();
        }else{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Inicio de sesión");
            alert.setContentText("El usuario no existe o los datos son erróneos.");
            alert.showAndWait();
        }
    }
    
    @FXML
    public void AbrirVentanaParaRegistrarUsuario(ActionEvent e) throws IOException{
        /*Abrir la ventana para crear un nuevo usuario*/
        Stage stage = new Stage();
        Stage stage_actual = (Stage) INPUT_BOTON_Ir_a_registrar.getScene().getWindow();
        
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("FXMLRegistrarUsuario.fxml"));
        Parent root = (Parent) fxml.load();
        stage.setTitle("UP Music - Creación de usuario");
        stage.getIcons().add(new Image(UPMusic.class.getResourceAsStream("app_icon.png")));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
        stage_actual.close();
    }
}
