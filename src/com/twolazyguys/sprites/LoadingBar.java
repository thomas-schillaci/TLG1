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
	private float chargingSpeed;
	private static int LENGTH = 340, WIDTH = 30; //200 //15
	private static float DISCHARGING_SPEED = 2f;
	private static float DONE_COLOR = 1, INPROGRESS_COLOR = 0.3f;
	private float accu = 0f;

	public LoadingBar() {
		super(172, 258, createEmptyBar()); //118 162 
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
		if (count >= 1f) {
			int step;
			float[][] res = this.getColors();
			if (wantedLevel > 0 && percent < wantedLevel) {
				accu += chargingSpeed*LENGTH/100;
				step = (int) accu;
				int sup = Math.min(percent + step, wantedLevel);
				for (int j = 1; j < WIDTH - 1; j++) {
					for (int i = percent + 1; i <= sup; i++) {
						res[i][j] = DONE_COLOR;
					}
					if (sup == wantedLevel) {
						wantedLevel = 0;
					}
				}
				percent += (sup-percent);
				if(step>0) {
					this.setColors(res);
					Main.callEvent(new SpriteChangedEvent(this));
					accu = 0;
				}
			}
			if(percent == LENGTH-2) return;
			if(wantedLevel == 0 && percent > 0){
				step = (int) (DISCHARGING_SPEED * count);
				int inf = Math.max(0,percent - step);
				for (int j = 1; j < WIDTH - 1; j++) {
					for (int i = inf+1; i <= percent; i++) {
						res[i][j] = 0f;
					}
				}
				percent=inf;
				if(step>0) {
					this.setColors(res);
					Main.callEvent(new SpriteChangedEvent(this));
					accu = 0;
				}
			}
			count = 0;
		}
	}

	@EventHandler
	public void onAttackEvent(AttackEvent attack) {
		if(wantedLevel==0) {
			chargingSpeed = attack.getEmergencyLevel();
			wantedLevel = Math.min(LENGTH-2,percent + attack.getWantedLevel()*LENGTH/100);
			float[][] res = this.getColors();
			for(int j = 1; j <= WIDTH-2; j++) {
				for(int i = percent+1 ; i <= wantedLevel; i++) {
					res[i][j] = INPROGRESS_COLOR;
				}
			}
			this.setColors(res);
			Main.callEvent(new SpriteChangedEvent(this));
		}
	}

}
