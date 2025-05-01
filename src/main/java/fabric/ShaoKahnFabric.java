package fabric;

import character.ShaoKahn;
import character.Enemy;

public class ShaoKahnFabric implements EnemyFabricInterface {

    @Override
    public Enemy create() {
        return new ShaoKahn(3, 100, 30);
    }
}
