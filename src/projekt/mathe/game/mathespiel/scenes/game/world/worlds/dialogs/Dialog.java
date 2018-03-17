package projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import projekt.mathe.game.engine.help.Helper;
import projekt.mathe.game.engine.help.Helper.FONT;
import projekt.mathe.game.engine.help.ResLoader;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.World;

public abstract class Dialog{

	private static Image boxImg = ResLoader.getImageByName("game/dialog/dialog.png");
	private static Image selectionBoxImg = ResLoader.getImageByName("game/dialog/selections.png");
	private static Image selecterImg = ResLoader.getImageByName("game/dialog/selecter.png");
	
	private ArrayList<Card> cards;
	private Card currCard;
	public World world;
	private ArrayList<Integer> wasPressedLastFrame;
	private int index;

	private final static float DELAY = 2;
	private final static short MAXCHARS = 65;
	private float currTextCounter;
	private String currText;
	
	public Dialog(World world) {
		cards = new ArrayList<>();
		this.world = world;
		wasPressedLastFrame = new ArrayList<>();
		index = 0;
		currText = "";
		currTextCounter = 0;
	}
	
	public abstract void onSelected(Card lastcard, boolean finished);
	
	public Dialog reset() {
		for(Card card : cards) {
			card.reset();
		}
		index = 0;
		wasPressedLastFrame.clear();
		currCard = cards.get(0);
		currText = "";
		currTextCounter = 0;
		currCard.openedSelections = false;
		return this;
	}
	
	public void addCard(Card card) {
		cards.add(card);
		if(currCard == null) {
			currCard = card;
		}
	}
	
	public Card getCard(int index) {
		return cards.get(index);
	}
	
	public Card getCurrentCard() {
		return currCard;
	}
	
	public void next() {
		int pos = cards.indexOf(currCard);
		if(currCard.hasSelected()) {
			onSelected(currCard, pos >= cards.size() - 1);
			index = 0;
		}
		if(pos < cards.size() - 1) {
			currCard = cards.get(pos + 1);
			currText = "";
		}else {
			world.closeDialog();
		}
	}
	
	public void press(boolean up, boolean down, boolean enter) {
		if(enter) {
			if(!currText.equals(currCard.message)) {
				currText = currCard.message;
			}else if(currCard.hasSelection()) {
				if(currCard.openedSelections) {
					currCard.select(index);
					next();
					currText = "";
				}else {
					currCard.openedSelections = true;
				}
			}else {
				next();
			}
		}
		if(currCard.hasSelection()) {
			if(up) {
				if(index > 0) {
					index--;
				}
			}
			if(down) {
				if(index < currCard.selections.length - 1) {
					index++;
				}
			}
		}
	}
	
	public void onTick(float delta) {
		
		currTextCounter += delta;
		if(currTextCounter >= DELAY) {
			currTextCounter = 0;
			if(currText.length() < currCard.message.length()) {
				char c = currCard.message.charAt(currText.length());
				currText += c;
				if(c == ' ') {
					currText.length();
				}
			}
		}
		
		boolean up = world.container.keyController.isPressed(KeyEvent.VK_UP);
		boolean down = world.container.keyController.isPressed(KeyEvent.VK_DOWN);
		boolean enter = world.container.keyController.isPressed(KeyEvent.VK_ENTER);
		
		press(up && !wasPressedLastFrame.contains(KeyEvent.VK_UP), down && !wasPressedLastFrame.contains(KeyEvent.VK_DOWN), enter && !wasPressedLastFrame.contains(KeyEvent.VK_ENTER));
		
		if(up) {
			if(!wasPressedLastFrame.contains(KeyEvent.VK_UP)) {
				wasPressedLastFrame.add(KeyEvent.VK_UP);
			}
		}else {
			wasPressedLastFrame.remove(Integer.valueOf(KeyEvent.VK_UP));
		}
		
		if(down) {
			if(!wasPressedLastFrame.contains(KeyEvent.VK_DOWN)) {
				wasPressedLastFrame.add(KeyEvent.VK_DOWN);
			}
		}else {
			wasPressedLastFrame.remove(Integer.valueOf(KeyEvent.VK_DOWN));
		}
		
		if(enter) {
			if(!wasPressedLastFrame.contains(KeyEvent.VK_ENTER)) {
				wasPressedLastFrame.add(KeyEvent.VK_ENTER);
			}
		}else {
			wasPressedLastFrame.remove(Integer.valueOf(KeyEvent.VK_ENTER));
		}
		
	}
	
