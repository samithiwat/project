package component.law;

import logic.GameSetUp;

public class Unite extends LawCard {

	public Unite() {
		super("SaMakeeProngDong", "Give 5,000,000 coconuts to player who doesn't have any weapon card in hand");
		this.img_path = "img/card/law/Unite.png";
	}

	public void activateEffectCard() {
		GameSetUp.gameLaw.giftNoWeapon = true;
	}
}