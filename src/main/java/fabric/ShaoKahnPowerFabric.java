package fabric;

import character.ShaoKahn;
import character.Character;

public class ShaoKahnPowerFabric implements EnemyFabricInterface {

    @Override
    public Character create() {
        return new ShaoKahn(3, 145, 44);
    }
}
