package com.twolazyguys.sprites;


import com.twolazyguys.Main;
import com.twolazyguys.events.AttackEvent;

import com.twolazyguys.events.GameTickEvent;
import net.colozz.engine2.events.Event;
import net.colozz.engine2.events.EventHandler;
import net.colozz.engine2.events.Listener;

public class LoadingBar extends Sprite implements Listener {
	private float count;
	private int wantedLevel;
	private int percent;
	private static int LENGTH = 150, WIDTH = 15, DOWNLOADING_SPEED = 1;
	private static float DONE_COLOR = 1, INPROGRESS_COLOR = 0.5f;
	
	public LoadingBar() {
		super(105,125,createEmptyBar());
		count = 0;
		wantedLevel = 0;
		percent = 0;
	}
	
	private static float[][] createEmptyBar() {
		// TODO Auto-generated method stub
		float[][] emptyBar = new float[LENGTH][WIDTH];
		for(int i = 0 ; i < LENGTH; i++) {
			for(int j = 0 ; j < WIDTH ; j++) {
				if(i==0 || i == LENGTH-1 || j==0 || j == WIDTH-1) emptyBar[i][j] = 0.7f;
			}
		}
		return emptyBar;
	}

	@EventHandler
	public void onGameTickEvent(GameTickEvent e) {
		count += Main.delta;
		if(count > 0.5f) {
			if(percent < wantedLevel) {
				for(int j = percent; j < percent + (int)(DOWNLOADING_SPEED*count); j++) {
					for(int i = 1; i < LENGTH-1; i++) {
						getColors()[i][j] = DONE_COLOR;
					}
				}
				percent += (int)(DOWNLOADING_SPEED*count);
			}
			count = 0;
		}
	}
	
	@EventHandler
	public void onAttackEvent(AttackEvent attack) {
		System.out.println("debug");
		wantedLevel = percent + attack.getWantedLevel();
		for(int j = percent; j <= Math.max(wantedLevel,WIDTH-2); j++) {
			for(int i = 1; i < LENGTH-1; i++) {
				getColors()[i][j] = INPROGRESS_COLOR;
			}
		}
	}

}
