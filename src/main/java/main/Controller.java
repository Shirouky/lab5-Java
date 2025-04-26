package main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;

import character.*;
import character.Action;
import character.Character;
import fabric.EnemyFabric;
import objects.Item;
import objects.Result;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import static character.Action.*;

public class Controller {
    Player player;
    Character enemy;
    Item[] items = new Item[3];
    private final ArrayList<Result> results = new ArrayList<>();
    character.Action[] behaviour = {DEFEND};
    private boolean playerTurn = true;
    int attackNumber = -1;
    private final int[] nextLevelExperience = {40, 90, 180, 260, 410, 1000};
    private final Action[][] enemyBehaviour = {{ATTACK, DEFEND}, {ATTACK, ATTACK, DEFEND}, {DEFEND, ATTACK, DEFEND}, {ATTACK, ATTACK, ATTACK, ATTACK}};
    private final Character[] enemies = new Character[6];
    EnemyFabric fabric = new EnemyFabric();
    private int locations;
    private int currentLocation;
    private int currentEnemy;
    private int locationEnemies;
    private int maxEnemies = 5;
    private int minEnemies = 2;

    public void initGame(int locations) {
        player = new Player(0, 80, 16);
        Random random = new Random();
        this.locations = locations;
        currentLocation = 1;
        currentEnemy = 1;
        locationEnemies = random.nextInt(maxEnemies - minEnemies) + minEnemies;

        for (int i = 0; i < 6; i++) {
            enemies[i] = fabric.create(i);
        }
        newEnemy();
        newItems();
    }

    public void newEnemy() {
        if (currentEnemy == locationEnemies) enemy = enemies[4];
        else enemy = enemies[(int) (Math.random() * 4)];
    }

    public void newItems() {
        items[0] = new Item("Малое зелье", 0);
        items[1] = new Item("Большое зелье", 0);
        items[2] = new Item("Крест возрождения", 10);
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

        playerTurn = !playerTurn;
        if (playerTurn) {
            return move(player, enemy);
        } else {
            return move(enemy, player);
        }
    }

