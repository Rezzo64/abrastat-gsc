package com.abrastat.general;

import static com.abrastat.general.Format.*;

public abstract class Game {
    Player PlayerA, PlayerB;

    // TODO: 20/08/2021 this class member and whole thing doesn't look right.
    // It shouldn't be fixed by initialising Formats as new object instances.
    // Dbl check that it's implemented safely.
    static Format gameFormat;

    public Game() {
        this.PlayerA = PlayerA;
        this.PlayerB = PlayerB;
        //Deciding which gen format to run battle mechanics for
    }

}

