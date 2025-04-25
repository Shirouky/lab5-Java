package objects;

public class Item {

    private final String name;
    private int amount;

    public Item(String name, int count) {
        this.name = name;
        this.amount = count;
    }

    public void increaseAmount() {
        this.amount++;
    }

    public String getName() {
        return this.name;
    }

    public int getAmount() {
        return this.amount;
    }

    public void decreaseAmount() {
        this.amount--;
    }
}
