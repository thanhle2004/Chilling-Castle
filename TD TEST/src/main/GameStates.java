package main;

public enum GameStates {

	PLAYING, MENU, SETTINGS, STAGE1, STAGE2,STAGE3, STAGE4;

	public static GameStates gameState = MENU;

	public static void SetGameState(GameStates state) {
		gameState = state;
	}

}
