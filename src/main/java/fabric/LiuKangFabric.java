package fabric;


import character.LiuKang;
import character.Enemy;

public class LiuKangFabric implements EnemyFabricInterface {

    @Override
    public Enemy create() {
        return new LiuKang(1, 70, 20);
    }
}
