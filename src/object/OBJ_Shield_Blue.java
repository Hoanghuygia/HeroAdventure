package object;

import entity.Entity;
import entity.Object;
import main.GamePanel;

public class OBJ_Shield_Blue extends Object {

    public OBJ_Shield_Blue(GamePanel gp) {
        super(gp);

        type = type_shield;
        name = "Blue Shield";
        down1 = setup("/objects/shield_blue", gp.tileSize, gp.tileSize);
        defenseValue = 2;
        damageRatio = 5;
        description = "[" + name + "]\nA shiny blue shield.";
    }
}
