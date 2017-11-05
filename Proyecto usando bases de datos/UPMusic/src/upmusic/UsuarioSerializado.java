package upmusic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class UsuarioSerializado {
    private ArrayList<Usuario> listaUsuario = new ArrayList<>();
    private String fileName = "sessiondata.upm";
    private FileOutputStream fo;
    private ObjectOutputStream oos;
    
    private FileInputStream fis;
    private ObjectInputStream ois;
    private Object usuarioRecuperado;
    
    public void serializar(ArrayList a){
        try{
            fo =  new FileOutputStream(fileName);
            oos = new ObjectOutputStream(fo);
            oos.writeObject(a);
            oos.flush();
            oos.close();
        } catch (FileNotFoundException ex) {
            System.out.println("No se encuentra el archivo :c");
        } catch (IOException ex) {
            System.out.println("Error en la escritura...");
        }
    }
    
    public ArrayList recuperarDatosDeSesion(){
        Object  leido;
        ArrayList<Usuario> lista = new ArrayList<>();
        try {
            fis = new FileInputStream(fileName);
            ois = new ObjectInputStream(fis);
            //usuarioRecuperado = ois.readObject();
            leido = ois.readObject();
            leido = (ArrayList<Usuario>) leido;
            ois.close();
        } catch (FileNotFoundException ex) {
            System.out.println("No se encuentra el archivo :c");
        } catch (IOException ex) {
            System.out.println("Error al escribir el archivo :c");
        } catch (ClassNotFoundException ex) {
            System.out.println("No se encuentra la clase Usuario :c");
        }
        return lista;
    }
}
