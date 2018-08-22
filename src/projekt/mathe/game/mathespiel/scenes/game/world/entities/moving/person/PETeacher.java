package projekt.mathe.game.mathespiel.scenes.game.world.entities.moving.person;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.mathespiel.scenes.game.player.MapPlayer;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.World;

public class PETeacher extends Person {

	private String state; //normal, activated, success
	
	public PETeacher(Scene container, World world, int x, int y) {
		super(container, world, TYPE.SPORTS, x, y, 5);
		state = "normal";
		addPlayerListenerArea(0, 0, 0, 0);
	}

	public void setState(String state) {
		this.state = state;
		switch (state) {
			case "normal" : 
				//irgendwo unten außerhalb der cam
				break;
			default :
				//neben dem eingang
				setDirection("right");
				break;
		}
	}
	
	@Override
	protected void aimChanged(int[] oldAim, int[] newAim) {
		super.aimChanged(oldAim, newAim);
		setMoving(false);
		//open minigamedialog
	}
	
	@Override
	public void onPlayerEntersListenerArea(MapPlayer player) {
		if(state.equals("normal")) {
			state = "activated";
			player.playerController.setActivated(false);
			player.direction = "down";
			setX(player.getX());
			addAim((int) player.getX(), (int) (player.getY() + player.getH()));
			setMoving(true);
			setDirection("up");
		}else if(state.equals("activated")) {
			//open try again dialog
		}
	}
	
	@Override
	public void onInteract(MapPlayer player) {
		if(state.equals("normal")) {
			//mürrischer anfangsdialog
		}else if(state.equals("success")) {
			//was willst du noch - dialog
		}
	}

	@Override
	public void onTick(float delta) {
		super.onTick(delta);
		
	}
	
}
