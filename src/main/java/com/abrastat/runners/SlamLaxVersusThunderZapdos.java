package com.abrastat.runners;

import com.abrastat.general.Item;
import com.abrastat.general.Messages;
import com.abrastat.gsc.GSCMove;
import com.abrastat.gsc.GSCPokemon;

import static com.abrastat.general.Game.player1;
import static com.abrastat.general.Game.player2;

public class SlamLaxVersusThunderZapdos {

    public static void main(String[] args) {

        GSCGameRunner runner = new GSCGameRunner(player1, player2);

        Messages.gameOver();
        System.exit(0);

    }
}
