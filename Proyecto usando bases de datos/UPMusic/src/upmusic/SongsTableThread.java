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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ListIterator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;

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
    private boolean playing;
    private Image blurCover;
    private Image caratula;
    private String imgPath = "";
    private String blurryBgd = "";
    private ImageView cuadroCaratula;
    private Slider barraTiempo;
    private BorderPane controlsBox;
    private Background controlsBoxBackground;
    private BackgroundSize bgdSize;
    private String strInfoSong = "";
    private Label lblInfoSong;
    private Image defaultCover;
    private CheckBox repeatSong;
    
    private ArrayList<String> rutaDeLasCanciones;
    private ListIterator iterator;
    private Button nextBtn, prevBtn;
    
    private int totalDeFilas,cursor;
    
    public SongsTableThread(TableView songsTable, Reproduccion r, Conexion con, boolean playing, ImageView cover, Slider s, BorderPane b, Label info, CheckBox rep, ArrayList array, Button n, Button pre){
        this.bgdSize = new BackgroundSize(b.getWidth(),b.getHeight(),true,true,true,true);
        this.tablaCanciones = songsTable;
        this.reproductor = r;
        this.conn = con;
        this.playing = false;
        this.firstTime = true;
        this.cuadroCaratula = cover;
        this.barraTiempo = s;
        this.controlsBox = b;
        this.lblInfoSong = info;
        this.repeatSong = rep;
        this.rutaDeLasCanciones = array;
        this.nextBtn = n;
        this.prevBtn = pre;
    }
    
    @Override
    public void run(){
        System.out.println("Hilo de actualizacion de las pistas iniciada :D");
        tablaCanciones.getSelectionModel().selectedItemProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(tablaCanciones.getSelectionModel().getSelectedItem() != null){
                    selectedRow = String.valueOf(tablaCanciones.getSelectionModel().getSelectedItem());
                    cursor = tablaCanciones.getSelectionModel().getSelectedIndex();
                    totalDeFilas = tablaCanciones.getItems().size();
                    System.out.println("Cursor: " + cursor + " - De: " + totalDeFilas);
                    song = (Cancion) tablaCanciones.getSelectionModel().getSelectedItem();
                    strInfoSong = song.getTitulo() + " - " + song.getArtista();
                    try{    
                        conn.comando = conn.conexion.createStatement();
                        listaDeCanciones = conn.comando.executeQuery(query);
                        while(listaDeCanciones.next()){
                            if(song.getTitulo().equals(listaDeCanciones.getString("Titulo"))){
                                ruta = listaDeCanciones.getString("ruta");
                                imgPath = listaDeCanciones.getString("Caratula");
                                blurryBgd = listaDeCanciones.getString("background");
                                caratula = new Image("file:" + imgPath);
                                blurCover = new Image("file:" + blurryBgd);
                            }
                        }
                    }catch(SQLException ex){
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("UP Music error :c");
                        alert.setContentText(String.valueOf(ex));
                        alert.showAndWait();
                    }
                    
                    lblInfoSong.setText(strInfoSong);

                    //Ya que tengo la ruta de la canción la mando al reproductor para reproducirla
                    if(reproductor.getPlayer() == null){
                        strInfoSong = "";
                        cuadroCaratula.setImage(caratula);
                        if(caratula != null){
                            controlsBoxBackground = new Background(new BackgroundImage(blurCover,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,bgdSize));
                            controlsBox.setBackground(controlsBoxBackground);   
                        }else{
                            try {
                                defaultCover = new Image(new FileInputStream("default.jpg"));
                            } catch (FileNotFoundException ex) {
                                System.out.println("No se encuentra la imagen :c");
                            }
                            controlsBoxBackground = new Background(new BackgroundImage(defaultCover,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,bgdSize));
                            controlsBox.setBackground(controlsBoxBackground);
                        }
                    cuadroCaratula.setFitWidth(150);
                    cuadroCaratula.setFitHeight(150);
                    cuadroCaratula.setPreserveRatio(true);
                    cuadroCaratula.setSmooth(true);
                    reproductor.setPath(ruta);
                    reproductor.play();                    
                    }else{
                        if(caratula != null){
                            controlsBoxBackground = new Background(new BackgroundImage(blurCover,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,bgdSize));
                            controlsBox.setBackground(controlsBoxBackground);   
                        }else{
                            //"default.svg"
                            try {
                                //"default.svg"
                                defaultCover = new Image(new FileInputStream("default.jpg"));
                            } catch (FileNotFoundException ex) {
                                System.out.println("No se encuentra la imagen :c");
                        }
                        controlsBoxBackground = new Background(new BackgroundImage(blurCover,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,bgdSize));
                        controlsBox.setBackground(controlsBoxBackground);
                    }
                    lblInfoSong.setText(strInfoSong);
                    strInfoSong = "";
                    cuadroCaratula.setImage(caratula);
                    cuadroCaratula.setFitWidth(150);
                    cuadroCaratula.setFitHeight(150);
                    cuadroCaratula.setPreserveRatio(true);
                    cuadroCaratula.setSmooth(true);
                    reproductor.getPlayer().stop();
                    reproductor.setPath(ruta);
                    reproductor.play();
                    playing = true;
                    }
                }
            }
        });
        
        nextBtn.setOnAction((ActionEvent ev) -> {
            if(reproductor.getPlayer() != null){
                if(cursor < totalDeFilas - 1){
                    //Como si existe un reproductor, se manda a pausar
                    reproductor.stop();
                    //Incrementamos el contador
                    cursor++;
                    //Obtenemos los datos de la cancion de la siguiente fila
                    song = (Cancion) tablaCanciones.getItems().get(cursor);
                    //Establecemos la nueva ruta
                    reproductor.setPath(song.getRuta());
                    //Actualizamos la informacion de la cancion
                    strInfoSong = song.getTitulo() + " - " + song.getArtista();
                    //Recuperamos la ruta de la caratula de la canción
                    imgPath = song.getCaratula();
                    blurryBgd = song.getBackground();
                    //Creamos la caratula
                    caratula = new Image("file:" + imgPath);
                    blurCover = new Image("file:" + blurryBgd);
                    //Asignamos la información de la cancion a la etiqueta
                    lblInfoSong.setText(strInfoSong);
                    //Actualizamos el background del borderPane contenedor
                    controlsBoxBackground = new Background(new BackgroundImage(blurCover,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,bgdSize));
                    controlsBox.setBackground(controlsBoxBackground);
                    cuadroCaratula.setFitWidth(150);
                    cuadroCaratula.setFitHeight(150);
                    cuadroCaratula.setPreserveRatio(true);
                    cuadroCaratula.setSmooth(true);
                    cuadroCaratula.setImage(caratula);
                    
                    reproductor.play();
                }else{
                    cursor = 0;
                }
            }
        });
        
        prevBtn.setOnAction((ActionEvent ev) -> {
            if(reproductor.getPlayer() != null){
                if(cursor > 0 && cursor <= totalDeFilas){
                    //Como si existe un reproductor, se manda a pausar
                    reproductor.stop();
                    //Decrementamos el contador
                    cursor--;
                    //Obtenemos los datos de la cancion de la siguiente fila
                    song = (Cancion) tablaCanciones.getItems().get(cursor);
                    //Establecemos la nueva ruta
                    reproductor.setPath(song.getRuta());
                    //Actualizamos la informacion de la cancion
                    strInfoSong = song.getTitulo() + " - " + song.getArtista();
                    //Recuperamos la ruta de la caratula de la canción
                    imgPath = song.getCaratula();
                    blurryBgd = song.getBackground();
                    blurCover = new Image("file:" + blurryBgd);
                    //Creamos la caratula
                    caratula = new Image("file:" + imgPath);
                    //Asignamos la información de la cancion a la etiqueta
                    lblInfoSong.setText(strInfoSong);
                    //Actualizamos el background del borderPane contenedor
                    controlsBoxBackground = new Background(new BackgroundImage(blurCover,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,bgdSize));
                    controlsBox.setBackground(controlsBoxBackground);
                    cuadroCaratula.setFitWidth(150);
                    cuadroCaratula.setFitHeight(150);
                    cuadroCaratula.setPreserveRatio(true);
                    cuadroCaratula.setSmooth(true);
                    cuadroCaratula.setImage(caratula);
                    
                    reproductor.play();
                }else{
                    cursor = totalDeFilas-1;
                }
            }
        }); 
    }
}
