package gui.entity;

import character.Teewada;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import logic.GameSetUp;
import logic.SceneController;

public class TurnChangeScreen extends VBox {

	private static TextTitle turn;
	private static TextTitle name;

	public TurnChangeScreen() {
//		setPadding(new Insets(265,150,250,150));
		setAlignment(Pos.CENTER);
		setSpacing(70);
		
		turn = new TextTitle("Turn " + GameSetUp.cycle, Color.web("0x393E46"), FontWeight.BOLD, 96);

		StackPane turnRoot = new StackPane(turn);
		turnRoot.setBackground(new Background(new BackgroundFill(Color.web("0xfefde8"), null, null)));
		turnRoot.setAlignment(Pos.CENTER);
		//turnRoot.setLayoutY(483);

		name = new TextTitle("" + GameSetUp.cycle, Color.web("0x393E46"), FontWeight.BOLD, 144, 0, 365);
		name.setStrokeWidth(5);

		getChildren().addAll(name,turnRoot);

	}

	public void update() {
		
		turn.setText("Turn " + GameSetUp.cycle);
		name.setText(GameSetUp.thisTurn.getName());
		name.setFill(GameSetUp.thisTurn.getColor());
		if (GameSetUp.thisTurn instanceof Teewada) {
			name.setStroke(Color.web("0x393E46"));
		} else {
			name.setStroke(Color.web("FEFDE8"));
		}
		

	}
}
