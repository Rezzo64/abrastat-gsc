package com.abrastat.runners;

import com.abrastat.general.Item;
import com.abrastat.general.Messages;
import com.abrastat.gsc.GSCMove;
import com.abrastat.gsc.GSCPokemon;

public class SingleInstanceMain {

    public static void main(String[] args) {

        new GSCGameRunner(100000);

        Messages.gameOver();
        System.exit(0);

    }
}
