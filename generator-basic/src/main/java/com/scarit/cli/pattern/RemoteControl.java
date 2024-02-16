package com.scarit.cli.pattern;

public class RemoteControl {

    private Commend commend;

    public void setCommend(Commend commend) {
        this.commend = commend;
    }

    public void pressButton() {
        commend.execute();
    }
}
