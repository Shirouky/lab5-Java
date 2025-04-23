package fabric;


import character.SubZero;
import character.Player;

public class SubZeroFabric implements EnemyFabricInterface {

    @Override
    public Player create() {
        return new SubZero(1, 60, 16);
    }

}
