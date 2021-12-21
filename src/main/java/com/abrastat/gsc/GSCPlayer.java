package com.abrastat.gsc;

import com.abrastat.general.Player;

public class GSCPlayer extends Player {

    @Override
    public GSCPokemon getCurrentPokemon()  {
        return (GSCPokemon) this.getPokemon(0);
    }

    @Override
    public GSCMove chooseAttack() {
        return (GSCMove) this.getCurrentPokemon().getMoves()[0];
    }
}
