package com.scarit.cli.pattern;

public class Device {

    private String name;

    public  Device(String name) {
        this.name = name;
    }
   public void turnOn() {
        System.out.println(name+"开启");
    }

    public void turnOff() {
        System.out.println(name+"关闭");
    }
}
