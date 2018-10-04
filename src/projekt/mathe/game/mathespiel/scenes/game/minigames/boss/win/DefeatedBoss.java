package projekt.mathe.game.mathespiel.scenes.game.minigames.boss.win;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.ScreenElement;
import projekt.mathe.game.engine.help.Animator;
import projekt.mathe.game.engine.help.ResLoader;
import projekt.mathe.game.engine.save.Saver;
import projekt.mathe.game.mathespiel.scenes.game.minigames.boss.Wand.Fenster.BossTextureHelper;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog.Card;

public class DefeatedBoss extends ScreenElement {

	private BossTextureHelper bossTextureHelper;
	private static Image bg = ResLoader.getImageByName("game/minigames/boss/windowbg.jpg");
	
	private Animator animator;
	
	private float yImg;
	
	public DefeatedBoss(Scene container) {
		super(container, 0, 0, 1280, 720);
		bossTextureHelper = new BossTextureHelper();
		bossTextureHelper.switchState("hit");
		animator = new Animator(80, 1);
		yImg = -400;
	}

	@Override
	public void onTick(float delta) {
		animator.calculate(delta);
		if(animator.finished() && !getContainer().world.isDialogOpen() && !getContainer().fading) {
			bossTextureHelper.switchState("boss");
			Dialog dialog = new Dialog(getContainer().world) {
				@Override
				public void onSelected(Card lastcard, boolean finished) {
					
				}
				@Override
				public void onFinished(Card lastcard) {
					Saver.setData("bossdefeated", true);
					getContainer().callScene("sek", getContainer().getDataForNextScene(), 150f);
				}
			};
			Card card = new Card("Wie... wie... ich verstehe das einfach nicht!");
			card.setTextColor(Color.RED);
			dialog.addCard(card);
			getContainer().world.openDialog(dialog);
		}else if(animator.finished() && !getContainer().world.isDialogOpen()) {
			yImg += 5f * delta;
		}else{
			bossTextureHelper.onTick(delta);
		}
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		g2d.drawImage(bg, 0, 0, 1280, 720, null);
		g2d.drawImage(bossTextureHelper.getCurrentImage(), -20, Math.round(yImg), 1300, 1300, null);
	}

}
