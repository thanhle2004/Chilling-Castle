package helpz;

public class Constants {

	public static class Direction {
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}
	
	public static class Tiles{
		public static final int WATER_TILE = 0;
		public static final int GRASS_TILE = 1;
		public static final int ROAD_TILE = 2;
	}
	
	public static class Enemy{
		public static final int OSTER = 0;
		public static final int SLIME = 1;
		public static final int DUDE = 2;
		public static final int PINKY = 3;



		public static int Health(int enemyType) {
			switch (enemyType) {
				case OSTER:
					return 25;
				case SLIME:
					return 10;
				case DUDE:
					return 50;
				case PINKY:
					return 110;
			}
			return 0;
		}

		public static int CoinReward (int enemyType) {
			switch (enemyType) {
				case OSTER:
					return 50;
				case SLIME:
					return 20;
				case DUDE:
					return 100;
				case PINKY:
					return 220;
			}
			return 0;
		}
	}

}
