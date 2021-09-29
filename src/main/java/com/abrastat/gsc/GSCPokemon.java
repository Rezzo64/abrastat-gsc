package com.abrastat.gsc;

import com.abrastat.general.Pokemon;

public class GSCPokemon extends Pokemon {
    /*
    // DVs are essentially IVs but halved; 15 is the maximum DV you can have in a stat.
    // Stat Experience is essentially EVs multiplied by 256.
    // Each base stat can be completely filled with Stat Experience to the maximum.
    */
    public GSCPokemon(String speciesName) {
        super(speciesName);
        this.setIvHP(30);
        this.setIvAtk(30);
        this.setIvDef(30);
        this.setIvSpA(30);
        this.setIvSpD(30);
        this.setIvSpe(30);
        this.setEvHP(252);
        this.setEvAtk(252);
        this.setEvDef(252);
        this.setEvSpA(252);
        this.setEvSpD(252);
        this.setEvSpe(252);
    }

}
