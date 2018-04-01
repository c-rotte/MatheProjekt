package projekt.mathe.game.mathespiel.scenes.game.minigames.area.models;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.lang.reflect.Modifier;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.BaseStream;

import org.omg.CORBA.PRIVATE_MEMBER;

import projekt.mathe.game.mathespiel.scenes.game.minigames.area.models.Model.TYPE;

public class ModelGenerator {
	
	public static Model[] generateRandomModel(Rectangle bounds) {
		Model[][] layouts = {
			{
				new Rechteck(
						new Point(bounds.x + bounds.width/3, bounds.y + bounds.height * 2/3), 
						new Point(bounds.x + bounds.width * 2/3, bounds.y + bounds.height * 2/3),
						new Point(bounds.x + bounds.width * 2/3, bounds.y + bounds.height/3),
						new Point(bounds.x + bounds.width/3, bounds.y + bounds.height/3))
			},
		};
		Model[] models = layouts[ThreadLocalRandom.current().nextInt(0, layouts.length)];
		if(ThreadLocalRandom.current().nextBoolean()) {
			models = flipModels(models, new Point((int) bounds.getCenterX(), (int) bounds.getCenterY()));
		}
		return models;
	}

	private static Model[] flipModels(Model[] models, Point center) {
		Model[] flippedModels = new Model[models.length];
		for(int i = 0; i < models.length; i++) {
			flippedModels[i] = rotateModel(models[i], Math.PI, center);
		}
		return flippedModels;
	}
	
	private static Model rotateModel(Model model, double rad, Point center) {
		Point[] points = {model.getA(), model.getB(), model.getC(), model.getD()};
		for(Point point : points) {
			if(point != null) {
				AffineTransform.getRotateInstance(rad, center.x, center.y).transform(point, point);
			}
		}
		model.setA(points[0]);
		model.setB(points[1]);
		model.setC(points[2]);
		model.setD(points[3]);
		model.reloadShape();
		return model;
	}
	
}
