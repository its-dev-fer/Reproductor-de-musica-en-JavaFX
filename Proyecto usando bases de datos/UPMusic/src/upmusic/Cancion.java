/*
Clase que será usada para tener las propiedades de una canción
 */
package upmusic;

import javafx.beans.property.SimpleStringProperty;

public class Cancion {
    private final SimpleStringProperty titulo;
    private final SimpleStringProperty artista;
    private final SimpleStringProperty album;
    private final SimpleStringProperty duracion;
    private final SimpleStringProperty caratula;
    
    public Cancion(String title, String artist, String album, String duration, String cover){
        this.titulo = new SimpleStringProperty(title);
        this.artista = new SimpleStringProperty(artist);
        this.album = new SimpleStringProperty(album);
        this.duracion = new SimpleStringProperty(duration);
        this.caratula = new SimpleStringProperty(cover);
    }
    
    public String getTitulo(){
        return titulo.get();
    }
    public String getArtista(){
        return artista.get();
    }
    public String getAlbum(){
        return album.get();
    }
    public String getDuracion(){
        return duracion.get();
    }
    public String getCaratula(){
        return caratula.get();
    }
}
