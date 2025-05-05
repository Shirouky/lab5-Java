package main;

import java.util.*;

import character.*;
import character.Character;
import fabric.EnemyFabric;
import objects.Item;
import objects.Result;

import static character.Action.*;

/**
 * Controller - это обработчик всех действий игрока и врага и хода игры
 *
 * @author Деребас Любовь
 */

public class Controller {
    private Player player;
    private Enemy enemy;
    private final Item[] items = new Item[3];
    private final Excel excel = new Excel();
    private ArrayList<Result> results = new ArrayList<>();

    private boolean playerTurn = true;
    private final int[] nextLevelExperience = {40, 90, 180, 260, 410, 1000};
    private final Enemy[] enemies = new Enemy[6];
    private int locations;
    private int currentLocation;
    private int currentEnemy;
    private int locationEnemies;
    private final int maxEnemies = 5;
    private final int minEnemies = 2;

    /**
     * <p>Метод начала игры</p>
     *
     * @param locations количество локаций в игре
     * @since 1.0
     */
    public void initGame(int locations) {
        player = new Player(0, 80, 16);
        Random random = new Random();
        this.locations = locations;
        currentLocation = 1;
        currentEnemy = 1;
        locationEnemies = random.nextInt(maxEnemies - minEnemies + player.getLevel()) + minEnemies;

        EnemyFabric fabric = new EnemyFabric();
        for (int i = 0; i < 6; i++) {
            enemies[i] = fabric.create(i);
        }
        newEnemy();
        newItems();
    }

