/*
 * Programa creado por Luis Fernando Hernández Morales
 * Ingeniería en desarrollo de software IV-A
 * Contacto: 163189@ids.upchiapas.edu.mx
 */
package protologuin;

import java.io.IOException;
import java.io.FileNotFoundException; 
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.animation.*;
import javafx.application.*;
import javafx.beans.property.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.*;
import javafx.scene.effect.*;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.image.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class FDGUI{
    private static final double cantidadDeDesenfoque = 10;
    private static final Effect EffectoHielo = new BoxBlur(cantidadDeDesenfoque, cantidadDeDesenfoque, 3);
    private static final ImageView fondo = new ImageView();
    private static final StackPane disenio = new StackPane();

    public void PrepararEscena(Stage stage, int tamX, int tamY, String archivoFXML, String nombreDeCSS) throws Exception{
    	disenio.getChildren().setAll(fondo, CrearContenidoDeLaVentana(archivoFXML));//CreateContent() = CrearContenidoDeLaVentana() que recibe el nombre del fxml
        disenio.setStyle("-fx-opacity: 90");//en color decía null ---- -fx-background-color: null; 

        Scene scene = new Scene(
                disenio,
                tamX, tamY,
                //Color.TRANSPARENT
                Color.DARKGRAY
        );

        scene.getStylesheets().add(getClass().getResource(nombreDeCSS).toExternalForm());

    	Platform.setImplicitExit(false);

    	scene.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) Platform.exit();
        });

        PonerVentanaEnBlanco(stage);

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();

        fondo.setImage(CopiarFondoDeLaVentana(stage));//CopyBackground() = CopiarFondoDeLaVentana()
        fondo.setEffect(EffectoHielo);
        makeDraggable(stage, disenio);
    }

 

        private static javafx.scene.shape.Rectangle PonerVentanaEnBlanco(Stage stage){
        	return new javafx.scene.shape.Rectangle(
                stage.getWidth(),
                stage.getHeight(),
                Color.WHITESMOKE.deriveColor(0, 1, 1, 0.08)
        	);
        }

        private  Image CopiarFondoDeLaVentana(Stage stage){
        	final int X = (int) stage.getX();
        	final int Y = (int) stage.getY();
        	final int W = (int) stage.getWidth();
        	final int H = (int) stage.getHeight();

        	try {
            	java.awt.Robot robot = new java.awt.Robot();
            	java.awt.image.BufferedImage image = robot.createScreenCapture(new java.awt.Rectangle(X, Y, W, H));

            	return SwingFXUtils.toFXImage(image, null);
        	} catch (java.awt.AWTException e) {
            	System.out.println("The robot of doom strikes!");
            	return null;
        	}
        }

        private Parent CrearContenidoDeLaVentana(String archivoFXML) throws IOException{
                Parent contenido = null;
                
                try{
                    contenido = FXMLLoader.load(getClass().getResource(archivoFXML));
                }catch(FileNotFoundException e){
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error al abrir el archivo FXML");
                    alert.setHeaderText(null);
                    alert.setContentText("Verifique el nombre del archivo fxml, o simplemente contacte con el desarrollador...");
                    alert.showAndWait();
                }
        	return contenido;
        }


        public void makeDraggable(final Stage stage, final Node byNode) {
        final Delta dragDelta = new Delta();
        byNode.setOnMousePressed(mouseEvent -> {
            // record a delta distance for the drag and drop operation.
            dragDelta.x = stage.getX() - mouseEvent.getScreenX();
            dragDelta.y = stage.getY() - mouseEvent.getScreenY();
            byNode.setCursor(Cursor.MOVE);
        });
        final BooleanProperty inDrag = new SimpleBooleanProperty(false);

        byNode.setOnMouseReleased(mouseEvent -> {
            byNode.setCursor(Cursor.HAND);

            if (inDrag.get()) {
                stage.hide();

                Timeline pause = new Timeline(new KeyFrame(Duration.millis(50), event -> {
                    fondo.setImage(CopiarFondoDeLaVentana(stage));
                    disenio.getChildren().set(
                            0,
                            fondo
                    );
                    stage.show();
                }));
                pause.play();
            }

            inDrag.set(false);
        });
        byNode.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() + dragDelta.x);
            stage.setY(mouseEvent.getScreenY() + dragDelta.y);

            disenio.getChildren().set(
                    0,
                    PonerVentanaEnBlanco(stage)
            );

            inDrag.set(true);
        });
        byNode.setOnMouseEntered(mouseEvent -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                byNode.setCursor(Cursor.HAND);
            }
        });
        byNode.setOnMouseExited(mouseEvent -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                byNode.setCursor(Cursor.DEFAULT);
            }
        });
    }

    private static class Delta {
        double x, y;
    }
}
