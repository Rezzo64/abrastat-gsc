package com.abrastat.runners;

import com.abrastat.general.Item;
import com.abrastat.general.Messages;
import com.abrastat.general.Pokemon;
import com.abrastat.gsc.GSCMoves;
import com.abrastat.gsc.GSCPokemon;

public class SlamLaxVsThunderZapdos {

    public static void main(String[] args) {
        Pokemon snorlax = new GSCPokemon.Builder("snorlax")
                .moves(GSCMoves.BODY_SLAM)
                .item(Item.LEFTOVERS)
                .build();

        Pokemon zapdos = new GSCPokemon.Builder("zapdos")
                .moves(GSCMoves.THUNDER)
                .item(Item.LEFTOVERS)
                .build();


        new GSCGameRunner(snorlax, zapdos, 100000);

        Messages.gameOver();
        System.exit(0);
    }

}
