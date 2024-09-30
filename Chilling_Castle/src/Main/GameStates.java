package Main;

import Main.GameStates;

public enum GameStates {
	PLAYING, MENU, SETTING;
	
	public static GameStates gameStates = MENU;
	
	public static void setGameState(GameStates states) {
		gameStates = states;
	}

}
