package character;

import java.util.Random;

import static character.Action.*;

/**
 * Baraka - это класс танка
 * <p>
 * Для более детального понимания посмотрите родительские классы {@link character.Enemy} и {@link character.Character}.
 * <p>
 *     Особенность врага - высокая защита
 * </p>
 * @author Деребас Любовь
 */
public class Baraka extends Enemy {
    public Baraka(int level, int health, int damage){
        super (level, health, damage);
        this.name = "Baraka";
        this.image = "Baraka.jpg";
        this.type = "танк";
    }

    @Override
    public void behave(int playerActions) {
        Random random = new Random();
        double randomN = random.nextDouble() * 100;

        if (randomN < 30 + playerActions) {
            action = ATTACK;
        } else {
            action = DEFEND;
        }
    }
}
