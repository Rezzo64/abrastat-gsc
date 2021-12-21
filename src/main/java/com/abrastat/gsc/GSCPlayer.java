package com.abrastat.gsc;

import com.abrastat.general.Move;
import com.abrastat.general.Player;
import com.abrastat.general.PlayerBehaviour;
import org.jetbrains.annotations.NotNull;

import static com.abrastat.gsc.GSCMove.*;

public class GSCPlayer extends Player {

    public GSCPlayer()  {
        super();
        this.addPokemon(this.getCurrentPokemon());
    }

    @Override
    public GSCPokemon getCurrentPokemon()  {
        return (GSCPokemon) this.getPokemon(0);
    }

    @Override
    public GSCMove chooseMove(Player opponent) {

        GSCMove chosenMove = EMPTY;

        if (justUseFirstAttack) {
            return (GSCMove) this.getCurrentPokemon().getMoves()[0];
        }

        for (PlayerBehaviour behaviour : behaviours)    {
            chosenMove = chooseMoveHelper(behaviour, opponent);
        }

        return chosenMove;

    }

    // Helper works in ascending order, that is, the lowest behaviour in the switch
    // will be the one who chooses which move to use

    public GSCMove chooseMoveHelper(@NotNull PlayerBehaviour behaviour, Player opponent)    {
        switch (behaviour)  {

            case JUST_ATTACK: // pre-processing damage calculations to choose the strongest attack


                for (Move move : this.getCurrentPokemon().getMoves())    {
                    GSCDamageCalc.calcDamageEstimate()
                }

        }
    }
}
