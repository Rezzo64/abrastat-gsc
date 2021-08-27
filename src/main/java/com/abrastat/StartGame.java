package com.abrastat;

import com.abrastat.general.*;
import com.abrastat.gsc.*;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import static com.abrastat.general.Format.*;

public class StartGame {


    public static void main(String[] args) {

        System.out.println("Game started...");
        Game currentGame = new GSCGame();

        System.out.println(GSCTypeEffectiveness.TYPECHART.rowMap());

        System.out.println("Calculate type effectiveness fire attack on water type:");
        System.out.println(GSCTypeEffectiveness.CalculateTypeEffectiveness(Type.FIRE, Type.WATER, Type.NONE));

    }

}
