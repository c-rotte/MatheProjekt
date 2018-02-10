package projekt.mathe.game.engine;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import projekt.mathe.game.engine.help.Animator;
import projekt.mathe.game.engine.help.Camera;
import projekt.mathe.game.engine.help.KeyController;
import projekt.mathe.game.mathespiel.scenes.game.player.MapPlayer;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.World;

public abstract class Scene {

	public Game container;
	private String id;
	public Color backgroundcolor;
	public Camera camera;
	public KeyController keyController;
	
	public boolean fading;
	protected SceneData dataForNextScene;
	protected String nextID;
	protected Animator fadeAnimator;
	
	public World world;
	public MapPlayer player;
	
	public Scene(Game container, String id, Color backgroundcolor){
		this.container = container;
		this.id = id;
		this.backgroundcolor = backgroundcolor;
		this.camera = new Camera(0, 0, this);
		this.keyController = new KeyController();
	}
	
	public void registerWorld(World world) {
		this.world = world;
	}
	
	public void registerPlayer(MapPlayer player) {
		this.player = player;
	}
	
	public String getId() {
		return id;
	}

	public final void onSceneTick(float delta) {
		if(world != null) {
			world.onWorldTick(delta);
		}
		if(player != null) {
			player.onTick(delta);
		}
		onTick(delta);
		if(fading) {
			if(fadeAnimator != null) {
				fadeAnimator.calculate(delta);
			}
			if(dataForNextScene == null) {
				if(fadeAnimator != null && fadeAnimator.finished()) {
					fadeAnimator = null;
					fading = false;
				}
			}else {
				if(fadeAnimator != null && fadeAnimator.finished()) {
					String id = nextID;
					SceneData data = dataForNextScene;
					float fade = fadeAnimator.getBeginningValue();
					fadeAnimator = null;
					fading = false;
					nextID = null;
					dataForNextScene = null;
					container.setCurrentScene(id, data, fade);
				}
			}
		}
	}
	
	public final void onScenePaint(Graphics2D g2d) {
		if(world != null) {
			world.onWorldPaintBelow(g2d);
		}
		if(player != null) {
			player.onPerformacePaint(g2d);
		}
		onPaint(g2d);
		if(world != null) {
			world.onWorldPaintOnTop(g2d);
		}
		if(fading) {
			float alpha;
			if(dataForNextScene != null) {
				alpha = fadeAnimator.getCurrValueRelative();
			}else {
				alpha = 1f - fadeAnimator.getCurrValueRelative();
			}
			fillScene(g2d, Color.BLACK, alpha);
		}
		g2d.setColor(Color.RED);
	}
	
	public void fillScene(Graphics2D g2d, Color color, float alpha) {
		Composite a = g2d.getComposite();
		try {
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
			g2d.setColor(color);
			g2d.fillRect(camera.translateAbsolutX(0), camera.translateAbsolutY(0) - 5, Values.WINDOW_WIDTH * 2, Values.WINDOW_HEIGHT * 2 + 10); //-5 und +10 als puffer
		}catch(Exception e) {}
		g2d.setComposite(a);
	}
	
	public abstract void onCall(String lastID, SceneData sceneData);
	
	public abstract void onTick(float delta);
	
	public abstract void onPaint(Graphics2D g2d);
	
	public abstract SceneData getDataForNextScene();
	
	public final void callScene(String id, SceneData sceneData, float fade) {
		fading = true;
		dataForNextScene = sceneData;
		nextID = id;
		fadeAnimator = new Animator(fade, 1);
	}
	
	protected final void onCall(float fade) {
		fading = true;
		fadeAnimator = new Animator(fade, 1);
		keyController.reset();
	}
	
	public void onMousePressed(MouseEvent e) {}
	public void onMouseReleased(MouseEvent e) {}
	public void onMouseClicked(MouseEvent e) {}
	public void onMouseDragged(MouseEvent e) {}
	public void onMouseMoved(MouseEvent e) {}
	public void onMouseWheelMoved(MouseWheelEvent e) {}
	public void onMouseExited() {}
	
	public final void onKeyPressed(KeyEvent e) {
		keyController.press(e.getKeyCode());
	}
	public final void onKeyReleased(KeyEvent e) {
		keyController.release(e.getKeyCode());
	}

}
