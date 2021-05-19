package component.law;

import logic.GameSetUp;

public class RecallArmy extends LawCard {

	public RecallArmy() {
		super("Recall Army", "Return all weapon card that have been used in this game into the deck");
		this.img_path = "img/card/law/RecallArmy.png";
	}

	public void activateEffectCard() {
		GameSetUp.removedDeck.returnAllToDeck(GameSetUp.weaponDeck);
	}
}