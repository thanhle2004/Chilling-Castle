package ui;

import main.GameStates;
import managers.StageManager;
import scenes.Menu;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static main.GameStates.MENU;

public class NotificationGameOver extends Bar{

    private BufferedImage img,HomeButton, ReplayButton;
    private SettingButton replayButton, homeButton;
    private StageManager stageManager;
    public NotificationGameOver(int x, int y, int width, int height, StageManager stageManager) {
        super(x, y, width, height);
        this.stageManager = stageManager;
        replayButton = new SettingButton(null,x + 320 + 50 + 10 + 5,y+340,width,height,ReplayButton);
        homeButton = new SettingButton(null,x + 160,y+340,width,height,HomeButton);
        importImage();
    }


    public void draw(Graphics g) {
        drawBoardNotification(g);
    }

    private void importImage() {
        try {
            img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/selectedBoard-export.png")));

            HomeButton = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/HomeButton.png")));
            ReplayButton = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ReplayButton.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawBoardNotification(Graphics g) {
        g.drawImage(img, x, y, null);
        g.drawImage(HomeButton, x + 160, y+340, width, height, null);
        g.drawImage(ReplayButton, x + 320 + 50 + 10 + 5, y+340, width, height, null);
        g.setFont(new Font("Times New Roman", Font.BOLD, 30));
        g.drawString("LOSER, HEHEHE", x + 200, y + 300);
    }

    public void mouseClicked(int x, int y) {
        if (homeButton.getBounds().contains(x,y)) {
            GameStates.SetGameState(MENU);
        } else if (replayButton.getBounds().contains(x,y)) {
            stageManager.resetGame();
            stageManager.isLose = false;
        }
    }
}
