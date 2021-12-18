package com.abrastat.general;

import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;

public final class PlayerPair {

    private final Player playerOne;
    private final Player playerTwo;

    public PlayerPair(@NotNull Player player1, @NotNull Player player2)   {
        this.playerOne = player1;
        this.playerTwo = player2;
    }

    public Player getPlayerOne()    {
        return playerOne;
    }

    public Player getPlayerTwo()    {
        return playerTwo;
    }

    public Player getOpponent(Player thisPlayer) {
        return (playerOne.equals(thisPlayer) ? playerTwo : playerOne);
    }
}
