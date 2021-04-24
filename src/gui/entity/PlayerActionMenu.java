package gui.entity;

import java.util.ArrayList;

import component.entity.Minion;
import exception.ExceedMinionInTileException;
import exception.ExceedToBuyMinionException;
import exception.FailToBuyLandException;
import exception.UnSpawnableTileException;
import exception.InvalidOwnershipException;
import exception.OutOfMinionException;
import gui.MainIsland;
import gui.MapOverview;
import gui.overlay.TileOverlay;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import logic.GameSetUp;
import update.FightOverlayUpdate;
import update.GameSettingUpdate;
import update.HexTileUpdate;
import update.TradeOverlayUpdate;

public class PlayerActionMenu extends ContextMenu implements Clickable {

	private static final int WIDTH = 300;

	private MenuItem buyMinion;
	private MenuItem buyLand;
	private MenuItem combine;
	private MenuItem split;
	private MenuItem trade;
	private MenuItem fight;
	private MenuItem councilFight;

	public PlayerActionMenu() {
		buyMinion = new MenuItem("Buy Minion");
		buyMinion.setGraphic(new ImageView(ClassLoader.getSystemResource("img/icon/GoldIngot.png").toString()));

		buyMinion.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				MainIsland.dataInteractMode();
				MainIsland.getInfoRoot().setAlignment(Pos.BOTTOM_CENTER);
				MainIsland.getInfo().setFontBold(48);
				MainIsland.getInfo().setFill(Color.web("0x393E46"));
				MainIsland.getInfo().setText("Select spawn location of your minion");
				GameSetUp.isHighlightSpawnable = true;
				GameSetUp.selectedTile = null;

				Thread confirmBuy = new Thread(() -> {
					while (true) {
						System.out.print("");
						if (GameSetUp.selectedTile != null) {
							GameSetUp.isReset = true;
							GameSetUp.isHighlightSpawnable = false;
							MainIsland.overlayInteractMode();
							try {
								GameSetUp.thisTurn.buyMinion();
								MainIsland.overlayInteractMode();
								MainIsland.setShowMessage("Hello master!", Color.web("0xFEFDE8"), Color.web("0x89949B"),
										120, 1, 3000);
							} catch (OutOfMinionException e) {
								EFFECT_ERROR.play();
								MainIsland.setShowMessage("I don't have any minion left!", Color.web("E04B4B"), 120,
										3000);
							} catch (UnSpawnableTileException e) {
								EFFECT_ERROR.play();
								MainIsland.setShowMessage("I can't spawn in this tile!", Color.web("E04B4B"), 120,
										3000);
							} catch (ExceedToBuyMinionException e) {
								EFFECT_ERROR.play();
								MainIsland.setShowMessage("I must wait next turn to spawn next minion!",
										Color.web("E04B4B"), 90, 3000);
							} catch (ExceedMinionInTileException e) {
								EFFECT_ERROR.play();
								MainIsland.setShowMessage("This tile has reach the maximum of minion!",
										Color.web("E04B4B"), 90, 3000);
							}
							GameSetUp.selectedTile = null;
							break;
						}
					}
					System.out.println();
				});
				confirmBuy.start();

			}
		});

		buyLand = new MenuItem("Buy Land");
		buyLand.setGraphic(new ImageView(ClassLoader.getSystemResource("img/icon/House1.png").toString()));
		buyLand.setVisible(false);

		buyLand.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					GameSetUp.thisTurn.buyLand();
				} catch (FailToBuyLandException e) {
					EFFECT_ERROR.play();
					MainIsland.setShowMessage(
							"This " + GameSetUp.selectedTile.getLocationType().getName() + " is already occupied!",
							Color.web("E04B4B"), 90, 3000);
				}

				GameSetUp.selectedTile = null;
			}

		});

		combine = new MenuItem("Combine");
		combine.setGraphic(new ImageView(ClassLoader.getSystemResource("img/icon/GroupIcon.png").toString()));
		combine.setVisible(false);

		combine.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				GameSetUp.selectedTile.updateMinionPane();
				GameSetUp.selectedTile.triggerOverlay();
				GameSetUp.selectedTile.getOverlay().getMinionPane().setMinionSelectMode();
				Thread selectMinion = new Thread(() -> {

					while (true) {
						System.out.print("");
						if (GameSetUp.selectedIcon.size() >= 2) {
							try {
								GameSetUp.thisTurn.combineMinion();
								GameSetUp.selectedTile.triggerOverlay();
								GameSetUp.selectedIcon.clear();
								MainIsland.setShowMessage("I can feel the power!!!", Color.web("0xFEFDE8"),
										Color.web("0x89949B"), 120, 1, 3000);
							} catch (InvalidOwnershipException e) {
								EFFECT_ERROR.play();
								GameSetUp.selectedIcon.clear();
								GameSetUp.selectedTile.triggerOverlay();
								MainIsland.setShowMessage("I don't like this guys!", Color.web("E04B4B"), 120, 3000);
							}
							break;
						}
					}

				});
				selectMinion.start();
			}
		});

		split = new MenuItem("Split");
		split.setGraphic(new ImageView(ClassLoader.getSystemResource("img/icon/ScissorIcon.png").toString()));
		split.setVisible(false);

		split.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				GameSetUp.selectedTile.updateMinionPane();
				GameSetUp.selectedTile.triggerOverlay();
				GameSetUp.selectedTile.getOverlay().getMinionPane().setMinionSelectMode();

				Thread selectMinion = new Thread(() -> {
					while (true) {
						System.out.print("");
						if (GameSetUp.selectedIcon.size() > 0) {
							try {
								GameSetUp.thisTurn.splitMinion();
								GameSetUp.selectedTile.triggerOverlay();
								GameSetUp.selectedIcon.clear();
								MainIsland.setShowMessage("Splited!", Color.web("0xFEFDE8"), Color.web("0x89949B"), 150,
										1, 3000);
							} catch (ExceedMinionInTileException e) {
								EFFECT_ERROR.play();
								GameSetUp.selectedIcon.clear();
								GameSetUp.selectedTile.triggerOverlay();
								MainIsland.setShowMessage("Too much minion in this tile!", Color.web("E04B4B"), 90,
										3000);
							} catch (InvalidOwnershipException e) {
								EFFECT_ERROR.play();
								GameSetUp.selectedIcon.clear();
								GameSetUp.selectedTile.triggerOverlay();
								MainIsland.setShowMessage("You're not my master!", Color.web("E04B4B"), 90, 3000);
							}
							break;
						}
						if(GameSetUp.isCancel) {
							GameSetUp.isCancel=false;
							break;
						}

					}
				});
				selectMinion.start();
			}
		});

		MenuItem cancle = new MenuItem("Cancle");
		cancle.setGraphic(new ImageView(ClassLoader.getSystemResource("img/icon/CancleIcon.png").toString()));

