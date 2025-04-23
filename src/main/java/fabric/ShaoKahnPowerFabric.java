package fabric;

import character.ShaoKahn;
import character.Player;

public class ShaoKahnPowerFabric implements EnemyFabricInterface {

    @Override
    public Player create() {
        return new ShaoKahn(3, 145, 44);
    }
}
