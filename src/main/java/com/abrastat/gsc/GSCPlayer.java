package com.abrastat.gsc;

import com.abrastat.general.Player;

public class GSCPlayer extends Player {

    @Override
    public GSCPokemon getCurrentPokemon()  {
        return (GSCPokemon) this.getPokemon(0);
    }

    @Override
    public GSCMoves chooseAttack() {
        return (GSCMoves) this.getCurrentPokemon().getMoves()[0];
    }
}