    public HashMap<String, String> move(Character character1, Character character2) {
        HashMap<String, String> labels = new HashMap<>();
        switch (character1.getAction()) {
            case REGENERATE:
                switch (character2.getAction()) {
                    case DEFEND: {
                        character1.heal((int) ((character1.getMaxHealth() - character1.getHealth()) * 0.5));
                        labels.put("action", character1.getName() + " regenerated");
                    }
                    case ATTACK: {
                        character1.takeDamage(character2.getDamage() * 2);
                        labels.put("action", character2.getName() + " stopped regeneration");
                    }
                }
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

    public HashMap<String, String> roundResult() {
        if (currentEnemy == locationEnemies) {
            return endRound();
        } else {
            return endBattle();
        }
    }

    public boolean isEnd() {
        return player.getHealth() == 0 | enemy.getHealth() == 0;
    }

    public HashMap<String, String> end() {
        HashMap<String, String> labels = new HashMap<>();
        labels.put("action", "endRound");
        if (player.getHealth() > 0) {
            labels.put("endRoundLabel", "You win");

            if (enemy instanceof ShaoKahn) {
                addItems(38, 23, 8);
                player.addPointsBoss();
            } else {
                addItems(25, 15, 5);
                player.addPoints();
            }
            enemy.reset();
            player.reset();
            upgrade();
            newEnemy();

            if (currentLocation == locations) return endGame();
        } else {
            labels.put("endRoundLabel", enemy.getName() + " win");
        }
        labels.put("currentEnemy", String.valueOf(currentEnemy));
        labels.put("currentLocation", String.valueOf(currentLocation));
        labels.put("locationEnemies", String.valueOf(locationEnemies));

        resetRound();
        return labels;
    }

    public HashMap<String, String> endBattle() {
        currentEnemy++;
        return end();
    }

    public HashMap<String, String> endRound() {
        Random random = new Random();
        currentEnemy = 1;
        currentLocation++;
        locationEnemies = random.nextInt(maxEnemies - minEnemies) + minEnemies;

        return end();
    }

    public HashMap<String, String> endGame() {
        HashMap<String, String> labels = new HashMap<>();
        labels.put("action", "endGame");
        String text = "Победа не на вашей стороне";
        if (player.getHealth() > 0) {
            player.addPoints();
            text = "Победа на вашей стороне";
        }

        int place = 0;
        for (Result result : results) {
            if (player.getPoints() < result.getPoints()) {
                place++;
            }
        }

        if (place < 10) {
            labels.put("dialog", "1");
            labels.put("victoryLabel", text);
        } else {
            labels.put("dialog", "2");
            labels.put("victoryLabel", text);
        }
        resetRound();
        return labels;
    }

    public ArrayList<Result> endGameTop(String name) {
        results.add(new Result(name, player.getPoints()));
        results.sort(Comparator.comparing(Result::getPoints).reversed());
        writeToExcel();
        return results;
    }

    public boolean resurrect() {
        if (player.getHealth() == 0 & items[2].getAmount() > 0) {
            player.setNewHealth((int) (player.getMaxHealth() * 0.05));
            items[2].decreaseAmount();
            return true;
        }
        return false;
    }

    public void upgrade() {
        if (player.getNextExperience() <= player.getExperience()) {
            player.levelUP(player);
            for (int i = 0; i < 5; i++) {
                if (nextLevelExperience[i] == player.getNextExperience()) {
                    player.setNextExperience(nextLevelExperience[i + 1]);
                    for (int j = 0; j < 4; j++) {
                        enemies[j].levelUP(player);
                    }
                    break;
                }
            }
        }
    }

    private void resetRound() {
        playerTurn = true;
        attackNumber = -1;
        behaviour = new Action[]{DEFEND};
    }

    public Action[] enemyBehavior(int prob1, int prob2, int prob3) {
        Action[] arr = null;
        Random random = new Random();
        double randomN = random.nextDouble() * 100;
        if (randomN < prob1) {
            arr = enemyBehaviour[0];
        } else if (randomN < prob1 + prob2) {
            arr = enemyBehaviour[1];
        } else if (randomN < prob1 + prob2 + prob3) {
            arr = enemyBehaviour[2];
        } else if (randomN < 100) {
            arr = enemyBehaviour[3];
        }
        return arr;
    }

    public Action[] chooseBehavior(Character enemy) {
        Action[] arr = null;
        if (enemy instanceof Baraka) {
            arr = enemyBehavior(15, 15, 60);
        }
        if (enemy instanceof SubZero) {
            arr = enemyBehavior(25, 25, 0);
        }
        if (enemy instanceof LiuKang) {
            arr = enemyBehavior(13, 13, 10);
        }
        if (enemy instanceof SonyaBlade) {
            arr = enemyBehavior(25, 25, 50);
        }
        if (enemy instanceof ShaoKahn) {
            arr = enemyBehavior(10, 45, 0);
        }
        return arr;
    }

    public void addItems(int prob1, int prob2, int prob3) {
        Random random = new Random();
        if (random.nextDouble() * 100 < prob1) {
            items[0].increaseAmount();
        }
        if (random.nextDouble() * 100 < prob2) {
            items[1].increaseAmount();
        }
        if (random.nextDouble() * 100 < prob3) {
            items[2].increaseAmount();
        }
    }

    public boolean useItem(int choice) {
        boolean isUsed = true;
        switch (choice) {
            case 1:
                if (items[0].getAmount() > 0) {
                    player.heal((int) (player.getMaxHealth() * 0.25));
                    items[0].decreaseAmount();
                } else isUsed = false;
                break;
            case 2:
                if (items[1].getAmount() > 0) {
                    player.heal((int) (player.getMaxHealth() * 0.5));
                    items[1].decreaseAmount();
                } else isUsed = false;
                break;
            case 3:
                isUsed = false;
                break;
        }
        return isUsed;
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

    public ArrayList<Result> readFromExcel() {
        try {
            XSSFWorkbook book = new XSSFWorkbook("Results.xlsx");
            XSSFSheet sh = book.getSheetAt(0);
            for (int i = 1; i <= sh.getLastRowNum(); i++) {
                results.add(new Result(sh.getRow(i).getCell(1).getStringCellValue(), (int) sh.getRow(i).getCell(2).getNumericCellValue()));
            }
        } catch (org.apache.poi.openxml4j.exceptions.InvalidOperationException | IOException e) {
            System.out.println("No file");
        }
        return results;
    }

    public boolean getTurn() {
        return this.playerTurn;
    }

    public Player getPlayer() {
        return player;
    }

    public Character getEnemy() {
        return enemy;
    }

    public Item[] getItems() {
        return items;
    }

    public Item getItem(int index) {
        return items[index];
    }

    public int getLocations() {
        return locations;
    }

    public int getCurrentLocation() {
        return currentLocation;
    }

    public int getEnemies() {
        return locationEnemies;
    }

    public int getCurrentEnemy() {
        return currentEnemy;
    }
}
