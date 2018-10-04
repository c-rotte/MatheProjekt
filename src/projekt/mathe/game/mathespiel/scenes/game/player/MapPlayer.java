package projekt.mathe.game.mathespiel.scenes.game.player;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.ScreenElement;
import projekt.mathe.game.mathespiel.Settings;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.World;

public class MapPlayer extends ScreenElement{

	public PlayerController playerController;
	public World world;
	public String direction;
	public boolean running;
	public static MapPlayerTextureHelper textureHelper = new MapPlayerTextureHelper();
	
	public MapPlayer(Scene container) {
		super(container, 0, 0, 54, 75);
		direction = "down";
		playerController = new PlayerController(this);
		textureHelper.setGender(Settings.GIRL);
	}
	
	public void reloadGender() {
		textureHelper.setGender(Settings.GIRL);
		textureHelper.switchPlayerState(textureHelper.getRawState());
	}
	
	public void setWorld(World world) {
		this.world = world;
	}
	
	@Override
	public void onTick(float delta) {
		playerController.moveKeyboard(delta);
		textureHelper.onTick(delta);
		setTexture();
		if(playerController.movementChanged) {
			playerController.movementChanged = false;
		}
	}
	
	public void onAction() {
		world.playerInteract();
	}

	public Rectangle getInteractionBounds() {
		Rectangle bounds = getBounds();
		switch(direction) {
			case "up" :
				return new Rectangle(bounds.x, bounds.y - bounds.width, bounds.width, bounds.width);
			case "down" :
				return new Rectangle(bounds.x, bounds.y + bounds.height, bounds.width, bounds.width);
			case "left" : 
				return new Rectangle(bounds.x - bounds.width, bounds.y, bounds.width, bounds.height);
			case "right" :
				return new Rectangle(bounds.x + bounds.width, bounds.y, bounds.width, bounds.height);
			default : return null;
		}
	}
	
	private void setTexture() {
		if(!playerController.movementChanged && running) {
			return;
		}
		switch(direction) {
			case "up" : textureHelper.switchPlayerState(running ? "up_running" : "up_standing"); break;
			case "down" : textureHelper.switchPlayerState(running ? "down_running" : "down_standing"); break;
			case "left" : textureHelper.switchPlayerState(running ? "left_running" : "left_standing"); break;
			case "right" : textureHelper.switchPlayerState(running ? "right_running" : "right_standing"); break;
		}
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) getX(), (int) (getY() + getH()/2), (int) getW(), (int) (getH()/2));
	}
	
	public Rectangle getOriginalBounds() {
		return super.getBounds();
	}
	
	@Override
	public void onPaint(Graphics2D g2d) {
		g2d.drawImage(textureHelper.getCurrentImage(), (int) (getX() + getW()/2 - 33), (int) getY(), 65, 75, null);
		if(Settings.HITBOXEN_ANZEIGEN) {
			g2d.setColor(Color.CYAN);
			g2d.draw(getBounds());
			g2d.setColor(Color.BLUE);
			g2d.draw(getInteractionBounds());
		}
	}

}
