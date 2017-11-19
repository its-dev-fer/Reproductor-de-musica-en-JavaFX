/*
 Esta clase almacenará las canciones que se encuentren en un arrayList
 */
package upmusic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author fer_i
 */
public class SongsList {
    
    //private ArrayList<String> paths;
    private ObservableList<Cancion> paths;
    private int cursor;
    
    public SongsList(){
        this.paths = FXCollections.observableArrayList();
        this.cursor = 0;
    }
    
    private int getCursor(){
        cursor++;
        return cursor;
    }
    
    public int getNextCursor(){
        return ++cursor;
    }
    
    public int getPreviousCursor(){
        return --cursor;
    }
    
    public int getCurrentCursor(){
        return cursor;
    }
    
    public ObservableList getList(){
        return paths;
    }
    
    public void setList(ObservableList a){
        paths = a;
        cursor = 0;
    }
    
    public void setCursor(int c){
        cursor = c;
    }
    
    public boolean hasNext(){
        boolean ok = true;
        try{
            System.out.println(paths.get(getCursor()));
        }catch(IndexOutOfBoundsException ex){
            System.out.println("Ya no hay siguiente");
            ok = false;
        }
        //Regreso el cursor porque previamente lo adelanté :v
        cursor--;
        return ok;
    }
    
    public boolean hasPrevious(){
        boolean ok = true;
        try{
            if(cursor > 0 ){
                cursor--;
                System.out.println(paths.get(cursor));
            }else{
                ok = false;
            }
        }catch(IndexOutOfBoundsException ex){
            System.out.println("No hay anterior");
            ok = false;
        }
        //Regreso el cursor porque previamente lo decrementé :v
        cursor++;
        return ok;
    }
    
    public Cancion getNext(){
        return paths.get(++cursor);
    }
    
    public Cancion getPrevious(){
        return paths.get(--cursor);
    }
}