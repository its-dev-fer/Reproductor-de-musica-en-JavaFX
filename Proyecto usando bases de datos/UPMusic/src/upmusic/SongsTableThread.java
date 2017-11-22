/*
    Clase que controla la reproducción.
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    //private String blurryBgd = "";
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
    private Button nextBtn, prevBtn,searchBtn;
    private TextField barraBusqueda;
    private String cancionBuscada;
    private TableView tablaGnro;
    
    private int totalDeFilas,cursor;
    
    public SongsTableThread(TableView songsTable, Reproduccion r, Conexion con, boolean playing, ImageView cover, Slider s, BorderPane b, Label info, CheckBox rep, ArrayList array, Button n, Button pre, Button search, TextField bus, TableView tblgnro){
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
        this.searchBtn = search;
        this.barraBusqueda = bus;
        this.tablaGnro = tblgnro;
    }
    
    @Override
    public void run(){
        System.out.println("Hilo de actualizacion de las pistas iniciada :D");
        
        searchBtn.setOnAction((ActionEvent evento) -> {
            System.out.println("Clicked");
            System.out.println("Busqueda = " + barraBusqueda.getText());
            String consulta_tmp = "SELECT * FROM `canciones` WHERE `Titulo` = ";
            ObservableList<Cancion> listaDeCoincidencias = FXCollections.observableArrayList();
            int contadorDeCoincidencias = 0;
            if(!barraBusqueda.getText().equals("")){
                //rgb(173, 173, 173)
                barraBusqueda.setStyle("-fx-background: rgb(173, 173, 173)");
                barraBusqueda.setPromptText("¿Qué vas a escuchar?");
                cancionBuscada = barraBusqueda.getText();
                consulta_tmp += "'" + cancionBuscada + "'";
                System.out.println("Consulta = " + consulta_tmp);
                try{
                    conn.comando = conn.conexion.createStatement();
                    listaDeCanciones = conn.comando.executeQuery(consulta_tmp);                            
                    while(listaDeCanciones.next()){
                        contadorDeCoincidencias++;
                        listaDeCoincidencias.add(new Cancion(listaDeCanciones.getString("Titulo"),listaDeCanciones.getString("Artista"), listaDeCanciones.getString("Album"), listaDeCanciones.getString("Caratula"), listaDeCanciones.getString("ruta"), listaDeCanciones.getString("background")));
                    }
                if(contadorDeCoincidencias == 0){
                    String lastQuery = "INSERT INTO `peticiones`(`Nombre_cancion`) VALUES('" + cancionBuscada + "')";
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("UP Music error");
                    alert.setContentText("La canción '" + cancionBuscada + "' no la tenemos disponible en estos momentos, le haremos saber a los desarrolladores que has buscado esta canción.");
                    alert.showAndWait();
                    barraBusqueda.setText(null);
                    //Agregarla a la lista de peticiones
                    conn.comando = conn.conexion.createStatement();
                    conn.comando.execute(lastQuery);
                    }else{
                        tablaCanciones.setItems(listaDeCoincidencias);
                    }     
                }catch(SQLException ex){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("UP Music error :c");
                    alert.setContentText(String.valueOf(ex));
                    alert.showAndWait();
                }
            }else{
                //barraBusqueda.setText("Ups, estoy seguro que algo falta aquí");
                barraBusqueda.setPromptText("Ups, estoy seguro que algo falta aquí");
                barraBusqueda.setStyle("-fx-background: rgb(244, 95, 66)");
            }
        });
        
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
                                //blurryBgd = listaDeCanciones.getString("background");
                                caratula = new Image("file:" + imgPath);
                                //blurCover = new Image("file:" + blurryBgd);
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
                            //controlsBoxBackground = new Background(new BackgroundImage(blurCover,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,bgdSize));
                            //controlsBox.setBackground(controlsBoxBackground);   
                        }else{
                            try {
                                defaultCover = new Image(new FileInputStream("default.jpg"));
                            } catch (FileNotFoundException ex) {
                                System.out.println("No se encuentra la imagen :c");
                            }
                            //controlsBoxBackground = new Background(new BackgroundImage(defaultCover,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,bgdSize));
                            //controlsBox.setBackground(controlsBoxBackground);
                        }
                    //cuadroCaratula.setFitWidth(150);
                    cuadroCaratula.setFitWidth(tablaGnro.getWidth());
                    cuadroCaratula.setFitHeight(150);
                    cuadroCaratula.setPreserveRatio(true);
                    cuadroCaratula.setSmooth(true);
                    reproductor.setPath(ruta);
                    reproductor.play();                    
                    }else{
                        if(caratula != null){
                            //controlsBoxBackground = new Background(new BackgroundImage(blurCover,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,bgdSize));
                            //controlsBox.setBackground(controlsBoxBackground);   
                        }else{
                            //"default.svg"
                            try {
                                //"default.svg"
                                defaultCover = new Image(new FileInputStream("default.jpg"));
                            } catch (FileNotFoundException ex) {
                                System.out.println("No se encuentra la imagen :c");
                        }
                        //controlsBoxBackground = new Background(new BackgroundImage(blurCover,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,bgdSize));
                        //controlsBox.setBackground(controlsBoxBackground);
                    }
                    lblInfoSong.setText(strInfoSong);
                    strInfoSong = "";
                    cuadroCaratula.setImage(caratula);
                    cuadroCaratula.setFitWidth(tablaGnro.getWidth());
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
                
            }
        });
        
        prevBtn.setOnAction((ActionEvent ev) -> {
            if(reproductor.getPlayer() != null){
            }
        }); 
    }
    
    public TableView getTabla(){
        return tablaCanciones;
    }
    
    public int getCursor(){
        return cursor;
    }
}
