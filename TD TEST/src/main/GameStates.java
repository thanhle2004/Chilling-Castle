package main;

public enum GameStates {

	PLAYING, MENU, SETTINGS, STAGE1, STAGE2,STAGE3, STAGE4, TOWER,  STAGES;

	public static GameStates gameState = MENU;

	public static void SetGameState(GameStates state) {
		gameState = state;
	}

}
