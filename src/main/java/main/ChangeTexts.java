/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import character.Human;
import objects.Items;
import character.Player;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;

public class ChangeTexts {
    public void newRoundTexts(Player human, Player enemy, JLabel label, JLabel label2, JLabel label3,
                              JLabel label4, JLabel label5, JLabel label6, JLabel label7, JLabel label8, JLabel label9,
                              int turn, Items[] items, JRadioButton rb1, JRadioButton rb2, JRadioButton rb3) {
        label.setText(Integer.toString(((Human) human).getPoints()));
        label2.setText(((Human) human).getExperience() + "/" + ((Human) human).getNextExperience());
        label3.setText(human.getLevel() + " level");
        label4.setText(enemy.getLevel() + " level");
        label5.setText(human.getMaxHealth() + "/" + human.getMaxHealth());
        label6.setText(enemy.getMaxHealth() + "/" + enemy.getMaxHealth());
        label7.setText(Integer.toString(human.getDamage()));
        if (turn % 2 == 1) {
            label8.setText("Your turn");
        } else {
            label8.setText(enemy.getName() + "'s turn");
        }

        bagText(items, rb1, rb2, rb3);
        label9.setText("");
    }

    public void roundTexts(Player human, Player enemy, JLabel label, JLabel label2, int i, JLabel label3) {
        if (enemy.getHealth() >= 0) {
            label.setText(enemy.getHealth() + "/" + enemy.getMaxHealth());
        } else {
            label.setText("0/" + enemy.getMaxHealth());
        }
        if (human.getHealth() >= 0) {
            label2.setText(human.getHealth() + "/" + human.getMaxHealth());
        } else {
            label2.setText("0/" + human.getMaxHealth());
        }
        if (i % 2 == 1) {
            label3.setText("Your turn");
        } else {
            label3.setText(enemy.getName() + "'s turn");
        }
    }

    public void endGameText(Human human, JLabel label) {
        if (human.getWin() == 12) {
            label.setText("Победа на вашей стороне");
        } else {
            label.setText("Победа не на вашей стороне");
        }
    }

    public void bagText(Items[] items, JRadioButton rb1, JRadioButton rb2, JRadioButton rb3) {
        rb1.setText(items[0].getName() + ", " + items[0].getCount() + " шт");
        rb2.setText(items[1].getName() + ", " + items[1].getCount() + " шт");
        rb3.setText(items[2].getName() + ", " + items[2].getCount() + " шт");
    }

}
