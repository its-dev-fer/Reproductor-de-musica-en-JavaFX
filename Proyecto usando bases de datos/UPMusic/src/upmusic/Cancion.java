/*
Clase que será usada para tener las propiedades de una canción
 */
package upmusic;

import javafx.beans.property.SimpleStringProperty;

public class Cancion {
    private final SimpleStringProperty titulo;
    private final SimpleStringProperty artista;
    private final SimpleStringProperty album;
    private final SimpleStringProperty caratula;
    private final SimpleStringProperty ruta;
    private final SimpleStringProperty background;
    
    public Cancion(String title, String artist, String album,String cover, String ruta, String bckg){
        this.titulo = new SimpleStringProperty(title);
        this.artista = new SimpleStringProperty(artist);
        this.album = new SimpleStringProperty(album);
        this.caratula = new SimpleStringProperty(cover);
        this.ruta = new SimpleStringProperty(ruta);
        this.background = new SimpleStringProperty(bckg);
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
    public String getCaratula(){
        return caratula.get();
    }
    public String getRuta(){
        return ruta.get();
    }
    public String getBackground(){
        return background.get();
    }
}
