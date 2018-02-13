package projekt.mathe.game.engine.elements;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

import projekt.mathe.game.engine.Scene;

public abstract class Holder <T extends ScreenElement>{

	private ArrayList<T> list;
	protected Scene container;
	
	public Holder(Scene container){
		list = new ArrayList<T>();
		this.container = container;
	}
	
	public void addElement(T t) {
		list.add(t);
	}
	
	public void removeElement(T t) {
		if(list.contains(t)) {
			list.remove(t);
		}
	}

	public ArrayList<T> getElements(){
		return list;
	}
	
	public void onTick(float delta) {
		for(T t : list) {
			t.onTick(delta);
		}
	}
	
	public void onPaint(Graphics2D g2d) {
		for(T t : list) {
			t.onPerformacePaint(g2d);
		}
	}
	
	public void onMousePressed(MouseEvent e) {
		for(T t : list) {
			t.onMousePressed(e);
		}
	}
	
	public void onMouseReleased(MouseEvent e) {
		for(T t : list) {
			t.onMouseReleased(e);
		}
	}
	
	public void onMouseClicked(MouseEvent e) {
		for(T t : list) {
			t.onMouseClicked(e);
		}
	}
	
	public void onMouseDragged(MouseEvent e) {
		for(T t : list) {
			t.onMouseDragged(e);
		}
	}
	
	public void onMouseMoved(MouseEvent e) {
		for(T t : list) {
			t.onMouseMoved(e);
		}
	}
	
	public void onMouseWheelMoved(MouseWheelEvent e) {
		for(T t : list) {
			t.onMouseWheelMoved(e);
		}
	}
	
	public void onMouseExited(MouseEvent e) {
		for(T t : list) {
			t.onMouseExited(e);
		}
	}
	
}
