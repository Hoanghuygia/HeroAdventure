package object;

import entity.Entity;
import entity.Object;
import main.GamePanel;

public class OBJ_Axe extends Object {

    public OBJ_Axe(GamePanel gp) {
        super(gp);

        type = type_axe;
        name = "Woodcutter's Axe";
        down1 = setup("/objects/axe", gp.tileSize, gp.tileSize);
        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "[" + name + "]\nA bit rusty but still can \ncut some trees.";
        knockBackPower = 7;
        motion1_duration = 20;
        motion2_duration = 40;
    }
}
