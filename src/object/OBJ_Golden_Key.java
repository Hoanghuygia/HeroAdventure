package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Golden_Key extends Entity{
    GamePanel gp;


    public OBJ_Golden_Key(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_consumable;
        name = "Golden Key";
        down1 = setup("/objects/Goldenkey", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nIt opens a golden chest.";
        stackable = true;
    }
    public boolean use(Entity entity){
        gp.gameState = gp.dialogueState;
        int objIndex = getDetected(entity, gp.obj, "Golden Chest");//down casting here and change its image

        if(objIndex != 999){//need one more if here to check opened
            if(gp.obj[gp.currentMap][objIndex] instanceof OBJ_Golden_Chest){
                OBJ_Golden_Chest goldenChest = (OBJ_Golden_Chest) gp.obj[gp.currentMap][objIndex];
                if(!goldenChest.opened){
                    goldenChest.opened = true;
                    gp.playSE(8);
                    gp.victoryOrNot = true;
                    gp.gameState = gp.gameOverAndVictoryState;
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
