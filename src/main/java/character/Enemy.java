package character;

/**
 * Enemy - это класс врага в игре
 * <p>
 * Для более детального понимания посмотрите родительский класс {@link character.Character}
 *
 * @author Деребас Любовь
 */
public class Enemy extends Character{
    public Enemy(int level, int health, int damage) {
        super(level, health, damage);
    }

    /**
     * <p>Метод, выбирающий действие врага</p>
     * @param playerActions накопленная статистика ходов игрока
     * @since 1.0
     */
    public void behave(int playerActions) {
        action = Action.ATTACK;
    }
}
