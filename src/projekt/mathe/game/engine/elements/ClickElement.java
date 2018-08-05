package projekt.mathe.game.engine.elements;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import projekt.mathe.game.engine.Scene;

public abstract class ClickElement extends ScreenElement{

	public static enum STATE{ 
		UNTOUCHED,
		HOVERED,
		PRESSED
	}
	
	private STATE clickstate; //momentaner Zustand
	
	public ClickElement(Scene container, int x, int y, int w, int h) {
		super(container, x, y, w, h);
		clickstate = STATE.UNTOUCHED; //anfangs unber�hrt
	}
	
	public abstract void onClicked(MouseEvent e); //wird ausgef�hrt, falls der Nutzer auf das Element geklickt hat
	
	public abstract void onMisClicked(MouseEvent e); //wird ausgef�hrt, falls der Nutzer neben das Element geklickt hat
	
	public STATE getState() {
		return clickstate;
	}
	
	@Override
	public void onMouseMoved(MouseEvent e) {
		clickstate = (getBounds().contains(e.getPoint()) ? STATE.HOVERED : STATE.UNTOUCHED); //Zustand ist "ber�hrt", falls der Mauszeiger innerhalb des Elements ist, sonst "unber�hrt"
		super.onMouseMoved(e);
	}
	
	@Override
	public void onMouseDragged(MouseEvent e) {
		clickstate = (getBounds().contains(e.getPoint()) ? STATE.PRESSED : STATE.UNTOUCHED);	//Zustand ist "gedr�ckt", falls der Mauszeiger innerhalb des Elements ist, sonst "unber�hrt"
		super.onMouseDragged(e);
	}
	
	@Override
	public void onMousePressed(MouseEvent e) {
		if(clickstate.equals(STATE.HOVERED) && getBounds().contains(e.getPoint())) { //Zustand ist "gedr�ckt", falls der Mauszeiger innerhalb des Elements ist
			clickstate = STATE.PRESSED;
		}
		super.onMousePressed(e);
	}
	
	@Override
	public void onMouseReleased(MouseEvent e) {
		if(clickstate.equals(STATE.PRESSED) && getBounds().contains(e.getPoint())) { //Zustand ist "ber�hrt", falls der Mauszeiger innerhalb des Elements ist, sonst "unber�hrt" -> entsprechender Methodenaufruf
			clickstate = STATE.HOVERED;
			onClicked(e);
		}else {
			clickstate = STATE.UNTOUCHED;
			onMisClicked(e);
		}
		super.onMouseReleased(e);
	}
	
	@Override
	public void onMouseWheelMoved(MouseWheelEvent e) {
		clickstate = (getBounds().contains(e.getPoint()) ? STATE.HOVERED : STATE.UNTOUCHED); //Zustand ist "ber�hrt", falls der Mauszeiger innerhalb des Elements ist, sonst "unber�hrt"
		super.onMouseWheelMoved(e);
	}
	
}
