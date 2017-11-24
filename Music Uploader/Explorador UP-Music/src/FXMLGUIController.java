/*
 *  Creado por: David Pérez Sánchez
 *  Matrícula: 163202
 *  Materia: 
 *  Universidad Politécnica de Chiapas.
 *  Fecha de Creación: /10/2017
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;

/**
 *
 * @author David Pérez S.
 */
public class FXMLGUIController implements Initializable {
      
      private ExploradorArchivos exploradorArchivos = new ExploradorArchivos();
      private ExploradorImagenes exploradorImagenes = new ExploradorImagenes();
      private ExploradorBD exploradorBD = new ExploradorBD();
      private String rutaArchivo;
      private Alert alertaDeCamposVacios = new Alert(Alert.AlertType.ERROR);
      private Alert alertaDeExito = new Alert(Alert.AlertType.INFORMATION);
      private boolean banderaBotonClickeado1= false;
      private boolean banderaBotonClickeado2= false;
      
      @FXML
      private TextField textFieldRutaArchivo;
      @FXML
      private TextField textFieldRutaImagen;
      @FXML
      private TextField textFieldNombre;
      @FXML
      private TextField textFieldArtista;
      @FXML
      private TextField textFieldAlbum;
      @FXML
      private Label labelVersion;
      @FXML
      private TextField textFieldRutaBD;
      @FXML
      private ChoiceBox choiceBoxGeneros= new ChoiceBox();
      
      @FXML
      public void abrirExploradorDeArchivos(ActionEvent event) {
            rutaArchivo= exploradorArchivos.startExplorer();
            textFieldRutaArchivo.setText(rutaArchivo);
            banderaBotonClickeado1= true;
            banderaBotonClickeado2= true;
      }
      @FXML
      public void abrirExploradorDeImagenes(ActionEvent event) {
            rutaArchivo= exploradorImagenes.startExplorer();
            textFieldRutaImagen.setText(rutaArchivo);
            banderaBotonClickeado1= true;
            banderaBotonClickeado2= true;
      }
      @FXML
      public void abrirExploradorBD(){
            rutaArchivo= exploradorBD.startExplorer();
            textFieldRutaBD.setText(rutaArchivo);
            banderaBotonClickeado1= true;
            banderaBotonClickeado2= true;
      }
      @FXML
      public void limpiarCamposGUI(ActionEvent event){
            textFieldRutaArchivo.clear();
            textFieldNombre.clear();
            textFieldArtista.clear();
            textFieldAlbum.clear();
            textFieldRutaImagen.clear();
            choiceBoxGeneros.setValue("Seleccione un género...");
            banderaBotonClickeado1= false;
            banderaBotonClickeado2= false;
      }
      @FXML
      public void limpiarCampoBD(){
            textFieldRutaBD.clear();
      }
      @FXML
      public void enviarArchivoALaBD(){
            
            if(!banderaBotonClickeado1 || !banderaBotonClickeado2 || textFieldNombre.equals("") || textFieldArtista.equals("") || textFieldAlbum.equals("") || choiceBoxGeneros.getValue() == "Seleccione un género..."){
                  alertaDeCamposVacios.setTitle("¡ Error !");
                  alertaDeCamposVacios.setHeaderText("Faltan campos por llenar aún");
                  alertaDeCamposVacios.showAndWait();
            }else{
                  alertaDeExito.setTitle("Información");
                  alertaDeExito.setHeaderText("Datos guadados exitósamente");
                  alertaDeExito.showAndWait();
            }
      }
      
      @Override
      public void initialize(URL url, ResourceBundle rb) {
            choiceBoxGeneros.setItems(FXCollections.observableArrayList("Seleccione un género...",new Separator(),"Jazz","Pop","Rock",new Separator(),"Otro..."));
            choiceBoxGeneros.setValue("Seleccione un género...");
      }
}