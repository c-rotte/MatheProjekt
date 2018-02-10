package projekt.mathe.game.engine.help;

public class Animator {

	private float startVal;
	private float value, diff;
	
	public Animator(float value, float diff) {
		this.value = value;
		this.startVal = value;
		this.diff = diff;
	}
	
	public void calculate(float delta) {
		value -= diff * delta;
	}
	
	public float getCurrValue() {
		return value;
	}
	
	public float getBeginningValue() {
		return startVal;
	}
	
	public boolean finished() {
		if(value <= 0) {
			value = 0;
		}
		return value <= 0;
	}
	
	public void reset() {
		value = startVal;
	}
	
	public float getCurrValueRelative() {
		return 1f - (Float.valueOf(value) / Float.valueOf(startVal));
	}
	
}
