package com.twolazyguys.events;

import net.colozz.engine2.events.Event;

public class AttackEvent extends Event {
	private float dammage;
	private int wantedLevel;
	
	public AttackEvent(float dammage, int wantedLevel) {
		this.setDammage(dammage);
		this.setWantedLevel(wantedLevel);
	}

	public float getDammage() {
		return dammage;
	}

	public void setDammage(float dammage) {
		this.dammage = dammage;
	}

	public int getWantedLevel() {
		return wantedLevel;
	}

	public void setWantedLevel(int wantedLevel) {
		this.wantedLevel = wantedLevel;
	}
	
}
