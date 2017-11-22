/*
 Clase que gestionará los temas disponibles para el usuario
 */
package upmusic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author fer_i
 */
public class Theme {
    private File themeFile;
    private final String filename;
    private FileReader reader;
    private BufferedReader buffer;
    private PrintWriter writer;
    
    public Theme(){
        this.filename = "themeSettings.upm";
        themeFile = new File(filename);
    }
    
    public void saveUserTheme(String themeName) throws FileNotFoundException{
        writer = new PrintWriter(themeFile);
        writer.print(themeName);
        writer.close();
    }
    
    public String getUserTheme() throws FileNotFoundException, IOException{
        String theme = "";
        String tmp;
        //reader = new FileReader(themeFile);
        reader = new FileReader(themeFile);
        buffer = new BufferedReader(reader);
        
        while((tmp = buffer.readLine()) != null){
            theme = tmp;
        }
        
        reader.close();
        buffer.close();
        return theme;
    }
}
