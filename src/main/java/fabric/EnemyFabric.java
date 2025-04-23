package fabric;

import character.Player;

public class EnemyFabric {

    public Player create(int enemyIndex) {
        EnemyFabricInterface fabric = switch (enemyIndex) {
            case 0 -> new BarakaFabric();
            case 1 -> new SubZeroFabric();
            case 2 -> new LiuKangFabric();
            case 3 -> new SonyaBladeFabric();
            case 4 -> new ShaoKahnFabric();
            case 5 -> new ShaoKahnPowerFabric();
            default -> null;
        };

        assert fabric != null;
        return fabric.create();
    }
}
