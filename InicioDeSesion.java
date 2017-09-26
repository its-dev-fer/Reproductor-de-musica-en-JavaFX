/*
 * Programa creado por Luis Fernando Hernández Morales
 * Ingeniería en desarrollo de software IV-A
 * Contacto: 163189@ids.upchiapas.edu.mx
 */
package protologuin;

import javafx.scene.control.TextField;
import java.io.*;
/*
Clase que leerá el archivo de texto donde se alojan la informacion de usuarios

Recuerda poner en un comentario qué hiciste y cuándo, por ejemplo:

Fernando: Creé la clase - 25/09/17
*/
public class InicioDeSesion {
    static int cantidad_de_usuarios = 0;
    
    public void LeerArchivoDeUsuarios(){//Este método abrirá el archivo y lo leerá
        //Código para leer el archivo

      File archivo = null;
      FileReader fr = null;
      BufferedReader br = null;

      try {
         // Apertura del fichero y creacion de BufferedReader para poder
         // hacer una lectura comoda (disponer del metodo readLine()).
         archivo = new File ("C:\\usr.txt"); //Cambien la ruta por donde está su archivo
         fr = new FileReader (archivo);
         br = new BufferedReader(fr);

         // Lectura del fichero
         String linea;
         while((linea=br.readLine())!=null)
            System.out.println(linea);
      }
      catch(Exception e){
         e.printStackTrace();
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
    
    private static int ObtenerCantidadDeUsuarios(){//Será llamada por el método anterior
     
        
    //En pocas palabras, regresará la cantidad de lineas que leyó
    
    return cantidad_de_usuarios;
    }
    
    public String ObtenerUsuario(TextField campo_usuario){//Este método recibirá el objeto para escribir el nombre del usuario y devolverá su valor como string
        return campo_usuario.getText();
    }
    
    public String ObtenerContraseniaUsuario(TextField campo_contrasenia){//Este método recibirá el objeto para escribir la contraseña y devolverá su valor como string
        return campo_contrasenia.getText();
    }
    
    //Si se necesitan más métodos hay que hacerlos :v
}
