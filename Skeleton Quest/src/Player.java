import java.util.ArrayList;
import java.util.Scanner;

// Creates the player and simulates combat
public class Player {

    // Creating global player variables
    private int health;
    private int maxHealth;
    private int mana;
    private int maxMana;
    private int attack;
    private int level;
    private ArrayList<Spell> playerSpells = new ArrayList<>(); // Contains spells the player has learned
    private Scanner input = new Scanner(System.in);

    // Initializing player attributes
    public Player() {
        level = 1;
        maxHealth = 18 + (level * 2);
        health = maxHealth;
        maxMana = 21 + (level * 3);
        mana = maxMana;
        attack = 3 + level * 2;
    }

    // Adds a spell to playerSpells allowing it to be used by the player
    public void learnSpell(Spell spellName) { // Teaches the player a spell
        playerSpells.add(spellName);
    }

    // Creating Getters and Setters
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
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

    // Returns a String containing the players health/maxHealth and mana/maxMana
    public String showStats() {
        return "You have " + this.health + "|" + this.maxHealth + " health and " +
                this.mana + "|" + this.maxMana + " mana";
    }

    // Gives the player full health and mana
    public void restore() {
        this.setHealth(this.maxHealth);
        this.setMana(this.maxMana);
    }

    // Player attacks enemy target
    public void attack(Enemy target) {
        target.setHealth(target.getHealth() - this.attack);
    }

    public void levelUp() {
        this.level += 1;
        this.maxHealth += 3;
        this.maxMana += 4;
        this.attack += 1;
    }

    // The following are functions used in the combat method

    // Checks that user input is an integer before proceeding
    public int checkIfInt(int choice) {
        while (!input.hasNextInt()) {
            System.out.println("You must enter the number of desired option");
            input.next();
        }
        choice = input.nextInt();
        return choice;
    }

    // Used when a user decides to cast a spell
    public void spellCastChosen(Spell spell, Player player, Enemy target, int choice) {
        int j = 0;
        while (j ==0) { // loop to allow for exception checks
            try {
                System.out.println("Choose a spell to cast or select " + (playerSpells.size() + 1) + " to attack instead");
                for (int i = 1; i <= playerSpells.size(); i++) {
                    spell = playerSpells.get(i - 1);
                    System.out.println(i + ") " + spell.getSpellName() + " | " + spell.getManaCost() + " Mana" + " | " +
                            spell.getPower() + " Power");
                }
                System.out.println(playerSpells.size() + 1 + ") Attack | " + player.getAttack() + " Power");

                choice = checkIfInt(choice); // Checking for valid input
                if (choice < 1 || choice > playerSpells.size() + 1) {
                    throw new InvalidInput("You must enter the number of desired option\n");
                }
                if (choice == playerSpells.size() + 1) { // Allows a regular attack within the spell menu to avoid having no usable options
                    player.attack(target);
                    return;
                }
                spell = playerSpells.get(choice - 1);
                if (spell.getManaCost() > this.getMana()) { // Throws error if player doesn't have enough mana for spell
                    throw new NotEnoughMana("You don't have enough mana to cast that spell\n");
                }
                if (spell.getSpellName().equals("Heal")) { // When "Heal" is used
                    spell.castHeal(this);
                    System.out.println("You recover some health");
                }
                else if (spell.getSpellName().equals("Curse of Weakness")) { // When "Curse of Weakness" is used
                    spell.castCurse(target, player);
                    System.out.println(target.getName() + "'s attack has been reduced by " + spell.getPower());
                }
                else {
                    spell.castHarm(target, this); // When a damaging spell is used
                    System.out.println("You deal " + spell.getPower() + " damage to the " + target.getName());
                }
            } catch (NotEnoughMana | InvalidInput e) {
                System.out.println(e);
                j--;
            }
            j++;
        }
    }


    // This method contains the program's combat loop. simulates combat between the player and a given enemy.
    public void combat(Enemy target) {
        Spell spell = null; // Value must be initialized here, changed by spellCastChosen if applicable
        int choice = 0;
        String enemy = target.getName();
        String combatStart = "You have entered battle with a " + enemy;
        String dead = "You have been defeated. GAME OVER.";
        String combatEnd = "You have defeated the enemy. A strange power creeps over you and you feel " +
                "magically rejuvenated. Your strength has increased.\n";
        System.out.println(combatStart); // Combat starts

        // Combat simulation loop runs while the player and enemy target are both alive
        while (this.getHealth() > 0 && target.getHealth() > 0) {
            try {
                System.out.println(this.showStats() + "\n" + target.showEnemyHealth() + "\n" + "What will you do?"
                                   + "\n1) Attack | " + this.getAttack() + " Power\n2) Cast Spell");
                choice = checkIfInt(choice); // Checking for valid input
                if (choice != 1 && choice != 2) {
                    throw new InvalidInput("You must enter the number of your desired option\n");
                }
                if (choice == 1) { // When the player chooses to attack
                    this.attack(target);
                    System.out.println("You deal " + this.attack + " damage to the " + enemy);
                }
                else { // Runs spellCastChosen when the player chooses to cast a spell
                    spellCastChosen(spell, this, target, choice);
                }
                if (target.getHealth() < 1) { // Ends loop if enemy dies
                    System.out.println(combatEnd);
                    this.levelUp();
                    break;
                }
                System.out.println("The " + enemy + " attacks and deals " + target.getAttack() + " damage");
                target.enemyAttack(this); // Enemy attacks
                if (this.getHealth() < 1) { // Ends game if player dies
                    System.out.println(dead);
                    System.exit(1);
                }
            }
            catch (InvalidInput e) {
                System.out.println(e);
            }
        }
        this.restore(); // Restores player health at the end of combat
    }
}