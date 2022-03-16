package com.abrastat.runners;

import com.abrastat.general.Item;
import com.abrastat.general.Messages;
import com.abrastat.general.Pokemon;
import static com.abrastat.gsc.GSCMove.*;
import com.abrastat.general.Type;
import com.abrastat.gsc.GSCPokemon;

public class CurseSlamRestTalkSnorlax_VS_ThunderIceRestTalkZapdos {

    public static void main(String[] args) {
        Pokemon snorlax = new GSCPokemon.Builder("snorlax")
                .moves(BODY_SLAM, CURSE, REST, SLEEP_TALK)
                .item(Item.LEFTOVERS)
                .build();

        Pokemon zapdos = new GSCPokemon.Builder("zapdos")
                .moves(THUNDER, HIDDEN_POWER, REST, SLEEP_TALK)
                .hiddenPowerType(Type.ICE)
                .item(Item.LEFTOVERS)
                .build();


        new GSCGameRunner(snorlax, zapdos, 100000);

        Messages.gameOver();
        System.exit(0);
    }
}
