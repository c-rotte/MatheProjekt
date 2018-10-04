package projekt.mathe.game.mathespiel.scenes.game.minigames.boss;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JButton;

import projekt.mathe.game.engine.GameScene;
import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.Holder;
import projekt.mathe.game.engine.elements.ScreenElement;
import projekt.mathe.game.engine.help.Animator;
import projekt.mathe.game.engine.help.Helper;
import projekt.mathe.game.engine.help.Helper.FONT;
import projekt.mathe.game.engine.help.ResLoader;
import projekt.mathe.game.engine.help.TextureHelper;
import projekt.mathe.game.mathespiel.scenes.MainSceneData;
import projekt.mathe.game.mathespiel.scenes.game.minigames.blackboard.TimeBar;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.World;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog.Card;

public class Wand extends ScreenElement{

	private FensterHolder fensterHolder;
	private Animator openDelay, closeDelay;
	
	private Object[] currMessage;
	
	private TimeBar timeBar;
	
	private static final Image wand = ResLoader.getImageByName("game/minigames/boss/wand.jpg");
	private static final Image heart = ResLoader.getImageByName("game/minigames/boss/heart.png");
	private static final Image schleudersymbol = ResLoader.getImageByName("game/minigames/boss/schleudersymbol.png");
	private static final Image pillar = ResLoader.getImageByName("game/minigames/boss/pillar.png");
	private static final Image floor = ResLoader.getImageByName("game/minigames/boss/floor.jpg");
	private static final Image heaven = ResLoader.getImageByName("game/minigames/boss/heaven.jpg");
	private int bosslife;
	
	public Wand(Scene container) {
		super(container, 140, 40, 1000, 440);
		currMessage = ShapeGenerator.generateMessage();
		fensterHolder = new FensterHolder(container);
		for(int x = 0; x < 3; x++) {
			for(int y = 0; y < 2; y++) {
				fensterHolder.addElement(new Fenster(container, 140 + 104 + 320 * x, 40 + 50 + 190 * y, 150, 150));
			}
		}
		fensterHolder.randomBoss(currMessage);
		openDelay = new Animator(13f * 60, 1);
		openDelay.setValue(5f);
		closeDelay = new Animator(2.5f * 60, 1);
		timeBar = new TimeBar(container, 905, 630, 365, 30, 13 * 60, 10);
		bosslife = 10;
		container.world.openDialog(new ExplanationDialog(container.world));
	}

	private class ExplanationDialog extends Dialog{

		public ExplanationDialog(World world) {
			super(world);
			addCard(new Card("Das ist Herr Flow, ein ehemaliger Lehrer aus England. Aber wieso ist er hier? Er ist doch  bereits vor 15 Jahren in Pension gegangen."));
			Card card = new Card("Ich bin seit meiner Pension immer hier, wenn keine Schüler anwesend sind, deshalb hat das auch noch niemand bemerkt.");
			card.setTextColor(Color.RED);
			addCard(card);
			Card card2 = new Card("Die heutige Art und Weise, wie Schüler unterrichtet werden, ist völlig falsch! Deshalb kontrolliere ich alles heimlich!");
			card2.setTextColor(Color.RED);
			addCard(card2);
			addCard(new Card("Der spinnt ja! Los, bewirf ihn mit Papier, damit er verschwindet!"));
			addCard(new Card("Halt dazu die Maus auf der Munitionshalterung gedrückt und ziehe diese nach unten."));
			addCard(new Card("Dann siehst du, wohin die Papierkugel fliegt, wenn du die Maus wieder loslässt."));
			Card card3 = new Card("Hast du alles verstanden?");
			card3.addSelection("Ja", "Nein");
			addCard(card3);
		}

		@Override
		public void onSelected(Card lastcard, boolean finished) {
			if(finished) {
				if(lastcard.selected.equals("Ja")) {
					world.openDialog(new GoDialog(world).reset());
				}else {
					world.openDialog(this.reset());
				}
			}
		}

		@Override
		public void onFinished(Card lastcard) {
			
		}
		
	}
	
	private class GoDialog extends Dialog {

