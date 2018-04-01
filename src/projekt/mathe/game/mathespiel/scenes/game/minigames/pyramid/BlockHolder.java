package projekt.mathe.game.mathespiel.scenes.game.minigames.pyramid;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.UUID;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.Holder;

public class BlockHolder extends Holder<LooseBlock>{

	private ArrayList<Place> places;
	
	public BlockHolder(Scene container) {
		super(container);
		places = new ArrayList<>();
	}

	@Override
	public void clear() {
		super.clear();
		places.clear();
	}
	
	public boolean grabbed() {
		for(LooseBlock looseBlock : getElements()) {
			if(looseBlock.wasGrabbed()) {
				return true;
			}
		}
		return false;
	}
	
	public void addPlace(Place place) {
		places.add(place);
	}
	
	public Place getPlaceByID(UUID id) {
		for(Place place : places) {
			if(place.getID().equals(id)) {
				return place;
			}
		}
		return null;
	}
	
	public Place getBoundsOfNearestPlace(LooseBlock looseBlock) {
		for(Place place : places) {
			if(Math.pow((looseBlock.getX() + looseBlock.getW()/2) - (place.getX() + place.getW()/2), 2) + Math.pow((looseBlock.getY() + looseBlock.getH()/2) - (place.getY() + place.getH()/2), 2) <= Math.pow(45, 2)) {
				return place;
			}
		}
		return null;
	}
	
	public void setPlaceOccupied(UUID id, boolean occupied) {
		for(Place place : places) {
			if(place.getID().equals(id)) {
				place.setOccupied(occupied);
				return;
			}
		}
	}
	
	public ArrayList<Place> getPlaces(){
		return places;
	}
	
	@Override
	public void onTick(float delta) {
		for(Place place : places) {
			place.onTick(delta);
		}
		super.onTick(delta);
	}
	
	@Override
	public void onMousePressed(MouseEvent e) {
		for(LooseBlock looseBlock : getElements()) {
			if(looseBlock.getBounds().contains(e.getPoint())) {
				removeElement(looseBlock);
				getElements().add(0, looseBlock);
				break;
			}
		}
		super.onMousePressed(e);
	}
	
	@Override
	public void onPaint(Graphics2D g2d) {
		for(Place place : places) {
			place.onPerformacePaint(g2d);
		}
		for(int i = getElements().size() - 1; i >= 0; i--) {
			getElements().get(i).onPerformacePaint(g2d);
		}
	}
	
}
