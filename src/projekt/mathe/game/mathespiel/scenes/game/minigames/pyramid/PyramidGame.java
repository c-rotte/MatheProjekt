package projekt.mathe.game.mathespiel.scenes.game.minigames.pyramid;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.help.Logger;
import projekt.mathe.game.engine.help.ResLoader;
import projekt.mathe.game.engine.help.TextureHelper;
import projekt.mathe.game.engine.minigame.MiniGame;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.World;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog;

public class PyramidGame extends MiniGame{

	private BlockHolder blockHolder;
	private TextureHelper backgroundHelper;
	private boolean finished;
	private boolean mouseBlocked;
	private boolean notFinishedAgain;
	
	public PyramidGame(Scene container) {
		super(container, "pyramid");
		backgroundHelper = new TextureHelper();
		backgroundHelper.addState("broken", 99999, ResLoader.getImageByName("game/minigames/pyramid/bg_broken.png"));
		blockHolder = new BlockHolder(container);
		renewPyramid();
	}

	public void setMouseBlocked(boolean mouseBlocked) {
		this.mouseBlocked = mouseBlocked;
	}
	
	public void generateBlocks(int startX, int startY, int xSpace, int ySpace) {
		ArrayList<LooseBlock> looseBlocks = new ArrayList<>();
		float[][] values = generateValues();
		int c = 0;
		for(int a = 0; a < 5; a++) {
			int startPos = startX - 200 - a * xSpace;
			for(int i = 1; i <= a + 1; i++) {
				int x = startPos + i * xSpace * 2;
				int y = startY + a * ySpace;
				int w = 100;
				int h = 50;
				looseBlocks.add(new LooseBlock(container, 0, 0, w, h, blockHolder, (int) values[values.length - c - 1][0], (int) values[values.length - c - 1][1]));
				blockHolder.addPlace(new Place(container, x, y, w, h, (int) values[values.length - c - 1][0], (int) values[values.length - c - 1][1]));
				c++;
			}
		}
		
		LooseBlock[] base = new LooseBlock[5];
		
		for(int i = 10; i < 15; i++) {
			base[i - 10] = looseBlocks.get(i);
		}
		
		for(int i = 14; i >= 10; i--) {
			looseBlocks.remove(i);
		}
		
		Collections.shuffle(looseBlocks);
		
		int mx = 130;
		int xc = 140;
		int yc = 540;
		for(LooseBlock looseBlock : looseBlocks) {
			looseBlock.x = xc;
			looseBlock.y = yc;
			xc += mx;
			if(xc > 1280 - mx) {
				xc = 140;
				yc = 620;
			}
		}
		
		for(int i = 10; i < 15; i++) {
			blockHolder.getPlaces().get(i).setOccupied(true);
		}
		
		for(int i = 0; i < 5; i++) {
			LooseBlock block = base[i];
			block.x = 290 + i * 150;
			block.y = 420;
			block.setOccupiedPlaceID(blockHolder.getPlaces().get(blockHolder.getPlaces().size() - 5 + i).getID());
			blockHolder.getPlaces().get(blockHolder.getPlaces().size() - 5 + i).setOccupied(true);
			block.setMoveable(false);
			blockHolder.addElement(block);
		}
		for(LooseBlock looseBlock : looseBlocks) {
			blockHolder.addElement(looseBlock);
		}
	}
	
	private float[][] generateValues(){
		long millis = System.currentTimeMillis();
		ArrayList<float[]> values = new ArrayList<>();
		float[][] base = new float[5][2];
		
		ArrayList<Float> existingValues = new ArrayList<Float>();
		
		for(float[] floats : base) {
			float z;
			float n;
			do {
				n = ThreadLocalRandom.current().nextInt(1, 4 + 1);
				z = ThreadLocalRandom.current().nextInt(1, 4 + 1);
			} while (n == z || existingValues.contains(z < n ? z / n : n / z));
			if(n < z) {
				floats[0] = n;
				floats[1] = z;
				existingValues.add(n / z);
			}else {
				floats[0] = z;
				floats[1] = n;
				existingValues.add(z / n);
			}
			values.add(floats);
		}
		
		float[][] layer = base;
		for(int i = 0; i < 4; i++) {
			layer = generateNextLayer(layer);
			for(float[] fs : layer) {
				values.add(fs);
			}
		}
		Logger.log("PyramidGame: Generated pyramid in " + (System.currentTimeMillis() - millis) + "ms");
		return values.toArray(new float[values.size()][2]);
	}
	
