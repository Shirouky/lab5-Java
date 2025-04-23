package fabric;


import character.LiuKang;
import character.Player;

public class LiuKangFabric implements EnemyFabricInterface {

    @Override
    public Player create() {
        return new LiuKang(1, 70, 20);
    }
}
