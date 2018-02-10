package projekt.mathe.game.engine.titlebar;

import projekt.mathe.game.engine.Values;
import projekt.mathe.game.engine.help.Helper;
import projekt.mathe.game.engine.help.ResLoader;

public class MaximizeButton extends TitlebarClickable{

	private Titlebar container;
	
	public MaximizeButton(int x, int y, int w, int h, Titlebar container) {
		super(x, y, w, h, x + Values.TITLEBAR_HEIGHT/4, y + Values.TITLEBAR_HEIGHT/4, w - Values.TITLEBAR_HEIGHT/2, h - Values.TITLEBAR_HEIGHT/2, Helper.colorImage(ResLoader.getImageByName("titlebar/max.png"), Values.TITLEBAR_COLOR_1),
				Helper.colorImage(ResLoader.getImageByName("titlebar/max.png"), Helper.getContrastColor(Values.TITLEBAR_COLOR_1)));
		this.container = container;
	}

	@Override
	public void onClick() {
		container.container.toggleFullScreen(1);
	}
	
}
