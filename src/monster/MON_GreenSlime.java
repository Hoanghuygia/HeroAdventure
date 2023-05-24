package monster;

import entity.Entity;
import main.GamePanel;

import java.util.Random;

public class MON_GreenSlime extends Entity {

    GamePanel gp;
    int countTime = 0;

    public MON_GreenSlime(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_monster;
        name = "Bee Mon";
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 4;
        life = maxLife;
        attack = 5;
        defense = 0;
        exp = 2;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage() {
        up1 = setup("/monster/001", gp.tileSize, gp.tileSize);
        up2 = setup("/monster/002", gp.tileSize, gp.tileSize);
        down1 = setup("/monster/001", gp.tileSize, gp.tileSize);
        down2 = setup("/monster/002", gp.tileSize, gp.tileSize);
        left1 = setup("/monster/001", gp.tileSize, gp.tileSize);
        left2 = setup("/monster/002", gp.tileSize, gp.tileSize);
        right1 = setup("/monster/001", gp.tileSize, gp.tileSize);
        right2 = setup("/monster/002", gp.tileSize, gp.tileSize);
    }


    public void setAction() {
        int xDistance = getXDistance(gp.player);//i think the problem is here, we need to consider the position of the monster instead
        int yDistance = Math.abs(worldY - gp.player.worldY);
        int titleDistance = (xDistance + yDistance)/gp.tileSize;

        if(onPath){
            //check if it stops chasing
            countTime++;
            if(titleDistance > 20){
                onPath = false;
                countTime = 0;
            }
            if(countTime > 120){
                onPath = false;
                countTime = 0;
            }
            //search the direction to chase
            searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
        }
        else{
            //check if it starts chasing
            checkStartChasingOrNot(gp.player, 5, 100);

            //get a random direction
            getRamdomDirection();
        }
    }

    public void damageReaction() {
        actionLockCounter = 0;

//        direction = gp.player.direction;//make the monster go away from player
        onPath = true;
    }
}
