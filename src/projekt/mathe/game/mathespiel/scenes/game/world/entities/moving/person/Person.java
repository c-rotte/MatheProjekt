package projekt.mathe.game.mathespiel.scenes.game.world.entities.moving.person;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import org.w3c.dom.css.Rect;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.help.ResLoader;
import projekt.mathe.game.engine.help.TextureHelper;
import projekt.mathe.game.mathespiel.Settings;
import projekt.mathe.game.mathespiel.scenes.game.player.MapPlayer;
import projekt.mathe.game.mathespiel.scenes.game.world.entities.moving.MovingEntity;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.World;

public abstract class Person extends MovingEntity {

	public static enum TYPE {
		POLICE,
		SPORTS,
		COOK,
		FEMALE,
		GLASSES
	}
	
	private String direction;
	private TextureHelper textureHelper;
	
	private Rectangle playerListenerArea;
	private boolean playerInArea;
	
	public Person(Scene container, World world, TYPE type, int x, int y, float speed) {
		super(container, world, x, y, 54, 75, speed, true, true);
		direction = "left";
		textureHelper = TextureGenerator.generateTextureHelper(type);
	}

	public void addPlayerListenerArea(int x, int y, int w, int h) {
		playerListenerArea = new Rectangle(x, y, w, h);
		playerInArea = false;
	}

	public void onPlayerEntersListenerArea(MapPlayer player) {}
	
	public void setDirection(String direction) {
		this.direction = direction;
		textureHelper.switchState(direction + (isMoving() ? "_running" : ""));
	}
	
	public String getDirection() {
		return direction;
	}
	
	@Override
	public void setMoving(boolean moving) {
		super.setMoving(moving);
		textureHelper.switchState(direction + (isMoving() ? "_running" : ""));
	}
	
	@Override
	protected void aimChanged(int[] oldAim, int[] newAim) {
		if(newAim[0] - oldAim[0] > 0) {
			setDirection("right");
		}else if(newAim[0] - oldAim[0] < 0) {
			setDirection("left");
		}else if(newAim[1] - oldAim[1] > 0) {
			setDirection("down");
		}else if(newAim[1] - oldAim[1] < 0) {
			setDirection("up");
		}
	}

	@Override
	public void onTick(float delta) {
		textureHelper.onTick(delta);
		if(playerListenerArea != null) {
			if(getContainer().world.player.getBounds().intersects(playerListenerArea)) {
				if(!playerInArea) {
					onPlayerEntersListenerArea(getContainer().world.player);
					playerInArea = true;
				}
			}else {
				playerInArea = false;
			}
		}
		super.onTick(delta);
	}
	
	@Override
	public void onPaint(Graphics2D g2d) {
		if(Settings.HITBOXEN_ANZEIGEN && playerListenerArea != null) {
			g2d.setColor(Color.YELLOW);
			g2d.draw(playerListenerArea);
		}
		g2d.drawImage(textureHelper.getCurrentImage(), (int) (getX() + getW()/2 - 33), (int) getY(), 65, 75, null);
	}

	public static class TextureGenerator {
		
		private static final int running_Step = 8;
		
		private static Image[][] images;
		
		static {
			images = new Image[5][12];
			for(int i = 0; i < 5; i++) {
				TYPE type = TYPE.values()[i];
				images[i][0] = ResLoader.getImageByName("game/entities/person/" + type.name().toLowerCase() + "/links_stehen.png");
				images[i][1] = ResLoader.getImageByName("game/entities/person/" + type.name().toLowerCase() + "/rechts_stehen.png");
				images[i][2] = ResLoader.getImageByName("game/entities/person/" + type.name().toLowerCase() + "/oben_stehen.png");
				images[i][3] = ResLoader.getImageByName("game/entities/person/" + type.name().toLowerCase() + "/unten_stehen.png");
				images[i][4] = ResLoader.getImageByName("game/entities/person/" + type.name().toLowerCase() + "/links_gehen_1.png");
				images[i][5] = ResLoader.getImageByName("game/entities/person/" + type.name().toLowerCase() + "/links_gehen_2.png");
				images[i][6] = ResLoader.getImageByName("game/entities/person/" + type.name().toLowerCase() + "/rechts_gehen_1.png");
				images[i][7] = ResLoader.getImageByName("game/entities/person/" + type.name().toLowerCase() + "/rechts_gehen_2.png");
				images[i][8] = ResLoader.getImageByName("game/entities/person/" + type.name().toLowerCase() + "/oben_gehen_1.png");
				images[i][9] = ResLoader.getImageByName("game/entities/person/" + type.name().toLowerCase() + "/oben_gehen_2.png");
				images[i][10] = ResLoader.getImageByName("game/entities/person/" + type.name().toLowerCase() + "/unten_gehen_1.png");
				images[i][11] = ResLoader.getImageByName("game/entities/person/" + type.name().toLowerCase() + "/unten_gehen_2.png");
			}
		}
		
		public static final TextureHelper generateTextureHelper(TYPE type) {
			TextureHelper textureHelper = new TextureHelper();
			int index = type.ordinal();
			textureHelper.addState("left", 10000, images[index][0]);
			textureHelper.addState("right", 10000, images[index][1]);
			textureHelper.addState("up", 10000, images[index][2]);
			textureHelper.addState("down", 10000, images[index][3]);
			textureHelper.addState("left_running", running_Step, images[index][4], images[index][0], images[index][5]);
			textureHelper.addState("right_running", running_Step, images[index][6], images[index][1], images[index][7]);
			textureHelper.addState("up_running", running_Step, images[index][8], images[index][2], images[index][9]);
			textureHelper.addState("down_running", running_Step, images[index][10], images[index][3], images[index][11]);
			return textureHelper;
		}
		
	}
	
}
