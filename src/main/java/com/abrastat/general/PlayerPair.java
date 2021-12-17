package com.abrastat.general;

import java.util.HashMap;
import java.util.Set;

public class PlayerPair {

    private final Player playerOne;
    private final Player playerTwo;

    public PlayerPair(Player player1, Player player2)   {
        assert player1 != null;
        assert player2 != null;

        this.playerOne = player1;
        this.playerTwo = player2;
    }

    public Player getPlayerOne()    {
        return playerOne;
    }

    public Player getPlayerTwo()    {
        return playerTwo;
    }

    public Player getOtherPlayer(Player thisPlayer) {
        return thisPlayer;
    }
}
