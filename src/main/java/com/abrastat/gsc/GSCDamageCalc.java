package com.abrastat.gsc;

import com.abrastat.general.Type;
import static com.abrastat.gsc.GSCTypeEffectiveness.CalcEffectiveness;

public final class GSCDamageCalc {

    private Type defendingType1, defendingType2;
    private GSCMove attack;

    private GSCDamageCalc()  {

    }

    public static void calcDamage(GSCPokemon defendingPokemon, GSCMove attack) {
    final double typeEffectiveness =
            CalcEffectiveness(attack.getType(),
                defendingPokemon.getTypes()[0],
                defendingPokemon.getTypes()[1]);
    }

}
