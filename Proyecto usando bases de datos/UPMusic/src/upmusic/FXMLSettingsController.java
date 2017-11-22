package upmusic;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author fer_i
 */
public class FXMLSettingsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private Button guardarCambios;
    
    @FXML
    private ChoiceBox selectTheme;
    
    private ObservableList<String> availableThemes;
    
    private Theme theme;
    private String actualTheme;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        theme = new Theme();
        availableThemes = FXCollections.observableArrayList();
        availableThemes.add("Dark");
        availableThemes.add("Classic");
        availableThemes.add("Orange");
        availableThemes.add("Red");
        
        selectTheme.setItems(availableThemes);
        
        try {
            actualTheme = theme.getUserTheme();
        } catch (IOException ex) {
            System.out.println("Error = " + ex);
        }
        selectTheme.getSelectionModel().select(actualTheme);
        
        //Desabilitar el botón para que no vuelva a guardar el mismo tema en caso que no lo modfique
        guardarCambios.setDisable(true);
        
        selectTheme.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(selectTheme.getSelectionModel().getSelectedItem().equals(actualTheme)){
                    guardarCambios.setDisable(false);
                }else{
                    guardarCambios.setDisable(true);
                }
            }
            
        });
    }    
 
    @FXML
    public void save(ActionEvent e) throws FileNotFoundException{
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("UP Music");
        alert.setContentText("El nuevo tema se ha aplicado correctamente.\nPara ver los cambios es necesario reiniciar la aplicación.");
        alert.showAndWait();
        theme.saveUserTheme((String)selectTheme.getSelectionModel().getSelectedItem());
        Stage st = (Stage) guardarCambios.getScene().getWindow();
        st.close();
    }
}
