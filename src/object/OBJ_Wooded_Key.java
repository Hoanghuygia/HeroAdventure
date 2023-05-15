package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Wooded_Key extends Entity{
    GamePanel gp;

    public OBJ_Wooded_Key(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_consumable;
        name = "Wooden Key";
        down1 = setup("/objects/Woodkey", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nIt opens a wooden chest.";
    }
    public boolean use(Entity entity){
        gp.gameState = gp.dialogueState;
        int objIndex = getDetected(entity, gp.obj, "Wooden Chest");//down casting here and change its image

        if(objIndex != 999){//need one more if here to check opened
            if(gp.obj[gp.currentMap][objIndex] instanceof OBJ_Wooden_Chest){
                OBJ_Wooden_Chest woodenChest = (OBJ_Wooden_Chest) gp.obj[gp.currentMap][objIndex];
                if(!woodenChest.opened){
                    woodenChest.opened = true;
                    gp.playSE(3);
                    StringBuilder sb = new StringBuilder();
                    sb.append("You use the " + name + " and open the wooden chest");

                    if(gp.player.inventory.size() == gp.player.maxInventorySize){
                        sb.append("\n...You cannot carry anymore!");
                    }
                    else{
                        System.out.println(woodenChest.worldX + ": " + woodenChest.worldY);
                        System.out.println(gp.obj[gp.currentMap][objIndex].worldX + ": " + gp.obj[gp.currentMap][objIndex].worldY);
                        sb.append("\nYou obtain the " + woodenChest.loot.name + "!!");
                        gp.player.inventory.add(woodenChest.loot);
                        woodenChest.down1 = woodenChest.image2;
                        woodenChest.collision = false;
                    }
                    gp.ui.currentDialogue = sb.toString();

                    return true;
                }
                else{
                    gp.ui.currentDialogue = "You look realy stuff now";
                }
            }
        }
        else{
            gp.ui.currentDialogue = "You look so stuff!!";
            return  false;
        }
        return false;
    }
}

