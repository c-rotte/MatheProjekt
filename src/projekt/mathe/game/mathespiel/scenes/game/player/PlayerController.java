package projekt.mathe.game.mathespiel.scenes.game.player;

import java.awt.event.KeyEvent;

import projekt.mathe.game.engine.Values;
import projekt.mathe.game.engine.help.Helper;

public class PlayerController {

	private float actionCooldown;
	
	private final int maxActionCooldown = 20;
	private final int SPEED = 5;

	private MapPlayer player;
	private boolean activated;
	
	private boolean actionPossible;
	public boolean actionHappening;
	
	public boolean movementChanged;
	
	public PlayerController(MapPlayer player) {
		this.player = player;
		this.activated = true;
		actionCooldown = 0;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}
	
	public void moveKeyboard(float delta) {
		
		String preDir = player.direction;
		boolean preRunning = player.running;
		
		player.running = false;
		
		boolean up = player.getContainer().keyController.isPressed(KeyEvent.VK_UP);
		boolean down = player.getContainer().keyController.isPressed(KeyEvent.VK_DOWN);
		boolean left = player.getContainer().keyController.isPressed(KeyEvent.VK_LEFT);
		boolean right = player.getContainer().keyController.isPressed(KeyEvent.VK_RIGHT);
		boolean action = player.getContainer().keyController.isPressed(KeyEvent.VK_SPACE);
		
		if(actionHappening) {
			actionCooldown += delta;
			if(actionCooldown >= maxActionCooldown) {
				actionHappening = false;
				actionCooldown = 0;
			}
		}
		
		if(!activated || player.world.isDialogOpen() || player.getContainer().fading || actionHappening) {
			return;
		}
		
		if(!action) {
			actionPossible = true;
		}
		
		if(action && !actionHappening && actionPossible) {
			actionHappening = true;
			player.onAction();
			actionPossible = false;
		}
		
		boolean[] checked = checkInputs(up, down, left, right);
		up = checked[0];
		down = checked[1];
		left = checked[2];
		right = checked[3];
		
		moveX(right, left, delta);
		moveY(up, down, delta);
		setDirection(up, down, left, right);
		if(player.running != preRunning || !player.direction.equals(preDir)) {
			movementChanged = true;
		}
	}
	
	private void setDirection(boolean up, boolean down, boolean left, boolean right) {
		if(up) {
			player.direction = "up";
		}else if(down) {
			player.direction = "down";
		}else if(left) {
			player.direction = "left";
		}else if(right) {
			player.direction = "right";
		}
	}

	private boolean[] checkInputs(boolean up, boolean down, boolean left, boolean right) {
		
		if(Helper.xOutOfxBooleansTrue(2, up, down, left, right)) {
			return new boolean[] {false, false, false, false};
		}
		
		return new boolean[] {up, down, left, right};
	}
	
	private void moveX(boolean right, boolean left, float delta) {
		
		float preX = player.getX();
		float preCamX = player.getContainer().camera.getX();
		
		int xSpeed = 0;
		if(right && !left) {
			xSpeed = SPEED;
		}
		if(left && !right) {
			xSpeed = -SPEED;
		}
		player.addToX(xSpeed * delta);;
		if((int) ((player.getX() + player.getW()/2) - player.getContainer().camera.getFocusX()) > getMaxXOffset()) {
			if(xSpeed > 0) {
				player.getContainer().camera.moveX(xSpeed * delta);
			}
		}else if((int) (player.getContainer().camera.getFocusX() - (player.getX() + player.getW()/2)) > getMaxXOffset()) {
			if(xSpeed < 0) {
				player.getContainer().camera.moveX(xSpeed * delta);
			}
		}
		if(Math.abs(xSpeed) > 0) {
			player.running = true;
		}
		
		if(player.world.doesPlayerCollideWithBarrier() || player.world.doesPlayerCollideWithEntity() != null) {
			player.setX(preX);
			player.getContainer().camera.setX(preCamX);
		}else if(player.getContainer().camera.outOfMaxBoundsX()) {
			player.getContainer().camera.setX(preCamX);
		}
		
	}

	private void moveY(boolean up, boolean down, float delta) {
		
		float preY = player.getY();
		float preCamY = player.getContainer().camera.getY();
		
		int ySpeed = 0;
		if(up && !down) {
			ySpeed = -SPEED;
		}
		if(down && !up) {
			ySpeed = SPEED;
		}
		player.addToY(ySpeed * delta);;
		if((int) ((player.getY() + player.getH()/2) - player.getContainer().camera.getFocusY()) > getMaxYOffset()) {
			if(ySpeed > 0) {
				player.getContainer().camera.moveY(ySpeed * delta);

			}
		}else if((int) (player.getContainer().camera.getFocusY() - (player.getY() + player.getH()/2)) > getMaxYOffset()) {
			if(ySpeed < 0) {
				player.getContainer().camera.moveY(ySpeed * delta);
			}
		}
		if(Math.abs(ySpeed) > 0) {
			player.running = true;
		}
	
		if(player.world.doesPlayerCollideWithBarrier() || player.world.doesPlayerCollideWithEntity() != null) {
			player.setY(preY);
			player.getContainer().camera.setY(preCamY);
		}else if(player.getContainer().camera.outOfMaxBoundsY()) {
			player.getContainer().camera.setY(preCamY);
		}
		
	}
	
	private int getMaxXOffset() {
		return (Values.WINDOW_WIDTH/2 - Values.MAXOFFSET);
	}
	
	private int getMaxYOffset() {
		return Values.WINDOW_HEIGHT/2 - Values.MAXOFFSET;
	}
	
}
