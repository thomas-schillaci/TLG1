package com.twolazyguys.attacks;

import com.twolazyguys.Main;
import com.twolazyguys.events.AttackEvent;
import com.twolazyguys.events.CommandEvent;
import com.twolazyguys.events.GameTickEvent;

import net.colozz.engine2.events.EventHandler;

public class Testattack extends Attack {
	private boolean running = false;
	private float count = 0;

	public Testattack() {
		super("testattack", 20, 5, 0.3f, 0.1f, 10);
		// TODO Auto-generated constructor stub
	}
	
	@EventHandler
	public void onCommandEvent(CommandEvent event) {
		if (!event.isCanceled()) {

			String formatted = event.getCommand().toLowerCase();

			if (formatted.equals(this.getName())) {
				this.running = true;
			}
		}
	}
	
	@EventHandler
	public void onGameTickEvent(GameTickEvent event) {
		this.count += Main.delta;
		if(running && count >= this.getDl()) {
			AttackEvent attack = new AttackEvent(this.getName(), this.getDl(), this.getDammage(), this.getCpuUsage(),
					this.getEmergencyLevel(), this.getWantedLevel());
			Main.callEvent(attack);
			this.count = 0;
			this.running = false;
		}
	}

}
