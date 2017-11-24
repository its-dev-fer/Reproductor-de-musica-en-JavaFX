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
public class ExploradorBD {
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
            fileChooser.setTitle("UP Music - Explorador de Base de Datos");
            fileChooser.setInitialDirectory(
                new File(System.getProperty("user.dir"))
            );
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("SQL", "*.sql"),
                new FileChooser.ExtensionFilter("Todos los archivos", "*.*")
            );
    }
}