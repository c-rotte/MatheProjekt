package projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs;

import projekt.mathe.game.mathespiel.scenes.game.world.worlds.World;

public class TestDialog extends Dialog{

	public TestDialog(World world) {
		super(world);
		Card card1 = new Card("Testdialog abc 123 test1234");
		Card card2 = new Card("Frage?");
		Card card3 = new Card("Frage2??");
		card3.addSelection("ja", "nein", "vllt", "123", "abc");
		Card card4 = new Card("wieso?");
		card4.addSelection(":D", "ne", "ok");	
		addCard(card1);
		addCard(card2);
		addCard(card3);
		addCard(card4);
	}

	@Override
	public void onSelected(Card lastcard, boolean finished) {

	}

}
