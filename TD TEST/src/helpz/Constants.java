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
					return 200;
				case SLIME:
					return 100;
				case DUDE:
					return 300;
				case PINKY:
					return 400;
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


	public static class Tower {
		public static final int FIRE_TOWER = 1;
		public static final int ICE_TOWER = 2;
		public static final int LIGHT_TOWER = 3;
		public static final int BUFF_TOWER = 4;
		public static final int SUMMON_TOWER = 5;


		public static int CoinUpgrade (int towerTypes) {
			switch (towerTypes) {
				case ICE_TOWER:
					return 20;
				case FIRE_TOWER:
					return 50;
				case LIGHT_TOWER:
					return 100;
				case BUFF_TOWER:
					return 220;
				case SUMMON_TOWER:
					return 300;
			}
			return 0;
		}


		public static double Dmg(int towerTypes) {
			switch (towerTypes) {
				case ICE_TOWER:
					return 15;
				case FIRE_TOWER:
					return 20;
				case LIGHT_TOWER:
					return 2;
				case SUMMON_TOWER:
					return 30;
			}
			return 0;
		}

		public static int Range(int towerTypes) {
			switch (towerTypes) {
				case ICE_TOWER:
					return 80;
				case FIRE_TOWER:
					return 100;
				case LIGHT_TOWER:
					return 200;
				case BUFF_TOWER:
					return 300;
				case SUMMON_TOWER:
					return 50;
			}
			return 0;
		}

		public static double CD(int towerTypes) {
			switch (towerTypes) {
				case ICE_TOWER:
					return 0.5;
				case FIRE_TOWER:
					return 1;
				case LIGHT_TOWER:
					return 0.05;
				case BUFF_TOWER:
					return 10;
				case SUMMON_TOWER:
					return 30;
			}
			return 0;
		}

		public static final int CLOSET = 1;
		public static final int STRONGEST = 2;
		public static final int FIRSTE = 3;
		public static final int LASTE = 4;
		public static final int RANDOM = 5;

	}

}
