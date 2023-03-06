package com.abrastat.runners;

import com.abrastat.general.Messages;
import com.abrastat.general.Pokemon;
import com.abrastat.rby.RBYMove;
import com.abrastat.rby.RBYPokemon;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
public class RBYMatchupsMain {
    public static void main(String[] args) {
        int simulationCount = 1;
        String pokemonPath = "./src/main/resources/rby/matchup-pokemon.txt";
        String matchupPath = "./src/main/resources/rby/matchup.txt";
        ArrayList<String> pokemonList = scan(pokemonPath);
        ArrayList<String> matchupList = scan(matchupPath);

        // 80 is num of lines in r-m-p.txt: unique pokemon in alphabetical order
        // 12960 is num of lines in r-m.txt: unique matchups, moveset of each pokemon, newline
        // probably shouldn't be hard coded, but didn't want to read file twice
        int numPokemon = 81;
        int numMatchups = 3240 * 4;
        int[][][] matchups = new int[numPokemon][numPokemon][3];
        for (int x = 0; x < numPokemon; x++)        // pokemon1
            for (int y = 0; y < numPokemon; y++)    // pokemon2
                for (int z = 0; z < 3; z++)         // wins, losses, draws
                    matchups[x][y][z] = 0;

        for (int i = 0; i < numMatchups; i += 4) {
            // parse the data
            String[] matchup = matchupList.get(i).split(" ");
            RBYMove[] moves1 = stringToMoves(matchupList.get(i+1).split(" "));
            RBYMove[] moves2 = stringToMoves(matchupList.get(i+2).split(" "));

            // create the pokemon and fight
            Pokemon pokemonPlayerOne = buildPokemon(matchup[0], moves1);
            Pokemon pokemonPlayerTwo = buildPokemon(matchup[1], moves2);
            RBYGameRunner runner = new RBYGameRunner(pokemonPlayerOne, pokemonPlayerTwo, simulationCount);

            // record the results
            int index1 = pokemonList.indexOf(matchup[0]);
            int index2 = pokemonList.indexOf(matchup[1]);
            matchups[index1][index2][0] = runner.displayP1Wins();
            matchups[index1][index2][1] = runner.displayP2Wins();
            matchups[index1][index2][2] = runner.displayDraws();
        }

        // json might be better to make a 1d array, 3240 x 3
        // else turn 3d array to 2d csv, 81 x 81 x 3
        File winsCsv = new File("./src/main/resources/rby/matchup-wins.csv");
        File lossesCsv = new File("./src/main/resources/rby/matchup-losses.csv");
        File drawsCsv = new File("./src/main/resources/rby/matchup-draws.csv");
        writeCsv(pokemonList, winsCsv, matchups, 0);
        writeCsv(pokemonList, lossesCsv, matchups, 1);
        writeCsv(pokemonList, drawsCsv, matchups, 2);

        Messages.gameOver();
        System.exit(0);
    }

    private static ArrayList<String> scan(String path) {
        ArrayList<String> list = new ArrayList<>();
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                list.add(scanner.nextLine().strip());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return list;
    }

    private static RBYMove[] stringToMoves (String[] moves) {
        RBYMove[] m = new RBYMove[moves.length];
        for (int j = 0; j < moves.length; j++) {
            try {
                m[j] = RBYMove.valueOf(moves[j]);
            } catch (IllegalArgumentException e) {
                m[j] = RBYMove.EMPTY;
            }
        }
        return m;
    }

    private static Pokemon buildPokemon (String species, RBYMove[] moves) {
        return new RBYPokemon.Builder(species.toLowerCase())
                .moves(moves)
                .build();
    }

    private static void writeCsv(ArrayList<String> pokemons, File csvFile, int[][][] matchups, int mode) {
        StringBuilder header = writeHeader(pokemons);
        try (FileWriter fileWriter = new FileWriter(csvFile)) {
            fileWriter.write(header.toString());
            for (int[][] data : matchups) {
                StringBuilder line = new StringBuilder();
                for (int i = 0; i < data.length; i++) {
                    // would like to have column before this with pokemon
                    line.append(data[i][mode]);
                    if (i != data.length - 1) {
                        line.append(',');
                    }
                }
                line.append("\n");
                fileWriter.write(line.toString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static StringBuilder writeHeader(ArrayList<String> pokemons) {
        StringBuilder header = new StringBuilder();
        for (String pokemon: pokemons) {
            header.append(pokemon);
            if (pokemon != pokemons.get(pokemons.size() - 1)) {
                header.append(',');
            }
        }
        header.append("\n");
        return header;
    }
}
