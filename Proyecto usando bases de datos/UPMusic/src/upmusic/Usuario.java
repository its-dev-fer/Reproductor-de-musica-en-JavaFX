/*
 Esta clase almacenará la información del último usuario que inició sesión.
Posteriormente se serializará para poder obtener sus datos sin volver a conectarse a la BD.

Cabe resaltar que NO guardará la contraseña por cuestiones de seguridad.
 */
package upmusic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

public class Usuario implements Serializable{
    private String username;
    private boolean premium;
    private String nameOfFile;
    
    public Usuario(){
        this.nameOfFile = "sessionData.upm";
        //Nada :v
    }
    public Usuario(String usr, boolean premium){
        this.nameOfFile = "sessionData.upm";
        this.username = usr;
        this.premium = premium;
    }
    
    public void setUser(String s){
        this.username = s;
    }
    public void setPremium(boolean b){
        this.premium = b;
    }
    public String getUser(){
        return this.username;
    }
    public boolean getPremiumStatus(){
        return this.premium;
    }
    
    public void almacenarUsuario(String s){
        try {
            PrintWriter escritura = new PrintWriter(nameOfFile);
            escritura.write(s);
            escritura.close();
        } catch (FileNotFoundException ex) {
            System.out.println("No se pudo escribir sobre el archivo...");
        }
    }
    
    public String recuperarUsuario(){
        File file;
        FileReader reader;
        BufferedReader buffer;
        String linea = "";
        try {
            file = new File(nameOfFile);
            reader = new FileReader(file);
            buffer = new BufferedReader(reader);
            String tmp;
            while((tmp = buffer.readLine())!= null){
                System.out.println("Linea = " + linea);
                System.out.println("Tmp = " + tmp);
                linea = tmp;
                System.out.println("Linea = " + linea);
            }
            buffer.close();
            reader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("No se encuentra el archivo...");
        } catch (IOException ex) {
            System.out.println("No se puede leer ek archivo...");
        }
        return linea;
    }
}
