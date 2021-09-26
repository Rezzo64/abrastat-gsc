package com.abrastat;

import com.abrastat.general.*;
import com.abrastat.gsc.*;

import java.util.Timer;

public class StartGame {


    public static void main(String[] args) {

        System.out.println("Game started...");
        Game currentGame = new GSCGame();

        Pokemon steelix = new GSCPokemon("steelix");
        steelix.toString();
    }

}
