package character;

public class Player extends Character {


    private int points;
    private int experience;
    private int nextExperience;


    public Player(int level, int health, int damage) {
        super(level, health, damage);
        this.points = 0;
        this.experience = 0;
        this.nextExperience = 40;
        this.name = "You";
    }


    public int getPoints() {
        return this.points;
    }

    public int getExperience() {
        return this.experience;
    }

    public int getNextExperience() {
        return this.nextExperience;
    }


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

    public void addPointsBoss() {
        experience += level * 10 + 10;
        points += level * 20 + health / 2;
    }

    public void setNextExperience(int experience) {
        this.nextExperience = experience;
    }
}