//		setId("player-action-menu-style");

		trade = new MenuItem("Trade");
		trade.setVisible(false);
		trade.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				if(!GameSetUp.isFightTradeMode) {
					TradeOverlayUpdate.trader = GameSetUp.thisTurn;
				}
				TradeOverlayUpdate.traded = GameSetUp.selectedCharacter;
				TradeOverlayUpdate.pfpUpdate();
				TradeOverlayUpdate.invUpdate();
				TradeOverlayUpdate.traderofferUpdate();
				TradeOverlayUpdate.tradedofferUpdate();
				TradeOverlayUpdate.acceptUpdate();
				for (int i = 0; i < MapOverview.allTradeOverlay.size(); i++) {
					MapOverview.allTradeOverlay.get(i).triggerOverlay(0, 825, 1000);
				}
			}
		});

		fight = new MenuItem("Fight");
		ImageView icon = new ImageView(ClassLoader.getSystemResource("img/icon/FightIcon.png").toString());
		icon.setFitHeight(25);
		icon.setFitWidth(25);
		fight.setGraphic(icon);
		fight.setVisible(false);

		fight.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				GameSetUp.selectedTile.updateMinionPane();
				GameSetUp.selectedTile.triggerOverlay();
				GameSetUp.selectedTile.getOverlay().getMinionPane().setMinionSelectMode();
				Thread selectMinion = new Thread(() -> {

					while (true) {
						System.out.print("");
						if (GameSetUp.selectedIcon.size() >= 2) {
							GameSetUp.selectedTile.triggerOverlay();
							GameSetUp.isFightTradeMode = true;
							FightOverlayUpdate.updateSelectors();
							TradeOverlayUpdate.trader = FightOverlayUpdate.challenged.getPossessedBy();
							for (int i = 0; i < GameSettingUpdate.getNPlayer(); i++) {
								GameSetUp.gameCharacter.get(i).setFightTraded(false);
							}
							for (int i = 0; i < MapOverview.allPlayerList1.size(); i++) {
								MapOverview.allPlayerList1.get(i).triggerOverlay(0, 825, 1000);
							}
							MainIsland.setShowMessage("You are being challenged, find someone to help!", Color.web("0xFEFDE8"), Color.web("0x89949B"),
									120, 1, 3000);
							while (true) {
								System.out.print("");
								if (GameSetUp.isFightTradeMode == false) {
									break;
								}
							}
							FightOverlayUpdate.pfpUpdate();
							FightOverlayUpdate.invUpdate();
							GameSetUp.isFightOverlayOffersUpdate = true;
							FightOverlayUpdate.acceptUpdate();

							for (int i = 0; i < MapOverview.allFightOverlay.size(); i++) {
								MapOverview.allFightOverlay.get(i).triggerOverlay(0, 825, 1000);
							}
							MainIsland.setShowMessage("Let's fight!!!", Color.web("0xFEFDE8"), Color.web("0x89949B"),
									120, 1, 3000);

							break;
						}
					}

				});
				selectMinion.start();
				MainIsland.setShowMessage("Choose your minion to fight", Color.web("0xFEFDE8"), Color.web("0x89949B"),
						120, 1, 3000);
			}
		});
		
		councilFight = new MenuItem("Challenge");
		councilFight.setGraphic(icon);
		councilFight.setVisible(false);
		councilFight.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(GameSetUp.theGovernment == null) {
					GameSetUp.selectedTile = null;
					HexagonPane.highlight2();
					HexTileUpdate.setChallengerInteract();
					MainIsland.setShowMessage("Choose your minion to challenge", Color.web("0xFEFDE8"), Color.web("0x89949B"),
							120, 1, 3000);
					Thread challenge = new Thread(() -> {
						while(true) {
							System.out.print("");
							if(GameSetUp.selectedTile != null) {
								if(GameSetUp.selectedTile.getLocationType().getMinionOnLocation().size() < 1) {
									break;									
								}
								else {
									GameSetUp.selectedTile.getOverlay().getMinionPane().setMinionSelectMode();
									while(true) {
										if(GameSetUp.selectedIcon.size() > 0) {
											if(GameSetUp.selectedIcon.get(0).getMinion().getPossessedBy() != GameSetUp.thisTurn) {
												MainIsland.setShowMessage("That's not yours!", Color.web("0xFEFDE8"), Color.web("0x89949B"),120, 1, 3000);
											}
											else {
												GameSetUp.isChallenge = true;
												FightOverlayUpdate.challenger = GameSetUp.selectedIcon.get(0).getMinion();
												FightOverlayUpdate.challenged = null;
											}
											break;											
										}
									}
									break;
								}
							}
						}
					});
					HexagonPane.unhighlight2();
					HexTileUpdate.setOverlayInteract();
				}
				
			}
		});

		getItems().addAll(buyMinion, buyLand, combine, split, trade, fight, cancle);
	}

	@Override
	public void interact() {
		// Empty
	}

	@Override
	public void triggerDisable() {
		// Empty
	}

// ------------------------------------------------ Getter and Setter ------------------------------------------------------------	

	public MenuItem getBuyMinion() {
		return buyMinion;
	}

	public MenuItem getBuyLand() {
		return buyLand;
	}

	public MenuItem getCombine() {
		return combine;
	}

	public MenuItem getSplit() {
		return split;
	}

	public MenuItem getTrade() {
		return trade;
	}

	public MenuItem getFight() {
		return fight;
	}

	public MenuItem getCouncilFight() {
		return councilFight;
	}
	

}
