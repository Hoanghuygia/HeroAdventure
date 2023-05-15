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
//    public void update(){
//        super.update();
//
//    }

    public void setAction() {

        if(onPath){
            //Check if it stops chasing
            checkStopChasingOrNot(gp.player, 15, 100);
            countTime++;
            System.out.println(countTime);
            if(countTime > 120){
                onPath = false;
                countTime = 0;
            }
            //Search the direction to go
            searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
        }
        else {
            //check if it starts chasing
            checkStartChasingOrNot(gp.player, 5, 100);
            //get random direction
            getRandomDirection();
        }
    }

    public void damageReaction() {
        actionLockCounter = 0;

//        direction = gp.player.direction;//make the monster go away from player
        onPath = true;
    }
}
