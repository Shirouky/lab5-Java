package character;

public class SonyaBlade extends Player {

    public SonyaBlade(int level, int health, int damage) {
        super(level, health, damage);
    }

    @Override
    public String getName() {
        return "Sonya Blade";
    }
}
