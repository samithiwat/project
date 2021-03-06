package gui;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.Transition;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import logic.AudioLoader;
import logic.SceneController;

public class StartMenu{

	private static boolean isVisible = true;
	private static int count;
	private static long lastTimeTrigger = -1;
	private static Scene currentScene;
	private static AnimationTimer animationTimer;
	private static AudioClip menuThemeSong;

	public StartMenu() throws Exception {
		
		ImageView logo = new ImageView(ClassLoader.getSystemResource("img/icon/ChulaIcon.png").toString());
		logo.setFitWidth(600);
		logo.setFitHeight(230);

		StackPane root = new StackPane();
		root.setAlignment(Pos.CENTER);
		root.getChildren().add(logo);

		currentScene = new Scene(root, SceneController.getFullscreenWidth(), SceneController.getFullscreenHeight());
		currentScene.setCursor(Cursor.DISAPPEAR);
	}

	public static Scene getScene() {
		//System.out.println("get Scene");
		return currentScene;
	}
	
	
	public static void setCurrentScene(Scene scene) {
		currentScene = scene;
	}

	public static void setCreditScene(int count) {
		//System.out.println("Switch case :" + count);
		switch (count) {
		case 0:
			setCurrentScene(CUEngineerIcon());
			currentScene.setCursor(Cursor.DISAPPEAR);
			break;
		case 1:
			setCurrentScene(JavaIcon());
			currentScene.setCursor(Cursor.DISAPPEAR);
			break;
		case 2 :
			setCurrentScene(WelcomeText());
			currentScene.setCursor(Cursor.DISAPPEAR);
			break;
		case 3 :
			setCurrentScene(StartBG());
			currentScene.setCursor(Cursor.DISAPPEAR);
			break;
		}
	}


	public static Scene CUEngineerIcon() {
		ImageView logo = new ImageView(ClassLoader.getSystemResource("img/icon/ChulaEngineerLogo.png").toString());
		StackPane root = new StackPane();
		root.setAlignment(Pos.CENTER);
		root.getChildren().add(logo);
		return new Scene(root, SceneController.getFullscreenWidth(), SceneController.getFullscreenHeight());
	}
	
	public static Scene JavaIcon() {
		ImageView logo = new ImageView(ClassLoader.getSystemResource("img/icon/javaIcon.png").toString());
		StackPane root = new StackPane();
		root.setAlignment(Pos.CENTER);
		root.getChildren().add(logo);
		return new Scene(root, SceneController.getFullscreenWidth(), SceneController.getFullscreenHeight());
	}
	
	public static Scene WelcomeText() {
		AudioClip effect = AudioLoader.keyBoardTypingEffect;
		effect.play();
		StackPane root = new StackPane();
		root.setAlignment(Pos.CENTER);
		
		final String content = "Welcome to Coconut Island.";
		Text welcome = new Text("");
		welcome.setFont(Font.font("Bai Jamjuree",FontWeight.MEDIUM,100));
		
		 final Animation animation = new Transition() {
		     {
		         setCycleDuration(Duration.millis(3000));
		     }
		 
		     protected void interpolate(double frac) {
		         final int length = content.length();
		         final int n = Math.round(length * (float) frac);
		         welcome.setText(content.substring(0, n));
		     }
		 
		 };
		 
		 animation.play();
		
		root.getChildren().add(welcome);
		return new Scene(root,SceneController.getFullscreenWidth(),SceneController.getFullscreenHeight());
	}
	
	public static Scene StartBG() {
		
		menuThemeSong = AudioLoader.menuThemeSong;
		menuThemeSong.setCycleCount(AudioClip.INDEFINITE);
		menuThemeSong.play();
		ImageView BG = new ImageView(ClassLoader.getSystemResource("img/background/StartBg.png").toString());
		
		AnchorPane root = new AnchorPane();
		
		
		Text text = new Text(380,750,"Press any key to continue");
		text.setFont(Font.font("Bai Jamjuree",FontWeight.BOLD,72));
		text.setFill(Color.WHITE);
		root.getChildren().addAll(BG,text);
		
		
		animationTimer = new AnimationTimer() {

			@Override
			public void handle(long now) {
				lastTimeTrigger = (lastTimeTrigger < 0 ? now : lastTimeTrigger);
				if(now - lastTimeTrigger >= 700000000) {
					//System.out.println(count);
					count ++;
					if(count%2 == 0) {
						//System.out.println("visible");
						isVisible = true;
						text.setVisible(isVisible);
					}
					else {
						//System.out.println("unvisible");
						isVisible = false;
						text.setVisible(isVisible);
					}
					lastTimeTrigger = now;
				}
				
			}
			
		};
		
		animationTimer.start();
		Scene scene = new Scene(root,SceneController.getFullscreenWidth(),SceneController.getFullscreenHeight());
		scene.setCursor(new ImageCursor((new Image(ClassLoader.getSystemResource("img/icon/mouseCursor.png").toString()))));
		scene.setOnKeyPressed(key ->{
			if(key.getCode() != KeyCode.ALT) {
				AudioClip effect = AudioLoader.clickEffect;
				effect.play();
				SceneController.setScene((new TransitionScreen().getScene()));
				SceneController.setGameSettingMenu((new GameLobbyMenu()).getScene());
			}
			
		});
		return scene;
	}
	
	public static AudioClip getMenuThemeSong() {
		return menuThemeSong;
	}
}
