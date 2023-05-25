package monster;

import entity.Entity;
import main.GamePanel;

public class MON_Orc extends Entity {
    GamePanel gp;
    int countTime = 0;

    public MON_Orc(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_monster;
        name = "Orc";
        speed = 1;
        maxLife = 10;
        life = maxLife;
        attack = 8;
        defense = 3;
        exp = 10;

        solidArea.x = 4;
        solidArea.y = 4;
        solidArea.width = 40;
        solidArea.height = 44;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 48;
        attackArea.height = 48;

        getImage();
        getAttackImage();
    }

    public void getImage() {
        up1 = setup("/monster/orc_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/monster/orc_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/monster/orc_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/monster/orc_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/monster/orc_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/monster/orc_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/monster/orc_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/monster/orc_right_2", gp.tileSize, gp.tileSize);
    }
    public void getAttackImage(){
        attackUp1 = setup("/monster/orc_attack_up_1", gp.tileSize, gp.tileSize * 2);
        attackUp2 = setup("/monster/orc_attack_up_2", gp.tileSize, gp.tileSize * 2);
        attackDown1 = setup("/monster/orc_attack_down_1", gp.tileSize, gp.tileSize * 2);
        attackDown2 = setup("/monster/orc_attack_down_2", gp.tileSize, gp.tileSize * 2);
        attackLeft1 = setup("/monster/orc_attack_left_1", gp.tileSize * 2, gp.tileSize);
        attackLeft2 = setup("/monster/orc_attack_left_2", gp.tileSize * 2, gp.tileSize);
        attackRight1 = setup("/monster/orc_attack_right_1", gp.tileSize * 2, gp.tileSize);
        attackRight2 = setup("/monster/orc_attack_right_2", gp.tileSize * 2, gp.tileSize);
    }
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