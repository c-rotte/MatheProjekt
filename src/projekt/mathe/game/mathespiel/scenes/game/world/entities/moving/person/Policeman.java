package projekt.mathe.game.mathespiel.scenes.game.world.entities.moving.person;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.save.Saver;
import projekt.mathe.game.mathespiel.scenes.MainSceneData;
import projekt.mathe.game.mathespiel.scenes.game.player.MapPlayer;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.World;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog.Card;

public class Policeman extends Person {

	private boolean playerInZone;
	private Polygon viewField;
	private boolean finishedMinigame;
	private Polygon viewFieldClip;
	
	public Policeman(Scene container, World world) {
		super(container, world, TYPE.POLICE, 300, 200, 3);
		int[] xpoints = {-50, -50, -150, -150, -50, -50, -450, -450, 0, 0, 400, 400, 450, 450, 500, 500, 515, 515, 500, 500, 250, 250, 100, 100};
		int[] ypoints = {-380, -180, -180, 20, 20, 220, 220, 650, 650, 820, 820, 700, 700, 350, 350, -150, -150, -280, -280, -380, -380, -400, -400, -380};
		viewFieldClip = new Polygon(xpoints, ypoints, xpoints.length);
	}

	private void setViewField(String direction) {
		int[] xpoints = {getMiddle().x, getMiddle().x - 100, getMiddle().x + 100};
		int[] ypoints = {getMiddle().y, getMiddle().y + 400, getMiddle().y + 400};
		viewField = new Polygon(xpoints, ypoints, 3);
		int degrees = 0;
		switch (direction) {
			case "up":
				degrees = 180;
				break;
			case "left":
				degrees = 90;
				break;
			case "right":
				degrees = -90;
				break;
		}
		AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(degrees), getMiddle().x, getMiddle().y);
		for(int i = 0; i < viewField.npoints; i++) {
			Point point = new Point(viewField.xpoints[i], viewField.ypoints[i]);
			at.transform(point, point);
			viewField.xpoints[i] = point.x;
			viewField.ypoints[i] = point.y;
		}
	}
	
	private void moveViewField() {
		AffineTransform at = AffineTransform.getTranslateInstance(getMiddle().x - viewField.xpoints[0], getMiddle().y - viewField.ypoints[0]);
		for(int i = 0; i < viewField.npoints; i++) {
			Point point = new Point(viewField.xpoints[i], viewField.ypoints[i]);
			at.transform(point, point);
			viewField.xpoints[i] = point.x;
			viewField.ypoints[i] = point.y;
		}
	}
	
	public void onCall(String lastScene, MainSceneData sceneData) {
		
		finishedMinigame = Saver.containsData("currCode") && Saver.containsData("safeCode") && Saver.getString("currCode").charAt(3) == Saver.getString("safeCode").charAt(3);
		
		clearAims();
		addAim(300, -250);
		addAim(50, -250);
		addAim(50, 300);
		addAim(-350, 300);
		addAim(-350, 500);
		addAim(300, 500);
		
		if(lastScene.equals("pyramid")) {
			if(sceneData.minigameCompleted()) {
				finishedMinigame = true;
				setMoving(false);
				setX(300);
				setY(200);
				getContainer().camera.focusX(50);
				getContainer().camera.focusY(200);
				setDirection("left");
				world.player.setX(220);
				world.player.setY(200);
				world.player.direction = "right";
				world.player.playerController.setActivated(true);
				Dialog dialog = new Dialog(world) {
					@Override
					public void onSelected(Card lastcard, boolean finished) {
						
					}
					@Override
					public void onFinished(Card lastcard) {
						
						StringBuilder builder = new StringBuilder(Saver.getString("currCode"));
						builder.setCharAt(3, Saver.getString("safeCode").charAt(3));
						Saver.setData("currCode", builder.toString());
						
						finishedMinigame = true;
						setDirection("up");
						setMoving(true);
						
						setViewField("up");
					}
				};
				dialog.addCard(new Card("Ich lasse dich nun gehen, wie abgemacht."));
				world.openDialog(dialog);
			}
		}else {
			playerInZone = false;
			world.player.playerController.setActivated(true);
			setMoving(true);
			setDirection("up");
			setViewField("up");
			setX(300);
			setY(200);
		}
		moveViewField();
	}
	
	@Override
	public void onTick(float delta) {
		MapPlayer player = world.player;
		boolean intersect = new Area(viewField).intersects(player.getOriginalBounds());
		if(intersect && !finishedMinigame && !world.isDialogOpen()) {
			if(!playerInZone) {
				playerInZone = true;
				switch (getDirection()) {
					case "up": 
						player.direction = "down";
						break;
					case "down":
						player.direction = "up";
						break;
					case "left":
						player.direction = "right";
						break;
					case "right":
						player.direction = "left";
						break;
				}
				player.playerController.setActivated(false);
				clearAims();
				int[] aim1 = {(int) getX(), (int) player.getY()};
				int[] aim2 = {(int) player.getX(), (int) player.getY()};
				aimChanged(new int[] {(int) getX(), (int) getY()}, aim1);
				addAim(aim1[0], aim1[1]);
				addAim(aim2[0], aim2[1]);
			}
			
		}
		
		if(isMoving()) {
			moveViewField();
		}
		
		super.onTick(delta);
		
		Rectangle playerBounds = new Rectangle(player.getOriginalBounds());
		playerBounds.x--;
		playerBounds.y--;
		playerBounds.width += 2;
		playerBounds.height += 2;
		
		if(playerInZone && playerBounds.intersects(getBounds()) && !world.isDialogOpen() && !getContainer().fading) {
			switch (getDirection()) {
				case "up": 
					player.direction = "down";
					break;
				case "down":
					player.direction = "up";
					break;
				case "left":
					player.direction = "right";
					break;
				case "right":
					player.direction = "left";
					break;
			}
			setMoving(false);
			Dialog dialog = new Dialog(world) {
				@Override
				public void onSelected(Card lastcard, boolean finished) {
					if(lastcard.selected.equals("Ja")) {
						Dialog dialog = new Dialog(world) {
							@Override
							public void onSelected(Card lastcard, boolean finished) {
								if(lastcard.selected.equals("blau")) {
									Dialog dialog = new Dialog(world) {
										@Override
										public void onSelected(Card lastcard, boolean finished) {
											if(lastcard.selected.equals("Ja")) {
												Dialog dialog = new Dialog(world) {
													@Override
													public void onSelected(Card lastcard, boolean finished) {
														
													}
													@Override
													public void onFinished(Card lastcard) {
														getContainer().callScene("pyramid", getContainer().getDataForNextScene(), 40f);
													}
												};
												dialog.addCard(new Card("Super!"));
												world.openDialog(dialog);
											}else {
												Dialog dialog = new Dialog(world) {
													@Override
													public void onSelected(Card lastcard, boolean finished) {
														
													}
													@Override
													public void onFinished(Card lastcard) {
														MainSceneData mainSceneData = (MainSceneData) getContainer().getDataForNextScene();
														mainSceneData.setlastLoadingZoneID("aulaEingang");
														getContainer().callScene("aula", mainSceneData, 40f);
													}
												};
												dialog.addCard(new Card("Nun gut. Auf Wiedersehen!"));
												world.openDialog(dialog);
											}
										}
										@Override
										public void onFinished(Card lastcard) {
											
										}
									};
									dialog.addCard(new Card("Genau! Blau ist die einzige Farbe, die zu einem Matheheft passt!"));
									dialog.addCard(new Card("Du bist mir irgendwie sympathisch. Ganz im Gegensatz zu den Frechdachsen, die gestern eine Wand hier im Geb‰ude besch‰digt haben."));
									dialog.addCard(new Card("..."));
									dialog.addCard(new Card("Ich habe eine Idee: Wenn du mir hilfst, die Wand zu reparieren darfst du bleiben."));
									Card card = new Card("Bist du dabei?");
									card.addSelection("Ja", "Nein");
									dialog.addCard(card);
									world.openDialog(dialog);
								}else {
									Dialog dialog = new Dialog(world) {
										@Override
										public void onSelected(Card lastcard, boolean finished) {
											
										}
										@Override
										public void onFinished(Card lastcard) {
											MainSceneData mainSceneData = (MainSceneData) getContainer().getDataForNextScene();
											mainSceneData.setlastLoadingZoneID("aulaEingang");
											getContainer().callScene("aula", mainSceneData, 40f);
										}
									};
									dialog.addCard(new Card("Falsche Antwort. Auf Wiedersehen!"));
									world.openDialog(dialog);
								}
							}
							@Override
							public void onFinished(Card lastcard) {
								
							}
						};
						Card card = new Card("Welche Farbe sollte ein Matheheft haben?");
						card.addSelection("gr¸n", "blau", "gelb", "rot", "weiﬂ");
						dialog.addCard(card);
						world.openDialog(dialog);
					}else {
						Dialog dialog = new Dialog(world) {
							@Override
							public void onSelected(Card lastcard, boolean finished) {
								
							}
							@Override
							public void onFinished(Card lastcard) {
								MainSceneData mainSceneData = (MainSceneData) getContainer().getDataForNextScene();
								mainSceneData.setlastLoadingZoneID("aulaEingang");
								getContainer().callScene("aula", mainSceneData, 40f);
							}
						};
						dialog.addCard(new Card("Na, dann auf Wiedersehen!"));
						world.openDialog(dialog);
					}
				}
				@Override
				public void onFinished(Card lastcard) {
					
				}
			};
			dialog.addCard(new Card("Ja, wen haben wir denn da..."));
			dialog.addCard(new Card("Hast gedacht, dass du dich hinter mir vorbeischleichen kannst?"));
			dialog.addCard(new Card("Falsch gedacht!"));
			dialog.addCard(new Card("..."));
			Card card = new Card("Bevor ich dich herauswerfe: Kann ich dir noch eine Frage stellen?");
			card.addSelection("Ja", "Nein");
			dialog.addCard(card);
			world.openDialog(dialog);
		}
	}
	
	@Override
	protected void aimChanged(int[] oldAim, int[] newAim) {
		if(oldAim[0] == newAim[0]) {
			if(oldAim[1] > newAim[1]) {
				setDirection("up");
				setViewField("up");
			}else {
				setDirection("down");
				setViewField("down");
			}
		}else if(oldAim[1] == newAim[1]) {
			if(oldAim[0] > newAim[0]) {
				setDirection("left");
				setViewField("left");
			}else {
				setDirection("right");
				setViewField("right");
			}
		}
	}
	
	@Override
	public void onInteract(MapPlayer player) {
		if(finishedMinigame) {
			Dialog dialog = new Dialog(world) {
				@Override
				public void onSelected(Card lastcard, boolean finished) {
					
				}
				@Override
				public void onFinished(Card lastcard) {
					
				}
			};
			dialog.addCard(new Card("Auch wenn ich nichts dagegen habe, dass du hier bleibts, solltest du lieber verschwinden. Wer weiﬂ, wer sich hier herumtreibt."));
		}
	}

	@Override
	public void onPerformacePaint(Graphics2D g2d) {
		if(isMoving() && world.player.playerController.isActivated()) {
			Shape shape = g2d.getClip();
			g2d.setClip(viewFieldClip);
			g2d.setColor(new Color(0, 72, 229, 120));
			g2d.fill(viewField);
			g2d.setClip(shape);
		}
		super.onPerformacePaint(g2d);
	}
	
}
