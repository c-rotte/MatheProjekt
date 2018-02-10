package projekt.mathe.game.engine.titlebar;

import projekt.mathe.game.engine.Values;
import projekt.mathe.game.engine.help.Helper;
import projekt.mathe.game.engine.help.ResLoader;

public class MinimizeButton extends TitlebarClickable{

	private Titlebar container;
	
	public MinimizeButton(int x, int y, int w, int h, Titlebar container) {
		super(x, y, w, h, x + Values.TITLEBAR_HEIGHT/4, y + Values.TITLEBAR_HEIGHT/4, w - Values.TITLEBAR_HEIGHT/2, h - Values.TITLEBAR_HEIGHT/2, Helper.colorImage(ResLoader.getImageByName("titlebar/min.png"), Values.TITLEBAR_COLOR_1),
				Helper.colorImage(ResLoader.getImageByName("titlebar/min.png"), Helper.getContrastColor(Values.TITLEBAR_COLOR_1)));
		this.container = container;
	}

	@Override
	public void onClick() {
		container.frame.minimize();
		this.setSelected(false);
	}

}
