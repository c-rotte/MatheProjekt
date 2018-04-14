package projekt.mathe.game.engine.help;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageFilter;
import java.awt.image.BufferedImageOp;
import java.awt.image.RGBImageFilter;
import java.awt.image.WritableRaster;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import projekt.mathe.game.engine.help.Helper.FONT;

public class Helper {
	
	private static HashMap<FONT, String> fonts = new HashMap<>();
	
	public static enum FONT{
		FreePixel, Chrobot, Ailerons, VCR
	}
	
	public static String fontToName(FONT font) {
		return fonts.get(font);
	}
	
	public static void loadFont() {
		try {
			Font[] fs = {
				Font.createFont(Font.TRUETYPE_FONT, ResLoader.getFile("general/fonts/FreePixel.ttf")),
				Font.createFont(Font.TRUETYPE_FONT, ResLoader.getFile("general/fonts/Chrobot.otf")),
				Font.createFont(Font.TRUETYPE_FONT, ResLoader.getFile("general/fonts/Ailerons.otf")),
				Font.createFont(Font.TRUETYPE_FONT, ResLoader.getFile("general/fonts/Vcr.ttf"))
			};
		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    for(Font font : fs) {
		    	ge.registerFont(font);
		    }
		    fonts.put(FONT.FreePixel, "Free Pixel");
		    fonts.put(FONT.Chrobot, "Chrobot");
		    fonts.put(FONT.Ailerons, "Ailerons");
		    fonts.put(FONT.VCR, "VCR OSD Mono");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static BufferedImage optimizeImage(Image img) {
		
		GraphicsConfiguration gfx_config = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		
		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB_PRE);
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();
		
		if (bimage.getColorModel().equals(gfx_config.getColorModel())) {
			return bimage;
		}

		BufferedImage new_image = gfx_config.createCompatibleImage(bimage.getWidth(), bimage.getHeight(), bimage.getTransparency());
		Graphics2D g2d = (Graphics2D) new_image.getGraphics();
		g2d.drawImage(bimage, 0, 0, null);
		g2d.dispose();
		return new_image; 
	}
	
	public static BufferedImage cutPart(Image image, int x, int y, int w, int h) throws OutOfMemoryError{
		BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g2d = bufferedImage.createGraphics();
		g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return bufferedImage.getSubimage(x, y, w, h);
	}
	
	public static Color getContrastColor(Color color) {
		double y = (299 * color.getRed() + 587 * color.getGreen() + 114 * color.getBlue()) / 1000;
		return y >= 128 ? Color.black : Color.white;
	}

	public static void drawStringFromLeft(int x, int y, String s, Color color, int size, FONT font, Graphics2D g2d, Color outline, float outlineSize) {
		final Font f = new Font(Helper.fontToName(font), Font.PLAIN, size);
		g2d.setFont(f);
		final FontMetrics fm = g2d.getFontMetrics();
		final Rectangle2D stringBounds = fm.getStringBounds(s, g2d);
		
		if(outline != null && outlineSize > 0) {
			g2d.setColor(outline);
			AffineTransform transform = new AffineTransform();
			transform.translate(x, (int) Math.round(y + fm.getAscent() + stringBounds.getY()/2));
			Shape shape = new TextLayout(s, f, g2d.getFontRenderContext()).getOutline(transform);
			g2d.setStroke(new BasicStroke(outlineSize));
			g2d.fill(shape);
			g2d.draw(shape);
		}
		
		g2d.setColor(color);
		g2d.drawString(s, x, (int) Math.round(y + fm.getAscent() + stringBounds.getY()/2));
	}

	public static void drawStringAroundPosition(int x, int y, String s, Color color, int size, FONT font, Graphics2D g2d, Color outline, float outlineSize) {
		final Font f = new Font(Helper.fontToName(font), Font.PLAIN, size);
		g2d.setFont(f);
		final FontMetrics fm = g2d.getFontMetrics();
		final Rectangle2D stringBounds = fm.getStringBounds(s, g2d);
		
		if(outline != null && outlineSize > 0) {
			g2d.setColor(outline);
			AffineTransform transform = new AffineTransform();
			transform.translate((int) Math.round(x - stringBounds.getWidth()/2), (int) Math.round(y + fm.getAscent() + stringBounds.getY()/2));
			Shape shape = new TextLayout(s, f, g2d.getFontRenderContext()).getOutline(transform);
			g2d.setStroke(new BasicStroke(outlineSize));
			g2d.fill(shape);
			g2d.draw(shape);
		}
		
		g2d.setColor(color);
		g2d.drawString(s, (int) Math.round(x - stringBounds.getWidth()/2), (int) Math.round(y + fm.getAscent() + stringBounds.getY()/2));
	}

	public static Image[] getImagesBySplices(String beginning, int amount, String imgEnd) {
		beginning = beginning.split("_")[0];
		Image[] images = new Image[amount];
		for(int i = 1; i <= amount; i++) {
			String name = beginning + "_" + (i <= 9 ? "0" : "") + i + "." + imgEnd.replace(".", "");
			try {
				images[i - 1] = ResLoader.getImageByName(name);
			}catch (Exception e) {}
		}
		return images;
	}
	
	public static Image colorImage(Image image, Color color) {
		ImageIcon icon = new ImageIcon(image);
		BufferedImage bufferedImage = new BufferedImage(
			icon.getIconWidth(),
			icon.getIconHeight(),
		    BufferedImage.TYPE_INT_ARGB);
		Graphics g = bufferedImage.createGraphics();
		icon.paintIcon(null, g, 0,0);
		g.dispose();
		WritableRaster raster = bufferedImage.getRaster();
		for (int xx = 0; xx < bufferedImage.getWidth(); xx++) {
            for (int yy = 0; yy < bufferedImage.getHeight(); yy++) {
                int[] pixels = raster.getPixel(xx, yy, (int[]) null);
                pixels[0] = color.getRed();
                pixels[1] = color.getGreen();
                pixels[2] = color.getBlue();
                raster.setPixel(xx, yy, pixels);
            }
        }
		return new ImageIcon(bufferedImage).getImage();
	}
	
	public static Point[] getCoordsByFile(InputStream inputStream){
		/*
		 * x,y
		 */
		ArrayList<Point> points = new ArrayList<>();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			String line;
			while((line = reader.readLine()) != null) {
				Point point = new Point(Integer.valueOf(line.split(",")[0]), Integer.valueOf(line.split(",")[1]));
				points.add(point);
			}
		} catch (Exception e) {e.printStackTrace();}
		return points.toArray(new Point[points.size()]);
	}
	
