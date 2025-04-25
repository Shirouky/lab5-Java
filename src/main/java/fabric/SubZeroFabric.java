package fabric;


import character.SubZero;
import character.Character;

public class SubZeroFabric implements EnemyFabricInterface {

    @Override
    public Character create() {
        return new SubZero(1, 60, 16);
    }

}
