package com.twolazyguys.events;

import net.colozz.engine2.events.Event;

public class AttackEvent extends Event {
	private float dammage;
	private int wantedLevel;
	private float emergencyLevel;
	
	public AttackEvent(float dammage, int wantedLevel, float emergencyLevel) {
		this.setDammage(dammage);
		this.setWantedLevel(wantedLevel);
		this.setEmergencyLevel(emergencyLevel);
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

	public float getEmergencyLevel() {
		return emergencyLevel;
	}

	public void setEmergencyLevel(float emergencyLevel) {
		this.emergencyLevel = emergencyLevel;
	}
	
}
