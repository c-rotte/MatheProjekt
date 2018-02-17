package projekt.mathe.game.engine.particle;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.Holder;

public class ParticleHolder extends Holder<Particle>{

	private float SPEED;
	private int RADIUS;
	private Color COLOR;
	
	public ParticleHolder(Scene container, float SPEED, int RADIUS, int AMOUNT, Color COLOR) {
		super(container);
		this.SPEED = SPEED;
		this.RADIUS = RADIUS;
		this.COLOR = COLOR;
		for(int i = 0; i < AMOUNT; i++) {
			addRandomCenteredParticle();
		}
	}

	@Override
	public void onTick(float delta) {
		super.onTick(delta);
		for(Particle particle : new ArrayList<>(getElements())) {
			if(particle.outOfBounds()) {
				removeElement(particle);
				addRandomCenteredParticle();
			}
		}
	}
	
	public void addRandomCenteredParticle() {
		Point pos = new Point(ThreadLocalRandom.current().nextInt(1, 1280), ThreadLocalRandom.current().nextInt(1, 720));
		Point aim = ThreadLocalRandom.current().nextBoolean() ? new Point(ThreadLocalRandom.current().nextBoolean() ? 0 : 1280, ThreadLocalRandom.current().nextInt(1, 720)) : new Point(ThreadLocalRandom.current().nextInt(1, 1280), ThreadLocalRandom.current().nextBoolean() ? 0 : 721);
		addElement(new Particle(container, pos, aim, SPEED, RADIUS, COLOR));
	}

}
