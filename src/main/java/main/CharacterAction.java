/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import character.*;
import fabric.EnemyFabric;
import character.Human;
import objects.Items;
import character.Player;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class CharacterAction {

    private final int[] experience_for_next_level = {40, 90, 180, 260, 410, 1000};

    private final int[][] kind_fight = {{1, 0}, {1, 1, 0}, {0, 1, 0}, {1, 1, 1, 1}};

    private final Player[] enemies = new Player[6];

    EnemyFabric fabric = new EnemyFabric();

    private Player enemy = null;

    public void setEnemies() {
        for (int i = 0; i < 6; i++) {
            enemies[i] = fabric.create(i);
        }
    }

    public Player[] getEnemies() {
        return this.enemies;
    }

    public Player chooseEnemy(JLabel label, JLabel label2, JLabel text, JLabel label3) {
        int i = (int) (Math.random() * 4);
        ImageIcon icon1 = null;
        enemy = enemies[i];
        switch (i) {
            case 0:
                icon1 = new ImageIcon("Baraka.jpg");
                label2.setText("Baraka (танк)");
                break;
            case 1:
                icon1 = new ImageIcon("Sub-Zero.jpg");
                label2.setText("Sub-Zero (маг)");
                break;
            case 2:
                icon1 = new ImageIcon("Liu Kang.jpg");
                label2.setText("Liu Kang (боец)");
                break;
            case 3:
                icon1 = new ImageIcon("Sonya Blade.jpg");
                label2.setText("Sonya Blade (солдат)");
                break;
        }
        label.setIcon(icon1);
        text.setText(Integer.toString(enemy.getDamage()));
        label3.setText(enemy.getHealth() + "/" + enemy.getMaxHealth());
        return enemy;
    }

    public Player chooseBoss(JLabel label, JLabel label2, JLabel text, JLabel label3, int i) {
        ImageIcon icon1 = new ImageIcon("Shao Kahn.png");
        label2.setText("Shao Kahn (босс)");
        switch (i) {
            case 2:
                enemy = enemies[4];
                break;
            case 4:
                enemy = enemies[5];
                break;
        }
        label.setIcon(icon1);
        text.setText(Integer.toString(enemy.getDamage()));
        label3.setText(enemy.getHealth() + "/" + enemy.getMaxHealth());
        return enemy;
    }

    public int[] enemyBehavior(int k1, int k2, int k3, double i) {
        int[] arr = null;
        if (i < k1 * 0.01) {
            arr = kind_fight[0];
        } else if (i < (k1 + k2) * 0.01) {
            arr = kind_fight[1];
        } else if (i < (k1 + k2 + k3) * 0.01) {
            arr = kind_fight[2];
        } else if (i < 1) {
            arr = kind_fight[3];
        }
        return arr;
    }

    public int[] chooseBehavior(Player enemy, CharacterAction action) {
        int[] arr = null;
        double i = Math.random();
        if (enemy instanceof Baraka) {
            arr = action.enemyBehavior(15, 15, 60, i);
        }
        if (enemy instanceof SubZero) {
            arr = action.enemyBehavior(25, 25, 0, i);
        }
        if (enemy instanceof LiuKang) {
            arr = action.enemyBehavior(13, 13, 10, i);
        }
        if (enemy instanceof SonyaBlade) {
            arr = action.enemyBehavior(25, 25, 50, i);
        }
        if (enemy instanceof ShaoKahn) {
            arr = action.enemyBehavior(10, 45, 0, i);
        }
        return arr;
    }

    public void setHP(Player player, JProgressBar progress) {
        progress.setValue(Math.max(player.getHealth(), 0));
    }

    public void addPoints(Human human, Player[] enemies) {
        switch (human.getLevel()) {
            case 0:
                human.setExperience(20);
                human.setPoints(25 + human.getHealth() / 4);
                break;
            case 1:
                human.setExperience(25);
                human.setPoints(30 + human.getHealth() / 4);
                break;
            case 2:
                human.setExperience(30);
                human.setPoints(35 + human.getHealth() / 4);
                break;
            case 3:
                human.setExperience(40);
                human.setPoints(45 + human.getHealth() / 4);
                break;
            case 4:
                human.setExperience(50);
                human.setPoints(55 + human.getHealth() / 4);
                break;
        }
        for (int i = 0; i < 5; i++) {
            if (experience_for_next_level[i] == human.getExperience()) {
                human.setLevel();
                human.setNextExperience(experience_for_next_level[i + 1]);
                newHealthHuman(human);
                for (int j = 0; j < 4; j++) {
                    newHealthEnemy(enemies[j], human);
                }
            }
        }
    }

    public void addPointsBoss(Human human, Player[] enemies) {
        switch (human.getLevel()) {
            case 2:
                human.setExperience(30);
                human.setPoints(45 + human.getHealth() / 2);
                break;
            case 4:
                human.setExperience(50);
                human.setPoints(65 + human.getHealth() / 2);
                break;
        }
        for (int i = 0; i < 5; i++) {
            if (experience_for_next_level[i] == human.getExperience()) {
                human.setLevel();
                human.setNextExperience(experience_for_next_level[i + 1]);
                newHealthHuman(human);
                for (int j = 0; j < 4; j++) {
                    newHealthEnemy(enemies[j], human);
                }
            }
        }
    }

    public void addItems(int k1, int k2, int k3, Items[] items) {
        double i = Math.random();
        if (i < k1 * 0.01) {
            items[0].setCount(1);
        } else if (i < (k1 + k2) * 0.01) {
            items[1].setCount(1);
        } else if (i < (k1 + k2 + k3) * 0.01) {
            items[2].setCount(1);
        }
    }

    public void newHealthHuman(Human human) {
        int hp = 0;
        int damage = switch (human.getLevel()) {
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
        human.setMaxHealth(hp);
        human.setDamage(damage);
    }

    public void newHealthEnemy(Player enemy, Human human) {
        int hp = 0;
        int damage = switch (human.getLevel()) {
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
        enemy.setMaxHealth(enemy.getMaxHealth() * hp / 100);
        enemy.setDamage(enemy.getDamage() * damage / 100);
        enemy.setLevel();
    }

    public void useItem(Player human, Items[] items, String name, JDialog dialog, JDialog dialog1) {
        switch (name) {
            case "jRadioButton1":
                if (items[0].getCount() > 0) {
                    human.setHealth((int) (human.getMaxHealth() * 0.25));
                    items[0].setCount(-1);
                } else {
                    dialog.setVisible(true);
                    dialog.setBounds(300, 200, 400, 300);
                }
                break;
            case "jRadioButton2":
                if (items[1].getCount() > 0) {
                    human.setHealth((int) (human.getMaxHealth() * 0.5));
                    items[1].setCount(-1);
                } else {
                    dialog.setVisible(true);
                    dialog.setBounds(300, 200, 400, 300);
                }
                break;
            case "jRadioButton3":
                dialog.setVisible(true);
                dialog.setBounds(300, 200, 400, 300);
                break;
        }

        if (!dialog.isVisible()) {
            dialog1.dispose();
        }
    }
}
