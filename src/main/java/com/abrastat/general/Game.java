package com.abrastat.general;

import static com.abrastat.general.Format.*;

public class Game {
    Player PlayerA, PlayerB;

    // TODO: 20/08/2021 this class member and whole thing doesn't look right.
    // It shouldn't be fixed by initialising Formats as new object instances.
    // Dbl check that it's implemented safely.
    static Format gameFormat;

    public Game(Format format) {
        this.PlayerA = PlayerA;
        this.PlayerB = PlayerB;

        //Deciding which gen format to run battle mechanics for
        if (RBY.equals(format)) {
            gameFormat = new Format(1);
        } else if (GSC.equals(format)) {
            gameFormat = new Format(2);
        } else if (ADV.equals(format)) {
            gameFormat = new Format(3);
        }
    }

    public Game(int gen)    {
        gameFormat = new Format(gen);
    }

    public Game()   {
        gameFormat = new Format();
    }

}

