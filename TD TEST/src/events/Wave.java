package events;

import managers.EnemyManager;

public class Wave {
    private int numInOneWave;
    private EnemyManager enemyManager;
    public Wave(EnemyManager enemyManager) {
        this.enemyManager = enemyManager;
    }

    public int numInOneWave() {
        return numInOneWave;
    }

    public void setNumInOneWave(int numInOneWave) {
        this.numInOneWave = numInOneWave;
    }
}
