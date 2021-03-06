package com.twolazyguys.events;

import net.colozz.engine2.events.Event;

public class AttackEvent extends Event {
	private String name;
	private int dl;
	private float dammage;
	private float cpuUsage;
	private float emergencyLevel;
	private int wantedLevel;
	
	public AttackEvent(String name, int dl, float dammage, float cpuUsage,
			float emergencyLevel, int wantedLevel) {
		this.setDammage(dammage);
		this.setEmergencyLevel(emergencyLevel);
		this.setName(name);
		this.setDl(dl);
		this.setCpuUsage(cpuUsage);
		this.setWantedLevel(wantedLevel);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDl() {
		return dl;
	}

	public void setDl(int dl) {
		this.dl = dl;
	}

	public float getCpuUsage() {
		return cpuUsage;
	}

	public void setCpuUsage(float cpuUsage) {
		this.cpuUsage = cpuUsage;
	}
	
	public float getDammage() {
		return dammage;
	}

	public void setDammage(float dammage) {
		this.dammage = dammage;
	}

	public float getEmergencyLevel() {
		return emergencyLevel;
	}

	public void setEmergencyLevel(float emergencyLevel) {
		this.emergencyLevel = emergencyLevel;
	}

	public int getWantedLevel() {
		return wantedLevel;
	}

	public void setWantedLevel(int wantedLevel) {
		this.wantedLevel = wantedLevel;
	}
	
}
