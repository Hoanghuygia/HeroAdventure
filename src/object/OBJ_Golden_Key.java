package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Golden_Key extends Entity{
    GamePanel gp;
//    OBJ_Golden_Chest goldenChest = new OBJ_Golden_Chest(gp, new OBJ_Potion_Red(gp));


    public OBJ_Golden_Key(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_consumable;
        name = "Golden Key";
        down1 = setup("/objects/key", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nIt opens a golden chest.";
    }
    public boolean use(Entity entity){
        gp.gameState = gp.dialogueState;
        int objIndex = getDetected(entity, gp.obj, "Golden Chest");//down casting here and change its image

        if(objIndex != 999){//need one more if here to check opened
            if(gp.obj[gp.currentMap][objIndex] instanceof OBJ_Golden_Chest){
                OBJ_Golden_Chest goldenChest = (OBJ_Golden_Chest) gp.obj[gp.currentMap][objIndex];
                gp.playSE(3);
                StringBuilder sb = new StringBuilder();
                sb.append("You use the " + name + "and open the golden chest");

                if(gp.player.inventory.size() == gp.player.maxInventorySize){
                    sb.append("\n...You cannot carry anymore!");
                }
                else{
                    sb.append("\nYou obtain the " + goldenChest.loot.name + "!!");
                    gp.player.inventory.add(goldenChest.loot);
                    goldenChest.down1 = goldenChest.image2;
                    goldenChest.collision = false;
                }
                gp.ui.currentDialogue = sb.toString();

//            gp.ui.currentDialogue = "You use the " + name + "and open the golden chest";
//            gp.obj[gp.currentMap][objIndex] = null;
                return true;
            }
        }
        else{
            gp.ui.currentDialogue = "You look so stuff!!";
            return  false;
        }
        return false;
    }
}
