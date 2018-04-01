package projekt.mathe.game.mathespiel.scenes.game.minigames.area;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.xml.bind.ValidationEvent;

import org.omg.CORBA.portable.ValueBase;

import projekt.mathe.game.engine.Game;
import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.SceneData;
import projekt.mathe.game.engine.Values;
import projekt.mathe.game.mathespiel.scenes.MainSceneData;
import projekt.mathe.game.mathespiel.scenes.game.minigames.area.models.Model;
import projekt.mathe.game.mathespiel.scenes.game.minigames.area.models.Model.TYPE;
import projekt.mathe.game.mathespiel.scenes.game.minigames.area.models.ModelGenerator;

public class AreaScene extends Scene {

	private Model[] model;
	
	public AreaScene(Game container) {
		super(container, "area", Values.SCENE_BG_COLOR);
		model = ModelGenerator.generateRandomModel(new Rectangle(320, 180, 640, 360));
	}

	@Override
	public void onCall(String lastID, SceneData sceneData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTick(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMouseClicked(MouseEvent e) {
		model = ModelGenerator.generateRandomModel(new Rectangle(320, 180, 640, 360));
	}
	
	@Override
	public void onPaint(Graphics2D g2d) {
		g2d.setStroke(new BasicStroke(4f));
		g2d.setColor(Color.RED);
		g2d.drawRect(320, 180, 640, 360);
		g2d.setColor(Color.GREEN);
		for(Model m : model) {
			m.onPaint(g2d);
		}
	}

	@Override
	public SceneData getDataForNextScene() {
		return new MainSceneData();
	}

}
