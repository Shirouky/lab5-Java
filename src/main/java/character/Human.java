/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package character;

/**
 * @author Мария
 */
public class Human extends Player {


    private int points;
    private int experience;
    private int win;
    private int nextExperience;


    public Human(int level, int health, int damage) {
        super(level, health, damage);
        this.points = 0;
        this.experience = 0;
        this.nextExperience = 40;
        this.win = 0;
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

    public int getWin() {
        return this.win;
    }

    public void setPoints(int points) {
        this.points += points;
    }

    public void setExperience(int experience) {
        this.experience += experience;
    }

    public void setNextExperience(int experience) {
        this.nextExperience = experience;
    }

    public void setWin() {
        this.win++;
    }

    @Override
    public String getName() {
        return "You";
    }
}