	private float[][] generateNextLayer(float[][] floats){
		float[][] layer = new float[floats.length - 1][2]; 
		for(int i = 0; i < floats.length - 1; i++) {
			float v1 = floats[i][0] / floats[i][1];
			float v2 = floats[i + 1][0] / floats[i + 1][1];
			
			float tol = 0.001f;
			float h1 = 1;
			float h2 = 0;
			float k1 = 0;
			float k2 = 1;
			float b = v1 + v2;
			do {
				float a = (float) Math.floor(b);
				float au = h1;
				h1 = a * h1 + h2;
				h2 = au;
				au = k1;
				k1 = a * k1 + k2;
				k2 = au;
				b = 1 / (b - a);
			} while (Math.abs(v1 + v2 - h1/k1) > (v1 + v2) * tol);
			layer[i][0] = h1;
			layer[i][1] = k1;
		}
		return layer;
	}
	
	public void renewPyramid() {
		finished = false;
		blockHolder.clear();
		generateBlocks(640, 100, 75, 80);
		notFinishedAgain = true;
	}
	
	public int getCorrectBlocks() {
		int i = 0;
		for(LooseBlock looseBlock : blockHolder.getElements()) {
			if(looseBlock.inRightPlace()) {
				i++;
			}
		}
		return i;
	}
	
	public int getPlacedBlocks() {
		int i = 0;
		for(LooseBlock looseBlock : blockHolder.getElements()) {
			if(looseBlock.inPlace()) {
				i++;
			}
		}
		return i;
	}
	
	@Override
	public void onTick(float delta) {
		blockHolder.onTick(delta);
		backgroundHelper.onTick(delta);
		if(getCorrectBlocks() == 15 && !finished) {
			finished = true;
			container.world.openDialog(new SuccessDialog(container.world));
		}else if(!finished && !container.world.isDialogOpen()) {
			if(getPlacedBlocks() == 15 && notFinishedAgain){
				mouseBlocked = true;
				notFinishedAgain = false;
				container.world.openDialog(new FailedDialog(container.world));
			}else if(getPlacedBlocks() != 15){
				notFinishedAgain = true;
			}
		}
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		g2d.drawImage(backgroundHelper.getCurrentImage(), 0, 0, 1280, 720, null);
		g2d.setColor(new Color(1f, 1f, 1f, .5f));
		g2d.fillRect(110, 510, 1070, 190);
		g2d.setColor(Color.DARK_GRAY);
		g2d.setStroke(new BasicStroke(5f));
		g2d.drawRect(110, 510, 1070, 190);
		blockHolder.onPaint(g2d);
	}
	
	@Override
	public void onMouseClicked(MouseEvent e) {
		if(!mouseBlocked) {
			blockHolder.onMouseClicked(e);
		}
	}
	
	@Override
	public void onMouseDragged(MouseEvent e) {
		if(!mouseBlocked) {
			blockHolder.onMouseDragged(e);
		}
	}

	@Override
	public void onMouseExited(MouseEvent e) {
		if(!mouseBlocked) {
			blockHolder.onMouseExited(e);
		}
	}
	
	@Override
	public void onMouseMoved(MouseEvent e) {
		if(!mouseBlocked) {
			blockHolder.onMouseMoved(e);
		}
	}
	
	@Override
	public void onMousePressed(MouseEvent e) {
		if(!mouseBlocked) {
			blockHolder.onMousePressed(e);
		}
	}
	
	@Override
	public void onMouseReleased(MouseEvent e) {
		if(!mouseBlocked) {
			blockHolder.onMouseReleased(e);
		}
	}
	
	@Override
	public void onMouseWheelMoved(MouseWheelEvent e) {
		if(!mouseBlocked) {
			blockHolder.onMouseWheelMoved(e);
		}
	}
	
	private class SuccessDialog extends Dialog{

		public SuccessDialog(World world) {
			super(world);
			addCard(new Card("Du hast es geschafft! Vielen Dank!"));
		}

		@Override
		public void onSelected(Card lastcard, boolean finished) {
			
		}

		@Override
		public void onFinished(Card lastcard) {
			container.callScene("pausenhof", container.getDataForNextScene(), 40f);
		}
		
	}
	
	private class FailedDialog extends Dialog{

		public FailedDialog(World world) {
			super(world);
			addCard(new Card("Das stimmt leider nicht. Überprüfe deine Auswahl bitte noch einmal."));
		}

		@Override
		public void onSelected(Card lastcard, boolean finished) {
			
		}

		@Override
		public void onFinished(Card lastcard) {
			mouseBlocked = false;
		}
		
	}
	
}