		public GoDialog(World world) {
			super(world);
			addCard(new Card("Na, dann los! Viel Glück!"));
			Card card = new Card("Du willst mich abwerfen? Na, das werden wir ja sehen!");
			card.setTextColor(Color.RED);
			addCard(card);
		}

		@Override
		public void onSelected(Card lastcard, boolean finished) {

		}

		@Override
		public void onFinished(Card lastcard) {
			if(fensterHolder.getCurrBossFenster().getOpen().equals(Fenster.OPEN.OPEN)) {
				fensterHolder.slide();
				closeDelay.reset();
				openDelay.reset();
				timeBar.reset();
			}
			((GameScene) getContainer()).getMouseRegisteredMiniGame().setMouseBlocked(false);
		}
		
	}
	
	public FensterHolder getFensterHolder() {
		return fensterHolder;
	}
	
	@Override
	public void onTick(float delta) {
		fensterHolder.onTick(delta);
		if(!openDelay.finished()) {
			openDelay.calculate(delta);
			timeBar.onTick(delta);
			if(openDelay.finished()) {
				fensterHolder.slide();
			}
		}else if(!closeDelay.finished()) {
			closeDelay.calculate(delta);
			if(closeDelay.finished() && !getContainer().world.isDialogOpen()) {
				fensterHolder.slide();
				closeDelay.reset();
				openDelay.reset();
				timeBar.reset();
			}
		}
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		g2d.drawImage(heaven, 0, 0, null);
		g2d.drawImage(wand, (int) getX(), (int) getY(), null);
		g2d.drawImage(floor, 0, 480, null);
		g2d.setColor(new Color(0, 0, 0, 150));
		g2d.fillRect(0, 475, 1280, 35);
		g2d.drawImage(pillar, 0, 19, null);
		g2d.drawImage(pillar, 1140, 19, null);
		g2d.setColor(new Color(56, 56, 56));
		g2d.fillRect(120, 29, 1040, 11);
		g2d.fillRect(120, 480, 1040, 12);
		fensterHolder.onPaint(g2d);
		getContainer().fillScene(g2d, new Color(21, 12, 21), 0.5f);
		timeBar.onPaint(g2d);
		if(fensterHolder.getCurrBossFenster().isHittable() && openDelay.getCurrValue() <= 60f && openDelay.getCurrValue() > 0f && fensterHolder.firstCloseFix) {
			g2d.drawImage(schleudersymbol, 875, 630, null);
		}
		g2d.setColor(new Color(0, 0, 0, 150));
		g2d.fillRect(870, 670, 400, 40);
		for(int x = 0; x < 10; x++) {
			Composite composite = g2d.getComposite();
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
			g2d.drawImage(heart, 875 + x * 40, 675, null);
			g2d.setComposite(composite);
		}
		for(int x = 0; x < bosslife; x++) {
			g2d.drawImage(heart, 875 + x * 40, 675, null);
		}
	}
	
	public void drawBubble(Graphics2D g2d) {
		if(!getContainer().world.isDialogOpen() && fensterHolder.getCurrBossFenster() != null && fensterHolder.getCurrBossFenster().isHittable() && !fensterHolder.getCurrBossFenster().wasBossHit() && !getContainer().world.isDialogOpen()) {
			g2d.setColor(Color.WHITE);
			g2d.fillRect(446, 495, 18, 30);
			g2d.fillRoundRect(60, 520, 450, 150, 30, 30);
			g2d.setColor(Color.BLACK);
			g2d.setStroke(new BasicStroke(10f));
			g2d.drawRoundRect(60, 520, 450, 150, 30, 30);
			g2d.setColor(Color.WHITE);
			int[] xTri = {480, 455, 430};
			int[] yTri = {530, 490, 530};
			g2d.fillPolygon(xTri, yTri, 3);
			g2d.setColor(Color.BLACK);
			g2d.setStroke(new BasicStroke(10f));
			g2d.drawLine(480, 530, 455, 490);
			g2d.drawLine(455, 490, 430, 530);
			String[] split = currMessage[0].toString().split(" ");
			String line1 = "", line2 = "";
			for(int i = 0; i < split.length; i++) {
				if(i < (split.length + 1) / 2) {
					line1 += split[i] + " ";
				}else {
					line2 += split[i] + " ";
				}
			}
			Helper.drawStringAroundPosition(285, 575, line1.trim(), Color.RED, 24, FONT.VCR, g2d, null, -1);
			Helper.drawStringAroundPosition(285, 615, line2.trim(), Color.RED, 24, FONT.VCR, g2d, null, -1);
		}
	}
	
