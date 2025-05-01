package character;

import static character.Action.*;

/**
 * Player - это класс игрока в игре
 * <p>
 * Для более детального понимания посмотрите родительский класс {@link character.Character}
 *
 * @author Деребас Любовь
 */

public class Player extends Character {
    private int points;
    private int experience;
    private int nextExperience;
    private int actions;


    public Player(int level, int health, int damage) {
        super(level, health, damage);
        this.points = 0;
        this.experience = 0;
        this.nextExperience = 40;
        this.name = "You";
        this.image = "images/Dipper.png";
    }

    /**
     * <p>Переопредление метода для сохранения статистики действий игрока и сохранения действия</p>
     *
     * @param action действие игрока в текущем ходе
     * @since 1.0
     */
    @Override
    public void setAction(Action action) {
        super.setAction(action);

        if (action == ATTACK) actions -= 5;
        else if (action == DEFEND) actions += 5;
        else if (action == WEAKEN) actions -= 3;

        actions = Math.min(actions, 20);
        actions = Math.max(actions, -20);
    }

    public int getActions() {
        return actions;
    }

    public int getPoints() {
        return this.points;
    }

    public int getExperience() {
        return this.experience;
    }

    public void setNextExperience(int experience) {
        this.nextExperience = experience;
    }

    public int getNextExperience() {
        return this.nextExperience;
    }

    /**
     * <p>Метод подсчета очков игрока за победу над обычным врагом
     * </p>
     *
     * @since 1.0
     */
    public void addPoints() {
        switch (level) {
            case 0:
                experience += 20;
                points += 25 + health / 4;
                break;
            case 1:
                experience += 25;
                points += 30 + health / 4;
                break;
            case 2:
                experience += 30;
                points += 35 + health / 4;
                break;
            case 3:
                experience += 40;
                points += 45 + health / 4;
                break;
            case 4:
                experience += 50;
                points += 55 + health / 4;
                break;
        }
    }

    /**
     * <p>Метод подсчета очков игрока за победу над боссом
     * </p>
     *
     * @since 1.0
     */
    public void addPointsBoss() {
        experience += level * 10 + 10;
        points += level * 20 + health / 2;
    }
}
