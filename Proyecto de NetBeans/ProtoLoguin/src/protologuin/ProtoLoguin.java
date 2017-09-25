/*
 * Programa creado por Luis Fernando Hernández Morales
 * Ingeniería en desarrollo de software IV-B
 * Contacto: 163189@ids.upchiapas.edu.mx
 */
package protologuin;


import javafx.application.Application;
import javafx.stage.Stage;



public class ProtoLoguin extends Application {
    
    
    @Override
    public void start(Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        ////////////////////////////////////////Fluent design
        FDGUI fluentDesignGUI = new FDGUI();
        
        fluentDesignGUI.PrepararEscena(stage, 300, 400, "FXMLDocument.fxml", "loguicss.css");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