	public class FensterHolder extends Holder<Fenster> {

		private int hits, successfullHits;
		private boolean hittable;
		private Fenster currBossFenster;
		
		public FensterHolder(Scene container) {
			super(container);
			hits = 0;
			successfullHits = 0;
		}
		
		public Fenster getCurrBossFenster() {
			return currBossFenster;
		}
		
		public boolean isHittable() {
			return hittable;
		}
		
		public int getHits() {
			return hits;
		}
		
		public void setHits(int hits) {
			this.hits = hits;
		}
		
		public int getSuccessfullHits() {
			return successfullHits;
		}
		
		public void slide() {
			getElements().forEach(fenster -> fenster.slide());
		}
		
		public void randomBoss(Object[] message) {
			Collections.shuffle(getElements());
			for(int i = 0; i < getElements().size(); i++) {
				getElements().get(i).setBoss(i == 0, (Shape) message[i + 1]);
			}
			currBossFenster = getElements().get(0);
		}
		
		public boolean hit(int x, int y) {
			if(Wand.this.getBounds().contains(new Point(x, y))) {
				hits++;
				if(hittable) {
					for(Fenster fenster : getElements()) {
						if(fenster.hasBoss()) {
							boolean hit = fenster.hit(x, y);
							if(hit) {
								successfullHits++;
								bosslife--;
								if(bosslife <= 0) {
									MainSceneData mainSceneData = (MainSceneData) container.getDataForNextScene();
									mainSceneData.setMinigameCompleted(true);
									container.callScene("boss_win", mainSceneData, 0f);
								}
								if(bosslife == 9) {
									Dialog firstHit = new Dialog(container.world) {
										@Override
										public void onSelected(Card lastcard, boolean finished) {
											
										}
										@Override
										public void onFinished(Card lastcard) {
											if(currBossFenster.getOpen().equals(Fenster.OPEN.OPEN)) {
												slide();
												closeDelay.reset();
												openDelay.reset();
												timeBar.reset();
												((GameScene) container).getMouseRegisteredMiniGame().setMouseBlocked(false);
											}
										}
									};
									Card card = new Card("Was? Wie kann das sein? Das ist unmöglich!");
									card.setTextColor(Color.RED);
									firstHit.addCard(card);
									firstHit.addCard(new Card("Super! Mach genauso weiter!"));
									container.world.openDialog(firstHit);
									((GameScene) container).getMouseRegisteredMiniGame().onMouseReleased(new MouseEvent(new JButton(), -1, -1, -1, 0, 0, -1, false));
									((GameScene) container).getMouseRegisteredMiniGame().setMouseBlocked(true);		
								}
							}
							return hit;
						}
					}
				}
				if(hits == 3) {
					Dialog dialog = new Dialog(container.world) {
						@Override
						public void onSelected(Card lastcard, boolean finished) {
							
						}
						@Override
						public void onFinished(Card lastcard) {
							world.openDialog(new ExplanationDialog(container.world));
						}
					};
					Card c1 = new Card("HAHAHA! Was soll das denn werden? Du triffst mich niemals!");
					c1.setTextColor(Color.RED);
					dialog.addCard(c1);
					dialog.addCard(new Card("Das klappt einfach nicht, die Fenster schließen sich zu schnell."));
					dialog.addCard(new Card("Ich fürchte, diesen Kampf kannst du nicht gewinnen!"));
					dialog.addCard(new Card("..."));
					dialog.addCard(new Card("Moment!"));
					dialog.addCard(new Card("Ich glaube, er verrät sich!"));
					Card c2 = new Card("3 Symmetrieachsen, die nehme... ich meine du wirst mich nie besiegen HAHAHA!");
					c2.setTextColor(Color.RED);
					dialog.addCard(c2);
					getContainer().world.openDialog(dialog);
					((GameScene) container).getMouseRegisteredMiniGame().onMouseReleased(new MouseEvent(new JButton(), -1, -1, -1, 0, 0, -1, false));
					((GameScene) container).getMouseRegisteredMiniGame().setMouseBlocked(true);
				}
			}
			return false;
		}
		
