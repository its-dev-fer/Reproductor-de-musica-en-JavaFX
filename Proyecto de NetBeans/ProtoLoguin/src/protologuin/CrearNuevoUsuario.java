/*
 * Programa creado por Luis Fernando Hernández Morales
 * Ingeniería en desarrollo de software IV-A
 * Contacto: 163189@ids.upchiapas.edu.mx
 */
package protologuin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * Clase que será de utilidad para crear un nuevo usuario y su estado
 */

public class CrearNuevoUsuario {
    
    private String nombre_de_usuario = null;
    private String contrasenia_de_usuario = null;
    private String email_de_usuario = null;
    
    public String  ObtenerNombreDeUsuario(TextField campo_usuario){
        return this.nombre_de_usuario = campo_usuario.getText();
    }
    
    public String ObtenerContraseniaDeUsuario(TextField campo_contrasenia){
        return this.contrasenia_de_usuario = campo_contrasenia.getText();
    }
    
    public String ObtenerEmailDeUsuario(TextField campo_email){
        return this.email_de_usuario = campo_email.getText();
    }
    
    public void EscribirArchivoDeUsuarios(String nombre_del_archivo, String usuario, String contrasenia, String email) throws IOException, Exception{
        //Escribir en el fichero
        //File archivoUsuarios = new File(nombre_del_archivo);
        String datos_a_guardar;
        try{
            datos_a_guardar = usuario + "/" + contrasenia + "/" + email;
            //FileWriter escritor = new FileWriter(archivoUsuarios);
            //FileWriter escritor = new FileWriter(getClass().getResource("usr,txt").toExternalForm());
            FileWriter escritor = new FileWriter(nombre_del_archivo);
            BufferedWriter buffer_escritura = new BufferedWriter(escritor);
            PrintWriter pw = new PrintWriter(buffer_escritura);
            pw.write(datos_a_guardar);
            pw.close();
            buffer_escritura.close();
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Aviso");
            alert.setContentText("Se ha registrado el usuario");
            alert.showAndWait();
            
            FDGUI NuevoUsuarioGUI = new FDGUI();
            Stage stage = new Stage();
            //Abrir la ventana para crear un nuevo usuario
        
            FXMLLoader lanzadorDeVentana = new FXMLLoader(getClass().getResource("CrearNuevoUsuario.fxml"));
            Parent root  = (Parent) lanzadorDeVentana.load();
        
            NuevoUsuarioGUI.PrepararEscena(stage, 0, 0, "FXMLDocument.fxml", "loguicss.css");
        }catch(IOException ex){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Algo salió mal :c");
            alert.setContentText("Ha ocurrido un error al obtener los datos de inicio de sesión, quizá el archivo está corrupto.\nContacte con el administrador.");
            alert.showAndWait();
        }
    }
    
    public boolean EstaVacioElCampo(TextField campo){
        boolean resultado;
        if(campo.getText().length() > 0){
            resultado = false;
        }else{
            resultado = true;
        }
        
        return resultado;
    }
}
