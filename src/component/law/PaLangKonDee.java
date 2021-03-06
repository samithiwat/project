package component.law;

import logic.GameSetUp;

public class PaLangKonDee extends LawCard{
	
public static final String IMG_PATH = "img/card/law/GoodPower.png";
	
	public PaLangKonDee() {
		super("PaLangKonDee","When a player with GoodPoint get attacked, the player's attack will increase by 1 point");
		this.img_path = "img/card/law/GoodPower.png";
	}
	public void activateEffectCard() {
		GameSetUp.gameLaw.goodPointAdvantage = 1;
	}
}
