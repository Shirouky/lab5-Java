package character;

import java.util.Random;

import static character.Action.*;

/**
 * SonyaBlade - это класс солдата
 * <p>
 * Для более детального понимания посмотрите родительские классы {@link character.Enemy} и {@link character.Character}.
 * <p>
 *     Особенностей нет
 * </p>
 * @author Деребас Любовь
 */
public class SonyaBlade extends Enemy {
    public SonyaBlade(int level, int health, int damage) {
        super(level, health, damage);
        this.name = "Sonya Blade";
        this.image = "Sonya Blade.jpg";
        this.type = "солдат";
    }

    @Override
    public void behave(int playerActions) {
        Random random = new Random();
        double randomN = random.nextDouble() * 100;

        if (randomN < 50 + playerActions) {
            action = ATTACK;
        } else {
            action = DEFEND;
        }
    }
}
