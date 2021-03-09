package tmedia;

import java.io.File;
import java.net.URL;
//import java.time.Duration;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

/**
 *
 * @author kappa
 */
public class FXMLDocumentController implements Initializable {
    
    private MediaPlayer mediaPlayer;
    protected double value;
    
    @FXML
    private MediaView mediaView;
    
    private String filePath;
    @FXML
    private Button openfile;
    @FXML
    private Button play;
    @FXML
    private Button pause;
    @FXML
    private Button stop;
    @FXML
    private Button slow;
    @FXML
    private Button fast;
    @FXML
    private Button exit;
    @FXML
    private StackPane button;
    @FXML
    private Slider slider;
    @FXML
    private Slider changeduration;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a File (*.mp4)", "*.mp4");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(null);
        filePath = file.toURI().toString();
        
        if(filePath != null){
            Media media = new Media(filePath);
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            DoubleProperty width = mediaView.fitWidthProperty();
            DoubleProperty height = mediaView.fitHeightProperty();
            
            width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
            height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
            
            slider.setValue(mediaPlayer.getVolume() * 100);
            slider.valueProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    mediaPlayer.setVolume(slider.getValue()/100 );
                }
            });
            
            mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                    changeduration.setValue(newValue.toSeconds()*100);
                }
            });
            
            changeduration.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    mediaPlayer.seek(Duration.seconds(changeduration.getValue()/100));
                }
            });
            
            mediaPlayer.play();
        }
    }
    
    @FXML
    private void pauseVideo(ActionEvent event){
        mediaPlayer.pause();
    }
    
    @FXML
    private void playVideo(ActionEvent event){
        mediaPlayer.play();
    }
    
    @FXML
    private void stopVideo(ActionEvent event){
        mediaPlayer.stop();
    }
    
    @FXML
    private void decreaseSpeedofVideo(ActionEvent event){
//        double value;
        value = mediaPlayer.getCurrentRate() - 0.25;
        if(value > 0){
            mediaPlayer.setRate(mediaPlayer.getCurrentRate() - 0.25);
        }
        else{
            mediaPlayer.setRate(1);
        }
        
    }
    
    @FXML
    private void increaseSpeedofVideo(ActionEvent event){
//        double value;
        value = mediaPlayer.getCurrentRate() + 0.25;
        if(value > 0 && value < 5){
            mediaPlayer.setRate(mediaPlayer.getCurrentRate() + 0.25);
        }
        else{
            mediaPlayer.setRate(1);
        }
    }
    
    @FXML
    private void exitVideo(ActionEvent event){
        mediaPlayer.stop();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleButtonAction(MouseEvent event) {
    }
    
}
