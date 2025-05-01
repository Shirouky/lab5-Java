package fabric;

import character.ShaoKahn;
import character.Enemy;

public class ShaoKahnPowerFabric implements EnemyFabricInterface {

    @Override
    public Enemy create() {
        return new ShaoKahn(3, 145, 44);
    }
}
