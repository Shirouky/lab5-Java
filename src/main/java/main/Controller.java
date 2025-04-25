package main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Objects;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import character.*;
import character.Action;
import character.Character;
import fabric.EnemyFabric;
import objects.Item;
import objects.Result;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import static character.Action.ATTACK;
import static character.Action.DEFEND;

public class Controller {
    Player player;
    Character enemy;
    Item[] items = new Item[3];
    private final ArrayList<Result> results = new ArrayList<>();
    character.Action[] behaviour = {DEFEND};
    //    int[] experiences = {40, 90, 180, 260, 410};
    private int turn = 1;
    int attackNumber = -1;

    private final Character[] enemies = new Character[6];

    EnemyFabric fabric = new EnemyFabric();

    public int getTurn() {
        return this.turn;
    }

    public void setEnemies() {
        for (int i = 0; i < 6; i++) {
            enemies[i] = fabric.create(i);
        }
    }

    public Character newEnemy() {
        setEnemies();
        if (player != null) {
            if (player.getVictories() == 6 | player.getVictories() == 11) {
                enemy = newBoss(player.getLevel());
            } else {
                enemy = enemies[(int) (Math.random() * 4)];
            }
        } else {
            enemy = enemies[(int) (Math.random() * 4)];
        }
        return enemy;
    }

    public Character newBoss(int playerLevel) {
        switch (playerLevel) {
            case 2:
                enemy = enemies[4];
                break;
            case 4:
                enemy = enemies[5];
                break;
        }
        return enemy;
    }

    public Player newPlayer() {
        if (player == null) player = new Player(0, 80, 16);
        return player;
    }

    public void newItems() {
        items[0] = new Item("Малое зелье", 0);
        items[1] = new Item("Большое зелье", 0);
        items[2] = new Item("Крест возрождения", 10);
    }

    public Item[] getItems() {
        return items;
    }

    public Item getItem(int index) {
        return items[index];
    }

    public void addItems() {

    }


    public boolean resurrect() {
        if (player.getHealth() == 0 & items[2].getAmount() > 0) {
            player.setNewHealth((int) (player.getMaxHealth() * 0.05));
            items[2].decreaseAmount();
            return true;
        }
        return false;
    }

    public ArrayList<Result> endGameTop(String name) {
        results.add(new Result(name, player.getPoints()));
        results.sort(Comparator.comparing(Result::getPoints).reversed());
        writeToExcel();
        return results;
    }

