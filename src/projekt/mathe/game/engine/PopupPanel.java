package projekt.mathe.game.engine;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public abstract class PopupPanel extends JPanel implements ActionListener{

	public PopupPanel() {
		new Timer(1000/100, this).start();
	}
	
	public abstract void onPaint(Graphics2D g2d);

	@Override
	public void paint(Graphics g) {
		onPaint((Graphics2D) g);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}

}
