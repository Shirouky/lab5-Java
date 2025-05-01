package fabric;


import character.Baraka;
import character.Enemy;

public class BarakaFabric implements EnemyFabricInterface {

    @Override
    public Enemy create() {
        return new Baraka(1, 100, 12);
    }
}
