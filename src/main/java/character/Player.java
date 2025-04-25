/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package character;

/**
 * @author Мария
 */
public class Player extends Character {


    private int points;
    private int experience;
    private int win;
    private int nextExperience;


    public Player(int level, int health, int damage) {
        super(level, health, damage);
        this.points = 0;
        this.experience = 0;
        this.nextExperience = 40;
        this.win = 0;
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

    public int getVictories() {
        return this.win;
    }

    public void setPoints(int points) {
        this.points += points;
    }

    public void addExperience(int experience) {
        this.experience += experience;
    }

    public void setNextExperience(int experience) {
        this.nextExperience = experience;
    }

    public void addVictory() {
        this.win++;
    }

    public void newHealth() {
        int hp = 0;
        int damage = switch (level) {
            case 1 -> {
                hp = 25;
                yield 3;
            }
            case 2 -> {
                hp = 30;
                yield 3;
            }
            case 3 -> {
                hp = 30;
                yield 4;
            }
            case 4 -> {
                hp = 40;
                yield 6;
            }
            default -> 0;
        };
        setMaxHealth(hp);
        setDamage(damage);
    }
}
