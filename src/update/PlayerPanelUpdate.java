package update;

import java.util.ArrayList;

import character.MainCharacter;
import gui.entity.HexagonPane;
import gui.entity.MapGrid;
import gui.entity.PlayerPanel;
import gui.entity.StatusBar;
import gui.entity.StatusPane;
import gui.entity.TextTitle;
import gui.entity.TurnBar;
import gui.entity.TurnCharacterIcon;
import javafx.scene.paint.Color;
import logic.GameController;
import logic.GameSetUp;

public class PlayerPanelUpdate {

// ------------------------------------------------------------ Update Crown In Turn Bar --------------------------------------------------------

	public static void updateTurnBar() {
		for (int i = 0; i < GameSettingUpdate.getNPlayer(); i++) {

			// -------------------------------- Find Current Turn Player --------------------------------

			if (GameSetUp.thisTurn == GameSetUp.gameCharacter.get(i)) {
				
				TurnCharacterIcon currentPlayerIcon = (TurnCharacterIcon) TurnBar.getIconPane().getChildren().get(i);
				TurnCharacterIcon peviousPlayerIcon = null;
				if (i > 0) {
					peviousPlayerIcon = (TurnCharacterIcon) TurnBar.getIconPane().getChildren().get(i - 1);
				} else {
					peviousPlayerIcon = (TurnCharacterIcon) TurnBar.getIconPane().getChildren()
							.get(GameSettingUpdate.getNPlayer() - 1);
				}

				// ------------------------------------- Update Crown --------------------------------------

				peviousPlayerIcon.setPlayerTurn(false);
				currentPlayerIcon.setPlayerTurn(true);
				break;
			}
		}
	}

// ---------------------------------------------------------- Update Player Status -------------------------------------------------------------

	public static void updateStatusPane() {
		MainCharacter character = GameSetUp.thisTurn;

		double money = character.getMoney() / MainCharacter.M;

		TextTitle moneyStatus = StatusPane.getMoney().getStatus();
		TextTitle minionStatus = StatusPane.getMinion().getStatus();
		TextTitle landStatus = StatusPane.getLand().getStatus();

		moneyStatus.setText("$" + money + " M");
		minionStatus.setText("" + character.getMyEntity().size());
		landStatus.setText("" + character.getPossessedArea().size());

	}

	public static void updateGoodnessPoint() {
		PlayerPanel.getGoodnessPoint().updatePoint(GameSetUp.thisTurn.getGoodPoint(), Color.WHITE);
	}

	public static void updateGovernmentPoint() {
		if (GameSetUp.theGovernment != null) {
			PlayerPanel.getGovernmentPoint().updatePoint(GameSetUp.governmentPoint, GameSetUp.theGovernment.getColor());
		} else {
			PlayerPanel.getGovernmentPoint().updatePoint(7, Color.WHITE);
		}
	}

// ---------------------------------------------------------- Toggle Grid -------------------------------------------------------------

	public static void toggleGridUpdate() {
		
		MapGrid.setEnable(!MapGrid.isEnable());
		
		String id;
		
		if(MapGrid.isEnable()) {
			id = "grid-release-style";
		}
		else {
			id = "grid-disable";
		}
		
		for (int i = 0; i < MapGrid.getGrids().size(); i++) {
			ArrayList<HexagonPane> column = MapGrid.getGrids().get(i);
			for (int j = 0; j < column.size(); j++) {
				column.get(j).setId(id);
			}
		}

	}
}
