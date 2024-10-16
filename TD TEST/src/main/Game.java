package main;

import javax.swing.JFrame;

import helpz.LoadPathImage;
import inputs.KeyboardListener;
import inputs.MyMouseListener;
import managers.TileManager;
import objects.SoundEffect;
import scenes.*;
import stages.Stage1;

public class Game extends JFrame implements Runnable {

	private GameScreen gameScreen;
	private Thread gameThread;

	private final double FPS_SET = 120.0;
	private final double UPS_SET = 60.0;

	// Classes
	private Render render;
	private Menu menu;
	private Stages stages;
	private Stage2 stage2;
	private Settings settings;
	private Towers towers;
	private SoundEffect soundEffect;
	private Stage1 stage1;

	private TileManager tileManager;

	public Game() {

		initClasses();
		createDefaultLevel();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		add(gameScreen);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);

	}

	private void createDefaultLevel() {
		int[] arr = new int[400];
		for (int i = 0; i < arr.length; i++)
			arr[i] = 0;
	}

	private void initClasses() {
		tileManager = new TileManager();
		render = new Render(this);
		gameScreen = new GameScreen(this);
		menu = new Menu(this);
		settings = new Settings(this,menu);
		towers = new Towers(this);
		stages = new Stages(this,towers.getBottomBar());
		stage1 = new Stage1(this, towers.getBottomBar(), settings);
		stage2 = new Stage2(this);

		soundEffect = new SoundEffect();
	

	}

	private void start() {
		gameThread = new Thread(this) {
		};

		gameThread.start();
	}

	private void updateGame() {
		switch (GameStates.gameState) {
		case MENU:
			break;
		case STAGE1:
			stage1.update();
			break;
		case STAGES:
			break;
		case STAGE2:
			stage2.update();
			break;
		case SETTINGS:
			break;
		case TOWER:
			break;
		default:
			break;
		}
	}

	public static void main(String[] args) {

		Game game = new Game();
		game.gameScreen.initInputs();
		game.start();

	}

	@Override
	public void run() {

		double timePerFrame = 1000000000.0 / FPS_SET;
		double timePerUpdate = 1000000000.0 / UPS_SET;

		long lastFrame = System.nanoTime();
		long lastUpdate = System.nanoTime();
		long lastTimeCheck = System.currentTimeMillis();

		int frames = 0;
		int updates = 0;

		long now;

		while (true) {
			now = System.nanoTime();

			// Render
			if (now - lastFrame >= timePerFrame) {
				repaint();
				lastFrame = now;
				frames++;
			}

			// Update
			if (now - lastUpdate >= timePerUpdate) {
				updateGame();
				lastUpdate = now;
				updates++;
			}

			if (System.currentTimeMillis() - lastTimeCheck >= 1000) {
				System.out.println("FPS: " + frames + " | UPS: " + updates);
				frames = 0;
				updates = 0;
				lastTimeCheck = System.currentTimeMillis();
			}

		}

	}

	// Getters and setters
	public Render getRender() {
		return render;
	}
	public Menu getMenu() {
		return menu;
	}
	public Stages getStages() {
		return stages;
	}
	public Settings getSettings() {
		return settings;
	}
	public TileManager getTileManager() {
		return tileManager;
	}
	public Stage2 getStage2() {
		return stage2;
	}
	public Towers getTowers() { return towers; }
	public Stage1 getStage1() { return stage1; }

}