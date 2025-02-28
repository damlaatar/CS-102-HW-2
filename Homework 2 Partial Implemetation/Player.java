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
     * TODO: removes and returns the tile in given index
     */
    public Tile getAndRemoveTile(int index) {
        return null;
    }

    /*
     * TODO: adds the given tile to the playerTiles in order
     * should also update numberOfTiles accordingly.
     * make sure playerTiles are not more than 15 at any time
     */
    public void addTile(Tile t) {

    }

    /*
     * checks if this player's hand satisfies the winning condition -done! 
     * to win this player should have 3 chains of length 4, extra tiles
     * does not disturb the winning condition
     * @return
     */
    public boolean isWinningHand() {
        int validChains = 0;
        int i = 0;
        int currentChain = 0;
        while ( i < 13 && validChains < 3) { // compares every tile to the next one
            Tile first = playerTiles[ i ];
            Tile second = playerTiles[ i + 1 ]; 
            if ( first.getValue() == second.getValue() && first.getColor() != second.getColor() ) { // if tiles are same number but diff color
                currentChain++; // increase the current chain
                if ( currentChain == 4 ) { // if it's a chain of 4, count a valid chain 
                    validChains++; 
                }
            } 
            else if ( first.getValue() != second.getValue() ) { // if tile number is different, reset chain
                currentChain = 0;
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
