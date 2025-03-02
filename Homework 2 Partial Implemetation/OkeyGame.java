import java.util.Random;
public class OkeyGame {

    Player[] players;
    Tile[] tiles;

    Tile lastDiscardedTile;

    int currentPlayerIndex = 0;

    public OkeyGame() {
        players = new Player[4];
    }

    public void createTiles() {
        tiles = new Tile[112];
        int currentTile = 0;

        // two copies of each color-value combination, no jokers
        for (int i = 1; i <= 7; i++) {
            for (int j = 0; j < 4; j++) {
                tiles[currentTile++] = new Tile(i,'Y');
                tiles[currentTile++] = new Tile(i,'B');
                tiles[currentTile++] = new Tile(i,'R');
                tiles[currentTile++] = new Tile(i,'K');
            }
        }
    }

    /*
     * DONE: distributes the starting tiles to the players
     * player at index 0 gets 15 tiles and starts first
     * other players get 14 tiles
     * this method assumes the tiles are already shuffled
     */
    public void distributeTilesToPlayers() {
        int tileIndex = tiles.length-1;//where distribution starts from
        for(int i = 0; i < players.length; i++)
        {
            int tileNumber;//14 or 15 depending on i
            if(i == 0)
            {
                tileNumber = 15;
            }
            else
            {
                tileNumber = 14;
            }
            for(int t = tileIndex; t > tileIndex-tileNumber; t--)
            {
                players[i].addTile(tiles[t]);
            }
            tileIndex -= tileNumber;
        }
        Tile [] newTiles = new Tile[tiles.length-57];// 14+14+14+15
        for(int i = 0; i < tiles.length-57; i++)
        {
            newTiles[i] = tiles[i];
        }
        tiles = newTiles;
    }

    /*
     * DONE: get the last discarded tile for the current player
     * (this simulates picking up the tile discarded by the previous player)
     * it should return the toString method of the tile so that we can print what we picked
     */
    public String getLastDiscardedTile() {
        players[currentPlayerIndex].addTile(lastDiscardedTile);
        //lastDiscardedTile will be updated to the one this user discards in dicardTile or discardTileForComputer
        return lastDiscardedTile.toString();
    }

    /*
     * DONE: get the top tile from tiles array for the current player
     * that tile is no longer in the tiles array (this simulates picking up the top tile)
     * it should return the toString method of the tile so that we can print what we picked
     */
    public String getTopTile(Player aPlayer) {
        if(aPlayer != null)
        {
            if(tiles.length != 0)
            {
                Tile topTile = tiles[tiles.length-1];
                aPlayer.addTile(topTile);
                Tile [] newTiles = new Tile[tiles.length-1];
                for(int i = 0; i < tiles.length-1; i++)
                {
                    newTiles[i] = tiles[i];
                }
                tiles = newTiles;//top tile has been removed 
                return topTile.toString();
            }
            else//no tiles left to draw
            {
                return null;//will lead to game ending with a tie
            }
        }
        else// player is null, called just to check tie
        {
            if(tiles.length==0)
            {
                return null;
            }
            else
            {
                return "";
            }
        }
    }

    /*
     * should randomly shuffle the tiles array before game starts -done!
     */
    public void shuffleTiles() {
        Random rand = new Random();
        for ( int i = tiles.length - 1; i > 0; i-- ) { // shuffles tiles with the fisher-yates algorithm
            int j = rand.nextInt( i );
            Tile temp = tiles[ j ];
            tiles[ j ] = tiles[ i ];
            tiles[ i ] = temp;
        }
    }

    /*
     * check if game still continues, should return true if current player -done!
     * finished the game, use isWinningHand() method of Player to decide
     */
    public boolean didGameFinish() {
        return players[ currentPlayerIndex ].isWinningHand(); // if player is winnning hand, game over
    }

