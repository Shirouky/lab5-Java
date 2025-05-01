package character;

import static character.Action.ATTACK;

/**
 * Character - это основной класс персонажа в игре (и игрока, и врага)
 * @author Деребас Любовь
 */
public class Character {
    protected int level;
    protected int health;
    protected int maxHealth;
    protected int damage;
    protected Action action;
    protected String image;
    protected String name;
    protected String type = "игрок";
    protected int vulnerable;
    private double damageDebuf = 1;
    private double incomeDebuf = 1;

    public Character(int level, int health, int damage) {
        this.level = level;
        this.health = health;
        this.damage = damage;
        this.action = ATTACK;
        this.maxHealth = health;
        vulnerable = 0;
    }

    public void levelUP() {
        level++;
    }

    /**
     * <p>Метод, повышающий здоровье персонажа при повышении уровня</p>
     * @param player текущий игрок
     * @since 1.0
     */
    public void levelHP(Player player) {
        int hp = switch (player.getLevel()) {
            case 1 -> 32;
            case 2 -> 30;
            case 3 -> 23;
            case 4 -> 25;
            default -> 31;
        };
        setMaxHealth(getMaxHealth() * hp / 100);
        health = maxHealth;
    }

    /**
     * <p>Метод, повышающий урон персонажа при повышении уровня</p>
     * @param player текущий игрок
     * @since 1.0
     */
    public void levelDMG(Player player) {
        int damage = switch (player.getLevel()) {
            case 1 -> 25;
            case 2 -> 20;
            case 3 -> 24;
            case 4 -> 26;
            default -> 30;
        };
        setDamage(getDamage() * damage / 100);
        health = maxHealth;
    }

    /**
     * <p>Метод подсчета урона персонажа от попадания</p>
     * @param damage урон соперника
     * @since 1.0
     */
    public void takeDamage(int damage) {
        damage = (int) (damage * incomeDebuf);
        if (this.health > damage) this.health -= damage;
        else this.health = 0;
    }

    /**
     * <p>Метод, лечаших персонажей</p>
     * @param heal количество лечения
     * @since 1.0
     */
    public void heal(int heal) {
        if (this.maxHealth < this.health + heal) this.health = maxHealth;
        else this.health += heal;
    }

    /**
     * <p>Метод, накладывающий на персонажа ослабление на level + 1 ходов</p>
     * @since 1.0
     */
    public void setVulnerable() {
        vulnerable = level + 1;
        damageDebuf = damageDebuf * 0.5;
        incomeDebuf = 1.25;
    }

    /**
     * <p>Метод, обрабатывающий действие персонажа </p>
     * @param action действие персонажа в текущем ходе
     * @since 1.0
     */
    public void setAction(Action action) {
        this.action = action;
        if (vulnerable == 1) {
            vulnerable = 0;
            damageDebuf = damageDebuf / 0.5;
            incomeDebuf = 1;
        } else if (vulnerable > 0) vulnerable--;
    }

    /**
     * <p>Метод, сбрасывающий ослабление с персонажа</p>
     * @since 1.0
     */
    public void resetVulnerable() {
        vulnerable = 0;
        damageDebuf = damageDebuf / 0.5;
        incomeDebuf = 1;
    }

    /**
     * <p>Метод, повышающий урон персонажа после попадания по ослабленному противнику</p>
     * @since 1.0
     */
    public void buff() {
        damageDebuf *= 1.15;
    }

    public boolean isVulnerable() {
        return vulnerable > 0;
    }

    /**
     * <p>Метод, сбрасывающий текущее HP и действие</p>
     * @since 1.0
     */
    public void reset() {
        health = maxHealth;
        action = ATTACK;
    }

    public void setMaxHealth(int health) {
        this.maxHealth += health;
    }

    public int getLevel() {
        return this.level;
    }

    public int getHealth() {
        return this.health;
    }

    public int getDamage() {
        return (int) (this.damage * damageDebuf);
    }

    public Action getAction() {
        return this.action;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public String getImage() {
        return image;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setDamage(int damage) {
        this.damage += damage;
    }
}
