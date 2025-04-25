package fabric;

import character.ShaoKahn;
import character.Character;

public class ShaoKahnFabric implements EnemyFabricInterface {

    @Override
    public Character create() {
        return new ShaoKahn(3, 100, 30);
    }
}
