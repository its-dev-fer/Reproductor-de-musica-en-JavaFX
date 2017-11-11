/*
    Clase que controlará la reproducción de una pista
 */
package upmusic;

import java.io.File;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;


/**
 *
 * @author fer_i
 */

public class Reproduccion { 
    
    private Media media;
    private String rutaDeLaCancion = new String("");
    private MediaPlayer player;
    private boolean isPlaying;
    private Slider barraTiempo;
    private Label timer;
    public Button playBtn;
    private CheckBox repetirCancion;
    private Duration duration;
    private SongsTableThread hilo;
    
    public Reproduccion(Slider b, Label l, Button play, CheckBox c,SongsTableThread h){
        this.isPlaying = false;
        this.barraTiempo = b;
        this.timer = l;
        this.playBtn = play;
        this.repetirCancion = c;
        this.hilo = h;
    }
    
    public void play(){
        isPlaying = true;
        media = new Media(new File(rutaDeLaCancion).toURI().toString());
        player = new MediaPlayer(media);
        playBtn.setText("II");
        player.play();        
        
        playBtn.setOnAction((ActionEvent e) -> {
            if(playBtn.getText().equals("II")){
                playBtn.setText("I>");
                player.pause();
            }else{
                playBtn.setText("II");
                //player.play();
                if(player.getCurrentTime() != Duration.ZERO){
                    player.play();
                }else{
                    play();
                }
            }
        });
        
        player.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                playBtn.setText("I>");
                player.stop();
                
                if(repetirCancion.isSelected()){
                    System.out.println("Se repite");
                    play();
                }else{
                    System.out.println("No se va a repetir");
                    playBtn.setText("I>");
                    //updateValues();
                    barraTiempo.setValue(0);
                }
            }
        });
        
        player.currentTimeProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                updateValues();
            }
        });
        
        player.setOnReady(new Runnable(){
            @Override
            public void run() {
                duration = player.getMedia().getDuration();
                updateValues();
            }
        });
                
        barraTiempo.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                if(barraTiempo.isValueChanging()){
                    player.seek(duration.multiply(barraTiempo.getValue()/100));
                }
            }
        });
        //updateValues();
        /*
        player.currentTimeProperty().addListener((Observable) -> {
            
            String currentTime;
            Long l; 
            int tmp;
            int minutos = 0;
            int seconds = 0;
            if(barraTiempo.isValueChanging()){
                player.seek(Duration.seconds((barraTiempo.getValue()*(player.getTotalDuration().toSeconds()/100))));
            }
            if(barraTiempo.isPressed()){
                player.seek(Duration.seconds((barraTiempo.getValue() * (player.getTotalDuration().toSeconds()/100))));
            }
            barraTiempo.setValue((player.getCurrentTime().toSeconds()*100) / player.getTotalDuration().toSeconds()*1);
            //currentTime = String.valueOf(barraTiempo.getValue()*1);
            l = Math.round(player.getCurrentTime().toSeconds()*1);
            currentTime = String.valueOf(Integer.valueOf(l.intValue()));
            if(Integer.valueOf(currentTime) <= 59){
                timer.setText(minutos + " : " + currentTime);
                if(Integer.valueOf(currentTime) == 59){
                    minutos++;
                }
            }else{
                seconds++;
                tmp = Integer.valueOf(currentTime) - 60;
                timer.setText(minutos + ":" + seconds);
            }
        });
    */
    }
    
    public void pause(){
        isPlaying = false;
        player.pause();
    }
    public void stop(){
        isPlaying = false;
        if(player != null){
            player.stop();
            playBtn.setText("I>");
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
    
    public void updateValues(){
        if(player != null){
            Platform.runLater(new Runnable(){
                @Override
                public void run() {
                    Duration tiempoActual = player.getCurrentTime();
                    timer.setText(formatTime(tiempoActual, duration));
                    barraTiempo.setDisable(duration.isUnknown());
                    if(!barraTiempo.isDisabled() && duration.greaterThan(Duration.ZERO) && !barraTiempo.isValueChanging()){
                        barraTiempo.setValue(tiempoActual.divide(duration).toMillis()*100.0);
                    }
                }
            });
        }
    }
    
   private static String formatTime(Duration elapsed, Duration duration) {
    int intElapsed = (int)Math.floor(elapsed.toSeconds());
    int elapsedHours = intElapsed / (60 * 60);
    if (elapsedHours > 0) {
        intElapsed -= elapsedHours * 60 * 60;
    }
    int elapsedMinutes = intElapsed / 60;
    int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 - elapsedMinutes * 60;
 
    if (duration.greaterThan(Duration.ZERO)) {
      int intDuration = (int)Math.floor(duration.toSeconds());
      int durationHours = intDuration / (60 * 60);
      if (durationHours > 0) {
         intDuration -= durationHours * 60 * 60;
      }
    int durationMinutes = intDuration / 60;
    int durationSeconds = intDuration - durationHours * 60 * 60 - 
    durationMinutes * 60;
      if (durationHours > 0) {
         return String.format("%d:%02d:%02d/%d:%02d:%02d", 
            elapsedHours, elapsedMinutes, elapsedSeconds,
            durationHours, durationMinutes, durationSeconds);
      } else {
          return String.format("%02d:%02d/%02d:%02d",
            elapsedMinutes, elapsedSeconds,durationMinutes, 
                durationSeconds);
      }
      } else {
          if (elapsedHours > 0) {
             return String.format("%d:%02d:%02d", elapsedHours, 
                    elapsedMinutes, elapsedSeconds);
            } else {
                return String.format("%02d:%02d",elapsedMinutes, 
                    elapsedSeconds);
            }
        }
    }
}
