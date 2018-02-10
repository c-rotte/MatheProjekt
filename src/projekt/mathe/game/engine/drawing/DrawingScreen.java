package projekt.mathe.game.engine.drawing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class DrawingScreen{

	private float xDrawingBoard, yDrawingBoard, wDrawingBoard, hDrawingBoard;
	private Color color;
	private int radius;
	private ArrayList<Pixel> pixels;
	private Point lastMousePos;
	private int maxPixels;
	
	public DrawingScreen(float xDrawingBoard, float yDrawingBoard, float wDrawingBoard, float hDrawingBoard, int maxPixels) {
		this.xDrawingBoard = xDrawingBoard;
		this.yDrawingBoard = yDrawingBoard;
		this.wDrawingBoard = wDrawingBoard;
		this.hDrawingBoard = hDrawingBoard;
		pixels = new ArrayList<>();
		color = Color.BLACK;
		radius = 2;
		this.maxPixels = maxPixels;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	public void onMouseDragged(MouseEvent e) {
		if(xDrawingBoard < e.getX() && xDrawingBoard + wDrawingBoard > e.getX()) {
			if(yDrawingBoard < e.getY() && yDrawingBoard + hDrawingBoard > e.getY()) {
				addPixels(e.getX(), e.getY());
				lastMousePos = new Point(e.getX(), e.getY());
				return;
			}
		}
		lastMousePos = null;
	}
	
	public void onMouseReleased(MouseEvent e) {
		lastMousePos = null;
	}
	
	public void onMousePressed(MouseEvent e) {
		addPixel(e.getX(), e.getY());
	}
	
	private void addPixel(int x, int y) {
		pixels.add(new Pixel(x, y, color, radius));
		if(pixels.size() > maxPixels) {
			pixels.remove(0);
		}
	}
	
	private void addPixels(int x, int y) {
		
		if(lastMousePos == null) {
			addPixel(x, y);
			return;
		}
		
        int x2 = lastMousePos.x;
        int y2 = lastMousePos.y;
		
		int d = 0;
 
        int dx = Math.abs(x2 - x);
        int dy = Math.abs(y2 - y);
 
        int dx2 = 2 * dx;
        int dy2 = 2 * dy;
 
        int ix = x < x2 ? 1 : -1;
        int iy = y < y2 ? 1 : -1;
 
        if (dx >= dy) {
            while (true) {
            	addPixel(x, y);
            	if (x == x2)
                    break;
                x += ix;
                d += dy2;
                if (d > dx) {
                    y += iy;
                    d -= dx2;
                }
            }
        } else {
            while (true) {
            	addPixel(x, y);
                if (y == y2)
                    break;
                y += iy;
                d += dx2;
                if (d > dy) {
                    x += ix;
                    d -= dy2;
                }
            }
        }
	}
	
	public void onPaint(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		g2d.fillRect((int) xDrawingBoard, (int) yDrawingBoard, (int) wDrawingBoard, (int) hDrawingBoard);
		for(Pixel pixel : pixels) {
			pixel.onPaint(g2d);
		}
	}
	
}
