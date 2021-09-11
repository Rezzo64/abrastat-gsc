package com.abrastat;

import com.abrastat.general.*;
import com.abrastat.gsc.*;

public class StartGame {


    public static void main(String[] args) {

        System.out.println("Game started...");
        Game currentGame = new GSCGame();

        System.out.println("Calculate type effectiveness fire attack on water type:");
        System.out.println(GSCTypeEffectiveness.CalcEffectiveness(Type.FIRE, Type.WATER, Type.NONE));

    }

}
