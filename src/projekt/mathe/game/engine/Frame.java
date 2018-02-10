package projekt.mathe.game.engine;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class Frame{

	private JFrame jframe;
	private Game game;
	private Rectangle currRealSize;
	private boolean focussed;
	private boolean started;
	
	public Frame() {
		jframe = new JFrame();
		jframe.setResizable(false);
		jframe.setUndecorated(true);
		jframe.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		jframe.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent windowEvent) {
		        if(game != null) {
		        	game.onExit();
		        }else {
		        	System.exit(0);
		        }
		    }
		});
	}
	
	public Rectangle getRealScreenSize() {
		Rectangle newSize = jframe.getGraphicsConfiguration().getDevice().getDefaultConfiguration().getBounds();
		if(currRealSize != null) {
			if(currRealSize.getWidth() != newSize.getWidth() || currRealSize.getHeight() != newSize.getHeight()) {
				//crash
			}
		}
		currRealSize = newSize;
		return currRealSize;
	}
	
	public Rectangle getBestScreenSize() {
		Rectangle rectangle = getRealScreenSize();
		float relation = (float) (rectangle.getWidth() / rectangle.getHeight());
		if(relation > 16f/9f) {
			float h = rectangle.height;
			float w = (int) ((16f/9f) * h);
			float y = rectangle.y;
			float x = rectangle.x + (((float) rectangle.width - w)/2);
			return new Rectangle((int) x, (int) y, (int) w, (int) h);
		}else {
			float w = rectangle.width;
			float h = (int) ((9f/16f) * w);
			float x = rectangle.x;
			float y = rectangle.y + (((float) rectangle.height - h)/2);
			return new Rectangle((int) x, (int) y, (int) w, (int) h);
		}
	}
	
	public Frame setIcon(Image icon) {
		jframe.setIconImage(icon);
		return this;
	}
	
	public Frame setSize(int w, int h) {
		jframe.setSize(w, h);
		return this;
	}
	
	public Dimension getSize() {
		return jframe.getSize();
	}
	
	public void setPos(int x, int y) {
		jframe.setLocation(x, y);
	}
	
	public Frame setTitle(String title) {
		jframe.setTitle(title);
		return this;
	}

	public Frame addGame(Game game) {
		this.game = game;
		return this;
	}
	
	public Frame show(){
		jframe.add(game);
		jframe.setVisible(true);
		focussed = true;
		return this;
	}
	
	public void minimize(){
		jframe.setExtendedState(JFrame.ICONIFIED);
	}
	
	public void shutdown() {
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.dispose();
	}
	
	public JFrame getJFrame() {
		return jframe;
	}
	
	public boolean focussed() {
		return focussed;
	}
	
	public boolean started() {
		if(!started) {
			if(jframe.isShowing()) {
				started = true;
			}
		}
		return started;
	}
	
}
