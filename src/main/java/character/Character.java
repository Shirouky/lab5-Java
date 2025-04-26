package character;

public class Character {

    protected int level;
    protected int health;
    protected int maxHealth;
    protected int damage;
    protected Action action;
    protected String image;
    protected String name;
    protected String type = "игрок";

    public Character(int level, int health, int damage) {
        this.level = level;
        this.health = health;
        this.damage = damage;
        this.action = Action.ATTACK;
        this.maxHealth = health;
    }

    public void takeDamage(int damage) {
        if (this.health > damage) this.health -= damage;
        else this.health = 0;
    }

    public void heal(int heal) {
        if (this.maxHealth < this.health + heal) this.health = maxHealth;
        else this.health += heal;
    }

    public void setNewHealth(int health) {
        this.health = health;
    }

    public void setDamage(int damage) {
        this.damage += damage;
    }

    public void setAction(Action action) {
        this.action = action;
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

    public Action getAction() {
        return this.action;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public void reset() {
        health = maxHealth;
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

    public void levelUP(Player player) {
        level++;
        int hp = 0;
        int damage = switch (player.getLevel()) {
            case 1 -> {
                hp = 32;
                yield 25;
            }
            case 2 -> {
                hp = 30;
                yield 20;
            }
            case 3 -> {
                hp = 23;
                yield 24;
            }
            case 4 -> {
                hp = 25;
                yield 26;
            }
            default -> 0;
        };
        setMaxHealth(getMaxHealth() * hp / 100);
        setDamage(getDamage() * damage / 100);
        health = maxHealth;
    }
}