		protected boolean firstCloseFix;
		@Override
		public void onTick(float delta) {
			Fenster.OPEN open = currBossFenster.getOpen();
			super.onTick(delta);
			if(open.equals(Fenster.OPEN.CLOSING) && currBossFenster.getOpen().equals(Fenster.OPEN.CLOSED)) {
				if(!currBossFenster.isHittable()) {
					currMessage = ShapeGenerator.generateMessage();
				}
				if(currBossFenster.isHittable() && firstCloseFix) {
					currMessage = ShapeGenerator.generateMessage();
				}else {
					firstCloseFix = "(Die 3 Symmetrieachsen sind wirklich eine gute Wahl!)".equals(currMessage[0].toString());
				}
				randomBoss(currMessage);
			}
		}
		
		private class ExplanationDialog extends Dialog {

			public ExplanationDialog(World world) {
				super(world);
				addCard(new Card("Jetzt können wir ihn schlagen! Ziele einfach schon BEVOR sich die Fenster öffnen auf das Fenster mit der Form, die er beschreibt!"));
				addCard(new Card("KURZ BEVOR sich die Fenster öffnen musst du bereits die Maustaste loslassen, damit die Papierkugel rechtzeitig trifft."));
				addCard(new Card("(Der optimale Zeitpunkt wird übrigens am Symbol neben der Leiste angezeigt.)"));
				Card card = new Card("Hast du alles verstanden?");
				card.addSelection("Ja", "Nein");
				addCard(card);
			}

			@Override
			public void onSelected(Card lastcard, boolean finished) {
				if(finished) {
					if(lastcard.selected.equals("Ja")) {
						Dialog dialog = new Dialog(world) {	
							@Override
							public void onSelected(Card lastcard, boolean finished) {
								
							}
							@Override
							public void onFinished(Card lastcard) {
								if(currBossFenster.getOpen().equals(Fenster.OPEN.OPEN)) {
									slide();
									closeDelay.reset();
									openDelay.reset();
									timeBar.reset();
								}
								hittable = true;
								for(Fenster fenster : getElements()) {
									fenster.setHittable(true);
								}
								((GameScene) container).getMouseRegisteredMiniGame().setMouseBlocked(false);
								currMessage = ShapeGenerator.generateMessage(3);
								currMessage[0] = "(Die 3 Symmetrieachsen sind wirklich eine gute Wahl!)";
							}
						};
						dialog.addCard(new Card("Viel Glück!"));
						Card c3 = new Card("Gib doch einfach auf! HAHAHA!");
						c3.setTextColor(Color.RED);
						dialog.addCard(c3);
						world.openDialog(dialog);
					}else {
						world.openDialog(this.reset());
					}
				}
			}

			@Override
			public void onFinished(Card lastcard) {
				
			}
			
		}
		
	}
	
	public static class Fenster extends ScreenElement {

		private static final Image bg = ResLoader.getImageByName("game/minigames/boss/windowbg.jpg");
		private static final Image rollade = ResLoader.getImageByName("game/minigames/boss/rollade.jpg");
		
		public static enum OPEN {
			OPEN,
			CLOSING,
			CLOSED,
			OPENING
		}
		
		private static final float SPEED = 3.5f;
		
		private int startX, startY;
		private OPEN open;
		private char sliding;
		
		private String boss;
		
		private Animator hitAnimator;
		private BossTextureHelper bossTextureHelper;
		private boolean hittable;
		
		private Shape correctShape;
		private ShapeAnimator shapeAnimator;
		
		public Fenster(Scene container, int x, int y, int w, int h) {
			super(container, x, y, w, h);
			startX = x;
			startY = y;
			sliding = ' ';
			hitAnimator = new Animator(1 * 60, 1);
			bossTextureHelper = new BossTextureHelper();
			open = OPEN.CLOSED;
			shapeAnimator = new ShapeAnimator(20, 5);
		}

