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
     * TODO: distributes the starting tiles to the players
     * player at index 0 gets 15 tiles and starts first
     * other players get 14 tiles
     * this method assumes the tiles are already shuffled
     */
    public void distributeTilesToPlayers() {
        for(int i = 0; i < players.length; i++)
        {
            int tileNumber;//14 or 15 depending on i
            int tileIndex = tiles.length-1;
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
                tileIndex -= tileNumber;
            }
        }
        Tile [] newTiles = new Tile[tiles.length-57];// 14+14+14+15
        for(int i = 0; i < tiles.length-57; i++)
        {
            newTiles[i] = tiles[i];
        }
        tiles = newTiles;
    }

    /*
     * TODO: get the last discarded tile for the current player
     * (this simulates picking up the tile discarded by the previous player)
     * it should return the toString method of the tile so that we can print what we picked
     */
    public String getLastDiscardedTile() {
        players[currentPlayerIndex].addTile(lastDiscardedTile);
        //lastDiscardedTile will be updated to the one this user discards in dicardTile or discardTileForComputer
        return lastDiscardedTile.toString();
    }

    /*
     * TODO: get the top tile from tiles array for the current player
     * that tile is no longer in the tiles array (this simulates picking up the top tile)
     * it should return the toString method of the tile so that we can print what we picked
     */
    public String getTopTile() {
        if(tiles.length != 0)
        {
            Tile topTile = tiles[tiles.length-1];
            players[currentPlayerIndex].addTile(topTile);
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

    /*
     * TODO: should randomly shuffle the tiles array before game starts
     */
    public void shuffleTiles() {

    }

    /*
     * TODO: check if game still continues, should return true if current player
     * finished the game, use isWinningHand() method of Player to decide
     */
    public boolean didGameFinish() {
        return false;
    }

    /*
     * TODO: Pick a tile for the current computer player using one of the following:
     * - picking from the tiles array using getTopTile()
     * - picking from the lastDiscardedTile using getLastDiscardedTile()
     * You should consider if the discarded tile is useful for the computer in
     * the current status. Print whether computer picks from tiles or discarded ones.
     */
    public void pickTileForComputer() {

    }

    /*
     * TODO: Current computer player will discard the least useful tile.
     * this method should print what tile is discarded since it should be
     * known by other players. You may first discard duplicates and then
     * the single tiles and tiles that contribute to the smallest chains.
     */
    public void discardTileForComputer() {
        
    }

    /*
     * TODO: discards the current player's tile at given index
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
