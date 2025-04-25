/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fabric;


import character.SonyaBlade;
import character.Character;

public class SonyaBladeFabric implements EnemyFabricInterface {

    @Override
    public Character create() {
        return new SonyaBlade(1, 80, 16);
    }

}
