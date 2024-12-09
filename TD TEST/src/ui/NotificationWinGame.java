package ui;

import main.GameStates;
import managers.StageManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static main.GameStates.*;

public class NotificationWinGame extends Board{

    private BufferedImage img,HomeButton, NextButton;
    private Button nextButton, homeButton;
    private StageManager stageManager;
    public NotificationWinGame(int x, int y, int width, int height, StageManager stageManager) {
        super(x, y, width, height);
        importImage();
        this.stageManager = stageManager;
        int buttonWidth = 100;
        int buttonHeight = 50;
        int spaceBetweenButtons = 50;
        int total_length = 2*buttonWidth + spaceBetweenButtons;
        int length_left = 640 - total_length;
        int startPointX = length_left / 2;
        int nextStartPointX = startPointX + buttonWidth + spaceBetweenButtons;
        homeButton = new Button(startPointX,370,buttonWidth,buttonHeight,HomeButton);
        nextButton = new Button(nextStartPointX,370,buttonWidth,buttonHeight, NextButton);

    }

    public void draw(Graphics g) {
        g.drawImage(img, x, y,width,height, null);
        homeButton.draw(g);
        nextButton.draw(g);
    }

    private void importImage() {
        try {
            img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/WinBoard.png")));
            HomeButton = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/backHome.png")));
            NextButton = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/nextStage.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void mouseClicked(int x, int y) {
        if (homeButton.getBounds().contains(x,y)) {
            GameStates.SetGameState(MENU);
            stageManager.resetGame();
            stageManager.isPaused = false;
            stageManager.isWin = false;
        } else if (nextButton.getBounds().contains(x,y)) {
            stageManager.resetGame();
            stageManager.isPaused = false;
            stageManager.isWin = false;
            switch (GameStates.GetGameState()) {
                case STAGE1:
                    GameStates.SetGameState(STAGE2);
                    stageManager.game().getStage2().getEnemyManager().setPauseGame(false);
                    break;
                case STAGE2:
                    GameStates.SetGameState(STAGE3);
                    stageManager.game().getStage3().getEnemyManager().setPauseGame(false);
                    break;
                case STAGE3:
                    GameStates.SetGameState(MENU);
                    break;
            }
        }
    }
}
