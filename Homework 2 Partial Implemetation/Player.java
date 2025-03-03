public class Player {
    String playerName;
    Tile[] playerTiles;
    int numberOfTiles;

    public Player(String name) {
        setName(name);
        playerTiles = new Tile[15]; // there are at most 15 tiles a player owns at any time
        numberOfTiles = 0; // currently this player owns 0 tiles, will pick tiles at the beggining of the game
    }

    /*
     * DONE: removes and returns the tile in given index
     */
    public Tile getAndRemoveTile(int index) 
    {
        if (index < 0 || index >= numberOfTiles) 
        {
            return null; //if it's an invalid index
        }
        
        Tile removedTile = playerTiles[index];
        
        //update after the removed one to fill the gap
        for (int i = index; i < numberOfTiles - 1; i++) 
        {
            playerTiles[i] = playerTiles[i + 1]; //shift to the left
        }
        
        playerTiles[numberOfTiles - 1] = null; //clear the last one
        numberOfTiles--; //decrement tile amount
        
        return removedTile;
    }

    /*
     * DONE: adds the given tile to the playerTiles in order
     * should also update numberOfTiles accordingly.
     * make sure playerTiles are not more than 15 at any time
     */
    public void addTile(Tile t) 
    {
        if (numberOfTiles >= 15) 
        {
            return;
        }
        
        // Find the correct position to insert the new tile 
        int insertingPos = 0;

        while (insertingPos < numberOfTiles && 
        (playerTiles[insertingPos].getValue() < t.getValue() || 
        (playerTiles[insertingPos].getValue() == t.getValue() && 
        playerTiles[insertingPos].colorNameToInt() < t.colorNameToInt()))) //corrected condition
        {
            insertingPos++;
        }
        
        for (int i = numberOfTiles; i > insertingPos; i--) 
        {
            playerTiles[i] = playerTiles[i - 1]; //shift to the right
        }
        
        playerTiles[insertingPos] = t; //insert new tile into right position
        numberOfTiles++; //increase tile amount
    }

    /*
     * checks if this player's hand satisfies the winning condition -done! 
     * to win this player should have 3 chains of length 4, extra tiles
     * does not disturb the winning condition
     * @return
     */
    public boolean isWinningHand() {
        int max;
        if ( playerTiles[ 14 ] != null ) {
            max = 14;
        }
        else {
            max = 13;
        }
        int validChains = 0;
        int i = 0;
        int currentChain = 1;
        while ( i < max && validChains < 3) { // compares every tile to the next one
            Tile first = playerTiles[ i ];
            Tile second = playerTiles[ i + 1 ]; 
            if ( first.getValue() == second.getValue() && first.getColor() != second.getColor() ) { // if tiles are same number but diff color
                currentChain++; // increase the current chain
                if ( currentChain == 4 ) { // if it's a chain of 4, count a valid chain 
                    validChains++; 
                    currentChain = 1;
                }
            } 
            else if ( first.getValue() != second.getValue() ) { // if tile number is different, reset chain
                currentChain = 1;
            }
            i++;
        }
        return validChains >= 3; // checks if player has at least 3 valid chains
    }

    public int findPositionOfTile(Tile t) {
        int tilePosition = -1;
        for (int i = 0; i < numberOfTiles; i++) {
            if(playerTiles[i].compareTo(t) == 0) {
                tilePosition = i;
            }
        }
        return tilePosition;
    }

    public void displayTiles() {
        System.out.println(playerName + "'s Tiles:");
        for (int i = 0; i < numberOfTiles; i++) {
            System.out.print(playerTiles[i].toString() + " ");
        }
        System.out.println();
    }

    public Tile[] getTiles() {
        return playerTiles;
    }

    public void setName(String name) {
        playerName = name;
    }

    public String getName() {
        return playerName;
    }
}
