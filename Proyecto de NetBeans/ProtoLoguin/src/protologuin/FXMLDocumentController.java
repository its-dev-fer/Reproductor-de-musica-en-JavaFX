/*
 * Programa creado por Luis Fernando Hernández Morales
 * Ingeniería en desarrollo de software IV-A
 * Contacto: 163189@ids.upchiapas.edu.mx
 */
package protologuin;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class FXMLDocumentController implements Initializable {
    Alert alert;
    
    
    @FXML
    TextField INPUT_LOGUIN_USR,INPUT_LOGUIN_PASS;
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    public void IniciarSesion(ActionEvent e){
        ////////////////////////////////////////
        
        
        /*
        alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Información de inicio de sesion");
        alert.setHeaderText(null);
        alert.setContentText(INPUT_LOGUIN_USR.getText() + " " + INPUT_LOGUIN_PASS.getText());
        alert.showAndWait();
        */
    }
    
    //Para cerrar la ventana es Platform.exit();
    public void CerrarVentana(ActionEvent e){
        Platform.exit();
    }
}
