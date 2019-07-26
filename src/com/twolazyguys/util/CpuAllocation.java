package com.twolazyguys.util;

public class CpuAllocation {

    private float requiredFrequency;
    private boolean allocated = false;

    public CpuAllocation(float requiredFrequency) {
        this.requiredFrequency = requiredFrequency;
    }

    public float getRequiredFrequency() {
        return requiredFrequency;
    }

    public boolean isAllocated() {
        return allocated;
    }

    public void setAllocated(boolean allocated) {
        this.allocated = allocated;
    }

}
