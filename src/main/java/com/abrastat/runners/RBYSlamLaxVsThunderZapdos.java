package com.abrastat.runners;

import com.abrastat.general.Messages;
import com.abrastat.general.Pokemon;
import com.abrastat.rby.RBYMove;
import com.abrastat.rby.RBYPokemon;

public class RBYSlamLaxVsThunderZapdos {
    public static void main(String[] args) {
        Pokemon snorlax = new RBYPokemon.Builder("snorlax")
                .moves(RBYMove.BODY_SLAM)
                .build();

        Pokemon zapdos = new RBYPokemon.Builder("zapdos")
                .moves(RBYMove.THUNDERBOLT)
                .build();

        new RBYGameRunner(snorlax, zapdos, 1);

        Messages.gameOver();
        System.exit(0);
    } // main
} // RBYSlamLaxVsThunderZapdos