    public void writeToExcel() {
        XSSFWorkbook book = new XSSFWorkbook();
        XSSFSheet sheet = book.createSheet("Результаты ТОП 10");
        XSSFRow r = sheet.createRow(0);
        r.createCell(0).setCellValue("№");
        r.createCell(1).setCellValue("Имя");
        r.createCell(2).setCellValue("Количество баллов");
        for (int i = 0; i < results.size(); i++) {
            if (i < 10) {
                XSSFRow r2 = sheet.createRow(i + 1);
                r2.createCell(0).setCellValue(i + 1);
                r2.createCell(1).setCellValue(results.get(i).getName());
                r2.createCell(2).setCellValue(results.get(i).getPoints());
            }
        }
        File f = new File("Results.xlsx");
        try {
            book.write(new FileOutputStream(f));
            book.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Result> getResults() {
        return this.results;
    }

    public void readFromExcel() {
        try {
            XSSFWorkbook book = new XSSFWorkbook("Results.xlsx");
            XSSFSheet sh = book.getSheetAt(0);
            for (int i = 1; i <= sh.getLastRowNum(); i++) {
                results.add(new Result(sh.getRow(i).getCell(1).getStringCellValue(), (int) sh.getRow(i).getCell(2).getNumericCellValue()));
            }
        } catch (org.apache.poi.openxml4j.exceptions.InvalidOperationException | IOException e) {
            System.out.println("No file");
        }
    }


    public HashMap<String, String> move(Character character1, Character character2) {
        HashMap<String, String> labels = new HashMap();
        switch (character1.getAction()) {
            case ATTACK:
                switch (character2.getAction()) {
                    case DEFEND: {
                        if (character1 instanceof ShaoKahn & Math.random() < 0.15) {
                            character2.takeDamage((int) (character1.getDamage() * 0.5));
                            labels.put("action", "Your block is broken");
                        } else {
                            character1.takeDamage((int) (character2.getDamage() * 0.5));
                            labels.put("action", character2.getName() + " counterattacked");
                        }
                        break;
                    }
                    case ATTACK: {
                        character2.takeDamage(character1.getDamage());
                        labels.put("action", character1.getName() + " attacked");
                        break;
                    }
                }
            case DEFEND:
                switch (character2.getAction()) {
                    case DEFEND: {
//                        isStunned = Math.random() <= 0.5;
                        labels.put("action", "Both defended themselves");
                        break;
                    }
                    case ATTACK: {
                        labels.put("action", character1.getName() + " didn't attacked");
                        break;
                    }
                }
            case STUN:
                labels.put("status", character1.getName() + " was stunned");
//                character1.setAction(ATTACK);
                switch (character2.getAction()) {
                    case DEFEND: {
                        labels.put("action", character2.getName() + " didn't attacked");
                        break;
                    }
                    case ATTACK: {
                        character1.takeDamage(character2.getDamage());
                        labels.put("action", character2.getName() + " attacked");
                        break;
                    }
                }
        }
        return labels;
    }

    public void defend() {
        player.setAction(DEFEND);
    }

    public void attack() {
        player.setAction(ATTACK);
    }

    public HashMap<String, String> act() {
        if (attackNumber < behaviour.length - 1) {
            attackNumber++;
        } else {
            behaviour = chooseBehavior(enemy);
            attackNumber = 0;
        }
        enemy.setAction(behaviour[attackNumber]);
        turn++;
        if (turn % 2 == 1) {
            return move(player, enemy);
        } else {
            return move(enemy, player);
        }
    }

    public HashMap<String, String> roundResult() {
        if (player.getVictories() == 1) {
//                if (player.getVictories() == 11) {
            return endFinalRound();
        } else {
            return endRound();
        }

    }

    public boolean isEnd() {
        return player.getHealth() == 0 | enemy.getHealth() == 0;
    }


    public HashMap<String, String> endRound() {
        HashMap<String, String> labels = new HashMap<>();
        labels.put("action", "endRound");
        if (player.getHealth() > 0) {
            labels.put("endRoundLabel", "You win");
            player.addVictory();

            if (enemy instanceof ShaoKahn) {
                addItems(38, 23, 8, items);
                addPointsBoss();
            } else {
                addItems(25, 15, 5, items);
                addPoints();
            }
        } else {
            labels.put("endRoundLabel", enemy.getName() + " win");
        }

        turn = 1;
        attackNumber = -1;
        behaviour = new Action[]{DEFEND};
        return labels;
    }

    public HashMap<String, String> endFinalRound() {
        HashMap<String, String> labels = new HashMap<>();
        labels.put("action", "endFinalRound");
        String text = "Победа не на вашей стороне";
        if (player.getHealth() > 0) {
            player.addVictory();
            addPoints();
            text = "Победа на вашей стороне";
        }
        boolean isTop = false;
        int place = 0;
        for (Result result : results) {
            if (player.getPoints() < result.getPoints()) {
                place++;
            }
        }
        if (place < 10) {
            isTop = true;
        }

        if (isTop) {
            labels.put("dialog", "1");
//            dialog1.setVisible(true);
//            dialog1.setBounds(150, 150, 600, 500);
            labels.put("victoryLabel", text);
        } else {
            labels.put("dialog", "2");
//            dialog2.setVisible(true);
//            dialog2.setBounds(150, 150, 470, 360);
            labels.put("victoryLabel", text);
        }
        return labels;
    }


    private final int[] nextLevelExperience = {40, 90, 180, 260, 410, 1000};
    //1 - attack, 0 - defend
    private final Action[][] enemyBehaviour = {{ATTACK, Action.DEFEND}, {ATTACK, ATTACK, Action.DEFEND}, {Action.DEFEND, ATTACK, Action.DEFEND}, {ATTACK, ATTACK, ATTACK, ATTACK}};

    public Character[] getEnemies() {
        return this.enemies;
    }


//    public Character chooseBoss(JLabel label, JLabel label2, JLabel text, JLabel label3, int i) {
//        ImageIcon icon1 = new ImageIcon("Shao Kahn.png");
//        label2.setText("Shao Kahn (босс)");
//        switch (i) {
//            case 2:
//                enemy = enemies[4];
//                break;
//            case 4:
//                enemy = enemies[5];
//                break;
//        }
//        label.setIcon(icon1);
//        text.setText(Integer.toString(enemy.getDamage()));
//        label3.setText(enemy.getHealth() + "/" + enemy.getMaxHealth());
//        return enemy;
//    }

    public Action[] enemyBehavior(int k1, int k2, int k3, double i) {
        Action[] arr = null;
        if (i < k1 * 0.01) {
            arr = enemyBehaviour[0];
        } else if (i < (k1 + k2) * 0.01) {
            arr = enemyBehaviour[1];
        } else if (i < (k1 + k2 + k3) * 0.01) {
            arr = enemyBehaviour[2];
        } else if (i < 1) {
            arr = enemyBehaviour[3];
        }
        return arr;
    }

    public Action[] chooseBehavior(Character enemy) {
        Action[] arr = null;
        double i = Math.random();
        if (enemy instanceof Baraka) {
            arr = enemyBehavior(15, 15, 60, i);
        }
        if (enemy instanceof SubZero) {
            arr = enemyBehavior(25, 25, 0, i);
        }
        if (enemy instanceof LiuKang) {
            arr = enemyBehavior(13, 13, 10, i);
        }
        if (enemy instanceof SonyaBlade) {
            arr = enemyBehavior(25, 25, 50, i);
        }
        if (enemy instanceof ShaoKahn) {
            arr = enemyBehavior(10, 45, 0, i);
        }
        return arr;
    }

    public void addPoints() {
        switch (player.getLevel()) {
            case 0:
                player.addExperience(20);
                player.setPoints(25 + player.getHealth() / 4);
                break;
            case 1:
                player.addExperience(25);
                player.setPoints(30 + player.getHealth() / 4);
                break;
            case 2:
                player.addExperience(30);
                player.setPoints(35 + player.getHealth() / 4);
                break;
            case 3:
                player.addExperience(40);
                player.setPoints(45 + player.getHealth() / 4);
                break;
            case 4:
                player.addExperience(50);
                player.setPoints(55 + player.getHealth() / 4);
                break;
        }
        upgrade();
    }

    public void upgrade() {
        for (int i = 0; i < 5; i++) {
            if (nextLevelExperience[i] == player.getExperience()) {
                player.levelUP();
                player.setNextExperience(nextLevelExperience[i + 1]);
                player.newHealth();
                for (int j = 0; j < 4; j++) {
                    enemy.newHealth(player);
                }
            }
        }
    }

    public void addPointsBoss() {
        switch (player.getLevel()) {
            case 2:
                player.addExperience(30);
                player.setPoints(45 + player.getHealth() / 2);
                break;
            case 4:
                player.addExperience(50);
                player.setPoints(65 + player.getHealth() / 2);
                break;
        }
        upgrade();
    }

    public void addItems(int k1, int k2, int k3, Item[] items) {
        double i = Math.random();
        if (i < k1 * 0.01) {
            items[0].increaseAmount();
        } else if (i < (k1 + k2) * 0.01) {
            items[1].increaseAmount();
        } else if (i < (k1 + k2 + k3) * 0.01) {
            items[2].increaseAmount();
        }
    }

    public boolean useItem(int choice, JDialog dialog, JDialog dialog1) {

        switch (choice) {
            case 1:
                if (items[0].getAmount() > 0) {
                    player.heal((int) (player.getMaxHealth() * 0.25));
                    items[0].decreaseAmount();
                } else {
                    dialog.setVisible(true);
                }
                break;
            case 2:
                if (items[1].getAmount() > 0) {
                    player.heal((int) (player.getMaxHealth() * 0.5));
                    items[1].decreaseAmount();
                } else {
                    dialog.setVisible(true);
                }
                break;
            case 3:
                dialog.setVisible(true);
                break;
        }
        return false;
//        if (!dialog.isVisible()) {
//            dialog1.dispose();
//        }
    }
}
