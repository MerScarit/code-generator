package com.scarit.cli.pattern;


public class TurnOffCommend implements Commend {

    private Device device;
    
    public  TurnOffCommend(Device device) {
        this.device = device;
    }
    
    @Override
    public void execute() {
        device.turnOff();
    }
}
