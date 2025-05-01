package character;

import java.util.Random;

import static character.Action.*;

/**
 * Subzero - это класс мага
 * <p>
 * Для более детального понимания посмотрите родительские классы {@link character.Enemy} и {@link character.Character}.
 * <p>
 *     Особенность врага - накладывать уязвимость на игрока
 * </p>
 * @author Деребас Любовь
 */
public class SubZero extends Enemy {
    public SubZero(int level, int health, int damage) {
        super(level, health, damage);
        this.name = "SubZero";
        this.image = "SubZero.jpg";
        this.type = "маг";
    }

    @Override
    public void behave(int playerActions) {
        Random random = new Random();
        double randomN = random.nextDouble() * 100;

        if (randomN < 30 + playerActions) {
            action = ATTACK;
        } else if (randomN < 60){
            action = DEFEND;
        } else {
            action = WEAKEN;
        }
    }
}
