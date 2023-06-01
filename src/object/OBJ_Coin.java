package object;

import entity.Entity;
import entity.Object;
import main.GamePanel;

public class OBJ_Coin extends Object {
    GamePanel gp;
    public OBJ_Coin(GamePanel gp) {
        super(gp);
        type = type_pinkUpOnly;
        down1 = setup("/objects/coin_bronze", gp.tileSize, gp.tileSize);
    }
    public boolean use(Entity entity){
        entity.coin += 500;
        return false;
    }

}
