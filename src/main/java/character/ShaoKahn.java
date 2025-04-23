package character;

public class ShaoKahn extends Player {

    public ShaoKahn(int level, int health, int damage) {
        super(level, health, damage);
    }

    @Override
    public String getName() {
        return "Shao Kahn";
    }
}
