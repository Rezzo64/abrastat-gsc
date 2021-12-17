package com.abrastat.general;

import com.abrastat.gsc.GSCMove;
import com.abrastat.gsc.GSCPokemon;
import org.jetbrains.annotations.NotNull;

public interface Game {

    static boolean checkPokemonAreNotFainted(@NotNull GSCPokemon attackingPokemon, GSCPokemon defendingPokemon) {
        return attackingPokemon.getCurrentHP() == 0 || defendingPokemon.getCurrentHP() == 0;
    }

    Move getLastMoveUsed();
    void setLastMoveUsed(GSCMove move);

}

