import java.util.Scanner;

// Creates the game's story that the user will see in the console
public class Game {

    private Scanner input = new Scanner(System.in);
    private int choice = 0;
    private Player player = new Player();

    public void initializePlayer() {
        Spell heal = new Spell("Heal", 8, 30);
        Spell zap = new Spell("Zap", 12, 8);
        Spell fireball = new Spell("Fireball", 19, 13);
        player.learnSpell(zap);
        player.learnSpell(heal);
        player.learnSpell(fireball);
    }

    // Function that checks if input is valid
    public int checkInput(int choice, int numChoices) {
        int i = 0;
        while (i == 0) {
            try {
                while (!input.hasNextInt()) {
                    System.out.println("You must enter the number of desired option");
                    input.next();
                }
                choice = input.nextInt();
                if (1 > choice || choice > numChoices) {
                    throw new InvalidInput("You must enter the number of desired option");
                }
            } catch (InvalidInput e) {
                System.out.println(e);
                i--;
            }
            i++;
        }
        return choice;
    }

    // The following methods represent different levels of the game

    public void levelOne() {
        Enemy skeleton1 = new Enemy("Skeleton", player.getLevel());
        Enemy skeleton2 = new Enemy("Skeleton", player.getLevel());
        Spell weakness = new Spell("Curse of Weakness", 20, 6);

        System.out.println("\nYou wake up in a dimly lit corridor. In one direction the passage has collapsed. " +
                "In the other direction you can see a faint light some way down the corridor." +
                "\nWhat will you do?\n1) Go back to sleep.\n2) Advance down the corridor.");
        choice = checkInput(choice, 2);
        switch (choice) {
            case 1:
                System.out.println("You drift peacefully back to sleep, never to wake again.\nGAME OVER.");
                System.exit(1);
            case 2:
                System.out.println("As you make your way down the passage, you see a small wooden door on one side.\n" +
                        "What will you do?\n1) Open the door and enter.\n2) Continue down the corridor");

                choice = checkInput(choice, 2);
                switch (choice) {
                    case 1:
                        System.out.println("As you enter the room you hear something moving behind the door." +
                                " You are attacked by a skeleton!");
                        player.combat(skeleton1);
                        System.out.println("You defeat the skeleton and find a magical book. You open it and feel a " +
                                "sudden increase in your magical knowledge.\nYou learned the spell Curse of Weakness!\n");
                        player.learnSpell(weakness);
                        System.out.println("You exit the room and continue down the corridor, only to be attacked by " +
                                "another skeleton!");
                        player.combat(skeleton2);
                        System.out.println("You continue down the corridor.");
                        break;
                    case 2:
                        System.out.println("As you continue down the corridor you are attacked by a skeleton!");
                        player.combat(skeleton2);
                        System.out.println("You continue down the corridor.");
                        break;
                }
        }
    }

    public void levelTwo() {
        Enemy skeleton = new Enemy("Skeleton", player.getLevel());
        skeleton.setAttack(skeleton.getAttack() + 3);

        System.out.println("As you approach the end of the corridor you see a large stone door that is broken down " +
                "the center. To the right of the door there is a skeleton on the ground with a large sword through " +
                "its chest.\nWhat will you do?\n1) Ignore the skeleton and squeeze through the crack in the door.\n" +
                "2) Poke the skeleton and see what happens.\n3) Remove the sword from the skeleton.");

        choice = checkInput(choice, 3);
        switch (choice) {
            case 1:
                System.out.println("You manage to squeeze through.");
                break;
            case 2:
                System.out.println("You poke the skeleton and are relieved to find that it stays dead.\n" +
                        "You continue through the door, just barely squeezing through.");
                break;
            case 3:
                System.out.println("As soon as you touch the sword the skeleton reanimates and wields the sword");
                player.combat(skeleton);
                System.out.println("You defeat the skeleton and claim the sword as your own.\nYou make your way" +
                        "through the crack in the door.");
                player.setAttack(player.getAttack()  + 4);
                break;
        }

    }

    public void levelThree() {
        Enemy giantSkeleton = new Enemy("Giant Skeleton", player.getLevel() + 2);

        System.out.println("On The other side of the door you find what appears to be a huge throne room. " +
                "The silhouette of a giant skeleton looms over you.\nWhat do you do?\n1) Run away." +
                "\n2) Attack the giant skeleton.\n3) Survey the room.");

        choice = checkInput(choice, 3);
        switch (choice) {
            case 1:
                System.out.println("You squeeze back through the crack in the door, and as you do so the passageway " +
                        "begins to collapse around you. Your adventure ends here.\nGAME OVER.");
                System.exit(1);
            case 2:
                player.combat(giantSkeleton);
                break;
            case 3:
                System.out.println("You look around the room and notice a huge chandelier suspended above the " +
                        "giant skeleton. You sneak your way to the rope supporting it at cut it.\nThe chandelier " +
                        "falls directly onto the skeletons head. That must've done some damage.");
                giantSkeleton.setHealth((int)giantSkeleton.getHealth() / 2);
                System.out.println("The injured giant skeleton charges at you.");
                player.combat(giantSkeleton);
                break;
        }
    }

    public void levelFour() {
        Enemy skeletonKing = new Enemy("The Skeleton King", player.getLevel() + 4);

        System.out.println("As the giant skeleton crumbles to dust you, you hear a voice from the darkness " +
                "\n\"You furry, flea-bitten fool, I’ll cover my throne with your hide!\"\nA hooded skeleton " +
                "appears on the throne\n\"MYAH\" he yells as he attacks you.");
        player.combat(skeletonKing);
        System.out.println("The skeleton king has been defeated and your adventure has concluded.\nYOU WON.");
    }

    // Initializes the player and creates each level as the player reaches it
    public void startGame() {
        this.initializePlayer();
        this.levelOne();
        this.levelTwo();
        this.levelThree();
        this.levelFour();
    }
}
