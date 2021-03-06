package component.law;

import java.util.ArrayList;

import component.weaponCard.WeaponCard;
import gui.entity.LawCardIcon;
import logic.GameSetUp;

public class LawSlot {
	public static int N_SLOT = 2;
	
	private ArrayList<LawCardIcon> slotCard;
	private ArrayList<WeaponCard> bannedWeapon;
	private ArrayList<WeaponCard> taxedWeapon;
	
	public LawSlot() {
		this.bannedWeapon = new ArrayList<WeaponCard>();
		this.taxedWeapon = new ArrayList<WeaponCard>();
		this.slotCard = new ArrayList<LawCardIcon>();
		for(int i=0;i<N_SLOT;i++) {
			this.slotCard.add(new LawCardIcon(null));			
		}
	}

	public boolean addLawToSlot(LawCardIcon lawCard){
		if (this.slotCard.size() >= 2) {
			return false;
		}
		this.slotCard.add(lawCard);
		return true;
	}
	
	public void setLawAtSlot(int index, LawCardIcon lawCard) {
		this.slotCard.set(index, lawCard);
	}

	public void remove(int index) {
		this.remove(index);
	}

	public void activateAllSlot() {
		GameSetUp.gameLaw.setDefault();
		for (int i = 0; i < this.slotCard.size(); i++) {
			if(this.slotCard.get(i).getLaw()!=null) {
					this.slotCard.get(i).getLaw().activateEffectCard();				
			}				
		}
	}
	
	public int nSlot() {
		return slotCard.size();
	}
	
	public LawCardIcon getSlot(int index) {
		return slotCard.get(index);
	}

	public void setSlot(int index,LawCardIcon law) {
		slotCard.set(index, law);
	}
	
	public void removeSlot(int index) {
		slotCard.remove(index);
	}
	
	public ArrayList<WeaponCard> getBannedWeapon() {
		return bannedWeapon;
	}

	public ArrayList<WeaponCard> getTaxedWeapon() {
		return taxedWeapon;
	}
	
	public ArrayList<LawCardIcon> getSlotCard() {
		return slotCard;
	}
	
}