		public void setBoss(boolean boss, Shape shape) {
			this.boss = boss ? "boss" : null;
			correctShape = shape;
			shapeAnimator.reset();
		}
		
		public void setHittable(boolean hittable) {
			this.hittable = hittable;
		}
		
		public boolean isHittable() {
			return hittable;
		}
		
		public OPEN getOpen() {
			return open;
		}
		
		public boolean hasBoss() {
			return boss != null;
		}
		
		public boolean wasBossHit() {
			return boss.equals("hit");
		}
		
		public boolean hit(int x, int y) {
			if(hittable && open.equals(OPEN.OPEN) && new java.awt.Rectangle(startX, startY, (int) getW(), (int) getH()).contains(x, y)) {
				boss = "hit";
				bossTextureHelper.switchState("hit");
				hitAnimator.reset();
				return true;
			}
			return false;
		}
		
		public void slide() {
			if(open.equals(OPEN.CLOSED)) {
				switch (ThreadLocalRandom.current().nextInt(1, 5)) {
					case 1: sliding = 'u'; break; 
					case 2: sliding = 'd'; break;
					case 3: sliding = 'l'; break;
					case 4: sliding = 'r'; break;
				}
				open = OPEN.OPENING;
			}else if(open.equals(OPEN.OPEN)){
				if(getX() > startX) {
					sliding = 'l';
				}else if(getX() < startX) {
					sliding = 'r';
				}else if(getY() > startY) {
					sliding = 'u';
				}else if(getY() < startY) {
					sliding = 'd';
				}
				open = OPEN.CLOSING;
			}
		}
		
		@Override
		public void onTick(float delta) {
			if(sliding != ' ' && !"hit".equals(boss)) {
				switch (sliding) {
					case 'u':
						addToY(-SPEED * delta);
						if(open.equals(OPEN.OPENING)) {
							if(getY() <= startY - getH()) {
								setY(startY - getH());
								sliding = ' ';
								open = OPEN.OPEN;
							}
						}else if(open.equals(OPEN.CLOSING)){
							if(getY() <= startY) {
								setY(startY);
								sliding = ' ';
								open = OPEN.CLOSED;
							}
						}
						break;
					case 'd':
						addToY(SPEED * delta);
						if(open.equals(OPEN.OPENING)) {
							if(getY() >= startY + getH()) {
								setY(startY + getH());
								sliding = ' ';
								open = OPEN.OPEN;
							}
						}else if(open.equals(OPEN.CLOSING)){
							if(getY() >= startY) {
								setY(startY);
								sliding = ' ';
								open = OPEN.CLOSED;
							}
						}
						break;
					case 'l':
						addToX(-SPEED * delta);
						if(open.equals(OPEN.OPENING)) {
							if(getX() <= startX - getW()) {
								setX(startX - getW());
								sliding = ' ';
								open = OPEN.OPEN;
							}
						}else if(open.equals(OPEN.CLOSING)){
							if(getX() <= startX) {
								setX(startX);
								sliding = ' ';
								open = OPEN.CLOSED;
							}
						}
						break;
					case 'r':
						addToX(SPEED * delta);
						if(open.equals(OPEN.OPENING)) {
							if(getX() >= startX + getW()) {
								setX(startX + getW());
								sliding = ' ';
								open = OPEN.OPEN;
							}
						}else if(open.equals(OPEN.CLOSING)){
							if(getX() >= startX) {
								setX(startX);
								sliding = ' ';
								open = OPEN.CLOSED;
							}
						}
						break;
				}
			}
			if(correctShape != null) {
				if(!shapeAnimator.finished()) {
					shapeAnimator.onTick(delta);
				}
			}
			if("hit".equals(boss)) {
				hitAnimator.calculate(delta);
				bossTextureHelper.onTick(delta);
				if(hitAnimator.finished()) {
					boss = "boss";
					bossTextureHelper.switchState("boss");
				}
			}
		}

