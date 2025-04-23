package fabric;

import character.ShaoKahn;
import character.Player;

public class ShaoKahnFabric implements EnemyFabricInterface {

    @Override
    public Player create() {
        return new ShaoKahn(3, 100, 30);
    }

    public Player createPowerful(){
        return new ShaoKahn(3, 145, 44);
    }
}
