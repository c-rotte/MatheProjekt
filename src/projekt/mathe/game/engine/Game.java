package projekt.mathe.game.engine;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

import projekt.mathe.game.engine.help.FPSHelper;
import projekt.mathe.game.engine.help.Helper;
import projekt.mathe.game.engine.help.Helper.FONT;
import projekt.mathe.game.engine.help.Logger;
import projekt.mathe.game.engine.titlebar.Titlebar;

public abstract class Game extends JPanel implements ActionListener{

	private static final long serialVersionUID = 8243059591883622826L;
	
	public float xRatio = 1;
	public float yRatio = 1;
	public int xOffset = 0;
	public int yOffset = 0;
	public boolean fullscreen;
	
	private ArrayList<Scene> scenes = new ArrayList<>();
	protected Scene currScene;
	private Timer timer;
	private Titlebar titlebar;
	public Frame container;
	public FPSHelper fpsHelper;
	
	public Game(int fps, Frame container, Image icon){
		this.container = container;
		titlebar = new Titlebar(Values.TITLEBAR_TEXT, container, this);
		titlebar.setImage(icon);
		fpsHelper = new FPSHelper();
		fullscreen = false;
		
		this.setOpaque(false);
		this.setFocusable(true);
		
		toggleFullScreen(2);
		
		this.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if(!fullscreen) {
					titlebar.onMouseDragged(e);
				}
				if(mouseEventValid(e) && currScene != null) {
					MouseEvent e2 = new MouseEvent((Component) e.getSource(), e.getID(), e.getWhen(), e.getModifiers(), (int) (currScene.camera.translateAbsolutX(e.getX() / xRatio)), (int) (currScene.camera.translateAbsolutY(e.getY() / yRatio)) - (fullscreen? 0 : Values.TITLEBAR_HEIGHT), e.getClickCount(), e.isPopupTrigger());
					currScene.onMouseDragged(e2);
				}
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				if(!fullscreen) {
					titlebar.onMouseMoved(e);
				}
				if(!fullscreen && e.getY() <= Values.TITLEBAR_HEIGHT){
					return;
				}
				if(mouseEventValid(e) && currScene != null) {
					MouseEvent e2 = new MouseEvent((Component) e.getSource(), e.getID(), e.getWhen(), e.getModifiers(), (int) (currScene.camera.translateAbsolutX(e.getX() / xRatio)), (int) (currScene.camera.translateAbsolutY(e.getY() / yRatio)) - (fullscreen? 0 : Values.TITLEBAR_HEIGHT), e.getClickCount(), e.isPopupTrigger());
					currScene.onMouseMoved(e2);
				}
			}
			
		});
		
		this.addMouseListener(new MouseAdapter () {
			
			@Override
			public void mousePressed(MouseEvent e) {
				if(!fullscreen) {
					titlebar.onMousePressed(e);
				}
				if(!fullscreen && e.getY() <= Values.TITLEBAR_HEIGHT){
					return;
				}
				if(mouseEventValid(e) && currScene != null) {
					MouseEvent e2 = new MouseEvent((Component) e.getSource(), e.getID(), e.getWhen(), e.getModifiers(), (int) (currScene.camera.translateAbsolutX(e.getX() / xRatio)), (int) (currScene.camera.translateAbsolutY(e.getY() / yRatio)) - (fullscreen? 0 : Values.TITLEBAR_HEIGHT), e.getClickCount(), e.isPopupTrigger());
					currScene.onMousePressed(e2);
				}
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!fullscreen) {
					titlebar.onMouseClicked(e);
				}
				if(!fullscreen && e.getY() <= Values.TITLEBAR_HEIGHT){
					return;
				}
				if(mouseEventValid(e) && currScene != null) {
					MouseEvent e2 = new MouseEvent((Component) e.getSource(), e.getID(), e.getWhen(), e.getModifiers(), (int) (currScene.camera.translateAbsolutX(e.getX() / xRatio)), (int) (currScene.camera.translateAbsolutY(e.getY() / yRatio)) - (fullscreen? 0 : Values.TITLEBAR_HEIGHT), e.getClickCount(), e.isPopupTrigger());
					currScene.onMouseClicked(e2);
					Logger.log("Clicked on Screen; coords=" + e2.getX() + "," + e2.getY()); 
				}
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				if(mouseEventValid(e) && currScene != null) {
					MouseEvent e2 = new MouseEvent((Component) e.getSource(), e.getID(), e.getWhen(), e.getModifiers(), (int) (currScene.camera.translateAbsolutX(e.getX() / xRatio)), (int) (currScene.camera.translateAbsolutY(e.getY() / yRatio)) - (fullscreen? 0 : Values.TITLEBAR_HEIGHT), e.getClickCount(), e.isPopupTrigger());
					currScene.onMouseReleased(e2);
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				if(!fullscreen) {
					titlebar.onMouseExited();
				}
				if(currScene != null) {
					currScene.onMouseExited();
				}
			}
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if(mouseEventValid(e) && currScene != null) {
					MouseWheelEvent e2 = new MouseWheelEvent(e.getComponent(), e.getID(), e.getWhen(), e.getModifiers(), (int) (currScene.camera.translateAbsolutX(e.getX() / xRatio)), (int) (currScene.camera.translateAbsolutY(e.getY() / yRatio)) - (fullscreen? 0 : Values.TITLEBAR_HEIGHT), e.getClickCount(), e.isPopupTrigger(), e.getScrollType(), e.getScrollAmount(), e.getWheelRotation());
					currScene.onMouseWheelMoved(e2);
				}
			}
			
		});
		
		this.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				if(currScene != null) {
					currScene.onKeyReleased(e);
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(currScene != null) {
					currScene.onKeyPressed(e);
				}
				if(fullscreen && e.getKeyCode() == KeyEvent.VK_F11) {
					toggleFullScreen(1);
				}
			}
		});
		
		timer = new Timer(0, this);
		timer.start();
	}
	
	private boolean mouseEventValid(MouseEvent e) {
		if(!fullscreen && e.getY() <= Values.TITLEBAR_HEIGHT){
			return false;
		}
		if(fullscreen) {
			Rectangle rectangle = container.getRealScreenSize();
			if(e.getX() < rectangle.x + xOffset || e.getX() > rectangle.x + rectangle.width - xOffset || e.getY() < rectangle.y + yOffset || e.getY() > rectangle.y + rectangle.height - yOffset) {
				return false;
			}
		}
		return true;
	}
	
	public void toggleFullScreen(int times) {
		for(int i = 0; i < times; i++) {
			fullscreen = !fullscreen;
			Rectangle r = container.getRealScreenSize();
			Rectangle br = container.getBestScreenSize();
			float x = (float) r.getX();
			float y = (float) r.getY();
			float w = (float) r.getWidth();
			float h = (float) r.getHeight();
			float bx = (float) br.getX();
			float by = (float) br.getY();
			float bw = (float) br.getWidth();
			float bh = (float) br.getHeight();
			if(fullscreen) {
				container.setPos((int) x, (int) y);
				container.setSize((int) w, (int) h);
				xRatio = Float.valueOf(bw)/Float.valueOf(Values.WINDOW_WIDTH);
				yRatio = Float.valueOf(bh)/Float.valueOf(Values.WINDOW_HEIGHT);
				xOffset = (int) bx;
				yOffset = (int) by;
			}else {
				container.setPos((int) (x + w/2 - Values.WINDOW_WIDTH/2), (int) (y + h/2 - Values.WINDOW_HEIGHT/2));
				container.setSize(Values.WINDOW_WIDTH, Values.WINDOW_HEIGHT + Values.TITLEBAR_HEIGHT);
				xRatio = 1;
				yRatio = 1;
				xOffset = 0;
				yOffset = 0;
			}
		}
	}
	
	public void registerScene(Scene scene) {
		scene.container = this;
		scenes.add(scene);
	}
	
	public void setCurrentScene(String id, SceneData sceneData, float fade){
		for(Scene scene : scenes){
			if(scene.getId().equals(id)){
				scene.onCall(currScene == null ? "" : currScene.getId(), sceneData);
				currScene = scene;
				currScene.onCall(fade);
				return;
			}
		}
	}
	
	public abstract void onExit();
	
	private void onTick(float delta) {
		if(currScene != null){
			currScene.onSceneTick(delta);
		}
	}
	
	private void onPaint(Graphics2D g2d) {
		currScene.onScenePaint(g2d);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		if(currScene == null) {
			return;
		}
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHints(Values.RENDERING_HINTS);
		g2d.clipRect(0, 0, (int) (Values.WINDOW_WIDTH * xRatio + 2 * xOffset), (int) (Values.WINDOW_HEIGHT * yRatio + 2 * yOffset + Values.TITLEBAR_HEIGHT));
		g2d.setColor(currScene.backgroundcolor);
		g2d.fillRect(0, fullscreen ? 0 : Values.TITLEBAR_HEIGHT, (int) (Values.WINDOW_WIDTH * xRatio) + xOffset*2, (int) (Values.WINDOW_HEIGHT * yRatio) + yOffset*2);
		AffineTransform affineTransform = g2d.getTransform();
		g2d.translate(-currScene.camera.getX() * xRatio + xOffset, -currScene.camera.getY() * yRatio + yOffset + (fullscreen ? 0 : Values.TITLEBAR_HEIGHT));
		g2d.scale(xRatio, yRatio);
		onPaint(g2d);
		g2d.setTransform(affineTransform);
		if(!fullscreen) {
			titlebar.onPaint(g2d);
		}else {
			Helper.drawStringAroundPoint(100, 100, fpsHelper.getFPS() + "", Color.BLACK, 40, FONT.Chrobot, g2d);
		}
		g2d.setTransform(affineTransform);
		
		Rectangle rectangle = container.getRealScreenSize();
		
		g2d.setColor(Color.BLACK);
	
		g2d.fillRect(0, 0, rectangle.width, yOffset);
		g2d.fillRect(0, rectangle.height - yOffset, rectangle.width, yOffset);
		g2d.fillRect(0, 0, xOffset, rectangle.height);
		g2d.fillRect(rectangle.width - xOffset, 0, xOffset, rectangle.height);

	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		loop();
	}

	private void loop() {
		onTick(getDelta());
		repaint();
		if(fpsHelper != null) {
			fpsHelper.frameFinished();
		}
	}
	
	private long startTime = System.currentTimeMillis();
	private float getDelta() {
		float optimal = 1000f / Values.FPS;
		float diff = System.currentTimeMillis() - startTime;
		float delta = diff / optimal;
		startTime = System.currentTimeMillis();
		return delta;
	}
	
}
