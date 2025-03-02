import java.util.Scanner;

public class ApplicationMain {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        OkeyGame game = new OkeyGame();

        int choice = 0;
        do{
            System.out.println("Choose playing option:\n1-With 3 computer players\n2- 4 computer players");
            if(sc.hasNextInt())
            {
                choice = sc.nextInt(); sc.nextLine();
            }
            else
            {
                sc.nextLine();
            }
        }while(choice < 1 || choice > 2);

        if(choice == 1)//3 computer, 1 user player option
        {
            System.out.print("Please enter your name: ");
            String playerName = sc.next();
            game.setPlayerName(0, playerName);
        }
        else//4 computer players option
        {
            game.setPlayerName(0, "Alan");
        }
        game.setPlayerName(1, "John");
        game.setPlayerName(2, "Jane");
        game.setPlayerName(3, "Ted");

        game.createTiles();
        game.shuffleTiles();
        game.distributeTilesToPlayers();

        // developer mode is used for seeing the computer players hands, to be used for debugging
        System.out.print("Play in developer's mode with other player's tiles visible? (Y/N): ");
        char devMode = sc.next().charAt(0);
        boolean devModeOn = (devMode == 'Y' || devMode == 'y');
        
        boolean firstTurn = true;
        boolean gameContinues = true;
        int playerChoice = -1;

        while(gameContinues && game.getTopTile(null) != null) {
            
            int currentPlayer = game.getCurrentPlayerIndex();
            System.out.println(game.getCurrentPlayerName() + "'s turn.");
            
            if(choice == 1 && currentPlayer == 0) {
                // this is the human player's turn (when it is the 3 computer players version meaning choice was 1)
                game.displayCurrentPlayersTiles();
                game.displayDiscardInformation();

                System.out.println("What will you do?");

                if(!firstTurn) {
                    // after the first turn, player may pick from tile stack or last player's discard
                    System.out.println("1. Pick From Tiles");
                    System.out.println("2. Pick From Discard");
                }
                else{
                    // on first turn the starting player does not pick up new tile
                    System.out.println("1. Discard Tile");
                }

                System.out.print("Your choice: ");
                playerChoice = sc.nextInt();

                // after the first turn we can pick up
                if(!firstTurn) {
                    if(playerChoice == 1) {
                        System.out.println("You picked up: " + game.getTopTile(game.getCurrentPlayer()));
                        firstTurn = false;
                    }
                    else if(playerChoice == 2) {
                        System.out.println("You picked up: " + game.getLastDiscardedTile()); 
                    }

                    // display the hand after picking up new tile
                    game.displayCurrentPlayersTiles();
                }
                else{
                    // after first turn it is no longer the first turn
                    firstTurn = false;
                }

                gameContinues = !game.didGameFinish();

                if(gameContinues) {
                    // if game continues we need to discard a tile using the given index by the player
                    System.out.println("Which tile you will discard?");
                    System.out.print("Discard the tile in index: ");
                    playerChoice = sc.nextInt();

                    // DONE: make sure the given index is correct, should be 0 <= index <= 14
                    while (playerChoice < 0 || playerChoice > 14) 
                    {
                        System.out.println("Invalid index, please enter an index between 0 and 14!");
                        playerChoice = sc.nextInt();
                    }
                    game.discardTile(playerChoice);
                    game.passTurnToNextPlayer();
                }
                else{
                    // if we finish the hand we win
                    System.out.println("Congratulations, you win!");
                }
            }
            else{
                // this is the computer player's turn
                if(devModeOn) {
                    game.displayCurrentPlayersTiles();
                }

                if(!firstTurn)
                {
                    // computer picks a tile from tile stack or other player's discard
                    game.pickTileForComputer();
                }
            
                gameContinues = !game.didGameFinish();

                if(gameContinues) {
                    // if game did not end computer should discard
                    game.discardTileForComputer();
                    game.passTurnToNextPlayer();
                    firstTurn = false;
                }
                else{
                    // current computer character wins
                    System.out.println(game.getCurrentPlayerName() + " wins.");
                }
            }
        }
    }
}
