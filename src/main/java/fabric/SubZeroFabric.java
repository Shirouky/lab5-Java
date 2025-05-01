package fabric;


import character.SubZero;
import character.Enemy;

public class SubZeroFabric implements EnemyFabricInterface {
    @Override
    public Enemy create() {
        return new SubZero(1, 60, 16);
    }

}
