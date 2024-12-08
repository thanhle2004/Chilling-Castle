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
				case OSTER: return 200;
				case SLIME: return 50;
				case DUDE: return 300;
				case PINKY: return 400;
			}
			return 0;
		}

		public static int CoinReward (int enemyType) {
			switch (enemyType) {
				case OSTER: return 50;
				case SLIME: return 20;
				case DUDE: return 100;
				case PINKY: return 220;
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


		public static int CoinToBuy (int towerTypes) {
			switch (towerTypes) {
				case ICE_TOWER: return 45;
				case FIRE_TOWER: return 40;
				case LIGHT_TOWER: return 20;
				case BUFF_TOWER: return 50;
				case SUMMON_TOWER: return 70;
			}
			return 0;
		}


		public static int CoinUpgradeL( int level) {
			switch (level) {
				case 2: return 40;
				case 3: return 100;
			}
			return 0;
		}
		public static int coinReceived(int level) {
			switch (level) {
				case 1: return 20;
				case 2: return 30;
				case 3: return 40;
			}
			return 0;
		}

		public static double DmgLv1(int towerTypes) {
			switch (towerTypes) {
				case ICE_TOWER: return 20;
				case FIRE_TOWER: return 30;
				case LIGHT_TOWER: return 2;
				case SUMMON_TOWER: return 40;
			}
			return 0;
		}

		public static double DmgLv2(int towerTypes) {
			switch (towerTypes) {
				case ICE_TOWER: return 30;
				case FIRE_TOWER: return 30;
				case LIGHT_TOWER: return 20;
				case BUFF_TOWER: return 0;
				case SUMMON_TOWER: return 80;
			}
			return 0;
		}

		public static double DmgLv3(int towerTypes) {
			switch (towerTypes) {
				case ICE_TOWER: return 40;
				case FIRE_TOWER: return 45;
				case LIGHT_TOWER: return 30;
				case BUFF_TOWER: return 0;
				case SUMMON_TOWER: return 120;
			}
			return 0;
		}



		public static int RangeLv1(int towerTypes) {
			switch (towerTypes) {
				case ICE_TOWER: return 32;
				case FIRE_TOWER: return 64;
				case LIGHT_TOWER: return 100;
				case BUFF_TOWER: return 64;
				case SUMMON_TOWER: return 0;
			}
			return 0;
		}

		public static int RangeLv2(int towerTypes) {
			switch (towerTypes) {
				case ICE_TOWER: return 64;
				case FIRE_TOWER: return 96;
				case LIGHT_TOWER: return 120;
				case BUFF_TOWER: return 96;
				case SUMMON_TOWER: return 0;
			}
			return 0;
		}

		public static int RangeLv3(int towerTypes) {
			switch (towerTypes) {
				case ICE_TOWER: return 96;
				case FIRE_TOWER: return 128;
				case LIGHT_TOWER: return 180;
				case BUFF_TOWER: return 128;
				case SUMMON_TOWER: return 0;
			}
			return 0;
		}

		public static double CDLv1(int towerTypes) {
			switch (towerTypes) {
				case ICE_TOWER: return 0.7;
				case FIRE_TOWER: return 1.2;
				case LIGHT_TOWER: return 0.1;
				case BUFF_TOWER: return 15;
				case SUMMON_TOWER: return 25;
			}
			return 0;
		}

		public static double CdLv2(int towerTypes) {
			switch (towerTypes) {
				case ICE_TOWER: return 2;
				case FIRE_TOWER: return 1;
				case LIGHT_TOWER: return 0.1;
				case BUFF_TOWER: return 0;
				case SUMMON_TOWER: return 3;
			}
			return 0;
		}

		public static double CdLv3(int towerTypes) {
			switch (towerTypes) {
				case ICE_TOWER: return 1;
				case FIRE_TOWER: return 1;
				case LIGHT_TOWER: return 0.1;
				case BUFF_TOWER: return 0;
				case SUMMON_TOWER: return 2;
			}
			return 0;
		}


		public static final int FIRSTE = 1;
		public static final int LASTE = 2;
		public static final int CLOSET = 3;
		public static final int STRONGEST = 4;
		public static final int RANDOM = 5;




	}

}
