package com.abrastat.gsc;

import com.abrastat.general.Types;

import java.util.HashMap;

import com.google.common.collect.*;

public class TypeEffectiveness {


    private static final HashMap<Integer, ?> GSCTYPES =
            Types.getTypes(2);

    // Row = attacking type
    // Column = defending type
    // Value = damage multiplier
    private final Table<Types, Types, Double> typeChart =
            HashBasedTable.create();



    //quick definition class for type effectiveness in GSC

    public TypeEffectiveness(HashBasedTable gscTypes)    {

    }
}
