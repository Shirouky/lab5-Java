package character;

public class Baraka extends Character {
    
    public Baraka(int level, int health, int  damage){
        super (level, health, damage);
        this.name = "Baraka";
        this.image = "Baraka.jpg";
        this.type = "танк";
    }
}
