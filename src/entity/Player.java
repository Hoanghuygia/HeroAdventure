package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import object.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Player extends Entity {

    KeyHandler keyH;
    public final int screenX;
    public int playerDefaultWorldX;
    public final int screenY;
    public int playerDefaultWorldY;
    int standCounter = 0;
    public boolean attackCanceled = false;
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);

        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;
        motion1_duration = 5;
        motion2_duration = 25;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
      //  getGuardImage();
        setItems();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 26;
        worldY = gp.tileSize * 22;
        playerDefaultWorldX = worldX;
        playerDefaultWorldY = worldY;
//        worldX = gp.tileSize * 12;
//        worldY = gp.tileSize * 13;
        defaultSpeed = 4;
        speed = defaultSpeed;
        direction = "down";

        // PLAYER STATUS
        level = 1;
        maxLife = 6;
        life = maxLife;
        strength = 1; // The more strength he has, the more damage he gives
        dexterity = 1; // The more dexterity he has, the less damage he receives
        exp = 0;
        nextLevelExp = 5;//I think that we should let it into a HashTable instead a individual variable
        coin = 0;
        currentWeapon = new OBJ_Sword_Normal(gp);
        currentShield = new OBJ_Shield_Wood(gp);
        attack = getAttack(); // The total attack value is decided by strength and weapon
        defense = getDefense(); // The total defense value is decided by dexterity and shield
    }

    public void setItems() {
        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new OBJ_Golden_Key(gp));
        inventory.add(new OBJ_Metal_Key(gp));
        inventory.add(new OBJ_Wooded_Key(gp));
    }

    private int getAttack() {
        attackArea = currentWeapon.attackArea;//?why this is currentShield, it must be currentWeapon instead
        motion1_duration = currentWeapon.motion1_duration;
        motion2_duration = currentWeapon.motion2_duration;
        return attack = strength * currentWeapon.attackValue;
    }

    private int getDefense() {
        return defense = dexterity * currentShield.defenseValue;
    }

    public void getPlayerImage() {
        up1 = setup("/player/up1", gp.tileSize, gp.tileSize);
        up2 = setup("/player/up2", gp.tileSize, gp.tileSize);
        down1 = setup("/player/down1", gp.tileSize, gp.tileSize);
        down2 = setup("/player/down2", gp.tileSize, gp.tileSize);
        left1 = setup("/player/left1", gp.tileSize, gp.tileSize);
        left2 = setup("/player/left2", gp.tileSize, gp.tileSize);
        right1 = setup("/player/right1", gp.tileSize, gp.tileSize);
        right2 = setup("/player/right2", gp.tileSize, gp.tileSize);
    }

    public void getPlayerAttackImage() {
        if (currentWeapon.type == type_sword) {
            attackUp1 = setup("/player/attack_up1",(int)(gp.tileSize * 1.25), (int)(gp.tileSize * 1.25));
            attackUp2 = setup("/player/attack_up2", (int)(gp.tileSize * 1.25), (int)(gp.tileSize * 1.25));
            attackDown1 = setup("/player/attack_down1", (int)(gp.tileSize * 1.25), (int)(gp.tileSize * 1.25));
            attackDown2 = setup("/player/attack_down2", (int)(gp.tileSize * 1.25), (int)(gp.tileSize * 1.25));
            attackLeft1 = setup("/player/attack_left1", (int)(gp.tileSize * 1.15), (int)(gp.tileSize * 1.15));
            attackLeft2 = setup("/player/attack_left2", (int)(gp.tileSize * 1.15), (int)(gp.tileSize * 1.15));
            attackRight1 = setup("/player/attack_right1", (int)(gp.tileSize * 1.15), (int)(gp.tileSize * 1.15));
            attackRight2 = setup("/player/attack_right2", (int)(gp.tileSize * 1.15), (int)(gp.tileSize * 1.15));
        }
//        if (currentWeapon.type == type_axe) {
//            attackUp1 = setup("/player/boy_axe_up_1", gp.tileSize, gp.tileSize * 2);
//            attackUp2 = setup("/player/boy_axe_up_2", gp.tileSize, gp.tileSize * 2);
//            attackDown1 = setup("/player/boy_axe_down_1", gp.tileSize, gp.tileSize * 2);
//            attackDown2 = setup("/player/boy_axe_down_2", gp.tileSize, gp.tileSize * 2);
//            attackLeft1 = setup("/player/boy_axe_left_1", gp.tileSize * 2, gp.tileSize);
//            attackLeft2 = setup("/player/boy_axe_left_2", gp.tileSize * 2, gp.tileSize);
//            attackRight1 = setup("/player/boy_axe_right_1", gp.tileSize * 2, gp.tileSize);
//            attackRight2 = setup("/player/boy_axe_right_2", gp.tileSize * 2, gp.tileSize);
//        }
    }
