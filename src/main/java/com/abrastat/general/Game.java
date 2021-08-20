package com.abrastat.general;

import static com.abrastat.general.Format.*;

public class Game {
    Player PlayerA, PlayerB;

    // TODO: 20/08/2021 this class member and whole thing doesn't look right.
    // It shouldn't be fixed by initialising Formats as new object instances.
    // Dbl check that it's implemented safely.
    static Format gameFormat = new Format();

    public Game(Format format)   {
        this.PlayerA = PlayerA;
        this.PlayerB = PlayerB;

        //Deciding which gen format to run battle mechanics for
        if (RBY.equals(format)) {
            RBYGame();
        } else if (GSC.equals(format)) {
            GSCGame();
        } else if (ADV.equals(format)) {
            ADVGame();
        }
    }

    public void RBYGame()   {
        gameFormat.setFormatAsRBY();
    }

    public void GSCGame()   {
        gameFormat.setFormatAsGSC();
    }

    public void ADVGame()   {
        gameFormat.setFormatAsADV();
    }
}
