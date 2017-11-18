/*
 * Programa creado por Luis Fernando Hernández Morales
 * Ingeniería en desarrollo de software IV-A
 * Contacto: 163189@ids.upchiapas.edu.mx
 */
package upmusic;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import com.itextpdf.text.BaseColor;

import java.io.IOException;
import com.itextpdf.text.BaseColor;
import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;





public class FXMLRegistrarUsuarioController implements Initializable {
    
    @FXML
    Button INPUT_BOTON_Registrar_usuario;
    
    @FXML
    CheckBox modo_invitado;
     @FXML
    CheckBox adquirir_premium;
    @FXML
    TextField INPUT_TEXTO_Usuario;
    @FXML
    TextField INPUT_TEXTO_Email;
    @FXML
    PasswordField INPUT_TEXTO_Password;
        
    private boolean modo_invitado_seleccionado = false;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    public void RegistrarUsuario(ActionEvent e) throws Exception{        
        
        ButtonType si = new ButtonType("Si");
        ButtonType no = new ButtonType("No");
        boolean usuario_ok = false;
        boolean password_ok = false; 
        boolean email_ok = false;
        boolean invitado = false;
        
        CrearNuevoUsuario nuevo_usuario = new CrearNuevoUsuario();
        String nombreDeUsuario,contrasenia,email;
        
        nombreDeUsuario = nuevo_usuario.ObtenerNombreDeUsuario(INPUT_TEXTO_Usuario);
        contrasenia = nuevo_usuario.ObtenerContraseniaDeUsuario(INPUT_TEXTO_Password);
        email = nuevo_usuario.ObtenerEmailDeUsuario(INPUT_TEXTO_Email);
        
            if(modo_invitado.isSelected()){
                usuario_ok = true;
                password_ok = true;
                email_ok = true;
                invitado = true;
                Alert alert;
                alert = new Alert(AlertType.CONFIRMATION,"",si,no);
                alert.setTitle("¿Esta seguro de entrar en modo de prueba?");
                alert.setHeaderText("¿Qué es el modo de prueba?");
                alert.setContentText("El modo de prueba le permite usar las características gratuitas sin necesidad de registrarse.");
                alert.showAndWait().ifPresent(new Consumer<ButtonType>() {
                @Override
                public void accept(ButtonType response) {
                    //response va a guardar la opción que el usuario eligió
                    if (response == si) {
                    //Entrar a la GUI del reproductor SIN ESCRIBIR EN EL ARCHIVO DE USUARIOS
                    Stage stage = new Stage();
                    Stage stage_acutal = (Stage) INPUT_BOTON_Registrar_usuario.getScene().getWindow();
                    FXMLLoader fxml = new FXMLLoader(getClass().getResource("FXMLCaratula.fxml"));
                    Parent root = null;
                        try {
                            root = (Parent) fxml.load();
                        } catch (IOException ex) {
                            Logger.getLogger(FXMLRegistrarUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    stage.setTitle("UP Music - en sesión [ Invitado ]");
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setScene(new Scene(root));
                    stage.show();
                    stage_acutal.close();
                    }
                }
            });
        }else{
            usuario_ok = false;
            password_ok = false;
            email_ok = false;
            invitado = false;
            
            if(invitado == false){
                /*Si no se ha clickeado el checkbox*/
                if(nuevo_usuario.ValidarLongitudDelCampo(INPUT_TEXTO_Usuario)){
                INPUT_TEXTO_Usuario.setStyle("-fx-background: rgb(66, 163, 79)");
                usuario_ok = true;
                }else{
                    usuario_ok = false;
                    INPUT_TEXTO_Usuario.setStyle("-fx-background: rgb(244, 95, 66)");
                }   
            
                if(nuevo_usuario.ValidarLongitudDelCampo(INPUT_TEXTO_Password)){
                    INPUT_TEXTO_Password.setStyle("-fx-background: rgb(66, 163, 79)");
                    password_ok = true;
                }else{
                    password_ok = false;
                    INPUT_TEXTO_Password.setStyle("-fx-background: rgb(244, 95, 66)");
                }
            
                if(nuevo_usuario.ValidarFormatoDeEmail(INPUT_TEXTO_Email)){
                    INPUT_TEXTO_Email.setStyle("-fx-background: rgb(66, 163, 79)");
                    email_ok = true;
                }else{
                    email_ok = false;
                    INPUT_TEXTO_Email.setStyle("-fx-background: rgb(244, 95, 66)");
                }    
            }
            
            
            if(usuario_ok && password_ok && email_ok){
                //Proceder a registrar
                if(invitado == false){  //Si no activa el modo invitado procede a registrarlo
                    nuevo_usuario.EscribirArchivoDeUsuarios(nombreDeUsuario, contrasenia, email);
                    /*Enviar de nuevo al login screen*/
                    Stage stage = new Stage();
                    Stage stage_actual = (Stage) INPUT_BOTON_Registrar_usuario.getScene().getWindow();
                    FXMLLoader fxml = new FXMLLoader(getClass().getResource("FXMLLoguin.fxml"));
                    Parent root = null;
                        try {
                            root = (Parent) fxml.load();
                        } catch (IOException ex) {
                            Logger.getLogger(FXMLRegistrarUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    stage.getIcons().add(new Image(UPMusic.class.getResourceAsStream("app_icon.png")));
                    stage.setTitle("UP Music - Inicio de sesion");
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setScene(new Scene(root));
                    stage.setResizable(false);
                    stage.show(); 
                    stage_actual.close();
                }else{          //En caso contrario, no los registra y quita los colores verdes
                    INPUT_TEXTO_Usuario.setStyle("-fx-background: rgb(188, 188, 188)");
                    INPUT_TEXTO_Password.setStyle("-fx-background: rgb(188, 188, 188)");
                    INPUT_TEXTO_Email.setStyle("-fx-background: rgb(188, 188, 188)");
                }
            }else{
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Datos erróneos");
                alert.setContentText("Verifique los campos seleccionados en rojo.");
                alert.showAndWait();
            }
            
        }
    }
    
    @FXML
    public void ModoInvitadoSeleccionado(ActionEvent e){
        if(!modo_invitado_seleccionado){
            modo_invitado_seleccionado = true;
            /*Bloquear TextFields*/
            INPUT_TEXTO_Usuario.setText("");
            INPUT_TEXTO_Usuario.setEditable(false);
            INPUT_TEXTO_Usuario.setDisable(true);
            
            INPUT_TEXTO_Password.setText("");
            INPUT_TEXTO_Password.setEditable(false);
            INPUT_TEXTO_Password.setDisable(true);
            
            INPUT_TEXTO_Email.setText("");
            INPUT_TEXTO_Email.setEditable(false);
            INPUT_TEXTO_Email.setDisable(true);
        }else{
            modo_invitado_seleccionado = false;
            /*Desbloquear TextFields*/
            INPUT_TEXTO_Usuario.setEditable(true);
            INPUT_TEXTO_Usuario.setDisable(false);
            
            INPUT_TEXTO_Password.setEditable(true);
            INPUT_TEXTO_Password.setDisable(false);
            
            INPUT_TEXTO_Email.setEditable(true);
            INPUT_TEXTO_Email.setDisable(false);
        }
    }
    @FXML
    public void AdquirirPremiumSeleccionado(ActionEvent e) throws FileNotFoundException, DocumentException{
             ButtonType si = new ButtonType("Si");
        ButtonType no = new ButtonType("No");
        CrearNuevoUsuario nuevo_usuario = new CrearNuevoUsuario();
        String nombreDeUsuario,contrasenia,email;
        
        nombreDeUsuario = nuevo_usuario.ObtenerNombreDeUsuario(INPUT_TEXTO_Usuario);
        contrasenia = nuevo_usuario.ObtenerContraseniaDeUsuario(INPUT_TEXTO_Password);
        email = nuevo_usuario.ObtenerEmailDeUsuario(INPUT_TEXTO_Email);
        
                Alert alert;
                alert = new Alert(AlertType.CONFIRMATION,"",si,no);
                alert.setTitle("¿Esta seguro de adquirir premium?");
                alert.setHeaderText("¿Qué es el modo premium?");
                alert.setContentText("El modo prmium le permite usar las características totales de la aplicación.\nTiene un costo de $100.00\n\tSe generará su ficha de pago.");
                alert.showAndWait().ifPresent(new Consumer<ButtonType>() {
                @Override
                public void accept(ButtonType response){
                    if (response == si) {
                        
                        try{
                            FileOutputStream archivo = new FileOutputStream("Ficha de pago.pdf");
                            Document documento = new Document();
                            PdfWriter.getInstance(documento, archivo);
                            documento.open();
                            documento.add(new Paragraph("Ficha de pago"));
                            documento.add(new Paragraph("Concepto: Pago PREMIUM de UPMusic"));
                            documento.add(new Paragraph("Nombre del Cliente: "+nombreDeUsuario));
                            documento.add(new Paragraph("Correo del Cliente: "+email));
                            documento.add(new Paragraph("Monto total del pago: $100.00"));
                            documento.add(new Paragraph("Cantidad con letra: cien pesos mexicanos"));
                            documento.add(new Paragraph("Numero de cuenta a realizar el pago: xxxx-xxxx-xxxx-xxxx"));
                            documento.add(new Paragraph("Gracias por su suscripción"));  
                            documento.close();
                        }  catch (IOException ex) {
                            Logger.getLogger(FXMLRegistrarUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (DocumentException ex) {
                            Logger.getLogger(FXMLRegistrarUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
    }

}

