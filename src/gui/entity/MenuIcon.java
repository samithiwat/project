package gui.entity;

import javafx.event.EventHandler;
import javafx.scene.ImageCursor;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import logic.AudioLoader;


public class MenuIcon extends ImageView implements Clickable{
	
	public MenuIcon(String img_path,int x, int y) {
		super(ClassLoader.getSystemResource(img_path).toString());
		setX(x);
		setY(y);
		interact();
	}

	@Override
	public void interact() {
		
		
		setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				setCursor(new ImageCursor((new Image(ClassLoader.getSystemResource("img/MouseCursorSelected.png").toString()))));
				AudioClip effect = AudioLoader.mouseEnterSound;
				effect.play();
				setEffect(new DropShadow());
			}
			
		});
		
		setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				setCursor(new ImageCursor((new Image(ClassLoader.getSystemResource("img/MouseCursor.png").toString()))));
				setEffect(null);
			}
		});
	}
	
	public void triggerDisable() {
		setDisable(!isDisable());
	}
}