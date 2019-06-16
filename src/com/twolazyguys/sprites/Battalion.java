package com.twolazyguys.sprites;

import java.util.ArrayList;

import com.twolazyguys.Main;
import com.twolazyguys.events.CommandEvent;
import com.twolazyguys.gamestates.Game;

import net.colozz.engine2.events.EventHandler;
import net.colozz.engine2.events.Listener;

public class Battalion extends Sprite implements Listener{
	private static int LENGTH = 210, WIDTH = 85;
	private ArrayList<Dwarf> army;
	private int nextX, nextY;

	public Battalion() {
		super(151, 162, new float[LENGTH][WIDTH]);
		this.army = new ArrayList<Dwarf>();
		this.nextX = 151;
		this.nextY = 184;
	}
	
	@EventHandler
    public void onCommandEvent(CommandEvent event) {
		if (!event.isCanceled()) {
            event.setCanceled(true);

            String formatted = event.getCommand().toLowerCase();

            if (formatted.equals("dwarf")) {
            	if(army.size()==5) {
        			for(Dwarf dwarf: army) {
        				dwarf.setY(206);
        			}
        		}
        		
        		Dwarf dwarf = new Dwarf(this.nextX,this.nextY);
        	    if(army.size()<=8) ((Game) Main.getGameState()).getColormap().addSprite(dwarf);
        	    Main.addListener(dwarf);
        	    army.add(dwarf);
        	    
        	    Boolean shift = false;
        	    if(army.size()==5) {
        	    	this.nextY=162;
        	    	this.nextX=151;
        	    }
        	    else if(army.size()<=9) {
        	    	this.nextX+=45;
        	    }
            }
		}
    }

}
