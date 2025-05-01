package character;

import java.util.Random;

import static character.Action.*;

/**
 * ShaoKahn - это класс босса
 * <p>
 * Для более детального понимания посмотрите родительские классы {@link character.Enemy} и {@link character.Character}.
 * <p>
 *     Особенность босса - регенерация
 * </p>
 * @author Деребас Любовь
 */
public class ShaoKahn extends Enemy {
    public ShaoKahn(int level, int health, int damage) {
        super(level, health, damage);
        this.name = "Shao Kahn";
        this.image = "Shao Kahn.jpg";
        this.type = "босс";
    }

    @Override
    public void behave(int playerActions) {
        Random random = new Random();
        double randomN = random.nextDouble() * 100;

        if (randomN < 40 + playerActions) {
            action = ATTACK;
        } else if (randomN < 80){
            action = DEFEND;
        } else {
            action = REGENERATE;
        }
    }
}
