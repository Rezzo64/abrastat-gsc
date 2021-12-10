package com.abrastat.runners;

import com.abrastat.general.Game;
import com.abrastat.general.Messages;
import com.abrastat.gsc.GSCGame;
import com.abrastat.gsc.GSCMove;
import com.abrastat.gsc.GSCPokemon;

import static com.abrastat.general.Game.player1;
import static com.abrastat.general.Game.player2;

public class SlamLaxVersusThunderZapdos {



    public static void main(String[] args) {

        player1.addPokemon(new GSCPokemon("snorlax"));
        player1.getCurrentPokemon().addMoves(new GSCMove("bodySlam"));
        player2.addPokemon(new GSCPokemon("zapdos"));
        player2.getCurrentPokemon().addMoves(new GSCMove("thunder"));
        Game game = new GSCGame();

        while (true)    {
            ((GSCGame) game).initTurn(
            (GSCMove) player1.getCurrentPokemon().getMoves()[0],
            (GSCMove) player2.getCurrentPokemon().getMoves()[0]
            );
            if (((GSCGame) game).someoneFainted())  {
                Messages.gameOver();
                System.exit(0);
            }
        }
    }
}