	public static boolean xOutOfxBooleansTrue(int amount, boolean...bs) {
		int i = 0;
		for(boolean b : bs) {
			if(b) {
				i++;
			}
		}
		return i >= amount;
	}
	
	public static void showExceptionInPane(Exception e) {
		StringBuilder sb = new StringBuilder("Ups! Ein Fehler ist aufgetreten!\n");
		sb.append(e.toString());
		int lines = 0;
		for (StackTraceElement ste : e.getStackTrace()) {
	        if(lines <= 7) {
	        	sb.append("\n\tat ");
		        sb.append(ste);
	        	lines++;
	        }else {
		        break;
	        }
		}
		sb.append("\n\t");
        sb.append("...");
		int a = JOptionPane.showOptionDialog(null, sb.toString(), "Programm angehalten!", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, new String[] {"Fehlerinformationen kopieren"}, "default");
		if(a == 0) {
			StringBuilder sb2 = new StringBuilder("Ups! Ein Fehler ist aufgetreten!\n");
			sb2.append(e.toString());
			int lines2 = 0;
			for (StackTraceElement ste : e.getStackTrace()) {
		        if(lines2 <= 7) {
		        	sb2.append("\n\tat ");
			        sb2.append(ste);
		        	lines2++;
		        }else {
			        break;
		        }
			}
			StringSelection selection = new StringSelection(sb2.toString());
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, selection);
		}
		System.exit(0);
	}
	
	public static int countCharOccurrences(String s, char c){
	    int count = 0;
	    for (int i=0; i < s.length(); i++)
	    {
	        if (s.charAt(i) == c)
	        {
	             count++;
	        }
	    }
	    return count;
	}
	
}
