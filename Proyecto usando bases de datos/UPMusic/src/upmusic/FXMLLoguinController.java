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

//Importamos las librerias para la conexion
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * FXML Controller class
 *
 * @author fer_i
 */
public class FXMLLoguinController implements Initializable {

    @FXML
    private Button INPUT_BOTON_Ir_a_registrar;
    
    @FXML
    private TextField INPUT_TEXTO_NombreDeUsuario;
    
    @FXML
    private PasswordField INPUT_TEXT_Password;
    
    @FXML
    private Button loguin;
    
    private String usuario;
    private Conexion conexionBD;
    private boolean premiumUser;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conexionBD = new Conexion();
        conexionBD.conectarBD();
    }    
    
    
    public void IniciarSesion(ActionEvent e) throws IOException, SQLException{
        InicioDeSesion inicio_de_sesion = new InicioDeSesion(conexionBD);
        String nombreUsuario,contrasenia;
        ArrayList<String> usuarios;
        ArrayList<String> passwords;
        
        boolean usuario_correcto = false;
        boolean contrasenia_correcta = false;
        
        nombreUsuario = inicio_de_sesion.ObtenerUsuario(INPUT_TEXTO_NombreDeUsuario);
        contrasenia = inicio_de_sesion.ObtenerContraseniaUsuario(INPUT_TEXT_Password);
        
        if(!nombreUsuario.equals("") && !contrasenia.equals("")){//Si los campos no se encuentran vacíos
            inicio_de_sesion.consultarUsuario(nombreUsuario, contrasenia);     
            usuarios = inicio_de_sesion.obtenerUsuariosQueCoinciden();
            passwords = inicio_de_sesion.obtenerPasswordsQueCoinciden();
            premiumUser = inicio_de_sesion.getPremiumStatus();
            
            for(int i = 0; i < usuarios.size(); i++){
                if(nombreUsuario.equals(usuarios.get(i))){
                    usuario_correcto = true;
                    //INPUT_TEXTO_NombreDeUsua= true;rio.setStyle("-fx-background: rgb(66, 163, 79)");
                }else{
                    usuario_correcto = false;
                    //INPUT_TEXTO_NombreDeUsuario.setStyle("-fx-background: rgb(244, 95, 66)");
                }
                
                if(contrasenia.equals(passwords.get(i))){
                    contrasenia_correcta = true;
                }else{
                    contrasenia_correcta = false;
                }
            }
        }
        
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
        if(usuario_correcto && contrasenia_correcta){
            Usuario guardarDatosDeSesion = new Usuario();
            String premiumStatus = "";
            
            this.usuario = nombreUsuario;
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Inicio de sesión");
            alert.setContentText("Éxito");
            alert.showAndWait();
            
            if(premiumUser){
                premiumStatus = "Premium";
            }else{
                premiumStatus = "Free";
            }
            
            guardarDatosDeSesion.almacenarUsuario(usuario);
            
            conexionBD.close();
            Stage actual = (Stage)loguin.getScene().getWindow();
            Stage nuevo = new Stage();
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("FXMLCaratula.fxml"));
            Parent root = (Parent) fxml.load();
            
            nuevo.getIcons().add(new Image(UPMusic.class.getResourceAsStream("app_icon.png")));
            nuevo.setTitle("UP Music - en sesión [ " + this.usuario +" ] - " + premiumStatus);
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
        conexionBD.close();
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