//    public void getGuardImage(){
//        guardUp = setup("/player/boy_guard_up", gp.tileSize, gp.tileSize);
//        guardDown = setup("/player/boy_guard_down", gp.tileSize, gp.tileSize);
//        guardLeft= setup("/player/boy_guard_left", gp.tileSize, gp.tileSize);
//        guardRight = setup("/player/boy_guard_right", gp.tileSize, gp.tileSize);
//    }

    public void update() {
        if (attacking) {
            attacking();
        }
        else if(keyH.backSpacePressed){//guarfing after attack mean that you can guarding when attacking
            guarding = true;
        }
        else if(keyH.quotePressed){
            flashing = true;
            attacking = true;
        }
        else if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.enterPress) {//why enterPress here?
            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed) {
                direction = "right";
            }

            // CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // CHECK NPC COLLISION
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            // CHECK Monster COLLISION
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
//            contactMonster(monsterIndex);
            if(monsterIndex < 20 && monsterIndex > 0){
                if(!gp.monster[gp.currentMap][monsterIndex].dying){
                    int monsterAttack = gp.monster[gp.currentMap][monsterIndex].attack;
//                    damePlayer(monsterAttack);
                    damagePlayer(monsterAttack);
                }
            }

            // CHECK EVENT
            gp.eHandler.checkEvent();//no need

            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if (!collisionOn && !keyH.enterPress) {
                switch (direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }

            if (keyH.enterPress && !attackCanceled) {
                gp.playSE(7);
                attacking = true;
                spriteCounter = 0;
            }
            if (keyH.quotePressed) {
                gp.playSE(7);
                attacking = true;
                spriteCounter = 0;
            }

            attackCanceled = false;
            gp.keyH.enterPress = false;
            guarding = false;
            flashing = false;//nên là shiftPressed = false hay vậy

            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        } else {
            standCounter++;

            if (standCounter == 20) {
                spriteNum = 1;
                standCounter = 0;
            }
            guarding = false;
            flashing = false;
            //when we dont press any key, then reset the guarding variable
            //why do not reset enterPressed
        }

        // This need to be outside of key if statement!
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if(life <= 0){
            gp.gameState = gp.gameOverState;
            gp.ui.commandNum = -1;//chỉnh cho nó không bị liền nút
            invincible = false;
            gp.playSE(10);
        }
    }

    public void pickUpObject(int i) {//Still cant open the doors or chests
        if (i != 999) {
            //PICK ONLY ITEMS
            if(gp.obj[gp.currentMap][i].type == type_pinkUpOnly){
                gp.obj[gp.currentMap][i].use(this);
                gp.obj[gp.currentMap][i] = null;
            }
            //OBSTACLE
            else if(gp.obj[gp.currentMap][i].type == type_obstacle){
                if(keyH.enterPress){
                    attackCanceled = true;
                    System.out.println(gp.obj[gp.currentMap][i].name);
                    gp.obj[gp.currentMap][i].interact();
                }
            }
            //INVERTORY ITEMS
            else{
                String text;

                if (canObtainItem(gp.obj[gp.currentMap][i])) {
                    gp.playSE(1);
                    text = "Got a " + gp.obj[gp.currentMap][i].name + "!";
                } else {
                    text = "You cannot carry any more!";
                }

                gp.ui.addMessage(text);
                gp.obj[gp.currentMap][i] = null;
            }

        }
    }

    public void interactNPC(int i) {
        if (gp.keyH.enterPress) {
            if (i != 999) {
                attackCanceled = true;//nếu ko có biến này thì nó đâm chết mẹ ổng sao
                gp.gameState = gp.dialogueState;
                gp.npc[gp.currentMap][i].speak();
            }
        }
    }

