package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Entity {

    GamePanel gp;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    public BufferedImage image, image2, image3;
    public BufferedImage guardUp, guardDown, guardLeft, guardRight;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collision = false;
    String dialogues[] = new String[20];
    public Entity attaccker;

    // STATE
    public int worldX, worldY;
    public String direction = "down";
    public int spriteNum = 1;
    int dialogueIndex = 0;
    public boolean collisionOn = false;
    public boolean invincible = false;
    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    boolean hpBarOn = false;
    public boolean onPath = false;
    public boolean knockBack = false;
    public String knockBackDirection;

    // COUNTER
    public int spriteCounter = 0;
    public int actionLockCounter = 0;
    public int invincibleCounter = 0;
    int dyingCounter = 0;
    int hpBarCounter = 0;
    int knockBackCounter = 0;
    public boolean guarding = false;

    // CHARACTER ATTRIBUTES
    public int defaultSpeed;
    public String name;
    public int speed;
    public int maxLife;
    public int life;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public int motion1_duration;
    public int motion2_duration;
    public Entity currentWeapon;
    public Entity currentShield;


    // ITEM ATTRIBUTES
    public int attackValue;
    public int defenseValue;
    public String description = "";
    public boolean stackable = false;
    public int amount = 1;
    public int knockBackPower = 0;

    // TYPE
    public int type; // 0 = player, 1 = npc, 2 = monster
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_sword = 3;
    public final int type_axe = 4;
    public final int type_shield = 5;
    public final int type_consumable = 6;
    public final int type_pinkUpOnly = 7;
    public final int type_obstacle = 8;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }
    public int getLeftX(){
        return worldX + solidArea.x;
    }
    public int getRightX(){
        return worldX + solidArea.x + solidArea.width;
    }
    public int getBottomY(){
        return worldY + solidArea.y + solidArea.height;
    }
    public int getTopY(){
        return worldY + solidArea.y;
    }
    public int getCol(){
         return (worldX + solidArea.x)/gp.tileSize;
    }
    public int getRow(){
        return (worldY + solidArea.y)/gp.tileSize;
    }
    public int getXDistance(Entity target){
        int xDistance = Math.abs(worldX - target.worldX);
        return xDistance;
    }
    public int getYDistance(Entity target){
        int yDistance = Math.abs(worldY - target.worldY);
        return yDistance;
    }
    public int tileDistance(Entity target){
        int tileDistance = (getXDistance(target) + getYDistance(target))/gp.tileSize;
        return tileDistance;
    }
    public int getGoalCol(Entity target){
        int goalCol = (target.worldX + target.solidArea.x) / gp.tileSize;
        return  goalCol;
    }
    public int getGoalRow(Entity target){
        int goalRow = (target.worldY + target.solidArea.y) / gp.tileSize;
        return  goalRow;
    }
    public void checkCollision(){
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if (this.type == type_monster && contactPlayer) {
            if (!gp.player.invincible) {
                // we can give damage
                gp.playSE(6);

                int damage = attack - gp.player.defense;
                if (damage < 0) {
                    damage = 0;
                }
                gp.player.life -= damage;
                gp.player.invincible = true;
            }
        }
    }

    public void setAction() {}
    public void damageReaction() {}

    public void speak() {
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch (gp.player.direction) {//này để khiến cho việc đối mặt một hướng thì mới tạo diologue
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }
    public void interact(){}
    public boolean use(Entity entity) {return false;}

    public void update() {
        if(knockBack){
            checkCollision();
            if(collisionOn){
                knockBackCounter = 0;//reset when it counter a solid tile
                knockBack = false;
                speed = defaultSpeed;
            }
            else if(!collisionOn){
                switch (knockBackDirection){
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }
            knockBackCounter++;
            if(knockBackCounter == 10){//time for the knowBack effect
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
        }
        else if(attacking){
            attacking();
        }
        else{
            setAction();
            checkCollision();

            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if (!collisionOn) {
                switch (direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 40) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
            worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
            worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
            worldY - gp.tileSize < gp.player.worldY + gp.player.screenY)
        {
            int tempScreenX = screenX;
            int tempScreenY = screenY;

            switch (direction) {
                case "up":
                    if (!attacking) {
                        if (spriteNum == 1) {image = up1;}
                        if (spriteNum == 2) {image = up2;}
                    }
                    if (attacking) {
                        tempScreenY = screenY - gp.tileSize;
                        if (spriteNum == 1) {image = attackUp1;}
                        if (spriteNum == 2) {image = attackUp2;}
                    }
                    break;
                case "down":
                    if (!attacking) {
                        if (spriteNum == 1) {image = down1;}
                        if (spriteNum == 2) {image = down2;}
                    }
                    if (attacking) {
                        if (spriteNum == 1) {image = attackDown1;}
                        if (spriteNum == 2) {image = attackDown2;}
                    }
                    break;
                case "left":
                    if (!attacking) {
                        if (spriteNum == 1) {image = left1;}
                        if (spriteNum == 2) {image = left2;}
                    }
                    if (attacking) {
                        tempScreenX = screenX - gp.tileSize;
                        if (spriteNum == 1) {image = attackLeft1;}
                        if (spriteNum == 2) {image = attackLeft2;}
                    }
                    break;
                case "right":
                    if (!attacking) {
                        if (spriteNum == 1) {image = right1;}
                        if (spriteNum == 2) {image = right2;}
                    }
                    if (attacking) {
                        if (spriteNum == 1) {image = attackRight1;}
                        if (spriteNum == 2) {image = attackRight2;}
                    }
                    break;
            }

            // Monster HP bar
            if (type == 2 && hpBarOn) {
                double oneScale = (double) gp.tileSize / maxLife;
                double hpBarValue = oneScale * life;

                g2.setColor(new Color(35, 35, 35));
                g2.fillRect(screenX - 1, screenY - 16, gp.tileSize + 2, 12);

                g2.setColor(new Color(255, 0, 30));
                g2.fillRect(screenX, screenY - 15, (int) hpBarValue, 10);

                hpBarCounter++;

                if (hpBarCounter > 300) {
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }

            if (invincible) {
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(g2, 0.4F);
            }
            if (dying) {
                dyingAnimation(g2);
            }

//            g2.drawImage(image, tempScreenX, tempScreenY, gp.tileSize, gp.tileSize, null);//no gp.tileSize here
            g2.drawImage(image, tempScreenX, tempScreenY, null);

            changeAlpha(g2, 1F);
        }
    }

    public void checkStopChasingOrNot(Entity target, int distance, int rate){
        if(tileDistance(target) > distance){
            int i = new Random().nextInt(rate);
            if(i == 0) {
                onPath = false;
            }
        }
    }
    public void checkStartChasingOrNot(Entity target, int distance, int rate){
        if(tileDistance(target) < distance){
            int i = new Random().nextInt(rate);
            if(i == 0) {
                onPath = true;
            }
        }
    }
    public void getRamdomDirection(){
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
    public String getOppositeDirection(String direction){
        String oppositeDirection = "";
        switch (direction){
            case "up": oppositeDirection = "down"; break;
            case "down": oppositeDirection = "up"; break;
            case "left": oppositeDirection = "right"; break;
            case "right": oppositeDirection = "left"; break;
        }
        return oppositeDirection;
    }
    public void attacking() {
        spriteCounter++;

        if (spriteCounter <= motion1_duration) {
            spriteNum = 1;
        }
        if (spriteCounter > motion1_duration && spriteCounter <= motion2_duration) {
            spriteNum = 2;

            // Save the current worldX, worldY solidArea
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // Adjust player's worldX/Y for the attackArea
            switch (direction) {
                case "up": worldY -= attackArea.height; break;
                case "down": worldY += attackArea.height; break;
                case "left": worldX -= attackArea.width; break;
                case "right": worldX += attackArea.width; break;
            }

            // attackArea becomes solidArea
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            if(type == type_monster){
                if(gp.cChecker.checkPlayer(this)){
//                    gp.player.damePlayer(attack);//there some error here!!!
                    damagePlayer(attack);
                }

            }
            else{
                //PLAYER
                // Check monster collision with the updated worldX, worldY and solidArea
                int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
                gp.player.damageMonster(monsterIndex,this,  attack, currentWeapon.knockBackPower);
            }



            // Attack checking collision, restore the original data
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if (spriteCounter > motion2_duration) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }
    public void damagePlayer(int attack){
        if(!gp.player.invincible){
            int damage = attack - gp.player.defense;

            //get the opposite direction
            String canGuarDirection = getOppositeDirection(direction);//nó chỉ pass mỗi direction là sao nhỉ

            if(gp.player.guarding && gp.player.direction.equals(canGuarDirection)){
                damage/= 5;
                gp.playSE(12);
//                if(damage < 0) damage = 0;

            }
            else{
                gp.playSE(6);
                if(damage < 1) damage = 1;
            }
            gp.player.life -= damage;
            gp.player.invincible = true;
        }
    }
    public void checkAttackOrNot(int rate, int straight, int horizontal){

        boolean targetInRange = false;
        int xDis = getXDistance(gp.player);
        int yDis = getYDistance(gp.player);

        switch (direction){
            case "up":
                if(gp.player.worldY < worldY && yDis < straight && xDis < horizontal){
                    targetInRange = true;
                }
                break;
            case "down":
                if(gp.player.worldY > worldY && yDis < straight && xDis < horizontal){
                    targetInRange = true;
                }
                break;
            case "left":
                if(gp.player.worldX < worldX && yDis < straight && yDis < horizontal){
                    targetInRange = true;
                }
                break;
            case "right":
                if(gp.player.worldX > worldX && yDis < straight && yDis < horizontal){
                    targetInRange = true;
                }
                break;
        }

        if(targetInRange){
            int i = new Random().nextInt(rate);
            if(i == 0){
                attacking = true;
                spriteNum = 1;
                spriteCounter = 0;

            }
        }
    }

    public void setKnockBack(Entity target, Entity attaccker, int knockBackPower){
        this.attaccker = attaccker;
        target.knockBackDirection = attaccker.direction;
        target.speed += knockBackPower;
        target.knockBack = true;
    }

    public void dyingAnimation(Graphics2D g2) {
        dyingCounter++;

        int i = 5;

        if (dyingCounter <= i) {changeAlpha(g2, 0f);}
        if (dyingCounter > i && dyingCounter <= i * 2) {changeAlpha(g2, 1f);}
        if (dyingCounter > i * 2 && dyingCounter <= i * 3) {changeAlpha(g2, 0f);}
        if (dyingCounter > i * 3 && dyingCounter <= i * 4) {changeAlpha(g2, 1f);}
        if (dyingCounter > i * 4 && dyingCounter <= i * 5) {changeAlpha(g2, 0f);}
        if (dyingCounter > i * 5 && dyingCounter <= i * 6) {changeAlpha(g2, 1f);}
        if (dyingCounter > i * 6 && dyingCounter <= i * 7) {changeAlpha(g2, 0f);}
        if (dyingCounter > i * 7 && dyingCounter <= i * 8) {changeAlpha(g2, 1f);}
        if (dyingCounter > i * 8) {
            dying = false;
            alive = false;
        }
//        dyingCounter = 0;
    }

    public void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }
    public void searchPath(int goalCol, int goalRow){
        int startCol = (worldX + solidArea.x) / gp.tileSize;
        int startRow = (worldY + solidArea.y) / gp.tileSize;

        gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);

        if(gp.pFinder.search()){
            //next worldX and worldY
            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;

            //Entity's solid area position
            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            if(enTopY > nextY && enLeftX >= nextX &&  enRightX < nextX + gp.tileSize){
                direction = "up";
            }
            else if(enTopY < nextY && enLeftX >= nextX &&  enRightX < nextX + gp.tileSize){
                direction = "down";
            }
            else if(enTopY >= nextY && enBottomY < nextY + gp.tileSize){
                //left or right
                if(enLeftX > nextX) direction = "left";
                if(enLeftX < nextX) direction = "right";
            }
            else if(enTopY > nextY && enLeftX > nextX){
                //up or left
                direction = "up";
                checkCollision();
                if(collisionOn) direction = "left";
            }
            else if(enTopY > nextY && enLeftX < nextX){
                //up or right
                direction = "up";
                checkCollision();
                if(collisionOn) direction = "right";
            }
            else if(enTopY < nextY && enLeftX > nextX){
                //down or left
                direction = "down";
                checkCollision();
                if(collisionOn) direction = "left";
            }
            else if(enTopY < nextY && enLeftX < nextX){
                //down or right
                direction = "down";
                checkCollision();
                if(collisionOn) direction = "right";
            }
//            int nextCol = gp.pFinder.pathList.get(0).col;
//            int nextRow = gp.pFinder.pathList.get(0).row;
//
//            //if reach the goal stop
//            if(nextCol == goalCol && nextRow == goalRow){
//                onPath = false;
//            }
        }
    }
    public int getDetected(Entity user, Entity target[][], String targetName){
        int index = 999;

        //check the surrounding object
        int nextWorldX = user.getLeftX();// hai dong nay lam gi can dau
        int nextWorldY = user.getTopY();

        switch (user.direction){
            case "up":
                nextWorldY = user.getTopY() - 1;
                break;
            case "down":
                nextWorldY = user.getBottomY() + 1;
                break;
            case "left":
                nextWorldX = user.getLeftX() - 1;
                break;
            case "right":
                nextWorldX = user.getRightX() + 1;
        }
        int col = nextWorldX /gp.tileSize;
        int row = nextWorldY/gp.tileSize;

        for(int i = 0; i < target[1].length; i++){
            if(target[gp.currentMap][i] != null){
                if(target[gp.currentMap][i].getCol() == col &&
                        target[gp.currentMap][i].getRow() == row &&
                        target[gp.currentMap][i].name.equals(targetName)){
                    index = i;
                    break;

                }
            }
        }
        return index;
    }

}