	private void drawText(Graphics2D g2d) {
		String[] split = currText.split(" ");
		String[] lines = new String[8];
		int index = 0;
		
		String currLine = "";
		int currWordNumber = 0;
		for(String word : split) {
			if(word == null) {
				continue;
			}
			if(currLine.length() + word.length() + currWordNumber <= MAXCHARS) {
				currLine += " " + word;
				currWordNumber++;
				currLine = currLine.trim();
				lines[index] = currLine;
			}else {
				currLine = " " + word;
				currWordNumber = 0;
				index++;
				lines[index] = currLine;
			}
		}

		int y = 435;
		for(String string : lines) {
			if(string != null && !string.equals("null")) {
				Helper.drawStringFromLeft(world.container.camera.translateAbsolutX(200), world.container.camera.translateAbsolutY(y), string.trim(), Color.BLACK, 30, FONT.FreePixel, g2d, null, -1);
				y += 25;
			}
		}
	}
	
	public void onPaint(Graphics2D g2d) {
		g2d.drawImage(boxImg, world.container.camera.translateAbsolutX(140), world.container.camera.translateAbsolutY(397), null);
		drawText(g2d);
		
		if(currCard.hasSelection() && currCard.openedSelections) {
			g2d.drawImage(selectionBoxImg, world.container.camera.translateAbsolutX(1100), world.container.camera.translateAbsolutY(500), 65 + 15 * currCard.getLongestSelection().length(), currCard.selections.length * 30 + 40, null);
			for(int i = 0; i < currCard.selections.length; i++) {
				if(i == index) {
					g2d.drawImage(selecterImg, world.container.camera.translateAbsolutX(1120), world.container.camera.translateAbsolutY(530) + i * 30, 18, 18, null);
					Helper.drawStringFromLeft(world.container.camera.translateAbsolutX(1140), world.container.camera.translateAbsolutY(530) + i * 30, currCard.selections[i], Color.GRAY, 30, FONT.FreePixel, g2d, null, -1);
				}else {
					Helper.drawStringFromLeft(world.container.camera.translateAbsolutX(1120), world.container.camera.translateAbsolutY(530) + i * 30, currCard.selections[i], Color.BLACK, 30, FONT.FreePixel, g2d, null, -1);
				}
			}
		}
	}
	
	public class Card{

		public String message;
		public String[] selections;
		public String selected;
		public boolean openedSelections;
		public int currIndex;
		private String longestSelection;
		
		public Card(String message) {
			this.message = message;
			currIndex = 0;
		}

		public void addSelection(String... selections) {
			this.selections = selections;
			longestSelection = selections[0];
			for(int i = 1; i < selections.length; i++) {
				if(selections[i].length() > longestSelection.length()) {
					longestSelection = selections[i];
				}
			}
			if(longestSelection.length() > 5) {
				throw new NullPointerException("Selektion zu lang!");
			}
		}
		
		public void select(int index) {
			selected = selections[index];
		}
		
		public boolean hasSelected() {
			return selected != null;
		}
		
		public boolean hasSelection() {
			return selections != null;
		}
		
		public String getLongestSelection() {
			return longestSelection;
		}
		
		public void reset() {
			currIndex = 0;
			selected = null;
		}
		
	}
	
}
