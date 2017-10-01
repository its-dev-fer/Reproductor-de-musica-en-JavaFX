/*
 * Programa creado por Luis Fernando Hernández Morales
 * Ingeniería en desarrollo de software IV-A
 * Contacto: 163189@ids.upchiapas.edu.mx
 */
package upmusic;

import javafx.scene.control.TextField;
import java.io.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


/*
Clase que leerá el archivo de texto donde se aloja la informacion de usuarios

Recuerda poner en un comentario qué hiciste y cuándo, por ejemplo:

Fernando: Creé la clase - 25/09/17
Fernando: Agregué una pila para almacenar a los usuarios ahí
*/
public class InicioDeSesion {
    static int cantidad_de_usuarios = 0;
    private String usuario;
    
    public void LeerArchivoDeUsuarios() throws IOException{//Este método abrirá el archivo y lo leerá
        //Código para leer el archivo
      //Stage stage = new Stage();
      //FileChooser seleccionar_archivo_de_usuarios = new FileChooser();
      File archivo = null;
      FileReader fr = null;
      BufferedReader br = null;
      String linea = null;
      //String ruta = getClass().getResource("usuarios.txt").getPath();
      
      try {
         // Apertura del fichero y creacion de BufferedReader para poder
         // hacer una lectura comoda (disponer del metodo readLine()).
         //archivo = new File (getClass().getResource(ruta).toExternalForm()); //Cambien la ruta por donde está su archivo
         //archivo = new File ("/src/protologuin/usr.txt"); //Cambien la ruta por donde está su archivo
         //archivo = seleccionar_archivo_de_usuarios.showOpenDialog(stage);
         archivo = new File("C:\\Users\\fer_i\\Documents\\UP Chiapas\\Programación visual\\Corte 1\\Proyecto\\UPMusic\\src\\upmusic\\usuarios.txt");
         fr = new FileReader (archivo);
         br = new BufferedReader(fr);

         // Lectura del fichero
         while((linea=br.readLine())!=null){
             System.out.println(linea);
             this.usuario = linea;
            this.cantidad_de_usuarios++;
         }
         
        if(linea == null){    //Si no se encuentra nada en el archivo de usuarios
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setContentText("No encontramos ningún usuario registrado, ¿Por qué no creas uno?");
            alert.showAndWait();
        }
      }catch(FileNotFoundException e){
         Alert alert = new Alert(AlertType.ERROR);
         alert.setTitle("Algo salió mal");
         alert.setContentText("Ha ocurrido un error mientras se leía el archivo de usuarios, contácte con el administrador.");
         alert.showAndWait();
      }finally{
         // En el finally cerramos el fichero, para asegurarnos
         // que se cierra tanto si todo va bien como si salta 
         // una excepcion.
         try{                    
            if( null != fr ){   
               fr.close();     
            }                  
         }catch (Exception e2){ 
            e2.printStackTrace();
         }
      }
    }
    
    public String ObtenerUsuario(TextField campo_usuario){//Este método recibirá el objeto para escribir el nombre del usuario y devolverá su valor como string
        return campo_usuario.getText();
    }
    
    public String ObtenerContraseniaUsuario(TextField campo_contrasenia){//Este método recibirá el objeto para escribir la contraseña y devolverá su valor como string
        return campo_contrasenia.getText();
    }
    
    public String RecuperarUsuarioLeido(){
        return this.usuario;
    }
}
