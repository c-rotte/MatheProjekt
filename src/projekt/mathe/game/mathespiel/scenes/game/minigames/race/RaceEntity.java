package projekt.mathe.game.mathespiel.scenes.game.minigames.race;

import java.awt.Color;
import java.awt.Graphics2D;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.ScreenElement;
import projekt.mathe.game.engine.help.Animator;
import projekt.mathe.game.mathespiel.Settings;

public abstract class RaceEntity extends ScreenElement{

	private static float SPEED = 7f;
	private static float BOOST_SPEED = 12f;
	private static float BOOST_SPEED_PS = 0.15f;
	private RaceTextures textures;
	private String boostingState;
	private float speed;
	private String sprintingState;
	private Animator startAnimator, stopAnimator;
	private boolean cameraMoveable;
	
	public RaceEntity(Scene container, int x, int y, int w, int h, RaceTextures textures, boolean cameraMoveable) {
		super(container, x, y, w, h);
		this.textures = textures;
		textures.setStill();
		boostingState = "normal";
		sprintingState = "still";
		speed = SPEED;
		this.cameraMoveable = cameraMoveable;
	}
	
	public void startRunning() {
		sprintingState = "starting";
		textures.setSprinting();
		startAnimator = new Animator(SPEED, 0.1f);
	}
	
	public void stopRunning() {
		if(isBoosting()) {
			return;
		}
		sprintingState = "stopping";
		stopAnimator = new Animator(SPEED, 0.1f);
	}
	
	public String getSprintingState() {
		return sprintingState;
	}
	
	public void boost() {
		if(boostingState.equals("normal") && !sprintingState.equals("still")) {
			boostingState = "faster";
		}
	}
	
	public boolean isBoosting() {
		return !boostingState.equals("normal");
	}

	@Override
	public void onTick(float delta) {
		switch (sprintingState) {
			case "still":
				textures.onTick(delta);
				break;
			case "starting":
				startAnimator.calculate(delta);
				textures.onTick(delta);
				float f = (SPEED - startAnimator.getCurrValue()) * delta;
				if(cameraMoveable) {
					getContainer().camera.moveY(-f);
				}
				addToY(-f);
				if(startAnimator.finished()) {
					sprintingState = "sprinting";
				}
				break;
			case "sprinting":
				textures.onTick(delta);
				if(sprintingState.equals("sprinting")) {
					if(boostingState.equals("faster")) {
						speed += BOOST_SPEED_PS *delta;
						if(speed >= BOOST_SPEED) {
							boostingState = "slower";
						}
					}else if(boostingState.equals("slower")) {
						speed += -BOOST_SPEED_PS * delta;
						if(speed <= SPEED) {
							boostingState = "normal";
						}
					}else if(boostingState.equals("normal")){
						speed = SPEED;
					}
					if(cameraMoveable) {
						getContainer().camera.moveY(-speed * delta);
					}
					addToY(-speed * delta);
				}
				break;
			case "stopping":
				stopAnimator.calculate(delta);
				textures.onTick(delta * (1 - stopAnimator.getCurrValueRelative()));
				float f2 = stopAnimator.getCurrValue() * delta;
				if(cameraMoveable) {
					getContainer().camera.moveY(-f2);
				}
				addToY(-f2);
				if(stopAnimator.finished()) {
					sprintingState = "still";
					textures.setStill();
				}
				break;
		}
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		g2d.drawImage(textures.getCurrImage(), (int) (getX() + getW()/2 - 33), (int) getY(), 65, 75, null);
		if(Settings.HITBOXEN_ANZEIGEN) {
			g2d.setColor(Color.CYAN);
			g2d.draw(getBounds());
		}
	}

}
