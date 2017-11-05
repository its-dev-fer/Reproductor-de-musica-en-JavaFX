/*
 * Copyright (C) 2017 fer_i
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package upmusic;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

/**
 *
 * @author fer_i
 */
public class SongsTableThread extends Thread{
    
    private boolean firstTime;
    private TableView tablaCanciones;
    private Reproduccion reproductor;
    private String ruta = "";
    private Conexion conn;
    private String query = "SELECT * FROM `canciones`";
    private Cancion song;
    private String selectedRow = "";
    private ResultSet listaDeCanciones;
    private Button playButton;
    private boolean playing;
    private Image caratula;
    private String imgPath = "";
    private ImageView cuadroCaratula;
    private double time;
    private Slider barraTiempo;
    
    public SongsTableThread(TableView songsTable, Reproduccion r, Conexion con, Button playBTN, boolean playing, ImageView cover, Slider s){
        this.tablaCanciones = songsTable;
        this.reproductor = r;
        this.conn = con;
        this.playButton = playBTN;
        this.playing = false;
        this.firstTime = true;
        this.cuadroCaratula = cover;
        this.barraTiempo = s;
    }
    
    @Override
    public void run(){
        System.out.println("Hilo de actualizacion de las pistas iniciada :D");
        tablaCanciones.setOnMouseClicked((MouseEvent event) -> {
            //Si el usuario hace doble clic en un elemento disponible de la lista...
            if(event.getClickCount() >= 2){
                //Establecer la ruta de la pista a actualizar
                if(tablaCanciones.getSelectionModel().getSelectedItem() != null){
                    selectedRow = String.valueOf(tablaCanciones.getSelectionModel().getSelectedItem());
                    song = (Cancion) tablaCanciones.getSelectionModel().getSelectedItem();
                    System.out.println("Cancion seleccionada = " + song.getTitulo());
                    try{
                        conn.comando = conn.conexion.createStatement();
                        listaDeCanciones = conn.comando.executeQuery(query);
                        while(listaDeCanciones.next()){
                            if(song.getTitulo().equals(listaDeCanciones.getString("Titulo"))){
                                ruta = listaDeCanciones.getString("ruta");
                                imgPath = listaDeCanciones.getString("Caratula");
                                caratula = new Image("file:" + imgPath);
                                System.out.println("Ruta de la cancion = " + ruta);
                            }
                        }
                    }catch(SQLException ex){
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("UP Music error :c");
                        alert.setContentText(String.valueOf(ex));
                        alert.showAndWait();
                    }
                }
            }
            
            //Ya que tengo la ruta de la canción la mando al reproductor para reproducirla
            /*
            System.out.println("Flag = " + reproductor.isPlaying());
            if(reproductor.isPlaying()){
                reproductor.getPlayer().stop();
                reproductor.setPath(ruta);
                playButton.setText("II");
                reproductor.play();
            }else{
                reproductor.setPath(ruta);
                playButton.setText("II");
                reproductor.play();
            }
            */
            System.out.println("PLAYER = " + reproductor.getPlayer());
            if(reproductor.getPlayer() == null){
                cuadroCaratula.setImage(caratula);
                cuadroCaratula.setFitWidth(100);
                cuadroCaratula.setFitHeight(100);
                cuadroCaratula.setPreserveRatio(true);
                cuadroCaratula.setSmooth(true);
                reproductor.setPath(ruta);
                playButton.setText("II");
                reproductor.play();
                time = reproductor.getPlayer().getTotalDuration().toSeconds();
            }else{
                time = reproductor.getPlayer().getTotalDuration().toSeconds();
                cuadroCaratula.setImage(caratula);
                cuadroCaratula.setFitWidth(100);
                cuadroCaratula.setFitHeight(100);
                cuadroCaratula.setPreserveRatio(true);
                cuadroCaratula.setSmooth(true);
                reproductor.getPlayer().stop();
                reproductor.setPath(ruta);
                playButton.setText("II");
                reproductor.play();
            }
        });
        
        playButton.setOnAction((ActionEvent e) -> {
            if(playButton.getText().equals("II")){
                playButton.setText("I>");
                reproductor.getPlayer().pause();
            }else{
                playButton.setText("II");
                reproductor.getPlayer().play();
            }
        });
    }
}
