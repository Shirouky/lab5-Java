package character;

import java.util.Random;

import static character.Action.ATTACK;
import static character.Action.DEFEND;

/**
 * LiuKang - это класс бойца
 * <p>
 * Для более детального понимания посмотрите родительские классы {@link character.Enemy} и {@link character.Character}.
 * <p>
 *     Особенность врага - высокая атака и шанс атаковать
 * </p>
 * @author Деребас Любовь
 */
public class LiuKang extends Enemy {

    public LiuKang(int level, int health, int damage) {
        super(level, health, damage);
        this.name = "Liu Kang";
        this.image = "images/LiuKang.png";
        this.type = "боец";
    }

    @Override
    public void behave(int playerActions) {
        Random random = new Random();
        double randomN = random.nextDouble() * 100;

        if (randomN < 80 + playerActions) {
            action = ATTACK;
        } else {
            action = DEFEND;
        }
    }
}
