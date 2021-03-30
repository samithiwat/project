package gui.overlay;

import gui.enity.MenuButton;
import gui.enity.TextTitle;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.FontWeight;
import logic.AudioLoader;
import update.CloseGame;

public class QuitOverlay extends Overlay {

	private final static int WIDTH = 1000;
	private final static int HEIGHT = 500;

	public QuitOverlay() {
		super((new AnchorPane()), WIDTH, HEIGHT, 250, -800);
		prefHeight(HEIGHT);
		prefWidth(WIDTH);
		setId("mainmenu-overlay");

		Rectangle bg = new Rectangle(WIDTH, HEIGHT);
		bg.setFill(Color.rgb(57, 62, 70));

		VBox textBox = new VBox();
		textBox.setAlignment(Pos.CENTER);

		TextTitle line1 = new TextTitle("Do you want to leave?",Color.WHITE,FontWeight.BOLD,50,0,0);
		
		TextTitle line2 = new TextTitle("We will miss you",Color.WHITE,FontWeight.BOLD,50,0,0);

		textBox.getChildren().addAll(line1, line2);
		textBox.setLayoutX(250);
		textBox.setLayoutY(100);

		MenuButton yes = new MenuButton("Yes", 50, 400, 100, Color.WHITE,68,350);
		yes.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				AudioClip effect = AudioLoader.quitSound;
				effect.play();
				Thread t = new Thread(()->{
					try {
						Thread.sleep(1500);
					}
					catch(InterruptedException e) {
						
					}
					
					Platform.runLater(new Runnable() {
						
						@Override
						public void run() {
							CloseGame.setIsCloseGame(true);
							CloseGame.update();
						}
					});
				});
				t.start();
			}
		});

		MenuButton no = new MenuButton("No", 50, 400, 100, Color.WHITE,533,350);
		no.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				triggerOverlay(0,1000,1000);
				setVisible(false);
				Thread t = new Thread(()->{
					try {
						Thread.sleep(1000);
					}
					catch(InterruptedException e) {
						
					}
					Platform.runLater(new Runnable() {
						
						@Override
						public void run() {
							CloseGame.backed();
						}
					});
					
				});
				t.start();
			}
			
		});

		root.getChildren().addAll(bg, yes, no, textBox);
	}

}
