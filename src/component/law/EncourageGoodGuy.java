package component.law;

import logic.GameSetUp;

public class EncourageGoodGuy extends LawCard {

	public EncourageGoodGuy() {
		super("SaNabSaNoonKonDee", "Player who has GoodPoint, gains 1,000,000 coconuts per GoodPoint");
		this.img_path = "img/card/law/EncourageGoodGuy.png";
	}

	public void activateEffectCard() {
		GameSetUp.gameLaw.giftPerGoodPoint = true;
	}
}