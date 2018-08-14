package projekt.mathe.game.engine;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JFrame;

public class Popup {

	private JFrame jframe;
	
	public Popup(PopupPanel drawing, int w, int h, int closeInMS) {
		jframe = new JFrame();
		jframe.setSize(new Dimension(w, h));
		Rectangle bounds = jframe.getGraphicsConfiguration().getDevice().getDefaultConfiguration().getBounds();
		jframe.setLocation((int) (0 + bounds.getWidth()/2 - w/2), (int) (0 + bounds.getHeight()/2 - h/2));
		jframe.setUndecorated(true);	
		//jframe.setBackground(new Color(1.0f, 1.0f, 1.0f, 0f));
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.add(drawing);
		if(closeInMS >= 0) {
			new Thread(() -> {
				try {
					Thread.sleep(closeInMS);
				} catch (InterruptedException e) {e.printStackTrace();}
				close();
			}).start();
		}
	}
	
	public void show() {
		jframe.setVisible(true);
	}
	
	public void close() {
		jframe.dispose();
	}
	
}