		@Override
		public void onPaint(Graphics2D g2d) {
			Shape shape = g2d.getClip();
			g2d.clipRect(startX, startY, (int) getW(), (int) getH());
			g2d.drawImage(bg, startX + 20, startY + 20, null);
			if("boss".equals(boss)) {
				g2d.drawImage(bossTextureHelper.getCurrentImage(), startX + 17, startY + 25, null);
			}else if("hit".equals(boss)) {
				g2d.drawImage(bossTextureHelper.getCurrentImage(), startX + 17, startY + 25, null);
			}
			g2d.drawImage(rollade, (int) getX(), (int) getY(), null);
			if(correctShape != null) {
				g2d.setColor(Color.WHITE);
				g2d.setStroke(new BasicStroke(10f));
				if(shapeAnimator.finished()) {
					g2d.fill(ShapeGenerator.setMidLocation(correctShape, (int) (getX() + getW() / 2), (int) (getY() + getH() / 2)));
					g2d.setColor(Color.BLACK);
					g2d.draw(ShapeGenerator.setMidLocation(correctShape, (int) (getX() + getW() / 2), (int) (getY() + getH() / 2)));
				}else {
					g2d.fill(ShapeGenerator.setMidLocation(shapeAnimator.getCurrShape(), (int) (getX() + getW() / 2), (int) (getY() + getH() / 2)));
					g2d.setColor(Color.BLACK);
					g2d.draw(ShapeGenerator.setMidLocation(shapeAnimator.getCurrShape(), (int) (getX() + getW() / 2), (int) (getY() + getH() / 2)));
				}
			}
			g2d.setColor(Color.BLACK);
			g2d.setStroke(new BasicStroke(40f));
			g2d.drawRect(startX, startY, (int) getW(), (int) getH());
			g2d.setClip(shape);
		}
		
		public static class ShapeAnimator {

			private Animator shapeAnimator;
			private float shapeAmount;
			private float currAmount;
			
			private boolean finished;
			
			private Shape currShape;
			
			private int currShapeIndex;
			
			public ShapeAnimator(float shapeAmount, float steps) {
				shapeAnimator = new Animator(steps, 1);
				this.shapeAmount = shapeAmount;
				currAmount = 0;
				currShapeIndex = ThreadLocalRandom.current().nextInt(6);
				currShape = ShapeGenerator.getShape(currShapeIndex);
			}
			
			public void onTick(float delta) {
				shapeAnimator.calculate(delta);
				if(shapeAnimator.finished()) {
					if(currAmount < shapeAmount) {
						currAmount++;
						shapeAnimator.reset();
						currShapeIndex++;
						if(currShapeIndex >= 6) {
							currShapeIndex = 0;
						}
						currShape = ShapeGenerator.getShape(currShapeIndex);
					}else if(currAmount == shapeAmount){
						finished = true;
						onFinished();
					}
				}
			}
			
			public void reset() {
				shapeAnimator.reset();
				currAmount = 0;
				currShapeIndex = ThreadLocalRandom.current().nextInt(6);
				finished = false;
				currShape = ShapeGenerator.getShape(currShapeIndex);
			}
			
			public boolean finished() {
				return finished;
			}
			
			public void onFinished() {};
			
			public Shape getCurrShape() {
				return currShape;
			}
			
		}
		
		public static class BossTextureHelper extends TextureHelper {
			
			private static final Image bossImage = ResLoader.getImageByName("game/minigames/boss/boss.png");
			private static final Image bossHitImage = ResLoader.getImageByName("game/minigames/boss/boss_hit.png"); 
			private static Image moving2 = new BufferedImage(bossImage.getWidth(null), bossImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
			static {
				moving2.getGraphics().drawImage(bossImage, -7, 0, null);
			}
			private static Image moving3 = new BufferedImage(bossImage.getWidth(null), bossImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
			static {
				moving3.getGraphics().drawImage(bossImage, 7, 0, null);
			}
			public BossTextureHelper() {
				addState("boss", 10000, bossImage);
				addState("hit", 2, bossHitImage, moving2, bossHitImage, moving3);
			}
			
		}
		
	}
	
}
