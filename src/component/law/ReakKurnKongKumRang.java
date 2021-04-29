package component.law;

import logic.GameSetUp;

public class ReakKurnKongKumRang extends InteractLawCard{
	
	public static final String IMG_PATH = "img/card/law/RecallArmy.png";
	
	public ReakKurnKongKumRang() {
		super("Recall Army","Return all weapon card that have been used in this game into the deck");
		this.img_path = "img/card/law/RecallArmy.png";
		this.icon_img_path = "img/icon/RecallArmyIcon.png";
	}
	public void activateEffectCard() {
		GameSetUp.removedDeck.returnAllToDeck(GameSetUp.weaponDeck);
	}
}
