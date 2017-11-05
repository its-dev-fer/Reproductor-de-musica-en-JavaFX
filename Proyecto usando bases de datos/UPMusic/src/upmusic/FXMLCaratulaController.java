/*
 * Copyright (C) 2017 Israel Gutiérrez

 */
package upmusic;

import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
/**
 * FXML Controller class*
 * @author Israel Gutiérrez
 */
public class FXMLCaratulaController implements Initializable {    
    private boolean isPremium;
    private Conexion BD;
    private UsuarioSerializado us= new UsuarioSerializado();
    private ArrayList<Usuario> data;
    
    @FXML
    private Button play;
    @FXML
    private Button back;
    @FXML
    private Button next;
    
    @FXML
    private TableView tablaGeneros;
    
    @FXML
    private TableView tablaCanciones;
    
    @FXML
    private ImageView caratula;
    
    @FXML
    private Slider barraTiempo;
    //El elemento de abajo es el contenedor de los controles de reproducción
    @FXML
    private BorderPane contenedor;
    private Usuario usuario = new Usuario();
    private String usuarioEnSesion = new String("");
    private ObservableList<Cancion> listaDeCanciones = FXCollections.observableArrayList();
    
    private TableColumn columnaNombreGeneros = new  TableColumn("Géneros");
    private TableColumn columnaNombreCancion = new TableColumn("Título");
    private TableColumn columnaArtistaCancion = new TableColumn("Artista");
    private TableColumn columnaAlbumCancion = new TableColumn("Álbum");
    
    private UpdateLibrary actualizarBiblioteca;
    private SongsTableThread actualizarTablaDeCanciones;
    private Reproduccion reproductor;
    
    private boolean playing;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reproductor = new Reproduccion();
        String query;
        BD = new Conexion();
        BD.conectarBD();
        //Recuperar el nombre del usuario logueado
        usuarioEnSesion = usuario.recuperarUsuario();
        query = "SELECT `Premium` FROM `usuarios` WHERE Nombre_usuario ='" + usuarioEnSesion + "'";
        try {
            //Realizar la consulta SQL para obtener el estado del servicio premium o gratuito
            BD.comando = BD.conexion.createStatement();
            ResultSet results = BD.comando.executeQuery(query);
            
            while(results.next()){
                isPremium = results.getBoolean("Premium");
            }
        } catch (SQLException ex) {
            System.out.println("Error = " + ex);
        }
        
        //Evaluar al usuario
        if(!isPremium){
            back.setDisable(true);
            next.setDisable(true);
        }
        
        ////////////////////////////////////////////////////////////////
        ///   Iniciar las tablas y posteriormente agregar contenido  ///
        ////////////////////////////////////////////////////////////////
        
        //Aquí limpio las columnas que agrega por defecto
        tablaGeneros.getColumns().clear();
        tablaCanciones.getColumns().clear();
        ListaGeneros listaGeneros;
        ObservableList<String> generosDisponibles;
        ObservableList<Genero> contenidoDeLaTablaGeneros = FXCollections.observableArrayList();
        tablaGeneros.setEditable(false);
        tablaCanciones.setEditable(false);
        
        //Hay que hacer una consulta sql para obtener todos los generos existentes.
        //Y posteriormente agregarlos en una lista y poner el contenido de la lista en su respectiva tabla
        
        tablaGeneros.getColumns().addAll(columnaNombreGeneros);
        tablaCanciones.getColumns().addAll(columnaNombreCancion,columnaArtistaCancion,columnaAlbumCancion);
        
        //Preparar y recuperar la lista que contiene todos los géneros existentes
        listaGeneros = new ListaGeneros(BD);
        generosDisponibles = listaGeneros.getArrayDeGeneros();
        
        for(int a = 0; a < generosDisponibles.size(); a++){
            contenidoDeLaTablaGeneros.add(new Genero(generosDisponibles.get(a)));
        }
        
        columnaNombreGeneros.setCellValueFactory(new PropertyValueFactory("genero"));
        tablaGeneros.setItems(contenidoDeLaTablaGeneros);
        
        columnaNombreCancion.setCellValueFactory(new PropertyValueFactory("titulo"));
        columnaArtistaCancion.setCellValueFactory(new PropertyValueFactory("artista"));
        columnaAlbumCancion.setCellValueFactory(new PropertyValueFactory("album"));
        
        actualizarBiblioteca = new UpdateLibrary(tablaGeneros, tablaCanciones, BD);
        actualizarTablaDeCanciones = new SongsTableThread(tablaCanciones, reproductor, BD,play, playing,caratula,barraTiempo);
        //actualizarBiblioteca.run();
        actualizarBiblioteca.start();
        actualizarTablaDeCanciones.start();
        
        
    }   
    
    /*
    public void reproducir(){
        if(reproductor.getPlayer() != null){
            if(play.getText().equals("II")){
                reproductor.getPlayer().pause();
                play.setText("I>");
            }else{
                reproductor.getPlayer().play();
                play.setText("II");
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("UP Music");
            alert.setContentText("No se ha seleccionado una canción aún.");
            alert.showAndWait();
        }
     
    }
    
    public void nextSong(){
        System.out.println("Siguiente");
    }
    */
}