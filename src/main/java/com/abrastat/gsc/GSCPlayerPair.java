package com.abrastat.gsc;

import com.abrastat.general.Player;
import org.jetbrains.annotations.NotNull;

public class GSCPlayerPair {

    private final GSCPlayer playerOne;
    private final GSCPlayer playerTwo;

    public GSCPlayerPair(@NotNull GSCPlayer player1, @NotNull GSCPlayer player2)   {
        this.playerOne = player1;
        this.playerTwo = player2;
    }

    public GSCPlayer playerOne() {
        return this.playerOne;
    }

    public GSCPlayer playerTwo() {
        return this.playerTwo;
    }

    public GSCPlayer opponent(GSCPlayer thisPlayer) {
        return (playerOne.equals(thisPlayer) ? playerTwo : playerOne);
    }
}
