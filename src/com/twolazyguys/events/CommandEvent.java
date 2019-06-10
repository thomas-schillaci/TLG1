package com.twolazyguys.events;

import net.colozz.engine2.events.Event;

public class CommandEvent extends Event {

    private String command;
    private String[] args;
    private boolean canceled = false;
    private String[] output = new String[0];

    public CommandEvent(String command, String[] args) {
        this.command = command;
        this.args = args;
    }

    public String getCommand() {
        return command;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public String[] getOutput() {
        return output;
    }

    public void setOutput(String output) {
        setOutput(new String[]{output});
    }

    public void setOutput(String[] output) {
        this.output = output;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

}
