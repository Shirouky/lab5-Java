/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package character;

public class SubZero extends Player {

    public SubZero(int level, int health, int damage) {
        super(level, health, damage);
    }

    @Override
    public String getName() {
        return "Sub-Zero";
    }
}
