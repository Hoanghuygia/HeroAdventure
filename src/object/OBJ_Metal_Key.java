package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Metal_Key extends Entity{
    GamePanel gp;

    public OBJ_Metal_Key(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_consumable;
        name = "Metal Key";
        down1 = setup("/objects/Metalkey", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nIt opens a metal chest.";
        stackable = true;
    }
    public boolean use(Entity entity){
        gp.gameState = gp.dialogueState;
        int objIndex = getDetected(entity, gp.obj, "Metal Chest");//down casting here and change its image

        if(objIndex != 999){//need one more if here to check opened
            if(gp.obj[gp.currentMap][objIndex] instanceof OBJ_Metal_Chest){
                OBJ_Metal_Chest matalChest = (OBJ_Metal_Chest) gp.obj[gp.currentMap][objIndex];
                if(!matalChest.opened){
                    matalChest.opened = true;
                    gp.playSE(3);
                    StringBuilder sb = new StringBuilder();
                    sb.append("You use the " + name + " and open the metal chest");

                    if(!gp.player.canObtainItem(matalChest.loot)){
                        sb.append("\n...BUt you cannot carry anymore!");
                    }
                    else{
                        System.out.println(matalChest.worldX + ": " + matalChest.worldY);
                        System.out.println(gp.obj[gp.currentMap][objIndex].worldX + ": " + gp.obj[gp.currentMap][objIndex].worldY);
                        sb.append("\nYou obtain the " + matalChest.loot.name + "!!");
                        matalChest.down1 = matalChest.image2;
                        matalChest.collision = false;
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

