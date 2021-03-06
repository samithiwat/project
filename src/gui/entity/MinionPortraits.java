package gui.entity;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class MinionPortraits extends Pane {

	private static double RADIUS = 12.5;
	private static int WIDTH = 25;
	private static int HEIGHT = 25;
	private ImageView img;

	public MinionPortraits(String img_path, double frame_size, int img_x, int img_y) {

		img = new ImageView(ClassLoader.getSystemResource(img_path).toString());
		if (frame_size > 0) {
			img.setViewport(new Rectangle2D(img_x, img_y, frame_size, frame_size));
		}
		img.setFitWidth(WIDTH);
		img.setFitHeight(HEIGHT);

		Circle shape = new Circle(RADIUS, RADIUS, RADIUS);

		setClip(shape);
		setId("character-icon");

		getChildren().add(img);
	}

	public void setImg(String img_path) {
		img.setImage(new Image(ClassLoader.getSystemResource(img_path).toString()));
	}
}
