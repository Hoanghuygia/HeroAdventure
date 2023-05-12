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
        speed = 1;
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
    public void update(){
        super.update();
        int xDistance = Math.abs(worldX - gp.player.worldX);//i think the problem is here, we need to consider the position of the monster instead
        int yDistance = Math.abs(worldY - gp.player.worldY);
        int titleDistance = (xDistance + yDistance)/gp.tileSize;

        if(!onPath && titleDistance < 5){
            int i = new Random().nextInt(100) + 1;
            if(i > 50) onPath = true;
        }
        if(onPath) countTime++;
        System.out.println(countTime);
        if(onPath && titleDistance > 20){
            onPath = false;
            countTime = 0;
        }
        if(onPath && countTime > 120){
            onPath = false;
            countTime = 0;
        }
    }

    public void setAction() {
        if(onPath){
            int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;
            searchPath(goalCol, goalRow);
        }
        else{
            actionLockCounter++;
            if (actionLockCounter == 120) {
                Random random = new Random();
                int i = random.nextInt(100) + 1; // pick up a number from 1 to 100

                if (i <= 25) {
                    direction = "up";
                }
                if (i > 25 && i <= 50) {
                    direction = "down";
                }
                if (i > 50 && i <= 75) {
                    direction = "left";
                }
                if (i > 75 && i <= 100) {
                    direction = "right";
                }

                actionLockCounter = 0;
            }
        }
    }

    public void damageReaction() {
        actionLockCounter = 0;

//        direction = gp.player.direction;//make the monster go away from player
        onPath = true;
    }
}
