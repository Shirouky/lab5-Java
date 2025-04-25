/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import character.*;
import fabric.EnemyFabric;
import character.Player;
import objects.Item;
import character.Character;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import java.util.Arrays;

public class CharacterAction {
//
//    private final int[] nextLevelExperience = {40, 90, 180, 260, 410, 1000};
//    //1 - attack, 0 - defend
//    private final Action[][] enemyBehaviour = {{Action.ATTACK, Action.DEFEND}, {Action.ATTACK, Action.ATTACK, Action.DEFEND}, {Action.DEFEND, Action.ATTACK, Action.DEFEND}, {Action.ATTACK, Action.ATTACK, Action.ATTACK, Action.ATTACK}};
//
//    private final Character[] enemies = new Character[6];
//
//    EnemyFabric fabric = new EnemyFabric();
//
//    private Character enemy = null;
//
//    public void setEnemies() {
//        for (int i = 0; i < 6; i++) {
//            enemies[i] = fabric.create(i);
//        }
//    }
//
//    public Character[] getEnemies() {
//        return this.enemies;
//    }
//
//
////    public Character chooseBoss(JLabel label, JLabel label2, JLabel text, JLabel label3, int i) {
////        ImageIcon icon1 = new ImageIcon("Shao Kahn.png");
////        label2.setText("Shao Kahn (босс)");
////        switch (i) {
////            case 2:
////                enemy = enemies[4];
////                break;
////            case 4:
////                enemy = enemies[5];
////                break;
////        }
////        label.setIcon(icon1);
////        text.setText(Integer.toString(enemy.getDamage()));
////        label3.setText(enemy.getHealth() + "/" + enemy.getMaxHealth());
////        return enemy;
////    }
//
//    public Action[] enemyBehavior(int k1, int k2, int k3, double i) {
//        Action[] arr = null;
//        if (i < k1 * 0.01) {
//            arr = enemyBehaviour[0];
//        } else if (i < (k1 + k2) * 0.01) {
//            arr = enemyBehaviour[1];
//        } else if (i < (k1 + k2 + k3) * 0.01) {
//            arr = enemyBehaviour[2];
//        } else if (i < 1) {
//            arr = enemyBehaviour[3];
//        }
//        return arr;
//    }
//
//    public Action[] chooseBehavior(Character enemy, CharacterAction action) {
//        Action[] arr = null;
//        double i = Math.random();
//        if (enemy instanceof Baraka) {
//            arr = action.enemyBehavior(15, 15, 60, i);
//        }
//        if (enemy instanceof SubZero) {
//            arr = action.enemyBehavior(25, 25, 0, i);
//        }
//        if (enemy instanceof LiuKang) {
//            arr = action.enemyBehavior(13, 13, 10, i);
//        }
//        if (enemy instanceof SonyaBlade) {
//            arr = action.enemyBehavior(25, 25, 50, i);
//        }
//        if (enemy instanceof ShaoKahn) {
//            arr = action.enemyBehavior(10, 45, 0, i);
//        }
//        return arr;
//    }
//
//    public void addPoints(Player player) {
//        switch (player.getLevel()) {
//            case 0:
//                player.addExperience(20);
//                player.setPoints(25 + player.getHealth() / 4);
//                break;
//            case 1:
//                player.addExperience(25);
//                player.setPoints(30 + player.getHealth() / 4);
//                break;
//            case 2:
//                player.addExperience(30);
//                player.setPoints(35 + player.getHealth() / 4);
//                break;
//            case 3:
//                player.addExperience(40);
//                player.setPoints(45 + player.getHealth() / 4);
//                break;
//            case 4:
//                player.addExperience(50);
//                player.setPoints(55 + player.getHealth() / 4);
//                break;
//        }
//        upgrade(player);
//    }
//
//    public void upgrade(Player player) {
//        for (int i = 0; i < 5; i++) {
//            if (nextLevelExperience[i] == player.getExperience()) {
//                player.levelUP();
//                player.setNextExperience(nextLevelExperience[i + 1]);
//                player.newHealth();
//                for (int j = 0; j < 4; j++) {
//                    enemy.newHealth(player);
//                }
//            }
//        }
//    }
//
//    public void addPointsBoss(Player player) {
//        switch (player.getLevel()) {
//            case 2:
//                player.addExperience(30);
//                player.setPoints(45 + player.getHealth() / 2);
//                break;
//            case 4:
//                player.addExperience(50);
//                player.setPoints(65 + player.getHealth() / 2);
//                break;
//        }
//        upgrade(player);
//    }
//
//    public void addItems(int k1, int k2, int k3, Item[] items) {
//        double i = Math.random();
//        if (i < k1 * 0.01) {
//            items[0].increaseAmount();
//        } else if (i < (k1 + k2) * 0.01) {
//            items[1].increaseAmount();
//        } else if (i < (k1 + k2 + k3) * 0.01) {
//            items[2].increaseAmount();
//        }
//    }
//
//    public void useItem(Character human, Item[] items, String name, JDialog dialog, JDialog dialog1) {
//        dialog.setBounds(300, 200, 400, 300);
//        switch (name) {
//            case "jRadioButton1":
//                if (items[0].getAmount() > 0) {
//                    human.heal((int) (human.getMaxHealth() * 0.25));
//                    items[0].decreaseAmount();
//                } else {
//                    dialog.setVisible(true);
//                }
//                break;
//            case "jRadioButton2":
//                if (items[1].getAmount() > 0) {
//                    human.heal((int) (human.getMaxHealth() * 0.5));
//                    items[1].decreaseAmount();
//                } else {
//                    dialog.setVisible(true);
//                }
//                break;
//            case "jRadioButton3":
//                dialog.setVisible(true);
//                break;
//        }
//
//        if (!dialog.isVisible()) {
//            dialog1.dispose();
//        }
//    }
}
