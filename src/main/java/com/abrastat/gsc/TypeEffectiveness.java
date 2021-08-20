package com.abrastat.gsc;

import com.abrastat.general.Type;

import com.google.common.collect.*;

public class TypeEffectiveness  {

    // Row = attacking type
    // Column = defending type
    // Value = damage multiplier
    private final Table<Type, Type, Double> typeChart =
            HashBasedTable.create();

    //quick definition class for type effectiveness in GSC

    public TypeEffectiveness(HashBasedTable gscTypes)    {

    }
}
