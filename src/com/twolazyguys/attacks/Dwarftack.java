package com.twolazyguys.attacks;

import com.twolazyguys.Main;
import com.twolazyguys.events.AttackEvent;
import com.twolazyguys.events.CommandEvent;
import com.twolazyguys.events.GameTickEvent;

import net.colozz.engine2.events.EventHandler;

public class Dwarftack extends Attack {

	private boolean running = false;
	private float count = 0;

	public Dwarftack() {
		super("dwarftack", 10, 0, 0.2f, 0.5f, 100);
		// TODO Auto-generated constructor stub 0.5% par seconde pour police
	}
	
	@EventHandler
	public void onCommandEvent(CommandEvent event) {
		if (!event.isCanceled()) {

			String formatted = event.getCommand().toLowerCase();

			if (formatted.equals(this.getName())) {
				event.setCanceled(true);
				this.running = true;
			}
		}
	}

	// Léo, je pense que cet évenement devrait être trigger depuis Dwarf (un nain lance des attaques,
	// et non pas l'attaque lance des attaques)
	@EventHandler
	public void onGameTickEvent(GameTickEvent event) {
		this.count += Main.delta;
		if(running && count >= 1) {
			AttackEvent attack = new AttackEvent(this.getName(), this.getDl(), this.getDammage(), this.getCpuUsage(),
					this.getEmergencyLevel(), this.getWantedLevel());
			Main.callEvent(attack);
			running = false;
			this.count = 0;
		}
	}

}
