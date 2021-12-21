package com.abrastat.general;

import com.abrastat.gsc.GSCMoves;
import com.abrastat.gsc.GSCPokemon;
import org.jetbrains.annotations.NotNull;

public interface Game {

    static boolean checkPokemonAreNotFainted(@NotNull GSCPokemon attackingPokemon, GSCPokemon defendingPokemon) {
        return attackingPokemon.getCurrentHP() == 0 || defendingPokemon.getCurrentHP() == 0;
    }

    Moves getLastMoveUsed();
    void setLastMoveUsed(GSCMoves move);

}

