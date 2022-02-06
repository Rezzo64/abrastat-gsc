package com.abrastat.runners;

import com.abrastat.general.Item;
import com.abrastat.general.Messages;
import com.abrastat.general.Move;
import com.abrastat.general.Pokemon;
import com.abrastat.gsc.GSCMove;
import com.abrastat.gsc.GSCPokemon;

import java.util.Scanner;

public class GSCSingleInstanceMain {



    public static void main(String[] args) {

        Pokemon pokemonPlayerOne, pokemonPlayerTwo;
        String speciesP1, speciesP2;
        String moveOneP1, moveTwoP1, moveThreeP1, moveFourP1;
        String moveOneP2, moveTwoP2, moveThreeP2, moveFourP2;
        String itemP1, itemP2;
        int simulationCount;

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the first POKEMON you would like to simulate:");

        speciesP1 = scanner.nextLine();

        System.out.println("Enter Player 1" + speciesP1 + "'s FIRST move.");

        moveOneP1 = scanner.nextLine();

        System.out.println("Enter Player 1" + speciesP1 + "'s SECOND move.");

        moveTwoP1 = scanner.nextLine();

        System.out.println("Enter Player 1 " + speciesP1 + "'s THIRD move.");

        moveThreeP1 = scanner.nextLine();

        System.out.println("Enter Player 1" + speciesP1 + "'s FOURTH move.");

        moveFourP1 = scanner.nextLine();

        System.out.println("Enter Player 1" + speciesP1 + "'s ITEM.");

        itemP1 = scanner.nextLine();

        System.out.println("Enter the second POKEMON you would like to simulate:");

        speciesP2 = scanner.nextLine();

        System.out.println("Enter Player 2" + speciesP1 + "'s FIRST move.");

        moveOneP2 = scanner.nextLine();

        System.out.println("Enter Player 2" + speciesP1 + "'s SECOND move.");

        moveTwoP2 = scanner.nextLine();

        System.out.println("Enter Player 2 " + speciesP1 + "'s THIRD move.");

        moveThreeP2 = scanner.nextLine();

        System.out.println("Enter Player 2" + speciesP1 + "'s FOURTH move.");

        moveFourP2 = scanner.nextLine();

        System.out.println("Enter Player 2" + speciesP1 + "'s ITEM.");

        itemP2 = scanner.nextLine();

        System.out.println("Enter number of simulations to run.");

        simulationCount = scanner.nextInt();

        pokemonPlayerOne = new GSCPokemon.Builder(speciesP1)
                .moves(GSCMove.valueOf(moveOneP1),
                        GSCMove.valueOf(moveTwoP1),
                        GSCMove.valueOf(moveThreeP1),
                        GSCMove.valueOf(moveFourP1))

                .item(Item.valueOf(itemP1))
                .build();

        pokemonPlayerTwo = new GSCPokemon.Builder(speciesP2)
                .moves(GSCMove.valueOf(moveOneP2),
                        GSCMove.valueOf(moveTwoP2),
                        GSCMove.valueOf(moveThreeP2),
                        GSCMove.valueOf(moveFourP2))

                .item(Item.valueOf(itemP2))
                .build();

        new GSCGameRunner(pokemonPlayerOne, pokemonPlayerTwo, simulationCount);

        Messages.gameOver();
        System.exit(0);

    }
}
