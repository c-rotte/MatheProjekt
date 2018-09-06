package projekt.mathe.game.mathespiel.scenes.game.world.entities.scenes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.ClickElement;
import projekt.mathe.game.engine.elements.Holder;
import projekt.mathe.game.engine.elements.ScreenElement;
import projekt.mathe.game.engine.help.Helper;
import projekt.mathe.game.engine.help.Helper.FONT;
import projekt.mathe.game.engine.save.Saver;

public class PasswordInput extends ScreenElement{

	private CharacterHolder characterHolder;
	private String password;
	
	private Color passwordColor;
	
	public PasswordInput(Scene container) {
		super(container, 370, 300, 560, 330);
		characterHolder = new CharacterHolder(container);
		password = "";
		passwordColor = Color.BLACK;
	}

	private void addChar(char c) {
		if(password.length() < 5) {
			password += c;
		}
	}
	
	private void removeChar() {
		if(password.length() <= 1) {
			password = "";
		}else {
			password = password.substring(0, password.length() - 1);
		}
	}
	
	public String getPassword() {
		return password;
	}
	
	public void reset() {
		password = "";
		characterHolder = new CharacterHolder(getContainer());
		passwordColor = Color.BLACK;
	}
	
	@Override
	public void onTick(float delta) {
		characterHolder.onTick(delta);
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		characterHolder.onPaint(g2d);
		for(int i = 0; i < 5; i++) {
			g2d.setColor(Color.DARK_GRAY);
			Rectangle rectangle = new Rectangle(getMiddle().x - 175 + i * (20 + 50), (int) (getY() - (50 + 20)), 50, 50);
			g2d.fill(rectangle);
			if((i + 1) <= password.length()) {
				Helper.drawStringAroundPosition((int) rectangle.getCenterX(), (int) rectangle.getCenterY(), String.valueOf(password.charAt(i)), passwordColor, 30, FONT.VCR, g2d, null, -1);
			}
		}
	}

	@Override
	public void onMouseDragged(MouseEvent e) {
		characterHolder.onMouseDragged(e);
		super.onMouseDragged(e);
	}
	
	@Override
	public void onMouseMoved(MouseEvent e) {
		characterHolder.onMouseMoved(e);
		super.onMouseMoved(e);
	}
	
	@Override
	public void onMousePressed(MouseEvent e) {
		characterHolder.onMousePressed(e);
		super.onMousePressed(e);
	}
	
	@Override
	public void onMouseReleased(MouseEvent e) {
		characterHolder.onMouseReleased(e);
		super.onMouseReleased(e);
	}
	
	private class CharacterHolder extends Holder<Character> {

		private static final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		
		public CharacterHolder(Scene container) {
			super(container);
			int i = 0;
			for(int y = 0; y < 5; y++) {
				for(int x = 0; x < 8; x++) {
					if(i < 36) {
						addElement(new Character(container, (int) PasswordInput.this.getX() + (50 + 20) * x, (int) PasswordInput.this.getY() + (50 + 20) * y, 50, 50, String.valueOf(chars.charAt(i))) {
							@Override
							public void onClicked(MouseEvent e) {
								addChar(getText().toCharArray()[0]);
								super.onClicked(e);
							}
						});
					}else if(i == 36) {
						addElement(new Character(container, (int) PasswordInput.this.getX() + (50 + 20) * x, (int) PasswordInput.this.getY() + (50 + 20) * y, 120, 50, "OK") {
							@Override
							public void onClicked(MouseEvent e) {
								if(Saver.containsData("safeCode") && Saver.containsData("currCode")) {
									if(Saver.getString("safeCode").equals(password) && Saver.getString("currCode").equals(password)) {
										passwordColor = Color.CYAN;
										//erfolgreich
									}else {
										passwordColor = Color.RED;
									}
								}else {
									Saver.setData("safeCode", Helper.generateRandomString(5));
									passwordColor = Color.RED;
								}
								super.onClicked(e);
							}
						});
					}else if(i == 38) {
						addElement(new Character(container, (int) PasswordInput.this.getX() + (50 + 20) * x, (int) PasswordInput.this.getY() + (50 + 20) * y, 120, 50, "<") {
							@Override
							public void onClicked(MouseEvent e) {
								removeChar();
								passwordColor = Color.BLACK;
								super.onClicked(e);
							}
						});
					}
					i++;
				}
			}
			addElement(new Character(container, 44, 27, 120, 50, "ZURÜCK") {
				@Override
				public void onClicked(MouseEvent e) {
					if(!getContainer().fading) {
						getContainer().callScene("sek", getContainer().getDataForNextScene(), 10f);
					}
					super.onClicked(e);
				}
			});
		}
		
	}
	
	private class Character extends ClickElement {

		private String s;
		
		public Character(Scene container, int x, int y, int w, int h, String s) {
			super(container, x, y, w, h);
			this.s = s;
		}

		public String getText() {
			return s;
		}
		
		@Override
		public void onTick(float delta) {
			
		}

		@Override
		public void onPaint(Graphics2D g2d) {
			switch (getState()) {
				case UNTOUCHED: g2d.setColor(Color.WHITE); break;
				case HOVERED: g2d.setColor(Color.LIGHT_GRAY); break;
				case PRESSED: g2d.setColor(Color.GRAY); break;
			}
			g2d.fill(getBounds());
			Helper.drawStringAroundPosition(getMiddle().x, getMiddle().y, s, Color.BLACK, 30, FONT.VCR, g2d, null, -1);
		}

		@Override
		public void onClicked(MouseEvent e) {
			
		}

		@Override
		public void onMisClicked(MouseEvent e) {
			
		}
		
	}
	
}
