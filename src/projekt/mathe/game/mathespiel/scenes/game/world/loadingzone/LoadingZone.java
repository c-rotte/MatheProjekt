package projekt.mathe.game.mathespiel.scenes.game.world.loadingzone;

import java.awt.Color;
import java.awt.Graphics2D;

import projekt.mathe.game.engine.elements.ScreenElement;
import projekt.mathe.game.mathespiel.scenes.MainSceneData;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.World;

public class LoadingZone extends ScreenElement{

	public World world;
	public String aim;
	private float fade;
	private String id;
	
	public LoadingZone(int x, int y, int w, int h, World world, String aim, float fade, String id) {
		super(world.container, x, y, w, h);
		this.aim = aim;
		this.fade = fade;
		this.world = world;
		this.id = id;
	}
	
	public String getID() {
		return id;
	}
	
	@Override
	public void onTick(float delta) {
		if(!world.container.fading) {
			if(world.player.getBounds().intersects(getBounds())) {
				MainSceneData data = (MainSceneData) world.container.getDataForNextScene();
				data.setlastLoadingZoneID(id);
				world.container.callScene(aim, data, fade);
			}
		}
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		g2d.setColor(Color.GREEN);
		g2d.drawRect((int) x, (int) y, (int) w, (int) h);
	}

}
