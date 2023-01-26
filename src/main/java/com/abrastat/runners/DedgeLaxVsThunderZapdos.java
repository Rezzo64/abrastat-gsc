package com.abrastat.runners;

import com.abrastat.general.Item;
import com.abrastat.general.Messages;
import com.abrastat.general.Pokemon;
import com.abrastat.gsc.GSCMove;
import com.abrastat.gsc.GSCPokemon;

import static com.abrastat.general.Type.WATER;

public class DedgeLaxVsThunderZapdos {

    public static void main(String[] args)  {

        GSCPokemon snorlax = (GSCPokemon) new GSCPokemon.Builder("snorlax")
                .moves(GSCMove.DOUBLE_EDGE)
                .item(Item.LEFTOVERS)
                .build();

        GSCPokemon zapdos = (GSCPokemon) new GSCPokemon.Builder("zapdos")
                .moves(GSCMove.THUNDER)
                .item(Item.LEFTOVERS)
                .hiddenPowerType(WATER)
                .build();

        new GSCGameRunner(snorlax, zapdos, 100000);

        Messages.gameOver();
        System.exit(0);
    }
}
