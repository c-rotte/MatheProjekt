package projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs;

import projekt.mathe.game.mathespiel.scenes.MainSceneData;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.World;

public class TestDialog extends Dialog{

	public TestDialog(World world) {
		super(world);
		Card card1 = new Card("Das ist ein Test für das Pyramidenspiel. Möchtest du anfangen?");
		card1.addSelection("ja", "nein");
		addCard(card1);
	}

	@Override
	public void onSelected(Card lastcard, boolean finished) {
		if(finished) {
			if(lastcard.selected.equals("ja")) {
				world.container.callScene("pyramid", new MainSceneData(), 40f);
			}
			
		}
	}

	@Override
	public void onFinished(Card lastcard) {
	
	}

}
