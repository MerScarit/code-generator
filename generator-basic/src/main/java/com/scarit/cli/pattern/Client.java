package com.scarit.cli.pattern;

public class Client {
    public static void main(String[] args) {

        Device tv = new Device("tv");
        Device computer = new Device("Computer");

        tv.turnOn();
        computer.turnOff();
        
    }
}
