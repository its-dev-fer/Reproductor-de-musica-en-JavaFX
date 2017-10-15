/*
 * Programa creado por Luis Fernando Hernández Morales
 * Ingeniería en desarrollo de software IV-A
 * Contacto: 163189@ids.upchiapas.edu.mx
 */
package upmusic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class UPMusic extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLLoguin.fxml"));
        Scene scene = new Scene(root,300,400);
        stage.setTitle("UP Music - Inicio de sesion");
        //stage.getIcons().add(new Image("file:src/app_icon.png"));
        //stage.getIcons().add(new Image(getClass().getResourceAsStream("../img/app_icon.png")));
        stage.getIcons().add(new Image(UPMusic.class.getResourceAsStream("app_icon.png")));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
