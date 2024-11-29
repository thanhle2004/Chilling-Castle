package inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import main.Game;
import main.GameStates;

public class MyMouseListener implements MouseListener, MouseMotionListener {

	private Game game;

	public MyMouseListener(Game game) {
		this.game = game;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		switch (GameStates.gameState) {
		case MENU:
			game.getMenu().mouseDragged(e.getX(), e.getY());
			break;
		case STAGES:
			game.getStages().mouseDragged(e.getX(), e.getY());
			break;
		case STAGE1:
			game.getStage1().mouseDragged(e.getX(), e.getY());
			break;
		case STAGE2:
			game.getStage2().mouseDragged(e.getX(), e.getY());
			break;
		case SETTINGS:
			game.getSettings().mouseDragged(e.getX(), e.getY());
			break;
		case TOWER:
			game.getTowers().mouseDragged(e.getX(), e.getY());
			break;
		default:
			break;
		}

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		switch (GameStates.gameState) {
		case MENU:
			game.getMenu().mouseMoved(e.getX(), e.getY());
			break;
		case STAGES:
			game.getStages().mouseMoved(e.getX(), e.getY());
			break;
		case STAGE1:
			game.getStage1().mouseMoved(e.getX(), e.getY());
			break;
		case STAGE2:
			game.getStage2().mouseMoved(e.getX(), e.getY());
			break;
		case SETTINGS:
			game.getSettings().mouseMoved(e.getX(), e.getY());
			break;
		case TOWER:
			game.getTowers().mouseMoved(e.getX(), e.getY());
			break;
		default:
			break;
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			switch (GameStates.gameState) {
				case MENU:
					game.getMenu().mouseClicked(e.getX(), e.getY());
					break;
				case STAGES:
					game.getStages().mouseClicked(e.getX(), e.getY());
					break;
				case STAGE1:
					game.getStage1().mouseClicked(e.getX(), e.getY());
					break;
				case STAGE2:
					game.getStage2().mouseClicked(e.getX(), e.getY());
					break;
				case SETTINGS:
					game.getSettings().mouseClicked(e.getX(), e.getY());
					break;
				case TOWER:
					game.getTowers().mouseClicked(e.getX(), e.getY());
					break;
				default:
					break;
			}
		}else if (e.getButton() == MouseEvent.BUTTON3) { // Right-click
			switch (GameStates.gameState) {
				case MENU:
					game.getMenu().mouseRightClicked(e.getX(), e.getY());
					break;
				case STAGES:
					game.getStages().mouseRightClicked(e.getX(), e.getY());
					break;
				case STAGE1:
					game.getStage1().mouseRightClicked(e.getX(), e.getY());
					break;
				case STAGE2:
					game.getStage2().mouseRightClicked(e.getX(), e.getY());
					break;
				case SETTINGS:
					game.getSettings().mouseRightClicked(e.getX(), e.getY());
					break;
				case TOWER:
					game.getTowers().mouseRightClicked(e.getX(), e.getY());
					break;
				default:
					break;
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch (GameStates.gameState) {
		case MENU:
			game.getMenu().mousePressed(e.getX(), e.getY());
			break;
		case STAGES:
			game.getStages().mousePressed(e.getX(), e.getY());
			break;
		case STAGE1:
			game.getStage1().mousePressed(e.getX(), e.getY());
			break;
		case STAGE2:
			game.getStage2().mousePressed(e.getX(), e.getY());
			break;
		case SETTINGS:
			game.getSettings().mousePressed(e.getX(), e.getY());
			break;
	 	case TOWER:
			game.getTowers().mousePressed(e.getX(), e.getY());
			break;
		default:
			break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		switch (GameStates.gameState) {
		case MENU:
			game.getMenu().mouseReleased(e.getX(), e.getY());
			break;
		case STAGES:
			game.getStages().mouseReleased(e.getX(), e.getY());
			break;
		case STAGE2:
			game.getStage2().mouseReleased(e.getX(), e.getY());
			break;
		case STAGE1:
			game.getStage1().mouseReleased(e.getX(), e.getY());
			break;
		case SETTINGS:
			game.getSettings().mouseReleased(e.getX(), e.getY());
			break;
		case TOWER:
			game.getTowers().mouseReleased(e.getX(), e.getY());
			break;
		default:
			break;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// We wont use this

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// We wont use this
	}

}
