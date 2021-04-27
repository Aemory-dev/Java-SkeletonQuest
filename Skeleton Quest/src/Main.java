
// Creating custom exceptions InvalidInput and NotEnoughMana
class InvalidInput extends Exception {
    public InvalidInput(String e) {super(e);}
}

class NotEnoughMana extends Exception {
    public NotEnoughMana(String e) {super(e);}
}

// Starts the game
public class Main {

    public static void main(String[] args) {

        Game newGame = new Game();
        newGame.startGame();
    }
}
