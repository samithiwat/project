package character;

import java.util.ArrayList;

import component.Component;
import component.entity.Minion;
import component.location.Location;
import component.weaponCard.WeaponCard;
import exception.ExceedMinionInTileException;
import exception.ExceedToBuyMinionException;
import exception.FailToBuyLandException;
import exception.UnSpawnableTileException;
import exception.InvalidOwnershipException;
import exception.OutOfMinionException;
import gui.entity.HexagonPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import logic.AudioLoader;
import logic.GameController;
import logic.GameSetUp;
import update.AudioUpdate;

public abstract class MainCharacter extends Component{
	public final static int M = 1000000;

	private ArrayList<WeaponCard> weaponOnHand;
	private ArrayList<Minion> myEntity;
	private ArrayList<Location> possessedArea;
	private int goodPoint;
	private int money;
	private int income;
	private String desciption;
	protected Color color;
	protected AudioClip bgm;
	protected AudioClip selectBGM;
	private int lossPerTurn;
	private boolean isWin;
	private int num_Axe;
	private int num_Sword;
	private int num_Bow;
	private int num_Shield;
	private int num_Gun;
	private String img_path;

	public MainCharacter(String name, String description) {
		super(name);
		this.desciption = description;
		this.money = 7 * M;
		this.weaponOnHand = new ArrayList<WeaponCard>();
		this.myEntity = new ArrayList<Minion>();
		this.possessedArea = new ArrayList<Location>();
		this.goodPoint = 0;
		this.isWin = false;
		this.income = 0;
		this.lossPerTurn = 0;
		this.num_Axe = 0;
		this.num_Bow = 0;
		this.num_Gun = 0;
		this.num_Shield = 0;
		this.num_Sword = 0;
	}

	public void countWeaponCard() {
		this.num_Axe = 0;
		this.num_Bow = 0;
		this.num_Gun = 0;
		this.num_Shield = 0;
		this.num_Sword = 0;
		for (int i = 0; i < weaponOnHand.size(); i++) {
			WeaponCard card = weaponOnHand.get(i);
			if (card.getName().equals("Axe")) {
				this.num_Axe++;
			} else if (card.getName().equals("Bow")) {
				this.num_Bow++;
			} else if (card.getName().equals("Gun")) {
				this.num_Gun++;
			} else if (card.getName().equals("Shield")) {
				this.num_Shield++;
			} else if (card.getName().equals("Sword")) {
				this.num_Sword++;
			}
		}
	}
	
	public int getArea() {
		int cnt = 0;
		for(int i = 0 ; i < this.possessedArea.size() ; i++) {
			Location area = this.possessedArea.get(i);
			if(area.getName().equals("Mine") || area.getName().equals("SecretBase")|| area.getName().equals("Village")|| area.getName().equals("Field")) {
				cnt++;
			}
		}
		return cnt;
	}

	public void addCardtoHand(WeaponCard card) {
		this.weaponOnHand.add(card);
	}

	public WeaponCard removeCardonHand(int index) {
		WeaponCard removedCard = this.weaponOnHand.get(index);
		this.weaponOnHand.remove(index);
		return removedCard;
	}

	public void addToMyEntity(Minion minion) {
		this.myEntity.add(minion);
	}

	public void removeFromMyEntity(Minion minion) {
		for (int i = 0; i < this.myEntity.size(); i++) {
			if (this.myEntity.get(i) == minion) {
				this.myEntity.remove(i);
			}
		}
	}

	public void addPossessedLocation(Location location) {
		this.possessedArea.add(location);
	}

	private void gainIncome() {
		this.setMoney(this.getMoney() + this.income);
	}

	private int totalIncome() {
		int sum = 0;
		for (int i = 0; i < this.possessedArea.size(); i++) {
			sum += this.possessedArea.get(i).getIncomePerRound();
		}
		this.income = sum;
		return sum;
	}

	private void restMinion() {
		for (int i = 0; i < myEntity.size(); i++) {
			myEntity.get(i).setMoveLeft(2);
		}
	}
	
	public void startNewTurn() {
		totalIncome();
		gainIncome();
		restMinion();
	}
	

	public void buyMinion() throws UnSpawnableTileException, ExceedToBuyMinionException, ExceedMinionInTileException, OutOfMinionException {
		if(Minion.MAX_MINION - GameSetUp.thisTurn.getMyEntity().size()<=0) {
			throw new OutOfMinionException();
		}
		if(!GameSetUp.canBuyMinion) {
			throw new ExceedToBuyMinionException();
		}
		if (GameSetUp.selectedTile.getLocationType().getMinionOnLocation().size() >= HexagonPane.getMAX_MINION()) {
			throw new ExceedMinionInTileException();
		}
		if (!GameSetUp.selectedTile.isSpawnable()) {
			throw new UnSpawnableTileException();
		}
			money -= Minion.COST;
			GameController.spawnMinion(new Minion(GameSetUp.thisTurn), GameSetUp.selectedTile);
			AudioUpdate.playCharacterSelectBGM(null, GameSetUp.thisTurn.bgm, GameSetUp.thisTurn.selectBGM);
			GameSetUp.canBuyMinion = false;
		
		
//		if (GameSetUp.canBuyMinion) {
//			if (GameSetUp.selectedTile.getLocationType().getMinionOnLocation().size() >= HexagonPane.getMAX_MINION()) {
//				throw new ExceedMinionInTileException();
//			} else if (!GameSetUp.selectedTile.isSpawnable()) {
//				throw new UnSpawnableTileException();
//
//			} else {
//				money -= Minion.COST;
//				GameController.spawnMinion(new Minion(GameSetUp.thisTurn), GameSetUp.selectedTile);
//				AudioUpdate.playCharacterSelectBGM(null, GameSetUp.thisTurn.bgm, GameSetUp.thisTurn.selectBGM);
//				GameSetUp.canBuyMinion = false;
//			}
//		}
//
//		else {
//			throw new ExceedToBuyMinionException();
//		}
	}

