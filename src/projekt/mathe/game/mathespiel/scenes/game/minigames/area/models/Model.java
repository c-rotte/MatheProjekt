package projekt.mathe.game.mathespiel.scenes.game.minigames.area.models;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;

public abstract class Model {

	private Polygon polygon;
	private Point A, B, C, D;
	private TYPE type;
	
	public static enum TYPE {
		PARALLELOGRAMM,
		TRAPEZ,
		DREIECK,
		RECHTECK
	}
	
	public Model(TYPE type, Point A, Point B, Point C, Point D) {
		this.type = type;
		this.A = A;
		this.B = B;
		this.C = C;
		this.D = D;
		reloadShape();
	}

	public void reloadShape() {
		if(type.equals(TYPE.DREIECK)) {
			polygon = new Polygon(new int[] {A.x, B.x, C.x}, new int[] {A.y, B.y, C.y}, 3);
		}else {
			polygon = new Polygon(new int[] {A.x, B.x, C.x, D.x}, new int[] {A.y, B.y, C.y, D.y}, 4);
		}
	}
	
	public abstract void onPaint(Graphics2D g2d);
	
	public Polygon getPolygon() {
		return polygon;
	}

	public Point getA() {
		return A;
	}

	public Point getB() {
		return B;
	}

	public Point getC() {
		return C;
	}

	public Point getD() {
		return D;
	}

	public TYPE getType() {
		return type;
	}

	public void setA(Point a) {
		A = a;
	}

	public void setB(Point b) {
		B = b;
	}

	public void setC(Point c) {
		C = c;
	}

	public void setD(Point d) {
		D = d;
	}
	
}
