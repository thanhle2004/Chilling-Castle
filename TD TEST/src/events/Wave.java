package events;

public class Wave {

    public Wave() {
    }

    public long[] WaveInTurn(int spawnX, int spawnY, int EnemyType,int numberPerTurn, long startTime, long nextSpawnTime, long timeSpawnInterval) {
        return new long[] {spawnX, spawnY, EnemyType,numberPerTurn, startTime, nextSpawnTime, timeSpawnInterval};
    }
}
