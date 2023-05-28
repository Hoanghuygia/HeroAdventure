package main;

import java.awt.*;

public class EventHandler {

    GamePanel gp;
    EventRect eventRect[][][];

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    Pit pit1 = new Pit(27, 16);
    Pit pit2 = new Pit(23, 19);

    public EventHandler(GamePanel gp) {
        this.gp = gp;

        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        int map = 0;
        int col = 0;
        int row = 0;
        while (map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow) {
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;

                if(row == gp.maxWorldRow){//load sang map 2
                    row = 0;
                    map++;
                }
            }
        }
    }

    public void checkEvent() {
        // Check if player character is more than 1 tile away from the last event
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if (distance > gp.tileSize) {
            canTouchEvent = true;//biến này có tác dụng gì nhỉ ??
        }

        if (canTouchEvent) {
            if (hitPit(0,pit1, "right")) {damagePit( pit1, gp.dialogueState);}
            else if (hitPit(0,pit2, "any")) {damagePit( pit2, gp.dialogueState);}
            else if (hit(1,26, 17, "up")) {healingPool( gp.dialogueState);}
            else if(hit(0, 26, 25, "any")) {teleport(1, 52, 17);}
            else if(hit(1, 42, 5, "any")) {teleport(0, 26, 25);}
            else if(hit(1, 27, 36, "any")) {teleport(1, 52, 38);}
            else if(hit(1, 53, 38, "any")) {teleport(1, 27, 36);}
            else if(hit(1, 52, 16, "any")) {teleport(1, 25, 44);}
            else if(hit(1, 25, 44, "any")) {teleport(1, 52, 16);}

        }
    }

    public boolean hit(int map, int col, int row, String reqDirection) {
        boolean hit = false;
        if(map == gp.currentMap){
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
            eventRect[map][col][row].x = col * gp.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * gp.tileSize + eventRect[map][col][row].y;

            if (gp.player.solidArea.intersects(eventRect[map][col][row]) && !eventRect[map][col][row].eventDone) {//cần sửa lại cái eventDone này thành true không ta
                if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                    hit = true;

                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;
                }
            }

            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }

        return hit;
    }
    public boolean hitPit(int map, Pit pit, String reqDirection) {
        boolean hit = false;
        if(pit.pitOn){
            if(map == gp.currentMap){
                gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
                gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
                eventRect[map][pit.x][pit.y].x = pit.x * gp.tileSize + eventRect[map][pit.x][pit.y].x;
                eventRect[map][pit.x][pit.y].y = pit.y * gp.tileSize + eventRect[map][pit.x][pit.y].y;

                if (gp.player.solidArea.intersects(eventRect[map][pit.x][pit.y]) && !eventRect[map][pit.x][pit.y].eventDone) {//cần sửa lại cái eventDone này thành true không ta
                    if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                        hit = true;

                        previousEventX = gp.player.worldX;
                        previousEventY = gp.player.worldY;
                    }
                }

                gp.player.solidArea.x = gp.player.solidAreaDefaultX;
                gp.player.solidArea.y = gp.player.solidAreaDefaultY;
                eventRect[map][pit.x][pit.y].x = eventRect[map][pit.x][pit.y].eventRectDefaultX;
                eventRect[map][pit.x][pit.y].y = eventRect[map][pit.x][pit.y].eventRectDefaultY;
            }
        }

        return hit;
    }

    public void damagePit( Pit pit,int gameState) {
        gp.gameState = gameState;
        gp.playSE(6);
        gp.ui.currentDialogue = "You fall into a pit!";
        gp.player.life -= 1;
        canTouchEvent = false;
        pit.pitOn = false;
    }

    public void healingPool( int gameState) {
        if (gp.keyH.enterPress) {
            gp.gameState = gameState;
            gp.player.attackCanceled = true;
            gp.playSE(2);
            gp.ui.currentDialogue = "You drink the water.\nYour life has been recovered.";
            gp.player.life = gp.player.maxLife;
//            gp.aSetter.setMonster();//có bỏ đi hàng này cũng sẽ chẳng khác gì vì mình đâu thể check đuọcw vị trí mấy con quái
        }
    }
    public void teleport(int map, int col, int row){
        gp.currentMap = map;
        gp.player.worldX = gp.tileSize * col;
        gp.player.worldY = gp.tileSize * row;
        previousEventX = gp.player.worldX;
        previousEventY = gp.player.worldY;//cái này để làm gì nhỉ
        canTouchEvent = false;
        gp.playSE(11);
    }
}
