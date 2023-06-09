package main;

import entity.Entity;
import object.OBJ_Heart;
import object.OBJ_Key;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font maruMonica, purisaB;
    BufferedImage heart_full, heart_half, heart_blank;
    public boolean messageOn = false;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int subState1 = 0;
    public boolean activeGuidance = true;
    public boolean activeOverscreen = true;
    public static int Count = 0;

    public int titleScreenState = 0; // 0: the first screen, 1: the second screen
    public int slotCol = 0;
    public int slotRow = 0;
    int subState = 0;

    public UI(GamePanel gp) {
        this.gp = gp;

        try {
            InputStream is = getClass().getResourceAsStream("/font/MaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
            purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // CREATE HUD OBJECT
        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
    }

    public void addMessage(String text) {
        message.add(text);
        messageCounter.add(0);
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(maruMonica);
//        g2.setFont(purisaB);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.white);

        // TITLE STATE
        if (gp.gameState == gp.titleState) {
            drawPlayerLife();
            drawTitleScreen();
        }
        // PLAY STATE
        if (gp.gameState == gp.playState) {
            activeOverscreen = false;
            if (activeGuidance == true){
                drawGuidance();
            }
            else{
                drawPlayerLife();
                drawMessage();
            }


        }
        // PAUSE STATE
        if (gp.gameState == gp.pauseState) {
            drawPlayerLife();
            drawPauseScreen();
        }
        // DIALOGUE STATE
        if (gp.gameState == gp.dialogueState) {
            drawPlayerLife();
            drawDialogueScreen();
        }
        // CHARACTER STATE
        if (gp.gameState == gp.characterState) {
            drawCharacterScreen();
            drawInventory();
        }
        //OPTION STATE
        if(gp.gameState == gp.optionsState){
            drawOptionsScreen();
        }
        //OPTION OVER/VICTORY
        if(gp.gameState == gp.gameOverAndVictoryState ){
            drawVictoryAndGameOverScreen();
        }


    }

    private void drawGuidance() {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));
        //SUB WINDOW
        int frameX = gp.tileSize * 6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 8;
        int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        option_Guidance(frameX,frameY);
        gp.player.attackCanceled = true;
        switch (subState1){
            case 0:
                option_Guidance(frameX,frameY);
        }

    }
    public void drawPlayerLife() {
        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        int i = 0;

        // DRAW MAX LIFE
        while (i < gp.player.maxLife / 2) {
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += gp.tileSize;
        }

        // RESET
        x = gp.tileSize / 2;
        y = gp.tileSize / 2;
        i = 0;

        // DRAW CURRENT LIFE
        while (i < gp.player.life) {
            g2.drawImage(heart_half, x, y, null);
            i++;
            if (i < gp.player.life) {
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += gp.tileSize;
        }
    }

    private void drawMessage() {
        int messageX = gp.tileSize;
        int messageY = gp.tileSize * 4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));

        for (int i = 0; i < message.size(); i++) {
            if (message.get(i) != null) {
                g2.setColor(Color.black);
                g2.drawString(message.get(i), messageX + 2, messageY + 2);
                g2.setColor(Color.white);//paint the shadow
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1; // messageCounter++
                messageCounter.set(i, counter); // set the counter to the array
                messageY += 50;

                if (messageCounter.get(i) > 120) {
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }

    public void drawTitleScreen() {
        g2.drawImage(gp.player.backGround, 0, 0, gp.screenWidth, gp.screenHeight, null);

        // TITLE NAME
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String text = "Monica's Adventure";
        int x = getXforCenteredText(text);
        int y = gp.tileSize * 3;

        // SHADOW
        g2.setColor(Color.white);
        g2.drawString(text, x + 5, y + 5);
        // MAIN COLOR
        g2.setColor(new Color(78,53,36));
        g2.drawString(text, x, y);

        // BLUE BOY IMAGE
        x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2;
        y += gp.tileSize * 2;
        g2.drawImage(gp.player.mainPlayerImage, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

        // MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

        text = "NEW GAME";
        x = getXforCenteredText(text);
        y += gp.tileSize * 3.5;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "LOAD GAME";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "QUIT";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 2) {
            g2.drawString(">", x - gp.tileSize, y);
        }
    }

    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSE";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 2;

        g2.drawString(text, x, y);
    }

    public void drawDialogueScreen() {
        // WINDOW
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 4;

        drawSubWindow(x, y, width, height);

        g2. setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));//cái này nó chỉ chỉnh kỉnh cỡ chữ tại điểm cần thôi
        x += gp.tileSize;
        y += gp.tileSize;

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
    }

    public void drawCharacterScreen() {
        // CREATE A FRAME
        final int frameX = gp.tileSize * 2;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize * 5;
        final int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // TEXT
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 35;

        // NAMES
        g2.drawString("Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Life", textX, textY);
        textY += lineHeight;
        g2.drawString("Strength", textX, textY);
        textY += lineHeight;
        g2.drawString("Dexterity", textX, textY);
        textY += lineHeight;
        g2.drawString("Attack", textX, textY);
        textY += lineHeight;
        g2.drawString("Defense", textX, textY);
        textY += lineHeight;
        g2.drawString("Exp", textX, textY);
        textY += lineHeight;
        g2.drawString("Next level", textX, textY);
        textY += lineHeight;
        g2.drawString("Coin", textX, textY);
        textY += lineHeight + 20;
        g2.drawString("Weapon", textX, textY);
        textY += lineHeight + 15;
        g2.drawString("Shield", textX, textY);
        textY += lineHeight;

        // VALUES
        int tailX = (frameX + frameWidth) - 30;
        // Reset textY
        textY = frameY + gp.tileSize;
        String value;

        value = String.valueOf(gp.player.level);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.dexterity);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.attack);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.defense);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.exp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.coin);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY - 14, null);
        textY += gp.tileSize;
        g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY - 14, null);
    }

    public void drawInventory() {
        // FRAME
        int frameX = gp.tileSize * 12;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 6;
        int frameHeight = gp.tileSize * 5;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // SLOT
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize + 3;

        // DRAW PLAYER'S ITEMS
        for (int i = 0; i < gp.player.inventory.size(); i++) {
            // EQUIP CURSOR
            if (gp.player.inventory.get(i) == gp.player.currentWeapon ||
                gp.player.inventory.get(i) == gp.player.currentShield)
            {
                g2.setColor(new Color(240, 190, 90));
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
            }

            g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null);

            //DISPLAY AMOUNT
            if(gp.player.inventory.get(i).amount > 1){
                g2.setFont(g2.getFont().deriveFont(32f));
                int amountX;
                int amountY;

                String s = "" + gp.player.inventory.get(i).amount;
                amountX = getXforAlignToRightText(s, slotX + 44);
                amountY = slotY + gp.tileSize;
                //SHADOW
                g2.setColor(new Color(60, 60, 60));
                g2.drawString(s, amountX, amountY);
                //NUMBEER
                g2.setColor(Color.white);
                g2.drawString(s, amountX - 3, amountY - 3);

            }
            slotX += slotSize;

            if (i == 4 || i == 9 || i == 13) {//not 14
                slotX = slotXstart;
                slotY += slotSize;
            }
        }

        // CURSOR
        int cursorX = slotXstart + (slotSize * slotCol);
        int cursorY = slotYstart + (slotSize * slotRow);
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;
        // DRAW CURSOR
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

        // DESCRIPTION FRAME
        int dFrameX = frameX;
        int dFrameY = frameY + frameHeight;
        int dFrameWidth = frameWidth;
        int dFrameHeight = gp.tileSize * 3;
        // DRAW DESCRIPTION TEXT
        int textX = dFrameX + 20;
        int textY = dFrameY + gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(28F));

        int itemIndex = getItemIndexOnSlot();

        if (itemIndex < gp.player.inventory.size()) {
            drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);

            for (String line: gp.player.inventory.get(itemIndex).description.split("\n")) {
                g2.drawString(line, textX, textY);
                textY += 32;
            }
        }
    }

    public int getItemIndexOnSlot() {
        int itemIndex = slotCol + (slotRow * 5);
//        System.out.println("Row: " + slotRow + "Column" + slotCol);
        return itemIndex;
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 150);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;

        return x;
    }

    public int getXforAlignToRightText(String text, int tailX) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;

        return x;
    }
    public void drawOptionsScreen(){
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(32F));

        //SUB WINDOW
        int frameX = gp.tileSize * 6;
        int frameY = gp.tileSize + 10;
        int frameWidth = gp.tileSize * 10 ;
        int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (subState){
            case 0:
                options_top(frameX, frameY);
                break;
            case 1:
                options_fullScreenNotification(frameX, frameY);
                break;
            case 2:
                options_control(frameX, frameY);
                break;
            case 3 :
                options_endGameInformation(frameX, frameY);
                break;
            case 4:
                options_Source(frameX,frameY);
                break;
            case 5:
                option_showName(frameX , frameY);
                break;
            case 7:
                option_showQR(frameX, frameY);
                break;
            case 8:
                option_ShowSource(frameX, frameY);
                break;
        }
        gp.keyH.enterPress = false;
    }
    public void drawVictoryAndGameOverScreen(){
        if(gp.victoryOrNot){
            g2.setColor(new Color(0,0,0, 20));
        }
        else{
            g2.setColor(new Color(0,0,0,150));
        }
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        int x,y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));

        if(gp.victoryOrNot){
            text = "Victory";
        }
        else{
            text = "Game Over";
        }
        //Shadow
        g2.setColor(Color.black);
        x = getXforCenteredText(text);
        y = gp.tileSize * 4;
        g2.drawString(text, x, y);
        //Main
        g2.setColor(Color.white);
        g2.drawString(text, x -4, y - 4);

        //Retry
        g2.setFont(g2.getFont().deriveFont(50f));
        if(gp.victoryOrNot){
            text = "Play again";
        }
        else{
            text = "Retry";
        }
        y += gp.tileSize * 4;
        x = getXforCenteredText(text);
        g2.drawString(text, x, y);
        if(commandNum == 0){
            g2.drawString(">", x - 40, y);
        }

        //Back to the main screen
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Main Screen";
        y += 55;
        x = getXforCenteredText(text) + 10 ;
        g2.drawString(text, x, y);
        if(commandNum == 1){
            g2.drawString(">", x - 40, y);

        }

    }

    public void option_Guidance(int frameX , int frameY){
        int textX;
        int textY;
        String text = "GAME GUIDANCE";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text,textX,textY);
        Line2D line = new Line2D.Float(frameX+gp.tileSize+135, textY+30,frameX+gp.tileSize+135,(gp.tileSize+10) * 6 + gp.tileSize * 2);
        g2.draw(line);

        // MOVEMENT CONTROL
        textX = frameX + gp.tileSize;
        textY = frameY + 2*gp.tileSize + 15;
        g2.drawString("W",textX-10 , textY);
        g2.drawString("Go ahead",textX+200, textY);
        textY+= gp.tileSize;
        g2.drawString("S",textX-10 , textY);
        g2.drawString("Go down",textX+200, textY);
        textY+= gp.tileSize;
        g2.drawString("A",textX-10 , textY);
        g2.drawString("Move left",textX+200, textY);
        textY+= gp.tileSize;
        g2.drawString("D",textX-10 , textY);
        g2.drawString("Move right",textX+200, textY);
        textY+= gp.tileSize;
        g2.drawString("Enter",textX-10 , textY);
        g2.drawString("Attack",textX+200, textY);

        textY+= gp.tileSize;
        g2.drawString("Quote",textX-10 , textY);
        g2.drawString("Flashing",textX+200, textY);

        textY+= gp.tileSize;
        g2.drawString("Backspace",textX-10 , textY);
        g2.drawString("Shield",textX+200, textY);

        //If the player position differ from the default position, the guide option disappear
        if(gp.player.worldX != gp.player.playerDefaultWorldX || gp.player.worldY != gp.player.playerDefaultWorldY){
            activeGuidance = false;
            addMessage("Welcome to Mocica's world!!!!");
            gp.gameState = gp.playState;
            commandNum = 0;
        }

    }
    public void options_top(int frameX, int frameY){
        int textX, textY;
        String text = "Option";
        textX = getXforCenteredText(text) + 40;
        textY = gp.tileSize + frameY ;
        g2.drawString(text, textX, textY);

        //Full screen On/Off
        textX = frameX + gp.tileSize;//cái này được dùng chung cho mấy cái chức năng ở dưới
        textY += 2 * gp.tileSize;
        g2.drawString("Full Screen", textX, textY);
        if(commandNum == 0){
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPress){
                if(!gp.fullScreenOn){
                    gp.fullScreenOn = true;
                }
                else if(gp.fullScreenOn){
                    gp.fullScreenOn = false;
                }
                subState = 1;
            }
        }

        //Music
        textY += gp.tileSize;
        g2.drawString("Music", textX, textY);
        if(commandNum == 1){
            g2.drawString(">", textX - 25, textY);
        }
        //SE
        textY += gp.tileSize;
        g2.drawString("Sound Effect", textX, textY);
        if(commandNum == 2 ){
            g2.drawString( ">", textX - 25, textY);
        }
        //Control
        textY += gp.tileSize;
        g2.drawString("Control", textX, textY);
        if(commandNum == 3){
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPress){
                subState = 2;
                commandNum = 0;
            }
        }
        //End game
        textY += gp.tileSize;
        g2.drawString("End Game", textX, textY);
        if(commandNum == 4){
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPress) {
                subState = 3;
                commandNum = 0;
            }
        }

        // SOURCE
        textY+=gp.tileSize;
        g2.drawString("More",textX,textY);
        if (commandNum == 5){
            g2.drawString(">",textX-25,textY);
            if (gp.keyH.enterPress){
                subState = 4;
                commandNum =0;
            }
        }

        //Back
        textY += gp.tileSize * 2 - 25;
        g2.drawString("Back", textX, textY);
        if(commandNum == 6){
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPress){
                gp.gameState = gp.playState;
                commandNum = 0;

            }
        }

        //FULL SCREEN CHECKBOX
        textX = frameX + (int)(gp.tileSize * 4.5);
        textY = frameY + gp.tileSize * 2 + 26;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 24, 24);
        if(gp.fullScreenOn){
            g2.fillRect(textX, textY, 24, 24);//có cách nào thay vì tô hết thì để dấu x hay dấu tích không, load ảnh instead
        }

        //MUSIC VOLUME
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 24*5, 24);
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        //SOUND EFFECT
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 24*5, 24);
        int seWidth = 24 * gp.se.volumeScale;
        g2.fillRect(textX, textY, seWidth, 24);

        gp.config.savaConfig();
    }
    public void options_fullScreenNotification(int frameX, int frameY) {
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;

        currentDialogue = "The change will take effect after \nrestart \nthe game!!!";
        for (String s : currentDialogue.split("\n")) {
            g2.drawString(s, textX, textY);
            textY += (gp.tileSize - 8);
        }
        //BACK
        textY = frameY + 9 * gp.tileSize;
        g2.drawString("Back", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPress) {
                subState = 0;
            }
        }
        //CONTINUE
        textY = textY - gp.tileSize;
        g2.drawString("Continue", textX, textY);
        if (commandNum == 1) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPress) {
                subState = 0;
            }

        }
    }
    public void options_control(int frameX, int frameY){
        int textX;
        int textY;
        //TITLE
        String text = "Control";
        textX = getXforCenteredText(text) + 40;
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        //Control
        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString("Move", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Conform/Attack", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Flashing attack", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Character Status", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Pause", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Option", textX, textY);

        textX = frameX + gp.tileSize * 6;
        textY = frameY + gp.tileSize * 2;
        g2.drawString("WASD", textX, textY);
        textY += gp.tileSize;
        g2.drawString("ENTER", textX, textY);
        textY += gp.tileSize;
        g2.drawString("QUOTE", textX, textY);
        textY += gp.tileSize;
        g2.drawString("C", textX, textY);
        textY += gp.tileSize;
        g2.drawString("P", textX, textY);
        textY += gp.tileSize;
        g2.drawString("ESC", textX, textY);

        //BACK
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize * 9;
        g2.drawString("Back", textX, textY);
        if(commandNum == 0){
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPress){
                subState = 0;
            }
        }
    }
    public void options_endGameInformation(int frameX, int frameY) {
        int textX = frameX + gp.tileSize;
        int textY = frameY + 3 * gp.tileSize;
        currentDialogue = "Do you want to quit the \ngame and return to the \nmain screen ? " ;
        for(String line :  currentDialogue.split("\n" )  ){
            g2.drawString(line ,textX, textY);
            textY += 40;
        }
//        subState = 1;
        //YES OPTION
        String text = "Yes";//nhìn 2 cái này nó cachs xa vl, chút phải chỉnh lại
        textX = getXforCenteredText(text) + 20;
        textY += gp.tileSize * 3 - 80;
        g2.drawString(text, textX, textY);
        if(commandNum == 0){
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPress){
                subState = 0;
                gp.gameState = gp.titleState;
                gp.stopMusic();
            }
        }
        //NO OPTION
        text = "No";
        textX = getXforCenteredText(text)+20;
        textY += gp.tileSize - 10;
        g2.drawString(text, textX, textY);
        if(commandNum == 1){
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPress){
                subState = 0;
//                gp.gameState = gp.titleState;
            }
        }

    }

    public void options_Source(int frameX , int frameY) {
        int textX;
        int textY;

//        int commandNum1 = 1;
        // TILE
        String text = "Source";
        textX = getXforCenteredText(text) + 40;
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);
        //commandNum = 0;

        // CONTROL ACTION
        textX = frameX + gp.tileSize;
        textY = frameY + 2 * gp.tileSize + 15;

        // NAME

        g2.drawString("Member information", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPress) {
                subState = 5;
            }
        }
        textY += gp.tileSize;

        g2.drawString("Source", textX, textY);
        if (commandNum == 1) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPress) {
                subState = 8;
            }
        }

        textY += gp.tileSize;
        g2.drawString("Enjoy us", textX, textY);
        if (commandNum == 2) {
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPress) subState = 7;

        }

        // BACK BUTTON
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize * 9 - 40;
        g2.drawString("Back", textX, textY);
        if(commandNum == 3){
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPress){
                subState = 0;
            }
        }

    }

    public void option_showName(int frameX , int frameY){
        int textX;
        int textY;
        String text = "NAME";
        textX = getXforCenteredText(text) + 40;
        textY = frameY + gp.tileSize;
        g2.drawString(text,textX , textY);

        // SHOW NAME
        textX = frameX + gp.tileSize;
        textY = frameY + 2*gp.tileSize + 15;
        g2.drawString("Huynh Thi Ngoc Tram - ITCSIU21238",textX-15,textY);
        textY+= gp.tileSize + 10;
        g2.drawString("Ma Phung Nghia - ITCSIU21206",textX-15,textY);
        textY+= gp.tileSize + 10;
        g2.drawString("Hoang Gia Huy - ITCSIU21186",textX-15,textY);
        textY+= gp.tileSize + 10;
        g2.drawString("Pham Binh Duong - ITCSIU21054",textX-15,textY);

        // BACK BUTTTON
        textX = frameX + gp.tileSize ;
        textY = frameY + gp.tileSize * 9 - 40;
        g2.drawString("Back", textX, textY);
        commandNum = 3;
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize * 9 - 40;
        g2.drawString("Back", textX, textY);
        if(commandNum == 3){
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPress){
                subState = 0;
            }
        }
    }
    public void option_showQR(int frameX, int frameY){
        int textX;
        int textY;
        String text = "Enjoy us here!!";

        textX = getXforCenteredText(text) + 40;
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX , textY);

        int QRx = textX;
        int QRy = textY;
        QRy += (1 * gp.tileSize);

        g2.drawImage(gp.player.QRPicture, QRx, QRy, gp.tileSize * 4, gp.tileSize * 6, null);

        commandNum = 0;
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize * 9 - 20;
        g2.drawString("Back", textX, textY);
        if(commandNum == 0){
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPress){
                subState = 0;
            }
        }

    }
    public void option_ShowSource(int frameX , int frameY) {
        int textX, textY;
        String text1 = "THANKS !";
        textX = getXforCenteredText(text1) + 40;
        textY = frameY + gp.tileSize;
        g2.drawString(text1, textX, textY);

        String text2 = "https://docs.google.com/document\n/d/1wwfB_c8VJqe478Ko8Sb_JgH\niTk5y0oxBy1J2nfUzC5I/edit";
        textX = frameX + gp.tileSize - 12;
        textY = frameY + 2 * gp.tileSize + 15;
        for (String line : text2.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 35;
        }

        String text3 = "https://tinyurl.com/33vth5y2";
        textX = frameX + gp.tileSize -12;
        textY = frameY + 2 * gp.tileSize + 160;
        for (String line : text3.split("\n")){
            g2.drawString(line, textX, textY);
            textY += 35;
        }

        // BACK BUTTON
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize * 9 - 40;
        g2.drawString("Back", textX, textY);
        commandNum = 2;
        if (commandNum == 2) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPress) {
                subState = 4;

            }
        }
    }
}
