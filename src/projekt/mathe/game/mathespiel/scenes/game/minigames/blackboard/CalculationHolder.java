package projekt.mathe.game.mathespiel.scenes.game.minigames.blackboard;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.Holder;

public class CalculationHolder extends Holder<Calculation>{

	public CalculationHolder(Scene container) {
		super(container);
	}

	public boolean containsBoth() {
		boolean right = false;
		boolean wrong = false;
		for(Calculation calculation : getElements()) {
			if(calculation.isRight()) {
				right = true;
			}else {
				wrong = true;
			}
			if(right && wrong) {
				return true;
			}
		}
		return false;
	}

	public int getWrong() {
		int wrong = 0;
		for(Calculation calculation : getElements()) {
			if(!calculation.isRight()) {
				wrong++;
			}
		}
		return wrong;
	}
	
	public boolean completed() {
		for(Calculation calculation : getElements()) {
			if(calculation.isRight() && calculation.isSelected()) {
				return false;
			}
			if(!calculation.isRight() && !calculation.isSelected()) {
				return false;
			}
		}
		return true;
	}
	
}
