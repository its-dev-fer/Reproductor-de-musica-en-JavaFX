/*
 * Programa creado por Luis Fernando Hernández Morales
 * Ingeniería en desarrollo de software IV-A
 * Contacto: 163189@ids.upchiapas.edu.mx
 */
package protologuin;

import javafx.scene.control.TextField;

/*
Clase que leerá el archivo de texto donde se alojan la informacion de usuarios

Recuerda poner en un comentario qué hiciste y cuándo, por ejemplo:

Fernando: Creé la clase - 25/09/17
*/
public class InicioDeSesion {
    static int cantidad_de_usuarios = 0;
    
    public void LeerArchivoDeUsuarios(String nombre_del_archivo){//Este método abrirá el archivo y lo leerá
        //Código para leer el archivo
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
