package main;

import character.Action;
import character.ShaoKahn;
import character.Player;
import objects.Item;
import character.Character;
import objects.Result;

import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import static character.Action.DEFEND;

public class Fight {

//    Action[] behaviour = {DEFEND};
//    //    int[] experiences = {40, 90, 180, 260, 410};
//    private int turn = 1;
//    int attackNumber = -1;
//
//    public int getTurn() {
//        return turn;
//    }
//
//    public void move(Character character1, Character character2, JLabel statusLabel, JLabel actionLabel) {
//        switch (character1.getAction()) {
//            case ATTACK:
//                switch (character2.getAction()) {
//                    case DEFEND: {
//                        if (character1 instanceof ShaoKahn & Math.random() < 0.15) {
//                            character2.takeDamage((int) (character1.getDamage() * 0.5));
//                            actionLabel.setText("Your block is broken");
//                        } else {
//                            character1.takeDamage((int) (character2.getDamage() * 0.5));
//                            actionLabel.setText(character2.getName() + " counterattacked");
//                        }
//                        break;
//                    }
//                    case ATTACK: {
//                        character2.takeDamage(character1.getDamage());
//                        actionLabel.setText(character1.getName() + " attacked");
//                        break;
//                    }
//                }
//            case DEFEND:
//                switch (character2.getAction()) {
//                    case DEFEND: {
////                        isStunned = Math.random() <= 0.5;
//                        actionLabel.setText("Both defended themselves");
//                        break;
//                    }
//                    case ATTACK: {
//                        actionLabel.setText(character1.getName() + " didn't attacked");
//                        break;
//                    }
//                }
//            case STUN:
//                statusLabel.setText(character1.getName() + " was stunned");
////                character1.setAction(ATTACK);
//                switch (character2.getAction()) {
//                    case DEFEND: {
//                        actionLabel.setText(character2.getName() + " didn't attacked");
//                        break;
//                    }
//                    case ATTACK: {
//                        character1.takeDamage(character2.getDamage());
//                        actionLabel.setText(character2.getName() + " attacked");
//                        break;
//                    }
//                }
//        }
//    }
//
//    public void defend(Player player, Character enemy, JLabel statusLabel, CharacterAction action, JLabel actionLabel, JProgressBar playerProgressBar, JProgressBar enemyProgressBar) {
//        player.setAction(DEFEND);
//        System.out.println(behaviour[0]);
//        if (attackNumber < behaviour.length - 1) {
//            attackNumber++;
//        } else {
//            behaviour = action.chooseBehavior(enemy, action);
//            attackNumber = 0;
//        }
//        enemy.setAction(behaviour[attackNumber]);
//        if (turn % 2 == 1) {
//            move(player, enemy, statusLabel, actionLabel);
//        } else {
//            move(enemy, player, statusLabel, actionLabel);
//        }
//        turn++;
//    }
//
//    public void attack(Player player, Character enemy, JLabel statusLabel, CharacterAction action, JLabel actionLabel, JProgressBar playerProgressBar, JProgressBar enemyProgressBar) {
//        player.setAction(Action.ATTACK);
//        if (attackNumber < behaviour.length - 1) {
//            attackNumber++;
//        } else {
//            behaviour = action.chooseBehavior(enemy, action);
//            attackNumber = 0;
//        }
//        enemy.setAction(behaviour[attackNumber]);
//        if (turn % 2 == 1) {
//            move(player, enemy, statusLabel, actionLabel);
//        } else {
//            move(enemy, player, statusLabel, actionLabel);
//        }
//        turn++;
//    }
//
//    public void hit(Player player, Character enemy, JDialog endRoundDialog, JLabel endRoundLabel, CharacterAction action, JDialog dialog1,
//                    JDialog dialog2, JFrame frame, ArrayList<Result> results,
//                    JLabel victoryLabel, JLabel label5, Item[] items) {
//
//        if (player.getHealth() == 0 | enemy.getHealth() == 0) {
//            if (player.getVictories() == 1) {
////                if (player.getVictories() == 11) {
//                endFinalRound(player, action, results, dialog1, dialog2,
//                        frame, victoryLabel, label5);
//            } else {
//                endRound(player, enemy, endRoundDialog, endRoundLabel, action, items);
//            }
//        }
//    }
//
//    public void endRound(Character character, Character enemy, JDialog endRoundDialog, JLabel endRoundLabel,
//                         CharacterAction action, Item[] items) {
//
//        endRoundDialog.setVisible(true);
//        endRoundDialog.setBounds(300, 150, 700, 600);
//        if (character.getHealth() > 0) {
//            endRoundLabel.setText("You win");
//            ((Player) character).addVictory();
//
//            if (enemy instanceof ShaoKahn) {
//                action.addItems(38, 23, 8, items);
//                action.addPointsBoss(((Player) character));
//            } else {
//                action.addItems(25, 15, 5, items);
//                action.addPoints(((Player) character));
//            }
//        } else {
//            endRoundLabel.setText(enemy.getName() + " win");
//        }
//
//        turn = 1;
//        attackNumber = -1;
//        behaviour = new Action[]{DEFEND};
//    }
//
//    public void endFinalRound(Player player, CharacterAction action,
//                              ArrayList<Result> results, JDialog dialog1, JDialog dialog2, JFrame frame,
//                              JLabel label1, JLabel label2) {
//        String text = "Победа не на вашей стороне";
//        if (player.getHealth() > 0) {
//            player.addVictory();
//            action.addPoints(player);
//            text = "Победа на вашей стороне";
//        }
//        boolean isTop = false;
//        if (results == null) {
//            isTop = true;
//        } else {
//            int place = 0;
//            for (Result result : results) {
//                if (player.getPoints() < result.getPoints()) {
//                    place++;
//                }
//            }
//            if (place < 10) {
//                isTop = true;
//            }
//        }
//        if (isTop) {
//            dialog1.setVisible(true);
//            dialog1.setBounds(150, 150, 600, 500);
//            label1.setText(text);
//        } else {
//            dialog2.setVisible(true);
//            dialog2.setBounds(150, 150, 470, 360);
//            label2.setText(text);
//        }
//        frame.dispose();
//    }

//    public Character newRound(Player player, JLabel label, JProgressBar pr1,
//                              JProgressBar pr2, JLabel label2, JLabel text, JLabel label3, CharacterAction action) {
//
//        Character enemy1 = null;
//        if (player.getVictories() == 6 | player.getVictories() == 11) {
//            enemy1 = action.chooseBoss(label, label2, text, label3, player.getLevel());
//        } else {
//            enemy1 = controller.newEnemy();
//        }
//        pr1.setMaximum(player.getMaxHealth());
//        pr2.setMaximum(enemy1.getMaxHealth());
//        player.setNewHealth(player.getMaxHealth());
//        enemy1.setNewHealth(enemy1.getMaxHealth());
//        return enemy1;
//    }
}
