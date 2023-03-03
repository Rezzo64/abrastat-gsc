package com.abrastat.runners;

import com.abrastat.general.Messages;
import com.abrastat.general.Pokemon;
import com.abrastat.rby.RBYMove;
import com.abrastat.rby.RBYPokemon;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
public class RBYMatchupsMain {
    public static void main(String[] args) {
        int simulationCount = 1;

        ArrayList<String> list = new ArrayList<String>();
        try {
            File matchups = new File("./src/main/resources/rby-matchup.txt");
            Scanner scanner = new Scanner(matchups);
            while (scanner.hasNextLine()) {
                list.add(scanner.nextLine().strip());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        for (int i = 0; i < 12960; i += 4) {
            String matchup[] = list.get(i).split(" ");
            String movesP1[] = list.get(i+1).split(" ");
            String movesP2[] = list.get(i+2).split(" ");
            RBYMove m1[] = new RBYMove[movesP1.length];
            RBYMove m2[] = new RBYMove[movesP2.length];
            for (int j = 0; j < movesP1.length; j++) {
                try {
                    m1[j] = RBYMove.valueOf(movesP1[j]);
                } catch (IllegalArgumentException e) {
                    m1[j] = RBYMove.EMPTY;
                }
            }
            for (int j = 0; j < movesP2.length; j++) {
                try {
                    m2[j] = RBYMove.valueOf(movesP2[j]);
                } catch (IllegalArgumentException e) {
                    m2[j] = RBYMove.EMPTY;
                }
            }
            Pokemon pokemonPlayerOne = new RBYPokemon.Builder(matchup[0].toLowerCase())
                    .moves(m1)
                    .build();
            Pokemon pokemonPlayerTwo = new RBYPokemon.Builder(matchup[1].toLowerCase())
                    .moves(m2)
                    .build();
            new RBYGameRunner(pokemonPlayerOne, pokemonPlayerTwo, simulationCount);
        }

        Messages.gameOver();
        System.exit(0);
    }
}
