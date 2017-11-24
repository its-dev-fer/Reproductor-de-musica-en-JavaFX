import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/*
 *  Creado por: David Pérez Sánchez
 *  Matrícula: 163202
 *  Materia: 
 *  Universidad Politécnica de Chiapas.
 *  Fecha de Creación: /10/2017
 */

/**
 * @author David Pérez S.
 */
public class ExploradorArchivos {

      private String rutaArchivo;
      
    public String startExplorer() {
        
        final FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser);
        rutaArchivo= buscarArchivo(fileChooser);
        return rutaArchivo;
    }
    
    public String buscarArchivo(FileChooser fileChooser) {
          Stage stage = new Stage();
                    File file = fileChooser.showOpenDialog(stage);
                    if (file != null) {
                          rutaArchivo= file.getPath();
                    }
          return rutaArchivo;
    }
    
     private void configureFileChooser(final FileChooser fileChooser) {
            fileChooser.setTitle("UP Music - Explorador de músicas");
            fileChooser.setInitialDirectory(
                new File(System.getProperty("user.dir"))
            );
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("MP3", "*.mp3"),
                new FileChooser.ExtensionFilter("AAC", "*.aac"),
                new FileChooser.ExtensionFilter("MP4", "*.mp4"),
                new FileChooser.ExtensionFilter("WMA", "*.wma"),
                new FileChooser.ExtensionFilter("WAV", "*.wav"),
                new FileChooser.ExtensionFilter("Todos los archivos", "*.*")
            );
    }
}