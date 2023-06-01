package entity;

import main.GamePanel;
import object.OBJ_Coin;
import object.OBJ_Potion_Red;

import java.util.Random;

public class Monster extends Entity{
    protected int coinValue = 0;
    protected int ratioOfItem = 0;
    public Monster(GamePanel gp) {
        super(gp);
    }
    public Object getRandomItem(){
        Object object;
        int random = new Random().nextInt(101);
        if(random > ratioOfItem){
            object = new OBJ_Potion_Red(gp);
        }
        else{
//            gp.player.coin += coinValue;
            object = new OBJ_Coin(gp);
        }
        return object;
    }
}
