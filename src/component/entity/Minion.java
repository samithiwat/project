package component.entity;

import java.util.ArrayList;

import character.BlackSkull;
import character.MainCharacter;
import component.Component;
import component.location.Location;
import component.location.Water;
import exception.InvalidOwnershipException;
import exception.NoMoveLeftException;
import exception.TooFarException;
import exception.WaterTileException;
import gui.entity.HexagonPane;
import logic.GameSetUp;

public class Minion extends Component implements moveable {
	public static final int COST = 3 * MainCharacter.M;
	public static final int MAX_MINION = 6;
	
	private MainCharacter possessedBy;
	private int posX;
	private int posY;
	private ArrayList<Minion> myMinion;
	private Location onLocation;
	private int moveLeft;

	public Minion(MainCharacter possessedBy) {
		super("");
		this.possessedBy = possessedBy;
		possessedBy.addToMyEntity(this);
		myMinion = new ArrayList<Minion>();
		this.moveLeft = 2;
	}

	public void addMinion(Minion minion) {
		this.myMinion.add(minion);
		addGroupMinion(minion);
		minion.possessedBy.removeFromMyEntity(minion);
		minion.getOnLocation().removeFromLocation(minion);
	}

	public void removeMinion(int index) {
		this.myMinion.remove(index);
	}

	public void addGroupMinion(Minion minion) {
		while (minion.myMinion.size() > 0) {
			this.myMinion.add(minion.myMinion.get(0));
			minion.myMinion.remove(0);
		}
	}

	public void returnThisToOwner() {
		this.possessedBy.addToMyEntity(this);
	}

// overwrite
	public void move(int x, int y) throws WaterTileException, NoMoveLeftException,InvalidOwnershipException, TooFarException {
		
		HexagonPane tile = GameSetUp.selectedTile;

		if(this.possessedBy != GameSetUp.thisTurn) {
			throw new InvalidOwnershipException();
		}
		
		else if (this.moveLeft == 0) {
			throw new NoMoveLeftException();
		}

		else if (tile.getLocationType() instanceof Water) {
			throw new WaterTileException();
		}
		else if(!(tile.isMoveable())) {
			throw new TooFarException();
		}

		this.setPosX(this.getPosX() + x);
		this.setPosY(this.getPosY() + y);
		this.onLocation.removeFromLocation(this);
		setOnLocation(GameSetUp.map[this.getPosY()][this.getPosX()]);
		this.onLocation.addMinionToLocation(this);

		this.moveLeft -= 1;
		BlackSkull.checkWin();
	}
	// ----------------------getter/setter--------------------------

	public MainCharacter getPossessedBy() {
		return possessedBy;
	}

	public void setPossessedBy(MainCharacter possessedBy) {
		this.possessedBy = possessedBy;
	}

	public ArrayList<Minion> getMyMinion() {
		return myMinion;
	}

	public void setMyMinion(ArrayList<Minion> myMinion) {
		this.myMinion = myMinion;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getMoveLeft() {
		return moveLeft;
	}

	public void setMoveLeft(int moveLeft) {
		this.moveLeft = moveLeft;
	}

	public Location getOnLocation() {
		return onLocation;
	}

	public void setOnLocation(Location onLocation) {
		this.onLocation = onLocation;
	}

	public static double getCost() {
		return COST;
	}

}
// I'm not sure about this part :/