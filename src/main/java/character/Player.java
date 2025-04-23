package character;

public class Player {

    private int level;
    private int health;
    private int maxHealth;
    private int damage;
    private int attack;

    public Player(int level, int health, int damage) {
        this.level = level;
        this.health = health;
        this.damage = damage;
        this.attack = 1;
        this.maxHealth = health;
    }

    public void setLevel() {
        this.level++;
    }

    public void setHealth(int health) {
        this.health += health;
    }

    public void setNewHealth(int health) {
        this.health = health;
    }

    public void setDamage(int damage) {
        this.damage += damage;
    }

    public void setAttack(int attack) {
        this.attack = attack;
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
        return this.damage;
    }

    public int getAttack() {
        return this.attack;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public String getName() {
        return "";
    }
}
