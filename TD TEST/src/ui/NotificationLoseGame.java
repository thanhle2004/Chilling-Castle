package ui;

import main.GameStates;
import managers.StageManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static main.GameStates.MENU;

public class NotificationLoseGame extends Board{

    private BufferedImage img,HomeButton, ReplayButton;
    private Button replayButton, homeButton;
    private StageManager stageManager;
    public NotificationLoseGame(int x, int y, int width, int height, StageManager stageManager) {
        super(x, y, width, height);
        importImage();
        this.stageManager = stageManager;
        int buttonWidth = 100;
        int buttonHeight = 100;
        int spaceBetweenButtons = 50;
        int total_length = 2*buttonWidth + spaceBetweenButtons;
        int length_left = 640 - total_length;
        int startPointX = length_left / 2;
        int nextStartPointX = startPointX + buttonWidth + spaceBetweenButtons;
        homeButton = new Button(startPointX,370,buttonWidth,buttonHeight,HomeButton);
        replayButton = new Button(nextStartPointX,370,buttonWidth,buttonHeight,ReplayButton);

    }


    public void draw(Graphics g) {
        g.drawImage(img, x, y, width, height, null);
        replayButton.draw(g);
        homeButton.draw(g);
    }

    private void importImage() {
        try {
            img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/LoseBoard.png")));
            HomeButton = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/HomeButton.png")));
            ReplayButton = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ReplayButton.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void mouseClicked(int x, int y) {
        if (homeButton.getBounds().contains(x,y)) {
            GameStates.SetGameState(MENU);
            stageManager.resetGame();
            stageManager.isPaused = false;
            stageManager.isLose = false;


        } else if (replayButton.getBounds().contains(x,y)) {
            stageManager.resetGame();
            stageManager.isLose = false;
            stageManager.isPaused = false;
            stageManager.getEnemyManager().setPauseGame(false);
        }
    }
}