    /*
     * DONE: Pick a tile for the current computer player using one of the following:
     * - picking from the tiles array using getTopTile()
     * - picking from the lastDiscardedTile using getLastDiscardedTile()
     * You should consider if the discarded tile is useful for the computer in
     * the current status. Print whether computer picks from tiles or discarded ones.
     */
    public void pickTileForComputer() {
        Player currentPlayer = players[currentPlayerIndex];
        Tile[] playerTiles = currentPlayer.getTiles();
        int numTiles = currentPlayer.numberOfTiles;
        if (lastDiscardedTile != null){
            boolean foundDuplicate = false;
            for (int i = 0; i < numTiles - 1 && !foundDuplicate; i++){
                if (playerTiles[i].getValue() == lastDiscardedTile.getValue() && playerTiles[i].getColor() == lastDiscardedTile.getColor()){   
                    foundDuplicate = true;
                }
            }
            if (foundDuplicate && getTopTile(null) != null) {
                currentPlayer.addTile(tiles[tiles.length-1]);
                System.out.println(currentPlayer.getName() + " took a tile from the pile.");
            }
            else {
                int[] valueCounts = new int[8];
                for (int i = 0; i < numTiles; i++){
                    int value = playerTiles[i].getValue();
                    valueCounts[value]++;
                }
                
                if (valueCounts[lastDiscardedTile.getValue()] >= 2 && valueCounts[lastDiscardedTile.getValue()] < 5) {
                    currentPlayer.addTile(lastDiscardedTile);
                    System.out.println(currentPlayer.getName() + " took the discarded tile.");
                }
                else {
                    currentPlayer.addTile(tiles[tiles.length-1]);
                    System.out.println(currentPlayer.getName() + " took a tile from the pile.");
                }
            }
        }
    }

    /*
     * DONE: Current computer player will discard the least useful tile.
     * this method should print what tile is discarded since it should be
     * known by other players. You may first discard duplicates and then
     * the single tiles and tiles that contribute to the smallest chains.
     */
    public void discardTileForComputer() {
        Player currentPlayer = players[currentPlayerIndex];
        Tile[] playerTiles = currentPlayer.getTiles();
        int numTiles = currentPlayer.numberOfTiles;
        
        boolean foundDuplicate = false;
        for (int i = 0; i < numTiles - 1 && !foundDuplicate; i++){
            for (int j = i + 1; j < numTiles && !foundDuplicate; j++){
                if (playerTiles[i].getValue() == playerTiles[j].getValue() && playerTiles[i].getColor() == playerTiles[j].getColor()){
                    lastDiscardedTile = currentPlayer.getAndRemoveTile(j);
                    System.out.println(currentPlayer.getName() + " discards: " + lastDiscardedTile.toString());
                    foundDuplicate = true;
                }
            }
        }
        
        if (!foundDuplicate){
            int[] valueCounts = new int[8];
            
            for (int i = 0; i < numTiles; i++){
                int value = playerTiles[i].getValue();
                valueCounts[value]++;
            }
            
            boolean foundSingle = false;
            for (int i = 0; i < numTiles && !foundSingle; i++){
                int value = playerTiles[i].getValue();
                if (valueCounts[value] == 1){
                    lastDiscardedTile = currentPlayer.getAndRemoveTile(i);
                    System.out.println(currentPlayer.getName() + " discards: " + lastDiscardedTile.toString());
                    foundSingle = true;
                }
            }
            
            if (!foundSingle){
                int[] chainPotentials = new int[numTiles];
                
                for (int i = 0; i < numTiles; i++) {
                    for (int j = 0; j < numTiles; j++) {
                        if (i != j && playerTiles[i].canFormChainWith(playerTiles[j])) {
                            chainPotentials[i]++;
                        }
                    }
                }
                
                int minPotentialIndex = 0;
                for (int i = 1; i < numTiles; i++) {
                    if (chainPotentials[i] < chainPotentials[minPotentialIndex]) {
                        minPotentialIndex = i;
                    }
                }
                
                lastDiscardedTile = currentPlayer.getAndRemoveTile(minPotentialIndex);
                System.out.println(currentPlayer.getName() + " discards: " + lastDiscardedTile.toString());
            }
        }
    }

    /*
     * DONE: discards the current player's tile at given index
     * this should set lastDiscardedTile variable and remove that tile from
     * that player's tiles
     */
    public void discardTile(int tileIndex) {
        lastDiscardedTile = players[currentPlayerIndex].getAndRemoveTile(tileIndex);
        //removes the tile from currentPlayer's tiles AND assigns it to lastDiscardedTile
        displayDiscardInformation();
    }

    public void displayDiscardInformation() {
        if(lastDiscardedTile != null) {
            System.out.println("Last Discarded: " + lastDiscardedTile.toString());
        }
    }

    public void displayCurrentPlayersTiles() {
        players[currentPlayerIndex].displayTiles();
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public Player getCurrentPlayer()
    {
        return players[currentPlayerIndex];
    }

      public String getCurrentPlayerName() {
        return players[currentPlayerIndex].getName();
    }

    public void passTurnToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % 4;
    }

    public void setPlayerName(int index, String name) {
        if(index >= 0 && index <= 3) {
            players[index] = new Player(name);
        }
    }

}
