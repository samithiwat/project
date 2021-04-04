package gui.entity;

import javafx.scene.ImageCursor;
import javafx.scene.image.Image;

public interface Clickable {
	
	String FONT_PATH_REGULAR = "font/Bai_Jamjuree/BaiJamjuree-Regular.ttf";
	String FONT_PATH_BOLD = "font/Bai_Jamjuree/BaiJamjuree-Bold.ttf";
	String FONT_PATH = "font/Bai_Jamjuree/BaiJamjuree-Bold.ttf";
	
	ImageCursor MOUSE_NORMAL = new ImageCursor((new Image(ClassLoader.getSystemResource("img/icon/MouseCursor.png").toString())));
	ImageCursor MOUSE_SELECT = new ImageCursor((new Image(ClassLoader.getSystemResource("img/icon/MouseCursorSelected.png").toString())));

	
	public void interact();

	public void triggerDisable(); 
}
