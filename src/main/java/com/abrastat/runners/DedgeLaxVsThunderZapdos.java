package com.abrastat.runners;

import com.abrastat.general.Item;
import com.abrastat.general.Messages;
import com.abrastat.general.Pokemon;
import com.abrastat.gsc.GSCMove;
import com.abrastat.gsc.GSCPokemon;

public class DedgeLaxVsThunderZapdos {

    public static void main(String[] args)  {

        Pokemon snorlax = new GSCPokemon.Builder("snorlax")
                .moves(GSCMove.DOUBLE_EDGE)
                .item(Item.LEFTOVERS)
                .build();

        Pokemon zapdos = new GSCPokemon.Builder("zapdos")
                .moves(GSCMove.THUNDER)
                .item(Item.LEFTOVERS)
                .build();

        new GSCGameRunner(snorlax, zapdos, 100000);

        Messages.gameOver();
        System.exit(0);
    }
}
