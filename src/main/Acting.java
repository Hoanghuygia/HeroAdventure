package main;

import monster.MON_Orc;

public class Acting implements Command{
    private MON_Orc orc;
    public Acting(MON_Orc orc){
        this.orc = orc;
    }
    @Override
    public void execute() {
        orc.setAction();
    }
}