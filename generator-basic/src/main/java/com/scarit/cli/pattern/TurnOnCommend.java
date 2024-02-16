package com.scarit.cli.pattern;


public class TurnOnCommend implements Commend {

    private Device device;
    
    public  TurnOnCommend(Device device) {
        this.device = device;
    }
    
    @Override
    public void execute() {
        device.turnOn();
    }
}
