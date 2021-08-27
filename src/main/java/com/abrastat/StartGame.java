package com.abrastat;

import com.abrastat.general.*;
import com.abrastat.gsc.*;
import static com.abrastat.general.Format.*;

public class StartGame {


    public static void main(String[] args) {

        System.out.println("Game started...");
        Game currentGame = new GSCGame();
        System.out.println("Type multiplier below");
        System.out.println(GSCTypeEffectiveness.CalculateTypeEffectiveness(Type.FIRE, Type.WATER, Type.NONE));
    }

}
