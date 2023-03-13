package com.abrastat.runners;

import com.abrastat.general.Messages;
import com.abrastat.general.Pokemon;
import com.abrastat.rby.RBYMove;
import com.abrastat.rby.RBYPokemon;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.*;

public class RBYMatchupsMain {
    // this program takes in a text file format
    public static void main(String[] args) {
        int simulationCount = 1;

        // read data
        String pokemonPath;
        ArrayList<String> pokemonList;
        if ((args == null) || (args.length == 0)) {
            pokemonPath = "./src/main/resources/rby/matchup/pokemon.txt";
//            pokemonPath = "./src/main/resources/rby/matchup/pokemon2.txt";   // custom matchups
        } else {
            pokemonPath = args[0];
        }
        try {
            pokemonList = scan(pokemonPath);
        } catch (Exception e) {
            throw new InputMismatchException(
                    "Argument must be a path to a text file"
            );
        }

        // create pokemon
        int numPokemon = (int) Math.ceil((double) pokemonList.size() / 3);
        if (numPokemon <= 1) {
            throw new InputMismatchException(
                    "Number of Pokemon must be greater than 1"
            );
        }
        Pokemon[] pokemons = new Pokemon[numPokemon];
        String[] names = new String[numPokemon];

        int p = 0;
        for (int i = 0; i < pokemonList.size(); i += 3) {
            String name = pokemonList.get(i);
            RBYMove[] moves = stringToMoves(pokemonList.get(i+1).split(" "));
            Pokemon pokemon = buildPokemon(name, moves);

            pokemons[p] = pokemon;
            names[p] = name;
            p++;
        }

        // initialize matchups
        int[][][] matchups = new int[numPokemon][numPokemon][3];
        for (int x = 0; x < numPokemon; x++)        // pokemon1
            for (int y = 0; y < numPokemon; y++)    // pokemon2
                for (int z = 0; z < 3; z++)         // wins, losses, draws
                    matchups[x][y][z] = 0;          // diagonal of 0s

        for (int i = 0; i < numPokemon - 1; i++) {
            for (int j = i + 1; j < numPokemon; j++) {
                RBYGameRunner runner = new RBYGameRunner(pokemons[i], pokemons[j], simulationCount);

                // record results
                matchups[i][j][0] = runner.displayP1Wins();
                matchups[i][j][1] = runner.displayP2Wins();
                matchups[i][j][2] = runner.displayDraws();
                matchups[j][i][0] = runner.displayP2Wins();
                matchups[j][i][1] = runner.displayP1Wins();
                matchups[j][i][2] = runner.displayDraws();
            }
        }

        // json might be better to make a 1d array, 3240 x 3
        // else turn 3d array to 2d csv, 81 x 81 x 3
        // first column pokemon 1
        // first row pokemon 2
        writeCsv(names, matchups);

        Messages.gameOver();
        System.exit(0);
    }

    private static ArrayList<String> scan(String path) throws FileNotFoundException {
        ArrayList<String> list = new ArrayList<>();
        File file = new File(path);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            list.add(scanner.nextLine().strip());
        }
        scanner.close();
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

    private static void writeCsv(String[] pokemons, int[][][] matchups) {
        // lossesCSV is transpose of winsCsv
        File winLossCsv = new File("./src/main/resources/rby/matchup/win-loss.csv");
        File winsCsv = new File("./src/main/resources/rby/matchup/wins.csv");
        File drawsCsv = new File("./src/main/resources/rby/matchup/draws.csv");
        try (
            FileWriter fileWriter1 = new FileWriter(winLossCsv);
            FileWriter fileWriter2 = new FileWriter(winsCsv);
            FileWriter fileWriter3 = new FileWriter(drawsCsv)
        ) {
            // add header
            StringBuilder header = writeHeader(pokemons);
            fileWriter1.write(header.toString());
            fileWriter2.write(header.toString());
            fileWriter3.write(header.toString());

            int p = 0;
            for (int[][] data : matchups) {
                List<StringBuilder> lines = new ArrayList<>();
                StringBuilder line1 = new StringBuilder();
                StringBuilder line2 = new StringBuilder();
                StringBuilder line3 = new StringBuilder();
                lines.add(line1);
                lines.add(line2);
                lines.add(line3);

                // p isn't effectively final
                String pokemon = pokemons[p];
                lines.forEach(line -> {
                    line.append(pokemon);
                    line.append(',');
                });

                for (int i = 0; i < data.length; i++) {
                    line1.append(data[i][0] - data[i][1]);
                    line2.append(data[i][0]);
                    line3.append(data[i][2]);
                    if (i != data.length - 1) {
                        lines.forEach(line -> line.append(','));
                    }
                }

                lines.forEach(line -> line.append("\n"));
                fileWriter1.write(line1.toString());
                fileWriter2.write(line2.toString());
                fileWriter3.write(line3.toString());
                p++;
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static StringBuilder writeHeader(String[] pokemons) {
        StringBuilder header = new StringBuilder();
        header.append("Pokemons");
        header.append(',');
        for (String pokemon: pokemons) {
            header.append(pokemon);
            if (!Objects.equals(pokemon, pokemons[pokemons.length - 1])) {
                header.append(',');
            }
        }
        header.append("\n");
        return header;
    }
}

