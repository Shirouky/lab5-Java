package fabric;


import character.LiuKang;
import character.Character;

public class LiuKangFabric implements EnemyFabricInterface {

    @Override
    public Character create() {
        return new LiuKang(1, 70, 20);
    }
}
