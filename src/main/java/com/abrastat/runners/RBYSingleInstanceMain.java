package com.abrastat.runners;

import com.abrastat.general.Messages;
import com.abrastat.rby.RBYMove;
import com.abrastat.rby.RBYPokemon;

import java.util.Scanner;

public class RBYSingleInstanceMain {
    public static void main(String[] args) {
        RBYPokemon[] pokemonPlayerOne, pokemonPlayerTwo;
        String speciesP1, speciesP2;
        String moveOneP1, moveTwoP1, moveThreeP1, moveFourP1;
        String moveOneP2, moveTwoP2, moveThreeP2, moveFourP2;
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

        System.out.println("Enter number of simulations to run.");
        simulationCount = scanner.nextInt();

        pokemonPlayerOne = new RBYPokemon[1];
        pokemonPlayerTwo = new RBYPokemon[1];

        pokemonPlayerOne[0] = new RBYPokemon.Builder(speciesP1)
                .moves(RBYMove.valueOf(moveOneP1),
                        RBYMove.valueOf(moveTwoP1),
                        RBYMove.valueOf(moveThreeP1),
                        RBYMove.valueOf(moveFourP1))
                .build();

        pokemonPlayerTwo[0] = new RBYPokemon.Builder(speciesP2)
                .moves(RBYMove.valueOf(moveOneP2),
                        RBYMove.valueOf(moveTwoP2),
                        RBYMove.valueOf(moveThreeP2),
                        RBYMove.valueOf(moveFourP2))
                .build();

        new RBYGameRunner(pokemonPlayerOne, pokemonPlayerTwo, simulationCount);

        Messages.gameOver();
        System.exit(0);
    } // main
} // RBYSingleInstanceMain
