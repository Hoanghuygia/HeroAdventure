package main;

import entity.NPC_OldMan;
import monster.MON_Bee;
import monster.MON_Orc;
import monster.MON_White;
import monster.MON_Yellow;
import object.*;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        int mapNum = 1;
        int i = 0;
        gp.obj[mapNum][i] = new OBJ_Wooden_Chest(gp, new OBJ_Potion_Red (gp));
        gp.obj[mapNum][i].worldX = gp.tileSize * 12;
        gp.obj[mapNum][i].worldY = gp.tileSize * 27;
        i++;

        gp.obj[mapNum][i] = new OBJ_Wooded_Key(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 32;
        gp.obj[mapNum][i].worldY = gp.tileSize * 13;
        i++;

        gp.obj[mapNum][i] = new OBJ_Wooden_Chest(gp, new OBJ_Potion_Red(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize * 13;
        gp.obj[mapNum][i].worldY = gp.tileSize * 15;
        i++;

        gp.obj[mapNum][i] = new OBJ_Wooded_Key(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 34;
        gp.obj[mapNum][i].worldY = gp.tileSize * 21;
        i++;

        gp.obj[mapNum][i] = new OBJ_Metal_Chest(gp, new OBJ_Shield_Blue(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize * 26;
        gp.obj[mapNum][i].worldY = gp.tileSize * 35;
        i++;

        gp.obj[mapNum][i] = new OBJ_Metal_Key(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 13;
        gp.obj[mapNum][i].worldY = gp.tileSize * 45;
        i++;

        gp.obj[mapNum][i] = new OBJ_Wooden_Chest(gp, new OBJ_Heart(gp) );
        gp.obj[mapNum][i].worldX = gp.tileSize * 54;
        gp.obj[mapNum][i].worldY = gp.tileSize * 24;
        i++;

        gp.obj[mapNum][i] = new OBJ_Wooded_Key(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 56;
        gp.obj[mapNum][i].worldY = gp.tileSize * 29;
        i++;

        gp.obj[mapNum][i] = new OBJ_Wooden_Chest(gp, new OBJ_Metal_Key(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize * 51;
        gp.obj[mapNum][i].worldY = gp.tileSize * 44;
        i++;

        gp.obj[mapNum][i] = new OBJ_Wooded_Key(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 48;
        gp.obj[mapNum][i].worldY = gp.tileSize * 41;
        i++;

        gp.obj[mapNum][i] = new OBJ_Metal_Chest(gp, new OBJ_Key(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize * 35;
        gp.obj[mapNum][i].worldY = gp.tileSize * 36;
        i++;

        gp.obj[mapNum][i] = new OBJ_Metal_Chest(gp, new OBJ_Golden_Key(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize * 24;
        gp.obj[mapNum][i].worldY = gp.tileSize * 46;
        i++;

        gp.obj[mapNum][i] = new OBJ_Metal_Key(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 29;
        gp.obj[mapNum][i].worldY = gp.tileSize * 55;
        i++;

        gp.obj[mapNum][i] = new OBJ_Door(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 37;
        gp.obj[mapNum][i].worldY = gp.tileSize * 53;
        i++;

        gp.obj[mapNum][i] = new OBJ_Golden_Chest(gp, new OBJ_Golden_Key(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize * 24;
        gp.obj[mapNum][i].worldY = gp.tileSize * 46;
    }

    public void setNPC() {
        int mapNum = 1;
        gp.npc[mapNum][0] = new NPC_OldMan(gp);
        gp.npc[mapNum][0].worldX = gp.tileSize * 34;
        gp.npc[mapNum][0].worldY = gp.tileSize * 29;
    }

    public void setMonster() {
        int mapNum = 1;
        int i = 0;

        gp.monster[mapNum][i] = new MON_Bee(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 32;
        gp.monster[mapNum][i].worldY = gp.tileSize * 14;

        i++;
        gp.monster[mapNum][i] = new MON_Bee(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 37;
        gp.monster[mapNum][i].worldY = gp.tileSize * 14;

        i++;
        gp.monster[mapNum][i] = new MON_Bee(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 28;
        gp.monster[mapNum][i].worldY = gp.tileSize * 17;

        i++;
        gp.monster[mapNum][i] = new MON_Bee(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 31;
        gp.monster[mapNum][i].worldY = gp.tileSize * 21;

        i++;
        gp.monster[mapNum][i] = new MON_Bee(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 23;
        gp.monster[mapNum][i].worldY = gp.tileSize * 19;

        i++;
        gp.monster[mapNum][i] = new MON_Bee(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 18;
        gp.monster[mapNum][i].worldY = gp.tileSize * 18;

        i++;
        gp.monster[mapNum][i] = new MON_White(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 15;
        gp.monster[mapNum][i].worldY = gp.tileSize * 19;

        //test
        i++;
        gp.monster[mapNum][i] = new MON_White(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 13;
        gp.monster[mapNum][i].worldY = gp.tileSize * 16;

        i++;
        gp.monster[mapNum][i] = new MON_Bee(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 18;
        gp.monster[mapNum][i].worldY = gp.tileSize * 27;

        i++;
        gp.monster[mapNum][i] = new MON_Bee(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 29;
        gp.monster[mapNum][i].worldY = gp.tileSize * 26;

        i++;
        gp.monster[mapNum][i] = new MON_Bee(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 23;
        gp.monster[mapNum][i].worldY = gp.tileSize * 26;

        i++;
        gp.monster[mapNum][i] = new MON_Bee(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 19;
        gp.monster[mapNum][i].worldY = gp.tileSize * 38;

        i++;
        gp.monster[mapNum][i] = new MON_Bee(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 42;
        gp.monster[mapNum][i].worldY = gp.tileSize * 25;

        i++;
        gp.monster[mapNum][i] = new MON_Bee(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 53;
        gp.monster[mapNum][i].worldY = gp.tileSize * 29;

        i++;
        gp.monster[mapNum][i] = new MON_Bee(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 55;
        gp.monster[mapNum][i].worldY = gp.tileSize * 26;

        i++;
        gp.monster[mapNum][i] = new MON_Bee(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 53;
        gp.monster[mapNum][i].worldY = gp.tileSize * 31;

        i++;
        gp.monster[mapNum][i] = new MON_Bee(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 56;
        gp.monster[mapNum][i].worldY = gp.tileSize * 24;

        i++;
        gp.monster[mapNum][i] = new MON_Bee(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 53;
        gp.monster[mapNum][i].worldY = gp.tileSize * 25;

        i++;
        gp.monster[mapNum][i] = new MON_Bee(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 24;
        gp.monster[mapNum][i].worldY = gp.tileSize * 54;

        i++;
        gp.monster[mapNum][i] = new MON_Bee(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 29;
        gp.monster[mapNum][i].worldY = gp.tileSize * 52;

        i++;
        gp.monster[mapNum][i] = new MON_Bee(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 29;
        gp.monster[mapNum][i].worldY = gp.tileSize * 43;

        i++;
        gp.monster[mapNum][i] = new MON_Bee(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 32;
        gp.monster[mapNum][i].worldY = gp.tileSize * 38;

        i++;
        gp.monster[mapNum][i] = new MON_Bee(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 50;
        gp.monster[mapNum][i].worldY = gp.tileSize * 40;

        i++;
        gp.monster[mapNum][i] = new MON_Bee(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 32;
        gp.monster[mapNum][i].worldY = gp.tileSize * 37;

        i++;
        gp.monster[mapNum][i] = new MON_Bee(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 28;
        gp.monster[mapNum][i].worldY = gp.tileSize * 39;

        i++;
        gp.monster[mapNum][i] = new MON_Bee(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 30;
        gp.monster[mapNum][i].worldY = gp.tileSize * 49;

        i++;
        gp.monster[mapNum][i] = new MON_White(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 15;
        gp.monster[mapNum][i].worldY = gp.tileSize * 35;

        i++;
        gp.monster[mapNum][i] = new MON_White(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 26;
        gp.monster[mapNum][i].worldY = gp.tileSize * 47;

        i++;
        gp.monster[mapNum][i] = new MON_White(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 41;
        gp.monster[mapNum][i].worldY = gp.tileSize * 37;

        i++;
        gp.monster[mapNum][i] = new MON_White(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 20;
        gp.monster[mapNum][i].worldY = gp.tileSize * 48;

        i++;
        gp.monster[mapNum][i] = new MON_White(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 21;
        gp.monster[mapNum][i].worldY = gp.tileSize * 53;

        i++;
        gp.monster[mapNum][i] = new MON_White(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 44;
        gp.monster[mapNum][i].worldY = gp.tileSize * 47;

        i++;
        gp.monster[mapNum][i] = new MON_White(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 49;
        gp.monster[mapNum][i].worldY = gp.tileSize * 53;

        i++;
        gp.monster[mapNum][i] = new MON_Yellow(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 17;
        gp.monster[mapNum][i].worldY = gp.tileSize * 42;

        i++;
        gp.monster[mapNum][i] = new MON_Yellow(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 20;
        gp.monster[mapNum][i].worldY = gp.tileSize * 36;

        i++;
        gp.monster[mapNum][i] = new MON_Yellow(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 22;
        gp.monster[mapNum][i].worldY = gp.tileSize * 44;

        i++;
        gp.monster[mapNum][i] = new MON_Yellow(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 46;
        gp.monster[mapNum][i].worldY = gp.tileSize * 50;

        i++;
        gp.monster[mapNum][i] = new MON_Yellow(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 49;
        gp.monster[mapNum][i].worldY = gp.tileSize * 56;

        i++;
        gp.monster[mapNum][i] = new MON_Orc(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 48;
        gp.monster[mapNum][i].worldY = gp.tileSize * 17;

    }
}