//    public void contactMonster(int i) {
//        if (i != 999) {
//            if (!invincible && gp.monster[gp.currentMap][i].dying == false) {
//                gp.playSE(6);
//
//                int damage = gp.monster[gp.currentMap][i].attack - defense;
//                if (damage < 0) {
//                    damage = 0;
//                }
//                life -= damage;
//                invincible = true;//Nếu trường hợp mà hp < 0 thì sao
//            }
//        }
//    }
    public void damePlayer(int attack){
        if (!invincible) {
            gp.playSE(6);

            int damage = attack - defense;
            if (damage < 0) {
                damage = 0;
            }
            life -= damage;
            invincible = true;//Nếu trường hợp mà hp < 0 thì sao
        }
    }


    public void damageMonster(int i, Entity attacker, int attack, int knockBackPower) {
//        Graphics2D g2 = new Graphics2D();
        if (i != 999) {
            if (!gp.monster[gp.currentMap][i].invincible) {
                gp.playSE(5);

                if(knockBackPower > 0) setKnockBack(gp.monster[gp.currentMap][i], attacker, knockBackPower);



                int damage = attack - gp.monster[gp.currentMap][i].defense;
                if (damage < 0) {
                    damage = 0;
                }
                int prevousHP = this.life;
                int bloodHealing = (int)(damage * attacker.currentWeapon.bloodAbsorbment);

//                if(gp.player.life < gp.player.maxLife){//blood absorb
//                    gp.player.life += bloodHealing;
//                    if(gp.player.life > gp.player.maxLife){
//                        gp.player.life = gp.player.maxLife;
//                    }
//                }
                bloodAbsorb(this, bloodHealing);

                gp.monster[gp.currentMap][i].life -= damage;
                gp.ui.addMessage(damage + " damage!");
                if(this.life > prevousHP) gp.ui.addMessage(this.life - prevousHP + " absorb!");

                gp.monster[gp.currentMap][i].invincible = true;
                gp.monster[gp.currentMap][i].damageReaction();

                if (gp.monster[gp.currentMap][i].life <= 0) {
                    gp.monster[gp.currentMap][i].dying = true;
                    gp.monster[gp.currentMap][i].alive = false;
//                    gp.monster[i] = null;
//                    dyingAnimation(g2);
                    gp.ui.addMessage("Killed the " + gp.monster[gp.currentMap][i].name + "!");
                    gp.ui.addMessage("Exp +" + gp.monster[gp.currentMap][i].exp);
                    exp += gp.monster[gp.currentMap][i].exp;

//                    gp.monster[i] = null;
                    checkLevelUp();
                }
            }
        }
    }



    public void checkLevelUp() {
        if (exp >= nextLevelExp) {
            level++;
            nextLevelExp = nextLevelExp * 2;
            maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();

            gp.playSE(8);
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "You are levell " + level + " now!\nYou feel stronger!";
        }
    }

    public void selectItem() {
        int itemIndex = gp.ui.getItemIndexOnSlot();

        if (itemIndex < inventory.size()) {
            Entity selectedItem = inventory.get(itemIndex);

            if (selectedItem.type == type_sword || selectedItem.type == type_axe) {
                currentWeapon = selectedItem;
                attack = getAttack();
                getPlayerAttackImage();
            }
            if (selectedItem.type == type_shield) {
                currentShield = selectedItem;
                defense = getDefense();
            }
            if (selectedItem.type == type_consumable) {
                if(selectedItem.use(this)){
//                    if(inventory.get(searchItemInInventory(selectedItem.name)).amount > 1){
//                        inventory.get(itemIndex).amount = inventory.get(itemIndex).amount - 1;
//                    }
//                    else{
//                        inventory.remove(itemIndex);
//                    }
                    if(selectedItem.amount > 1){
                        selectedItem.amount--;
                    }
                    else{
                        inventory.remove(itemIndex);
                    }
                }
            }
        }
    }
    public int searchItemInInventory(String itemName){
        int itemIndex = 999;
        for(int i = 0; i <  inventory.size();i++){
            if(inventory.get(i).name.equals(itemName)){
                itemIndex = i;
                break;
            }
        }
        return itemIndex;
    }
    public boolean canObtainItem(Entity item){
        boolean canObtain = false;
        //CHECK IF ITEM IS STACKABLE
        if(item.stackable){
            int index = searchItemInInventory(item.name);
            if(index != 999){
                inventory.get(index).amount++;
                canObtain = true;
            }
            else{
                if(inventory.size() != maxInventorySize){
                    inventory.add(item);
                    canObtain = true;
                }
            }
        }
        else{
            //NOT STACKEBLE
            if(inventory.size() != maxInventorySize){
                inventory.add(item);
                canObtain = true;
            }
        }
        return canObtain;
    }
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction) {
            case "up":
                if (!attacking) {
                    if (spriteNum == 1) {image = up1;}
                    if (spriteNum == 2) {image = up2;}
                }
                if (attacking) {
                  //  tempScreenY = screenY - gp.tileSize;
                    if (spriteNum == 1) {image = attackUp1;}
                    if (spriteNum == 2) {image = attackUp2;}
                }
                if(flashing){
                    tempScreenY = screenY - gp.tileSize;
                    if (spriteNum == 1) {image = attackUp1;}
                    if (spriteNum == 2) {image = attackUp2;}
                }
                if(guarding) image = guardUp;
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
                if(flashing){
                    tempScreenY = screenY + gp.tileSize;
                    if (spriteNum == 1) {image = attackDown1;}
                    if (spriteNum == 2) {image = attackDown2;}
                }
                if(guarding) image = guardDown;

                break;
            case "left":
                if (!attacking) {
                    if (spriteNum == 1) {image = left1;}
                    if (spriteNum == 2) {image = left2;}
                }
                if (attacking) {
                    if (spriteNum == 1) {image = attackLeft1;}
                    if (spriteNum == 2) {image = attackLeft2;}
                }
                if(flashing){
                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNum == 1) {image = attackLeft1;}
                    if (spriteNum == 2) {image = attackLeft2;}

                }
                if(guarding) image = guardLeft;

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
                if(flashing){
                    tempScreenX = screenX + gp.tileSize;
                    if (spriteNum == 1) {image = attackRight1;}
                    if (spriteNum == 2) {image = attackRight2;}
                }
                if(guarding) image = guardRight;

                break;
        }

        if (invincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }
        g2.drawImage(image, tempScreenX, tempScreenY, null);

        // Reset alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        // DEBUG
//        g2.setFont(new Font("Arial", Font.PLAIN, 26));
//        g2.setColor(Color.white);
//        g2.drawString("Invincible: " + invincibleCounter, 10, 400);
    }
}
