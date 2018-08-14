package projekt.mathe.game.mathespiel.scenes.game.minigames.boss;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.ScreenElement;
import projekt.mathe.game.engine.help.ResLoader;
import projekt.mathe.game.engine.help.TextureHelper;

public class Projectile extends ScreenElement{

	private static final Image paper = ResLoader.getImageByName("game/minigames/boss/paper.png");
	private static final Image[] dust = {
		ResLoader.getImageByName("game/minigames/boss/dust/dust_1.png"),
		ResLoader.getImageByName("game/minigames/boss/dust/dust_2.png"),
		ResLoader.getImageByName("game/minigames/boss/dust/dust_3.png"),
		ResLoader.getImageByName("game/minigames/boss/dust/dust_4.png"),
		ResLoader.getImageByName("game/minigames/boss/dust/dust_5.png"),
		ResLoader.getImageByName("game/minigames/boss/dust/dust_6.png")
	};
	
	private static final float Z_RATIO = 0.6f;
	
	private boolean launched;
	private float z;
	
	private float xVel, yVel;
	private float xAcc, yAcc;
	
	private float w, h;
	
	private boolean destroyed;
	private boolean destroying;
	private Point dustPoint;
	private TextureHelper textureHelper;
	private float textureSteps;
	
	public Projectile(Scene container, int x, int y) {
		super(container, x, y, 40, 32);
		z = 0;
		this.w = getW();
		this.h = getH();
		textureHelper = new TextureHelper();
		textureHelper.addState("normal", 5, dust);
		textureSteps = 6 * 5;
	}

	public void launch(float xvector, float yvector) {
		this.xVel = -xvector * 0.04f * 1.3f;
		this.yVel = yvector * -0.06f * 1.3f;
		xAcc = 0;
		yAcc = 15f * 0.02f;
		launched = true;
	}
	
	public float getZ() {
		return z;
	}
	
	public boolean wasDestroyed() {
		return destroyed;
	}
	
	public boolean isDestroying() {
		return destroying;
	}
	
	@Override
	public void onTick(float delta) {
		if(launched && !destroying && !destroyed) {
			z += delta * Z_RATIO;
			addToX(xVel * delta);
			yVel += yAcc * delta;
			addToY(yVel * delta);
			setW(w * (1f - 0.01f * z));
			setH(h * (1f - 0.01f * z));
			if(z > 60) {
				destroying = true;
				dustPoint = new Point((int) getX(), (int) getY());
			}
		}
		if(destroying && !destroyed) {
			textureHelper.onTick(delta);
			textureSteps -= delta;
			if(textureSteps <= 0) {
				destroyed = true;
			}
		}
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		if(onScreen() && !destroying && !destroyed) {
			g2d.drawImage(paper, (int) (getX() - getW()/2), (int) (getY() - getH()/2), (int) getW(), (int) getH(), null);
		}
		if(destroying && !destroyed) {
			g2d.drawImage(textureHelper.getCurrentImage(), dustPoint.x - 50, dustPoint.y - 50, null);
		}
	}

}
