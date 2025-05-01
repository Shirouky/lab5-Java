package fabric;


import character.SonyaBlade;
import character.Enemy;

public class SonyaBladeFabric implements EnemyFabricInterface {

    @Override
    public Enemy create() {
        return new SonyaBlade(1, 80, 16);
    }

}
