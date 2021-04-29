package character;

import component.location.Council;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import logic.AudioLoader;
import logic.GameSetUp;

public class Teewada extends MainCharacter {
	
	public static final String IMG_PATH = "img/character/SirTewada.png";
	public static final String IMG_PATH_MINION_IDLE = "img/character/SirTewadaMinionIdle.png";
	
	public static boolean warCry = true;

	public Teewada() {
		super("Sir Tewada", ""); 
		this.selectBGM = AudioLoader.sirTewadaSelectBGM;
		this.color = Color.web("0xFEF67A");
		this.setImg_path("img/character/SirTewada.png");
		this.setPfp(new ImageView(ClassLoader.getSystemResource(IMG_PATH).toString()));
		this.winnerImg = new ImageView(ClassLoader.getSystemResource("img/character/SirTewadaWinner.png").toString());
		this.objectiveInfo1 = "Has government";
		this.objectiveInfo2 = "times in a row.";
		this.skill = "Power of goodness (can use only one time)";
		this.nWinCount = 7;
	}

	public int checkIsWin() {
		int howLong = Council.howLong();
		if (howLong >= nWinCount) {
			this.setWin(true);
//			GameSetUp.theGovernment.setWin(true);
		} else {
			this.setWin(false);
//			GameSetUp.theGovernment.checkIsWin();
		}
		return howLong;
	}
}
