package com.twolazyguys.sprites;

import com.twolazyguys.Main;
import com.twolazyguys.events.AttackEvent;

import com.twolazyguys.events.GameTickEvent;
import com.twolazyguys.events.SpriteChangedEvent;

import net.colozz.engine2.events.EventHandler;
import net.colozz.engine2.events.Listener;

public class LoadingBar extends Sprite implements Listener {
	private float count;
	private int wantedLevel;
	private int percent;
	private static int LENGTH = 150, WIDTH = 15;
	private static float DOWNLOADING_SPEED = 2f;
	private static float DONE_COLOR = 1, INPROGRESS_COLOR = 0.3f;

	public LoadingBar() {
		super(105, 125, createEmptyBar());
		count = 0;
		wantedLevel = 0;
		percent = 0;
	}

	private static float[][] createEmptyBar() {
		// TODO Auto-generated method stub
		float[][] emptyBar = new float[LENGTH][WIDTH];
		for (int i = 0; i < LENGTH; i++) {
			for (int j = 0; j < WIDTH; j++) {
				if (i == 0 || i == LENGTH - 1 || j == 0 || j == WIDTH - 1)
					emptyBar[i][j] = 0.7f;
			}
		}
		return emptyBar;
	}

	@EventHandler
	public void onGameTickEvent(GameTickEvent e) {
		count += Main.delta;
		if (count > 0.5f) {
			if (wantedLevel > 0 && percent < wantedLevel) {
				int step = (int) (DOWNLOADING_SPEED * count);
				int sup = Math.min(percent + step, wantedLevel);
				for (int j = 1; j < WIDTH - 1; j++) {
					for (int i = percent + 1; i <= sup; i++) {
						getColors()[i][j] = DONE_COLOR;
					}
					if (sup == wantedLevel) {
						wantedLevel = 0;
					}
				}
				percent += (sup-percent);
				Main.callEvent(new SpriteChangedEvent(this));
			}
			count = 0;
		}
	}

	@EventHandler
	public void onAttackEvent(AttackEvent attack) {
		if(wantedLevel==0) {
			wantedLevel = Math.min(LENGTH-2,percent + attack.getWantedLevel());
			for(int j = 1; j <= WIDTH-2; j++) {
				for(int i = percent+1 ; i <= wantedLevel; i++) {
					getColors()[i][j] = INPROGRESS_COLOR;
				}
			}
		}
	}

}
