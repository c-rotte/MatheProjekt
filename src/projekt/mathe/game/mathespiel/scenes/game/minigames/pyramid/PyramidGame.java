package projekt.mathe.game.mathespiel.scenes.game.minigames.pyramid;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.minigame.MiniGame;

public class PyramidGame extends MiniGame{

	private BlockHolder blockHolder;
	
	public PyramidGame(Scene container) {
		super(container, "pyramid");
		blockHolder = new BlockHolder(container);
		generateBlocks(640, 100, 75, 80);
	}

	public void generateBlocks(int startX, int startY, int xSpace, int ySpace) {
		blockHolder.clear();
		ArrayList<LooseBlock> looseBlocks = new ArrayList<>();
		ArrayList<Float> values = generateValues();
		int c = 0;
		for(int a = 0; a < 5; a++) {
			int startPos = startX - 200 - a * xSpace;
			for(int i = 1; i <= a + 1; i++) {
				int x = startPos + i * xSpace * 2;
				int y = startY + a * ySpace;
				int w = 100;
				int h = 50;
				looseBlocks.add(new LooseBlock(container, 0, 0, w, h, blockHolder, values.get(values.size() - c - 1)));
				blockHolder.addPlace(new Place(container, x, y, w, h, values.get(values.size() - c - 1)));
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
			blockHolder.addBlock(block);
		}
		for(LooseBlock looseBlock : looseBlocks) {
			blockHolder.addBlock(looseBlock);
		}
	}
	
	private ArrayList<Float> generateValues(){
		ArrayList<Float> floats = new ArrayList<>();
		Random random = new Random();
		float[][] base = new float[5][2];
		ArrayList<Float> existingBaseValues = new ArrayList<>(); 
		for(int i = 0; i < 5; i++) {
			int n1;
			int n2;
			do {
				n1 = random.nextInt(4) + 1;
				n2 = random.nextInt(4) + 1;
			} while (n1 == n2 || (n1 < n2 ? existingBaseValues.contains(Float.valueOf(n1) / Float.valueOf(n2)) : existingBaseValues.contains(Float.valueOf(n2) / Float.valueOf(n1))));
			if(n1 < n2) {
				base[i][0] = n1;
				base[i][1] = n2;
				existingBaseValues.add(Float.valueOf(n1) / Float.valueOf(n2));
			}else {
				base[i][1] = n1;
				base[i][0] = n2;
				existingBaseValues.add(Float.valueOf(n2) / Float.valueOf(n1));
			}
		}
		float[] row2 = new float[4];
		for(int i = 0; i < 4; i++) {
			float value = (base[i][0] / base[i][1]) + (base[i + 1][0] / base[i + 1][1]);
			row2[i] = value;
		}
		float[] row3 = new float[3];
		for(int i = 0; i < 3; i++) {
			float value = row2[i] + row2[i + 1];
			row3[i] = value;
		}
		float[] row4 = new float[2];
		for(int i = 0; i < 2; i++) {
			float value = row3[i] + row3[i + 1];
			row4[i] = value;
		}
		float row5 = row4[0] + row4[1];
		
		for(float[] f : base) {
			floats.add((float) (f[0] / f[1]));
		}
		for(float f : row2) {
			floats.add(f);
		}
		for(float f : row3) {
			floats.add(f);
		}
		for(float f : row4) {
			floats.add(f);
		}
		floats.add(row5);
		return floats;
	}
	
	@Override
	public void onTick(float delta) {
		blockHolder.onTick(delta);
		int i = 0;
		for(LooseBlock looseBlock : blockHolder.getElements()) {
			if(looseBlock.inRightPlace()) {
				i++;
			}
		}
		System.out.println(i);
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		blockHolder.onPaint(g2d);
	}
	
	@Override
	public void onMouseClicked(MouseEvent e) {
		blockHolder.onMouseClicked(e);
	}
	
	@Override
	public void onMouseDragged(MouseEvent e) {
		blockHolder.onMouseDragged(e);
	}

	@Override
	public void onMouseExited(MouseEvent e) {
		blockHolder.onMouseExited(e);
	}
	
	@Override
	public void onMouseMoved(MouseEvent e) {
		blockHolder.onMouseMoved(e);
	}
	
	@Override
	public void onMousePressed(MouseEvent e) {
		blockHolder.onMousePressed(e);
	}
	
	@Override
	public void onMouseReleased(MouseEvent e) {
		blockHolder.onMouseReleased(e);
	}
	
	@Override
	public void onMouseWheelMoved(MouseWheelEvent e) {
		blockHolder.onMouseWheelMoved(e);
	}
	
}