    public void newEnemy() {
        if (currentEnemy == locationEnemies) {
            Random random = new Random();
            enemy = enemies[random.nextInt(2) + 4];
        } else enemy = enemies[(int) (Math.random() * 4)];
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

    public void weaken() {
        player.setAction(WEAKEN);
    }

    /**
     * <p>Метод совершения хода</p>
     *
     * @since 1.0
     */
    public HashMap<String, String> act() {
        enemy.behave(player.getActions());

        if (playerTurn) {
            playerTurn = false;
            return move(player, enemy);
        } else {
            playerTurn = true;
            return move(enemy, player);
        }

    }

    /**
     * <p>Метод определения результата хода</p>
     *
     * @param character1 действующий персонаж
     * @param character2 отвечающий персонаж
     * @since 1.0
     */
    public HashMap<String, String> move(Character character1, Character character2) {
        HashMap<String, String> labels = new HashMap<>();
        labels.put("status", "");
//        System.out.println(character1.getAction().toString() + " " + character2.getAction().toString() + " " + character1.getType() + " " + character2.getType() + String.valueOf(character1.isVulnerable()) + String.valueOf(character2.isVulnerable()));
        if (character1.isVulnerable() && character2.getAction() == ATTACK) {
            character1.resetVulnerable();
            character2.buff();
            labels.put("status", character1.getName() + " removed weakening and " + character2.getName() + " got buff");
            labels.put("progressbar", String.valueOf(character1 instanceof Player));
        }
        if (character2.isVulnerable() && character1.getAction() == ATTACK) {
            character2.resetVulnerable();
            character1.buff();
            labels.put("status", character2.getName() + " removed weakening and " + character1.getName() + " got buff");
            labels.put("progressbar", String.valueOf(character2 instanceof Player));
        }

        switch (character1.getAction()) {
            case REGENERATE:
                switch (character2.getAction()) {
                    case DEFEND: {
                        character1.heal((int) ((character1.getMaxHealth() - character1.getHealth()) * 0.5));
                        labels.put("action", character1.getName() + " regenerated");
                        break;
                    }
                    case STUN: {
                        character1.heal((int) ((character1.getMaxHealth() - character1.getHealth()) * 0.6));
                        labels.put("action", character1.getName() + " regenerated");
                        break;
                    }
                    case ATTACK: {
                        character1.takeDamage(character2.getDamage() * 2);
                        labels.put("action", character2.getName() + " stopped regeneration");
                        break;
                    }
                }
                break;
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
                    case ATTACK, STUN: {
                        character2.takeDamage(character1.getDamage());
                        labels.put("action", character1.getName() + " attacked");
                        break;
                    }
                }
                break;
            case DEFEND:
                switch (character2.getAction()) {
                    case DEFEND: {
                        if (Math.random() <= 0.5) character2.setAction(STUN);
                        labels.put("action", "Both defended themselves");
                        break;
                    }
                    case ATTACK: {
                        labels.put("action", character1.getName() + " didn't attack");
                        break;
                    }
                    case STUN: {
                        labels.put("action", "Nobody attacked");
                        break;
                    }
                }
                break;
            case STUN:
                labels.put("status", character1.getName() + " was stunned");
                character1.setAction(ATTACK);
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
                    case WEAKEN: {
                        labels.put("action", character1.getName() + " was weakened");
                        break;
                    }
                }
                break;
            case WEAKEN:
                switch (character2.getAction()) {
                    case DEFEND: {
                        if (Math.random() <= 0.75) {
                            character2.setVulnerable();
                            labels.put("action", character2.getName() + " was weakened");
                            labels.put("progressbar", String.valueOf(character2 instanceof Player));
                        } else {
                            labels.put("action", character2.getName() + " wasn't weakened");
                        }
                        break;
                    }
                    case ATTACK: {
                        character1.takeDamage(character2.getDamage());
                        labels.put("action", character2.getName() + " wasn't weakened and attacked");
                        break;
                    }
                    case WEAKEN: {
                        labels.put("action", "Nobody was weakened");
                        break;
                    }
                }
                break;
        }
        return labels;
    }

    /**
     * <p>Метод завершения прохождения локации</p>
     *
     * @since 1.0
     */
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

    /**
     * <p>Метод завершения сражения</p>
     *
     * @since 1.0
     */
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
        } else {
            labels.put("endRoundLabel", enemy.getName() + " win");
        }
        enemy.reset();
        player.reset();

        newEnemy();

        if (currentLocation == locations) return endGame();

        labels.put("currentEnemy", String.valueOf(currentEnemy));
        labels.put("currentLocation", String.valueOf(currentLocation));
        labels.put("locationEnemies", String.valueOf(locationEnemies));

        playerTurn = true;
        return labels;
    }

    public HashMap<String, String> endBattle() {
        currentEnemy++;
        return end();
    }

    /**
     * <p>Метод обновления данных для новой локации</p>
     *
     * @since 1.0
     */
    public HashMap<String, String> endRound() {
        Random random = new Random();
        currentEnemy = 1;
        currentLocation++;
        locationEnemies = random.nextInt(maxEnemies - minEnemies) + minEnemies;

        return end();
    }

    /**
     * <p>Метод завершения игры</p>
     *
     * @since 1.0
     */
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
        playerTurn = true;
        return labels;
    }

    /**
     * <p>Метод завершения игры в топе</p>
     *
     * @param name имя игрока для сохранения
     * @since 1.0
     */
    public ArrayList<Result> endGameTop(String name) {
        results.add(new Result(name, player.getPoints()));
        results.sort(Comparator.comparing(Result::getPoints).reversed());
        exportData();
        return results;
    }

    /**
     * <p>Метод воскрешения игрока</p>
     *
     * @since 1.0
     */
    public boolean resurrect() {
        if (player.getHealth() == 0 & items[2].getAmount() > 0) {
            player.setHealth((int) (player.getMaxHealth() * 0.05));
            items[2].decreaseAmount();
            return true;
        }
        return false;
    }


    public boolean checkLevel() {
        return player.getNextExperience() <= player.getExperience();
    }

    /**
     * <p>Метод поднятия уровня игрока и врагов</p>
     *
     * @param choice вывбор игрока: улучшать здоровье или урон
     * @since 1.0
     */
    public void levelUP(String choice) {
        player.levelUP();
        if (Objects.equals(choice, "HP")) player.levelHP(player);
        else player.levelDMG(player);
        for (int i = 0; i < 5; i++) {
            if (nextLevelExperience[i] == player.getNextExperience()) {
                player.setNextExperience(nextLevelExperience[i + 1]);
                for (int j = 0; j < 4; j++) {
                    enemies[j].levelUP();
                    enemies[j].levelHP(player);
                    enemies[j].levelDMG(player);
                }
                break;
            }
        }

    }

    /**
     * <p>Метод добавления новых предментов после сражения</p>
     *
     * @param prob1 вероятность выпадения первого предмета
     * @param prob2 вероятность выпадения второго предмета
     * @param prob3 вероятность выпадения третьего предмета
     * @since 1.0
     */
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

    /**
     * <p>Метод использования предметов</p>
     *
     * @param choice выбор игроком предмета
     * @since 1.0
     */
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


    public void exportData() {
        excel.exportData(results);
    }

    public ArrayList<Result> importData() {
        results = excel.importData();
        return results;
    }

    public boolean getTurn() {
        return this.playerTurn;
    }

    public Player getPlayer() {
        return player;
    }

    public Enemy getEnemy() {
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
