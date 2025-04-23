package fabric;


import character.Baraka;
import character.Player;

public class BarakaFabric implements EnemyFabricInterface {

    @Override
    public Player create() {
        return new Baraka(1, 100, 12);
    }
}
