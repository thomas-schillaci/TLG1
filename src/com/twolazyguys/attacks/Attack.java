package com.twolazyguys.attacks;

import com.twolazyguys.events.CommandEvent;

import net.colozz.engine2.events.EventHandler;
import net.colozz.engine2.events.Listener;

public abstract class Attack implements Listener {
	private String name;
	private int dl;
	private float dammage;
	private float cpuUsage;
	private float emergencyLevel;
	private int wantedLevel;
	
	public Attack(String name, int dl, float dammage, float cpuUsage,
			float emergencyLevel, int wantedLevel) {
		super();
		this.name = name;
		this.dl = dl;
		this.dammage = dammage;
		this.cpuUsage = cpuUsage;
		this.emergencyLevel = emergencyLevel;
		this.wantedLevel = wantedLevel;
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

	public float getDammage() {
		return dammage;
	}

	public void setDammage(float dammage) {
		this.dammage = dammage;
	}

	public float getCpuUsage() {
		return cpuUsage;
	}

	public void setCpuUsage(float cpuUsage) {
		this.cpuUsage = cpuUsage;
	}

	public float getEmergencyLevel() {
		return emergencyLevel;
	}

	public void setEmergencyLevel(float emergencyLevel) {
		this.emergencyLevel = emergencyLevel;
	}
	
	@EventHandler
	public void onCommandEvent(CommandEvent event) {
		if (!event.isCanceled()) {

			String formatted = event.getCommand().toLowerCase();

			if (formatted.equals(this.getName())) {
				
			}
		}
	}

	public int getWantedLevel() {
		return wantedLevel;
	}

	public void setWantedLevel(int wantedLevel) {
		this.wantedLevel = wantedLevel;
	}

}
