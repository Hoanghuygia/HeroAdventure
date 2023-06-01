package object;

import entity.Object;
import main.GamePanel;

public class OBJ_Coin extends Object {
    public OBJ_Coin(GamePanel gp) {
        super(gp);
        type = type_pinkUpOnly;
        down1 = setup("/objects/coin_bronze", gp.tileSize, gp.tileSize);
    }
}
