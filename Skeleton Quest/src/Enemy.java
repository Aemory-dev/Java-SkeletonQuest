
// Creates enemies
public class Enemy {

    private String name;
    private int maxHealth;
    private int health;
    private int attack;
    private int level;

    public Enemy(String name, int level) {
        this.name = name;
        this.level = level;
        this.maxHealth = 16 + level * 2;
        this.attack = 3 + level * 2;
        this.health = maxHealth;
    }

    public void enemyAttack(Player target) {
        target.setHealth(target.getHealth() - this.attack);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String showEnemyHealth() {
        return "The " + this.getName() + " has " + this.getHealth() + "|" + this.getMaxHealth() + " health";
    }
}
