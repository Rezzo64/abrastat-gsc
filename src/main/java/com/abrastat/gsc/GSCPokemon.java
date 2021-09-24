package com.abrastat.gsc;

import com.abrastat.general.Pokemon;

public final class GSCPokemon extends Pokemon {

    private Pokemon[] allMons = new GSCPokemon[250];

    public GSCPokemon() {
        super();
        this.setGSCPokemon();
    }

    public Pokemon getGSCPokemon()  {
        return new GSCPokemon();
    }

    private void setGSCPokemon() {

    }

}
