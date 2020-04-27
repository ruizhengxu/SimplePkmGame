package pkm;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import pkm.controller.OwController;

public class Pkm extends Application {
		
	public static MediaPlayer mediaPlayerOw = new MediaPlayer(new Media(new File("/Users/ruizheng/Work/eclipse/javaFx/Pkm/res/bgm2.wav").toURI().toString()));
	public static MediaPlayer mediaPlayer = new MediaPlayer(new Media(new File("/Users/ruizheng/Work/eclipse/javaFx/Pkm/res/bgm1.wav").toURI().toString()));

	public static FXMLLoader fxmlLoader;
	public static FXMLLoader fxmlLoaderOw;
	public static Parent root;
	public static Scene scene;
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		
		// Play music of OverWorld
		mediaPlayerOw.play();
		
		fxmlLoader = new FXMLLoader(getClass().getResource("./vue/pkm.fxml"));
		fxmlLoaderOw = new FXMLLoader(getClass().getResource("./vue/ow.fxml"));
		
		root = fxmlLoaderOw.load();
		
		scene = new Scene(root);
				
		primaryStage.setScene(scene);
		primaryStage.setTitle("Pokémon");
		primaryStage.getIcons().add(new Image(new File("./res/pokeball.png").toURI().toURL().toString()));
		primaryStage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
	
	public void loadOW() {
		try {
			mediaPlayer.stop();
			mediaPlayerOw.play();
			root = fxmlLoaderOw.load();
			scene.setRoot((Parent) fxmlLoaderOw.getRoot());
			fxmlLoader = new FXMLLoader(getClass().getResource("./vue/pkm.fxml"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadBattle() {
		try {
			OwController.battle = true;
			fxmlLoader = new FXMLLoader(getClass().getResource("./vue/pkm.fxml"));
			mediaPlayerOw.stop();
			mediaPlayer.play();
			root = fxmlLoader.load();
			scene.setRoot((Parent) fxmlLoader.getRoot());
			fxmlLoaderOw = new FXMLLoader(getClass().getResource("./vue/ow.fxml"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
