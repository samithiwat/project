package update;

import java.util.ArrayList;

import character.*;
import component.entity.Minion;
import gui.entity.HexagonPane;
import gui.entity.MapGrid;
import gui.entity.MenuIcon;
import gui.entity.MinionPortraits;
import gui.entity.MinionPane;
import javafx.scene.layout.GridPane;
import logic.GameSetUp;

public class HexTileUpdate {

	private static final int N_COLUMN = 3;

	private static HexagonPane hexTile;

// ---------------------------------------------------- Update Tile's Interact Type ----------------------------------------------------------
	
	public static void setDataInteract() {
		for (int i = 0; i < MapGrid.getGrids().size(); i++) {
			ArrayList<HexagonPane> column = MapGrid.getGrids().get(i);
			for (int j = 0; j < column.size(); j++) {
				column.get(j).dataInteract();
			}
		}
	}
	
	public static void setChallengerInteract() {
		for(int i = 0 ; i < 6 ; i++) {
			MapGrid.getGrids().get(HexagonPane.tileSurroundCouncil[2*i]).get(HexagonPane.tileSurroundCouncil[2*i+1]).dataInteract();;
		}
	}
	
	public static void setOverlayInteract(String mode) {
		for (int i = 0; i < MapGrid.getGrids().size(); i++) {
			ArrayList<HexagonPane> column = MapGrid.getGrids().get(i);
			for (int j = 0; j < column.size(); j++) {
				column.get(j).overlayInteract(mode);
			}
		}
	}
	
	
// ---------------------------------------------------- Update Minion Icon in Hex Tile -----------------------------------------------------------
	
	public static void updateMinionIcon() {
		for (int i = 0; i < MapGrid.getGrids().size(); i++) {
			ArrayList<HexagonPane> column = MapGrid.getGrids().get(i);
			ArrayList<GridPane> iconColumn = MapGrid.getMinionIconPanes().get(i);
			for (int j = 0; j < column.size(); j++) {
				column.get(j).updateMinionIcon(iconColumn.get(j));
			}
		}
	}
	
// ----------------------------------------------------- Show Land Info ----------------------------------------------------------------
	
	public static void showLandInfo() {
		for (int i = 0; i < MapGrid.getGrids().size(); i++) {
			ArrayList<HexagonPane> column = MapGrid.getGrids().get(i);
			for (int j = 0; j < column.size(); j++) {
				column.get(j).showLandInfo();;
			}
		}
	}

// ---------------------------------------------------- Getter and Setter ------------------------------------------------------------

	public static HexagonPane getHexTile() {
		return hexTile;
	}

	public static void setHexTile(HexagonPane hexTile) {
		HexTileUpdate.hexTile = hexTile;
	}

	
}
