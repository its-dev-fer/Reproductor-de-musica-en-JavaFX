/*
    Clase que controlará la reproducción de una pista
 */
package upmusic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


/**
 *
 * @author fer_i
 */

public class Reproduccion { 
    
    private Media media;
    private String rutaDeLaCancion = new String("");
    private MediaPlayer player;
    private boolean isPlaying;
    
    public Reproduccion(){
        this.isPlaying = false;
    }
    
    public void play(){
        isPlaying = true;
        media = new Media(new File(rutaDeLaCancion).toURI().toString());
        player = new MediaPlayer(media);
        player.play();
    }
    
    public void pause(){
        isPlaying = false;
        player.pause();
    }
    public void stop(){
        isPlaying = false;
        if(player != null){
            player.stop();
        }
    }
    public void nextSong(String path){
         if(player != null){
            media = new Media(new File(rutaDeLaCancion).toURI().toString());
            player = new MediaPlayer(media);
            player.play();   
        }else{
            player.pause();
        }
    }
    public void previousSong(String path){
         if(player != null){
            media = new Media(new File(rutaDeLaCancion).toURI().toString());
            player = new MediaPlayer(media);
            player.play();   
        }else{
            player.pause();
        }
    }
    public void setPath(String s){
        this.rutaDeLaCancion = s;
    }
    public String getPath(){
        return this.rutaDeLaCancion;
    }
    public MediaPlayer getPlayer(){
        return this.player;
    }
    public boolean isPlaying(){
        return this.isPlaying;
    }
}
