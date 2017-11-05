/*
Esta clase contendrá un hilo que estará buscando constantemente las canciones
del género seleccionado.
 */
package upmusic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Fernando
 */
public class UpdateLibrary extends Thread{
    
    private TableView tablaGeneros;
    private TableView tablaCanciones;
    private Conexion con;
    //private String queryGenero = "SELECT * FROM `genero` WHERE 'Descripcion' = '";
    private String queryGenero = "SELECT * FROM `genero`";
    private String queryCanciones = "";
    private String generoSeleccionado = "";
    private String tmp = "";
    private int id_genero = 1;
    private ResultSet cancionesDelGeneroConsultado;
    private ResultSet songs;
    private ObservableList<Cancion> listaDeCanciones = FXCollections.observableArrayList();
    private Genero itemGenero;
    
    private ArrayList<Integer> ID_GENERO_BD = new ArrayList<>();
    private ArrayList<String> GENERO_BD = new ArrayList<>();
    
    public UpdateLibrary(TableView t, TableView c, Conexion con){
        this.tablaGeneros = t;
        this.tablaCanciones = c;
        this.con = con;
    }
    
    @Override
    public void run(){
        System.out.println("En ejecución :v");
        tablaGeneros.setOnMouseClicked((MouseEvent ev) -> {
            if(ev.getClickCount() >= 1){
                tablaCanciones.getItems().clear();
                System.out.println("==============================================");
                if(tablaGeneros.getSelectionModel().getSelectedItem() != null){
                    generoSeleccionado = String.valueOf(tablaGeneros.getSelectionModel().getSelectedItem());
                    itemGenero = (Genero) tablaGeneros.getSelectionModel().getSelectedItem();
                    try {
                        con.comando = con.conexion.createStatement();
                        cancionesDelGeneroConsultado = con.comando.executeQuery(queryGenero);
                        queryGenero = "SELECT * FROM `genero`";
                        while(cancionesDelGeneroConsultado.next()){
                           id_genero = cancionesDelGeneroConsultado.getInt("ID_Genero");
                           if(itemGenero.equals(cancionesDelGeneroConsultado.getString("Descripcion"))){
                               ID_GENERO_BD.add(id_genero);
                               GENERO_BD.add(cancionesDelGeneroConsultado.getString("Descripcion"));   
                           }
                        }
                        //Una vez obtenido el ID_Genero del género seleccionado, procederemos a consultar todas las canciones que tengan el mismo ID
                        queryCanciones = "SELECT * FROM `canciones` WHERE `Descripcion` = '" + itemGenero.getGenero() + "'";
                    } catch (SQLException ex) {
                        Alert alert = new Alert(AlertType.WARNING);
                        alert.setTitle("UP Music error :c");
                        alert.setContentText(String.valueOf(ex));
                        alert.showAndWait();
                    }
                    
                    try {
                        con.comando = con.conexion.createStatement();
                        songs = con.comando.executeQuery(queryCanciones);
                        
                        while(songs.next()){
                            listaDeCanciones.add( new Cancion(songs.getString("Titulo"), songs.getString("Artista"), songs.getString("Album"), String.valueOf(songs.getDouble("Duracion")), songs.getString("Caratula")));
                        }
                    } catch (SQLException ex) {
                        Alert alert = new Alert(AlertType.WARNING);
                        alert.setTitle("UP Music error :c");
                        alert.setContentText(String.valueOf(ex));
                        alert.showAndWait();
                    }
                    
                    tablaCanciones.setItems(listaDeCanciones);
                }
            }
        });
    }
    
    public ObservableList getSongsList(){
        return listaDeCanciones;
    }
}