	public void buyLand() throws FailToBuyLandException {
		if (GameSetUp.selectedTile.getLocationType().getOwner() == null) {
			money -= GameSetUp.selectedTile.getLocationType().getCost();
			GameSetUp.selectedTile.getLocationType().setOwner(GameSetUp.thisTurn);
			addPossessedLocation(GameSetUp.selectedTile.getLocationType());
			AudioLoader.buySoundEffect.play();
		} else {
			throw new FailToBuyLandException();
		}
	}

	public void combineMinion() throws InvalidOwnershipException {
		if (GameSetUp.selectedIcon.get(0).getMinion().getPossessedBy() == GameSetUp.selectedIcon.get(1).getMinion()
				.getPossessedBy()) {
			GameSetUp.selectedIcon.get(0).getMinion().addMinion(GameSetUp.selectedIcon.get(1).getMinion());
			GameSetUp.selectedTile.getLocationType().removeFromLocation(GameSetUp.selectedIcon.get(1).getMinion());
			AudioLoader.combineEffect.play();
		} else {
			throw new InvalidOwnershipException();
		}
	}

	public void splitMinion() throws ExceedMinionInTileException, InvalidOwnershipException {

		if (GameSetUp.selectedTile.getLocationType().getMinionOnLocation().size() >= 6) {
			throw new ExceedMinionInTileException();
		} else {
			Minion minion = GameSetUp.selectedIcon.get(0).getMinion();

			int index = findMyMinionIndex(minion);

			if (index < 0) {
				throw new InvalidOwnershipException();
			} else {
				Minion splitedMinion = minion.getMyMinion().get(index);
				minion.removeMinion(index);
				GameSetUp.selectedTile.getLocationType().addMinionToLocation(splitedMinion);
				AudioLoader.splitEffect.play();
			}
		}
	}

	private int findMyMinionIndex(Minion minion) {
		for (int i = 0; i < minion.getMyMinion().size(); i++) {
			if (minion.getMyMinion().get(i).getPossessedBy().equals(GameSetUp.thisTurn)) {
				return i;
			}
		}
		return -1;
	}

	public abstract int checkIsWin();
	// ----------------------getter/setter---------------------
	
	

	public ArrayList<WeaponCard> getWeaponHand() {
		return weaponOnHand;
	}

	public String getImg_path() {
		return img_path;
	}

	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}

	public int getNum_Axe() {
		return num_Axe;
	}

	public int getNum_Sword() {
		return num_Sword;
	}

	public int getNum_Bow() {
		return num_Bow;
	}

	public int getNum_Shield() {
		return num_Shield;
	}

	public int getNum_Gun() {
		return num_Gun;
	}

	public ArrayList<Location> getPossessedArea() {
		return possessedArea;
	}

	public void setPossessedArea(ArrayList<Location> possessedArea) {
		this.possessedArea = possessedArea;
	}

	public void setWeaponHand(ArrayList<WeaponCard> weaponOnHand) {
		this.weaponOnHand = weaponOnHand;
	}

	public ArrayList<WeaponCard> getWeaponOnHand() {
		return weaponOnHand;
	}

	public void setWeaponOnHand(ArrayList<WeaponCard> weaponOnHand) {
		this.weaponOnHand = weaponOnHand;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = Math.max(0, money);
	}

	public ArrayList<Minion> getMyEntity() {
		return myEntity;
	}

	public int getGoodPoint() {
		return goodPoint;
	}

	public void setGoodPoint(int goodPoint) {
		this.goodPoint = goodPoint;
	}

	public String getDesciption() {
		return desciption;
	}

	public void setDesciption(String desciption) {
		this.desciption = desciption;
	}

	public void setMyEntity(ArrayList<Minion> myEntity) {
		this.myEntity = myEntity;
	}

	public void setWin(boolean isWin) {
		this.isWin = isWin;
	}

	public Color getColor() {
		return color;
	}

	public int getIncome() {
		return income;
	}

	public void setIncome(int income) {
		this.income = income;
	}

	public boolean isWin() {
		return isWin;
	}

	public int getLossPerTurn() {
		return lossPerTurn;
	}

	public void setLossPerTurn(int lossPerTurn) {
		this.lossPerTurn = lossPerTurn;
	}

	public AudioClip getBgm() {
		return bgm;
	}

	public AudioClip getSelectBGM() {
		return selectBGM;
	}

////////////////////////////////////////////////////////////// FOR DEBUG ONLY ///////////////////////////////////////////////////////////////////

	public String toString() {
		return "-------------------------------------\n" + "Name: " + getName() + "\n" + "Description: "
				+ getDesciption() + "\n" + "GoodPoint: " + getGoodPoint() + "\n" + "Weapond on hand: " + getWeaponHand()
				+ "\n" + "Money: " + getMoney() + "\n" + "Minion" + getMyEntity() + "\n"
				+ "-------------------------------------";
	}

////////////////////////////////////////////////////////////// END OF DEBUG ///////////////////////////////////////////////////////////////////

}
