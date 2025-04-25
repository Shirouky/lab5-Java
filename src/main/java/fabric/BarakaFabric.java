package fabric;


import character.Baraka;
import character.Character;

public class BarakaFabric implements EnemyFabricInterface {

    @Override
    public Character create() {
        return new Baraka(1, 100, 12);
    }
}
