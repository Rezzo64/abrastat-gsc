package com.abrastat.runners;

import com.abrastat.general.Messages;
import com.abrastat.rby.RBYMove;
import com.abrastat.rby.RBYPokemon;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.*;

public class RBYMatchupsMain {
    // this program takes in a text file path
    // the text file must be formatted:
    //
    // pokemonSpecies1
    // pokemonMoveSet1
    // pokemonSpecies2
    // pokemonMoveSet2
    // ...
    // pokemonSpeciesN
    // pokemonMoveSetN
    //
    // where species are valid names of pokemon
    // and move sets are names of moves delimited by spaces
    // empty lines and comments beginning with "#" are ignored
    //
    // if a move inherently has a space,
    // it should use an underscore instead
    // (e.g. double edge becomes double_edge)
    //
    // the number of moves is limited to four
    // unknown moves are defaulted to empty
    //
    // if you are using IntelliJ IDEA CE,
    // the green arrow to the left allows you to
    // Modify Run Configuration
    // and add Program arguments
    public static void main(String[] args) {
        // TODO make this optional argument
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
            // make sure argument is path to readable file
            throw new InputMismatchException(
                    "Argument must be a path to a text file"
            );
        }

        // create pokemon
        int numPokemon = pokemonList.size() / 2;
        if (pokemonList.size() % 2 == 1) {
            // make sure all pokemon have move sets
            // if an even number of pokemon don't have a move set,
            // this exception will not be thrown
            throw new InputMismatchException(
                    "Every Pokemon must have a move set"
            );
        }
        if (numPokemon < 2) {
            // make sure there are at least two sets of pokemon and moves
            throw new InputMismatchException(
                    "Number of Pokemon must be at least 2"
            );
        }

        RBYPokemon[] pokemons = new RBYPokemon[numPokemon];
        String[] names = new String[numPokemon];
        int index = 0;
        for (int i = 0; i < pokemonList.size(); i += 2) {
            String name = pokemonList.get(i);
            RBYMove[] moves = stringToMoves(pokemonList.get(i+1).split(" "));
            RBYPokemon pokemon = buildPokemon(name, moves);

            // format header output
            name = name.substring(0, 1).toUpperCase()
                    + name.substring(1).toLowerCase();
            // make names distinct
            int occurrences = numOccurrences(names, name);
            if (occurrences > 0)
                name += occurrences;

            pokemons[index] = pokemon;
            names[index] = name;
            index++;
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

        // output results
        // json might be better to make a 1d array, 3240 x 3
        // else turn 3d array to 2d csv, 81 x 81 x 3
        // first column pokemon 1
        // first row pokemon 2
        writeCsv(names, matchups);

        Messages.gameOver();
        System.exit(0);
    }

    // read the file
    // return data
    // ignore newlines and comments beginning with "#"
    private static @NotNull ArrayList<String> scan(String path) throws FileNotFoundException {
        ArrayList<String> list = new ArrayList<>();
        File file = new File(path);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().toUpperCase().strip();
            if ((line.length() > 0)
                    && (!Objects.equals(line.charAt(0), '#'))){
                list.add(line);
            }
        }
        scanner.close();
        return list;
    }

    // parse moves
    // return valid move set
    private static RBYMove @NotNull [] stringToMoves (String @NotNull [] moves) {
        if (moves.length > 4) {
            throw new ArrayIndexOutOfBoundsException(
                    "Number of Moves must be at most 4"
            );
        }

        RBYMove[] m = new RBYMove[moves.length];
        for (int i = 0; i < moves.length; i++) {
            try {
                m[i] = RBYMove.valueOf(moves[i]);
            } catch (IllegalArgumentException e) {
                m[i] = RBYMove.EMPTY;   // testing
                // make sure the move is spelled correctly
                // make sure the move is implemented
//                throw new IllegalArgumentException(
//                        "Move " + moves[i] + " does not exist or is not implemented"
//                );
            }
        }
        return m;
    }

    // return RBYPokemon
    // the slowest part of the program
    private static @NotNull RBYPokemon buildPokemon (@NotNull String species, RBYMove @NotNull [] moves) {
        try {
            return new RBYPokemon.Builder(species.toLowerCase())
                    .moves(moves)
                    .build();
        } catch (NullPointerException e) {
            // make sure the species is spelled correctly
            // make sure all pokemon have move sets
            // if a move set is shown,
            // an even number of pokemon may not have a move set
            throw new NullPointerException(
                    "Pokemon " + species + " does not exist"
            );
        }
    }

    // return occurrences
    private static int numOccurrences(String @NotNull [] names, @NotNull String name) {
        int count = 0;
        for (String n : names) {
            if (Objects.equals(name, n))
                count++;
        }
        return count;
    }

    // write to win-loss, wins, and draws
    // probably some way to refactor this
    private static void writeCsv(String @NotNull [] pokemons, int @NotNull [][][] matchups) {
        // lossesCsv is transpose of winsCsv
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

            int index = 0;
            for (int[][] data : matchups) {
                List<StringBuilder> lines = new ArrayList<>();
                StringBuilder line1 = new StringBuilder();
                StringBuilder line2 = new StringBuilder();
                StringBuilder line3 = new StringBuilder();
                lines.add(line1);
                lines.add(line2);
                lines.add(line3);

                // index isn't effectively final
                String pokemon = pokemons[index];
                lines.forEach(line -> {
                    line.append(pokemon);
                    line.append(',');
                });

                for (int i = 0; i < data.length; i++) {
                    // reason why I can't refactor
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
                index++;
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    // adding a row is easy
    // adding a column is not
    private static @NotNull StringBuilder writeHeader(String @NotNull [] pokemons) {
        StringBuilder header = new StringBuilder();
        header.append("Pokemon");
        header.append(',');
        for (int i = 0; i < pokemons.length; i++) {
            header.append(pokemons[i]);
            if (i != pokemons.length - 1) {
                header.append(',');
            }
        }
        header.append("\n");
        return header;
    }
}

