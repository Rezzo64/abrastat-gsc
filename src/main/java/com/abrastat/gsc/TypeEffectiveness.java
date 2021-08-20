package com.abrastat.gsc;

import com.abrastat.general.Type;

import java.util.HashMap;
import java.util.Map;
import com.google.common.collect.*;

public class TypeEffectiveness {


    private static final HashMap<Integer, Type> ALLTYPES =
            Type.getTypes(2);

    // Row = attacking type
    // Column = defending type
    // Value = damage multiplier
    private final Table<Type, Type, Double> typeChart =
            HashBasedTable.create();



    //quick definition class for type effectiveness in GSC

    public TypeEffectiveness(HashBasedTable allTypes)    {

    }
}
