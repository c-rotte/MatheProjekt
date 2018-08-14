package projekt.mathe.game.mathespiel.scenes.game.minigames.boss;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class ShapeGenerator {

	public static final Shape getShape(int index) {
		switch (index) {
			case 0 :
				return new Rectangle(0, 0, 80, 80);
			case 1 :
				return new Rectangle(20, 0, 60, 80);
			case 2 :
				return new Ellipse2D.Float(0, 0, 80, 80);
			case 3 :
				int[] xCoords = {40, 80, 0};
				int[] yCoords = {0, 80, 80};
				return new Polygon(xCoords, yCoords, 3);
			case 4 :
				int[] xCoords2 = {0, 60, 80, 20};
				int[] yCoords2 = {0, 0, 80, 80};
				return new Polygon(xCoords2, yCoords2, 4);
			case 5 :
				int[] xCoords3 = {20, 60, 80, 80, 60, 20, 0, 0};
				int[] yCoords3 = {0, 0, 20, 60, 80, 80, 60, 20};
				return new Polygon(xCoords3, yCoords3, 8);
		}
		return null;
	}
	
	private static ArrayList<Integer> indexe = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5));
	
	public static final Object[] generateMessage() {
		if(indexe.size() == 0) {
			indexe = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5));
		}
		int index = indexe.remove(ThreadLocalRandom.current().nextInt(indexe.size()));
		return generateMessage(index);
	}
	
	public static final Object[] generateMessage(int random) {
		Object[] objects = new Object[7];
		String message = "Ich denke an ";
		Shape shape = null;
		shape = getShape(random);
		switch (random) {
			case 0:
				message += "4 gleich lange Seiten";
				break;
			case 1: 
				message += "2 Symmetrieachsen";
				break;
			case 2:
				message += ThreadLocalRandom.current().nextBoolean() ? "unendlich Symmetrieachsen" : "eine Kante";
				break;
			case 3:
				message += ThreadLocalRandom.current().nextBoolean() ? "eine Innenwinkelsumme von 180°" : "3 Symmetrieachsen";
				break;
			case 4: 
				message += "Punkt-, aber nicht an Achsensymmetrie";
				break;
			case 5: 
				message += ThreadLocalRandom.current().nextBoolean() ? "8 Ecken" : "8 Symmetrieachsen";
				break;
		}
		message += ".";
		objects[0] = message;
		objects[1] = shape;
		ArrayList<Shape> shapes = new ArrayList<>();
		for(int i = 0; i < 6; i++) {
			if(i != random) {
				shapes.add(getShape(i));
			}
		}
		Collections.shuffle(shapes);
		for(int i = 0; i < 5; i++) {
			objects[i + 2] = shapes.get(i);
		}
		return objects;
	}
	
	public static final Shape setMidLocation(Shape shape, int x, int y) {
		Rectangle bounds = shape.getBounds();
		AffineTransform affineTransform = AffineTransform.getTranslateInstance(x - bounds.x - bounds.width / 2, y - bounds.y - bounds.height / 2);
		return affineTransform.createTransformedShape(shape);
	}
	
}
