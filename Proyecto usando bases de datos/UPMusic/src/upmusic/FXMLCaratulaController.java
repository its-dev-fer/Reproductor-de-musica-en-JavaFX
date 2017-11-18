/*
 * Copyright (C) 2017 Israel Gutiérrez

 */
package upmusic;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
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
    
    @FXML
    private Label infoSong;
    
    @FXML
    private BorderPane controlsBox;
    
    @FXML
    private CheckBox repetirCancion;
    
    @FXML
    private CheckBox repetirLista;
    
    @FXML
    private Button searchButton;
    
    @FXML
    private TextField busqueda;
    
    @FXML
    private Label timer;
    
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
    
    private ArrayList<String> rutas = new ArrayList<>();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        reproductor = new Reproduccion(barraTiempo,timer,play,repetirCancion,tablaCanciones,caratula,infoSong);
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
            repetirCancion.setDisable(true);
            barraTiempo.setDisable(true);
            busqueda.setDisable(true);
            searchButton.setDisable(true);
            busqueda.setPromptText("Actualízate a premium para desbloquear todas las funcionalidades :D");
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
        
        actualizarBiblioteca = new UpdateLibrary(tablaGeneros, tablaCanciones, BD,reproductor);
        System.out.println("Lista de canciones = " + listaDeCanciones);
        actualizarTablaDeCanciones = new SongsTableThread(tablaCanciones, reproductor, BD,playing,caratula,barraTiempo,controlsBox,infoSong,repetirCancion, rutas,next,back,searchButton, busqueda);
        actualizarBiblioteca.start();
        actualizarTablaDeCanciones.start();
    }   
}