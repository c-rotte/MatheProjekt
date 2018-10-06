package projekt.mathe.game.mathespiel.scenes.game.world.entities.moving.person;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.help.Animator;
import projekt.mathe.game.engine.help.ResLoader;
import projekt.mathe.game.engine.save.Saver;
import projekt.mathe.game.mathespiel.scenes.game.player.MapPlayer;
import projekt.mathe.game.mathespiel.scenes.game.world.entities.Entity;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.World;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog.Card;

public class Boss extends Entity {
	
	private String state; //invisible, cameramoving, speaking, fading
	private int fadingState;
	
	private static final Image bossImage = ResLoader.getImageByName("game/entities/person/boss.png");
	private static final float CAMERA_SPEED = 5;
	private static final float RECT_SPEED = 35;
	
	private FloatRectangle[] rectangles;
	
	private Animator animator;
	
	public Boss(Scene container, World world) {
		super(container, world, 1450, 96, 38, 51, true, true);
		reset();
	}

	public void onCall() {
		if(Saver.containsData("currCode") && Saver.containsData("safeCode") && Saver.getString("currCode").charAt(4) == Saver.getString("safeCode").charAt(4) && !Saver.containsData("bossdefeated") && !Saver.getBoolean("bossdefeated")) {
			trigger();
		}
	}
	
	public void reset() {
		state = null;
		fadingState = 0;
		rectangles = new FloatRectangle[7];
		animator = new Animator(60f, 1f);
	}
	
	public void trigger() {
		state = "invisible";
	}
	
	@Override
	public void onInteract(MapPlayer player) {
		
	}

	@Override
	public void onTick(float delta) {
		if(state == null) {
			return;
		}
		switch (state) {
			case "invisible":
				if(new Rectangle(800, 70, 50, 120).intersects(world.player.getOriginalBounds())) {
					world.player.playerController.setActivated(false);
					animator.calculate(delta);
					if(animator.finished()) {
						state = "cameramoving";
					}
				}
				break;
			case "cameramoving" : 
				if(getContainer().camera.translateAbsolutX(getContainer().camera.getX()) < 550) {
					getContainer().camera.setX(getContainer().camera.getRawX() + delta * CAMERA_SPEED);
				}else {
					getContainer().camera.focusX(915);
					Dialog dialog = new Dialog(world) {
						@Override
						public void onSelected(Card lastcard, boolean finished) {
							
						}
						@Override
						public void onFinished(Card lastcard) {
							state = "fading";
						}
					};
					dialog.addCard(new Card("Was ist, wieso bleibst du stehen?"));
					Card card = new Card("Noch so ein Bursche! Dich werde ich auch belehren!");
					card.setTextColor(Color.RED);
					dialog.addCard(card);
					Card card2 = new Card("Für Früchtchen wie dich habe ich extra eine Verwirrungsanlage gebaut! In Zukunft wirst du dir sicher zweimal überlegen, nachts in die Schule zu gehen!");
					card2.setTextColor(Color.RED);
					dialog.addCard(card2);
					world.openDialog(dialog);
					state = "speaking";
				}
				break;
			case "speaking" : 
				break;
			case "fading" :
				switch (fadingState) {
					case 0: 
						if(rectangles[0] == null) {
							rectangles[0] = new FloatRectangle(1280, 0, 0, 180);
						}
						if(rectangles[0].getX() > 0) {
							rectangles[0].expand(-RECT_SPEED * delta, 0);
						}else {
							rectangles[0].setX(0);
							rectangles[0].setW(1280);
							fadingState++;
						}
						break;
					case 1: 
						if(rectangles[1] == null) {
							rectangles[1] = new FloatRectangle(0, 180, 320, 0);
						}
						if(180 + rectangles[1].getH() < 720) {
							rectangles[1].expand(0, RECT_SPEED * delta);
						}else {
							rectangles[1].setY(180);
							rectangles[1].setH(720 - 180);
							fadingState++;
						}
						break;
					case 2:
						if(rectangles[2] == null) {
							rectangles[2] = new FloatRectangle(320, 720 - 180, 0, 180);
						}
						if(320 + rectangles[2].getW() < 1280) {
							rectangles[2].expand(RECT_SPEED * delta, 0);
						}else {
							rectangles[2].setX(320);
							rectangles[2].setW(1280 - 320);
							fadingState++;
						}
						break;
					case 3:
						if(rectangles[3] == null) {
							rectangles[3] = new FloatRectangle(1280 - 320, 720 - 180, 320, 0);
						}
						if(rectangles[3].getY() > 180) {
							rectangles[3].expand(0, -RECT_SPEED * delta);
						}else {
							rectangles[3].setY(180);
							rectangles[3].setH(180 * 2);
							fadingState++;
						}
						break;
					case 4:
						if(rectangles[4] == null) {
							rectangles[4] = new FloatRectangle(1280 - 320, 180, 20, 180);
						}
						if(rectangles[4].getX() > 320) {
							rectangles[4].expand(-RECT_SPEED * delta, 0);
						}else {
							rectangles[4].setX(320);
							rectangles[4].setW(320 * 2);
							fadingState++;
						}
						break;
					case 5:
						if(rectangles[5] == null) {
							rectangles[5] = new FloatRectangle(320, 2 * 180, 320, 0);
						}
						if(rectangles[5].getH() < 180) {
							rectangles[5].expand(0, RECT_SPEED * delta);
						}else {
							rectangles[5].setY(2 * 180);
							rectangles[5].setH(180);
							fadingState++;
						}
						break;
					case 6:
						if(rectangles[6] == null) {
							rectangles[6] = new FloatRectangle(320 * 2, 2 * 180, 0, 180);
						}
						if(rectangles[6].getW() < 320) {
							rectangles[6].expand(RECT_SPEED * delta, 0);
						}else {
							rectangles[6].setX(320 * 2);
							rectangles[6].setW(320);
							getContainer().callScene("boss", getContainer().getDataForNextScene(), 40f);
							fadingState++;
						}
						break;
				}
				break;
		}
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		if(state != null && !state.equals("invisible")) {
			g2d.drawImage(bossImage, (int) (getX() + getW()/2 - 33), (int) getY(), 65, 75, null);
			g2d.setColor(Color.BLACK);
			for(FloatRectangle floatRectangle : rectangles) {
				if(floatRectangle != null) {
					g2d.fill(floatRectangle.getRectangle());
				}
			}
		}
	}

	private class FloatRectangle {
		
		private float x, y, w, h;

		public FloatRectangle(float x, float y, float w, float h) {
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
		}

		public float getX() {
			return x;
		}

		public void setX(float x) {
			this.x = x;
		}

		public float getY() {
			return y;
		}

		public void setY(float y) {
			this.y = y;
		}

		public float getW() {
			return w;
		}

		public void setW(float w) {
			this.w = w;
		}

		public float getH() {
			return h;
		}

		public void setH(float h) {
			this.h = h;
		}
		
		public void expand(float x, float y) {
			if(x < 0) {
				this.x += x;
				this.w -= x;
			}else {
				this.w += x;
			}
			if(y < 0) {
				this.y += y;
				this.h -= y;
			}else {
				this.h += y;
			}
		}
		
		public Rectangle getRectangle() {
			return new Rectangle(getContainer().camera.translateAbsolutX(x), getContainer().camera.translateAbsolutY(y), (int) w, (int) h);
		}
		
	}
	
}
