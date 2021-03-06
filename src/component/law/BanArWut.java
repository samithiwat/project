package component.law;

import component.weaponCard.WeaponCard;
import logic.GameSetUp;

public class BanArWut extends LawCard {
	public static LawCard instance;
	private WeaponCard bannedWeapon;
	public static final String IMG_PATH = "img/card/law/BanWeapon.png";
	
	public BanArWut() {
		super("BanArWut","Players can't use the weapon that is in the ban list");
		this.img_path = "img/card/law/BanWeapon.png";
	}
	
	public BanArWut(WeaponCard weapon) {
		super("BanArWut","Players can't use the weapon that is in the ban list");
		this.img_path = "img/card/law/BanWeapon.png";
		this.bannedWeapon = weapon;
	}
	
	public void activateEffectCard() {
//		don't forget to add weapon in banned weapon
//		parameter name -> GameController.gameLaw.bannedWeapon = bannedWeapon
		GameSetUp.gameLaw.banWeapon = true;
	}

	public WeaponCard getBannedWeapon() {
		return bannedWeapon;
	}

	
// ----------------------------------- Equal Method -------------------------------------------
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		BanArWut other = (BanArWut) obj;
		if (bannedWeapon == null) {
			if (other.bannedWeapon != null)
				return false;
		} else if (!bannedWeapon.equals(other.bannedWeapon))
			return false;
		return true;
	}
}
