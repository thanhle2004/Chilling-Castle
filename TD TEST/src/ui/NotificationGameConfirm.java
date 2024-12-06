package ui;

import main.GameStates;
import managers.StageManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static main.GameStates.MENU;

public class NotificationGameConfirm extends Board{


    private BufferedImage img;
    private Button buttonYes, Buttonno;
    private SettingBoardUI settingBoardUI;
    public NotificationGameConfirm(int x, int y, int width, int height ,SettingBoardUI settingBoardUI) {
        super(x, y, width, height);
        this.settingBoardUI = settingBoardUI;
        buttonYes= new Button("Yes", x + 160 , y + 340, 50, 50);
        Buttonno= new Button("No", x + 320 + 50 + 10 + 5, y + 340 , 50, 50);
        ImprortImg();
    }

    public void draw(Graphics g) {
        drawNotification( g);
        buttonYes.draw(g);
        Buttonno.draw(g);
    }

    public void ImprortImg() {
        try {
            img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/selectedBoard-export.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawNotification(Graphics g) {
        g.drawImage(img, x-25, y, null);

    }

    public void mouseClicked(int x, int y) {
        if (buttonYes.getBounds().contains(x, y)) {
            GameStates.SetGameState(MENU);
            settingBoardUI.getStageManager().resetGame();
            settingBoardUI.openConfirmDialog(false);
            settingBoardUI.getStageManager().isPaused = false;


        } else if (Buttonno.getBounds().contains(x,y)) {
            settingBoardUI.openConfirmDialog(false);
            settingBoardUI.getSoundEffect().playEffect(1);
            settingBoardUI.setIsOpen(true);
            System.out.println("set is open " + settingBoardUI.getIsOpen());
        }
    }

}
