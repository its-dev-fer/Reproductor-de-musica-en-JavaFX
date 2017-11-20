/*
 * Copyright (C) 2017 Israel Gutiérrez
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package upmusic;


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
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

/**
 * FXML Controller class
 *
 * @author Israel Gutiérrez
 */
public class FXMLAboutController implements Initializable {
    @FXML Button ok;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @FXML
    public void Regresar(ActionEvent e){
        Stage stage = new Stage();
                    Stage stage_actual = (Stage) ok.getScene().getWindow();
                    FXMLLoader fxml = new FXMLLoader(getClass().getResource("FXMLLoguin.fxml")); //Pon al que lo vayas a regresar
                    Parent root = null;
                        try {
                            root = (Parent) fxml.load();
                        } catch (IOException ex) {
                            Logger.getLogger(FXMLRegistrarUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    stage.getIcons().add(new Image(UPMusic.class.getResourceAsStream("app_icon.png")));
                    stage.setTitle("UP Music - Inicio de sesion");//Configura el Title de la ventana que lo regresarás
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setScene(new Scene(root));
                    stage.setResizable(false);
                    stage.show(); 
                    stage_actual.close();
    }
}
