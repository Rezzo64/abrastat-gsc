package com.abrastat.gsc;

import com.abrastat.general.Pokemon;

public final class GSCPokemon extends Pokemon {

    public GSCPokemon(String speciesName) {
        super(speciesName);
        this.setGSCPokemon();
    }

    public Pokemon getGSCPokemon(String species)  {
        return new GSCPokemon(species);
    }

    private void setGSCPokemon() {

    }

}
