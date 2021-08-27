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

        Table<String, String, Double> table = HashBasedTable.create();
        table.put("Normal", "Normal", 1.0);
        table.put("Normal", "Ghost", 0.0);
        table.put("Normal", "Fire", 1.0);
        table.put("Normal", "Grass", 1.0);
        table.put("Fire", "Fire", 0.5);
        table.put("Fire", "Grass", 2.0);
        table.put("Electric", "Flying", 2.0);

        System.out.println("Rowmap:");
        System.out.println(table.rowMap());
        System.out.println("Columnmap:");
        System.out.println(table.columnMap());

        System.out.println(table.values());
    }

}
