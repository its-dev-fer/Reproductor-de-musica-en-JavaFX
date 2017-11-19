/*
    Clase que controlará la reproducción de una pista
 */
package upmusic;

import java.io.File;

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
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;


/**
 *
 * @author fer_i
 */

public class Reproduccion{ 
    
    private Media media;
    private String rutaDeLaCancion = new String("");
    private MediaPlayer player;
    private boolean isPlaying;
    private Slider barraTiempo;
    private Label timer;
    public Button playBtn;
    private Button next;
    private Button previous;
    private CheckBox repetirCancion;
    private CheckBox repetirLista;
    private Duration duration;
    private TableView tablaDeCanciones;
    private int cursor;
    private int max;
    private int min = 0;
    private Cancion cancion;
    private ImageView caratula;
    private boolean firsTime = true;
    private Label infoSong;
    
    
    public Reproduccion(Slider b, Label l, Button play, CheckBox c, TableView tbl, ImageView img, Label inf, CheckBox repList, Button nxt, Button prev){
        this.isPlaying = false;
        this.barraTiempo = b;
        this.timer = l;
        this.playBtn = play;
        this.repetirCancion = c;
        this.tablaDeCanciones = tbl;
        this.caratula = img;
        this.infoSong = inf;
        this.repetirLista = repList;
        this.next = nxt;
        this.previous = prev;
    }
        
    public void play(){
        System.out.println("Se ha seleccionado la cancion [" + cursor + " de " + max + " ]");
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
        
        next.setOnAction((ActionEvent ev) -> {            
            if(cursor < max){
                if(player != null){
                    player.stop();
                }
                cursor++;
                cancion = (Cancion) tablaDeCanciones.getItems().get(cursor);
                System.out.println("Nueva cancion = " + cancion.getTitulo());
                setPath(cancion.getRuta());
                infoSong.setText(cancion.getTitulo() + " - " + cancion.getArtista());
                caratula.setImage(new Image("file:" + cancion.getCaratula()));
                caratula.setFitWidth(150);
                caratula.setFitHeight(150);
                caratula.setPreserveRatio(true);
                caratula.setSmooth(true);
                play();
            }else{
                if(repetirLista.isSelected()){
                    if(cursor == max){
                        if(player != null){
                           player.stop();
                        }
                        cursor = 0;
                        cancion = (Cancion) tablaDeCanciones.getItems().get(cursor);
                        System.out.println("Nueva cancion = " + cancion.getTitulo());
                        setPath(cancion.getRuta());
                        infoSong.setText(cancion.getTitulo() + " - " + cancion.getArtista());
                        caratula.setImage(new Image("file:" + cancion.getCaratula()));
                        caratula.setFitWidth(150);
                        caratula.setFitHeight(150);
                        caratula.setPreserveRatio(true);
                        caratula.setSmooth(true);
                        play();
                    }
                }
            }
        });
        
        previous.setOnAction((ActionEvent evv) -> {
            if(cursor > 0){
                if(player != null){
                   player.stop();
                }
                cursor--;
                cancion = (Cancion) tablaDeCanciones.getItems().get(cursor);
                System.out.println("Nueva cancion = " + cancion.getTitulo());
                setPath(cancion.getRuta());
                infoSong.setText(cancion.getTitulo() + " - " + cancion.getArtista());
                caratula.setImage(new Image("file:" + cancion.getCaratula()));
                caratula.setFitWidth(150);
                caratula.setFitHeight(150);
                caratula.setPreserveRatio(true);
                caratula.setSmooth(true);
                play();
            }else{
                if(repetirLista.isSelected()){
                    if(cursor == 0){
                        if(player != null){
                           player.stop();
                        }
                        cursor = max;
                        cancion = (Cancion) tablaDeCanciones.getItems().get(cursor);
                        System.out.println("Nueva cancion = " + cancion.getTitulo());
                        setPath(cancion.getRuta());
                        infoSong.setText(cancion.getTitulo() + " - " + cancion.getArtista());
                        caratula.setImage(new Image("file:" + cancion.getCaratula()));
                        caratula.setFitWidth(150);
                        caratula.setFitHeight(150);
                        caratula.setPreserveRatio(true);
                        caratula.setSmooth(true);
                        play();
                    }
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
                    //Preparar el reproductor para reproducir la canción, si la hay.
                    if(cursor < max){
                        //Cuando acabe una canción, se incrementará el cursor
                        cursor++;
                        //Recuperar la siguiente cancion
                        cancion = (Cancion) tablaDeCanciones.getItems().get(cursor);
                        System.out.println("Nueva cancion = " + cancion.getTitulo());
                        setPath(cancion.getRuta());
                        infoSong.setText(cancion.getTitulo() + " - " + cancion.getArtista());
                        caratula.setImage(new Image("file:" + cancion.getCaratula()));
                        caratula.setFitWidth(150);
                        caratula.setFitHeight(150);
                        caratula.setPreserveRatio(true);
                        caratula.setSmooth(true);
                        play();
                    }else{
                        //La reproducción se acaba, a menos que esté seleccionada la casilla de repetir lista
                        System.out.println("No se va a repetir");
                        playBtn.setText("I>");
                        barraTiempo.setValue(0);
                        player.stop();
                        if(repetirLista.isSelected()){
                            if(cursor == max){
                                cursor = 0;
                                cancion = (Cancion) tablaDeCanciones.getItems().get(cursor);
                                System.out.println("Nueva cancion = " + cancion.getTitulo());
                                setPath(cancion.getRuta());
                                infoSong.setText(cancion.getTitulo() + " - " + cancion.getArtista());
                                caratula.setImage(new Image("file:" + cancion.getCaratula()));
                                caratula.setFitWidth(150);
                                caratula.setFitHeight(150);
                                caratula.setPreserveRatio(true);
                                caratula.setSmooth(true);
                                play();
                            }
                        }
                    }
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
                int tmpCursor;
                duration = player.getMedia().getDuration();
                updateValues();
                tablaDeCanciones.getItems().size();
                System.out.println("La tabla tiene un total de " + tablaDeCanciones.getItems().size() + " canciones.");
                max = tablaDeCanciones.getItems().size()-1;
                tmpCursor = tablaDeCanciones.getSelectionModel().getSelectedIndex();
                tablaDeCanciones.getSelectionModel().selectedItemProperty().addListener(new ChangeListener(){
                    @Override
                    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                        firsTime = true;
                    }
                    
                });
                if(firsTime){
                    cursor = tmpCursor;
                    firsTime = false;
                }
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
    }
    
    public void pause(){
        isPlaying = false;
        player.pause();
    }
    
    public void stopSong(){
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
