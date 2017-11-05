package upmusic;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author fer_i
 */
public class Genero{
    
    //private final SimpleStringProperty genero;
    private SimpleStringProperty genero;
    
    public Genero(String s){
        this.genero = new SimpleStringProperty(s);
    }
    
    public String getGenero(){
        return genero.get();
    }
}
