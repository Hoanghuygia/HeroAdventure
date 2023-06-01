package object;

import entity.Entity;
import entity.Object;
import main.GamePanel;

public class OBJ_Shield_Wood extends Object {

    public OBJ_Shield_Wood(GamePanel gp) {
        super(gp);

        type = type_shield;
        name = "Wood Shield";
        down1 = setup("/objects/shield_wood", gp.tileSize, gp.tileSize);
        defenseValue = 1;
        damageRatio = 3;
        description = "[" + name + "]\nMake by wood.";
    }
}
