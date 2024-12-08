package ui;

import main.GameStates;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static main.GameStates.MENU;

public class NotificationGameConfirm extends Board{


    private BufferedImage img;
    private final Button buttonYes, buttonNo;
    private SettingBoardUI settingBoardUI;
    private BufferedImage Yes, No;
    public NotificationGameConfirm(int x, int y, int width, int height, SettingBoardUI settingBoardUI) {
        super(x, y, width, height);
        this.settingBoardUI = settingBoardUI;
        ImprortImg();
        int buttonWidth = 100;
        int buttonHeight = 100;
        int spaceBetweenButtons = 50;
        int total_length = 2*buttonWidth + spaceBetweenButtons;
        int length_left = 640 - total_length;
        int startPointX = length_left / 2;
        int nextStartPointX = startPointX + buttonWidth + spaceBetweenButtons;

        // Căn chỉnh tọa độ nút
        buttonYes = new Button(startPointX,370 , buttonWidth, buttonHeight, Yes);
        buttonNo = new Button(nextStartPointX, 370, buttonWidth, buttonHeight, No);


    }


    public void draw(Graphics g) {
        drawNotification( g);
        buttonYes.draw(g);
        buttonNo.draw(g);
    }

    public void ImprortImg() {
        try {
            img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/LeaveGame.png")));
            Yes = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Sure.png")));
            No = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Cancel.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawNotification(Graphics g) {
        g.drawImage(img, x, y,width,height , null);

    }

    public void mouseClicked(int x, int y) {
        if (buttonYes.getBounds().contains(x, y)) {
            GameStates.SetGameState(MENU);
            settingBoardUI.getStageManager().resetGame();
            settingBoardUI.openConfirmDialog(false);
            settingBoardUI.getStageManager().isPaused = false;

        } else if (buttonNo.getBounds().contains(x,y)) {
            settingBoardUI.openConfirmDialog(false);
            settingBoardUI.getSoundEffect().playEffect(1);
            settingBoardUI.setIsOpen(true);
            settingBoardUI.getStageManager().isPaused = true;
        }
    }

}
