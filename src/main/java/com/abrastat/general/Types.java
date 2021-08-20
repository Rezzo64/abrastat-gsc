package com.abrastat.general;

import java.util.HashMap;

public final class Type {

    /* Pokemon types and move types definitions are static and narrow,
    // so I've decided to just implement them in a class rather than
    // reading the JSON and importing them.
    */

    enum Types  {
        NORMAL,
        FIRE,
        WATER,
        ELECTRIC,
        GRASS,
        ICE,
        FIGHTING,
        POISON,
        GROUND,
        FLYING,
        PSYCHIC,
        BUG,
        ROCK,
        GHOST,
        DRAGON,
        DARK,
        STEEL,
        FAIRY
    }
//    private static final Type NORMAL = new Type();
//    private static final Type FIRE = new Type();
//    private static final Type WATER = new Type();
//    private static final Type ELECTRIC = new Type();
//    private static final Type GRASS = new Type();
//    private static final Type ICE = new Type();
//    private static final Type FIGHTING = new Type();
//    private static final Type POISON = new Type();
//    private static final Type GROUND = new Type();
//    private static final Type FLYING = new Type();
//    private static final Type PSYCHIC = new Type();
//    private static final Type BUG = new Type();
//    private static final Type ROCK = new Type();
//    private static final Type GHOST = new Type();
//    private static final Type DRAGON = new Type();
//    private static final Type DARK = new Type();
//    private static final Type STEEL = new Type();
//    private static final Type FAIRY = new Type();

    // Cast types to hashmap for use
    private static final HashMap<Integer, Types> ALLTYPES
            = new HashMap<>() {
        {
            put(0, null);
            put(1, Types.NORMAL);
            put(2, Types.FIRE);
            put(3, Types.WATER);
            put(4, Types.ELECTRIC);
            put(5, Types.GRASS);
            put(6, Types.ICE);
            put(7, Types.FIGHTING);
            put(8, Types.POISON);
            put(9, Types.GROUND);
            put(10, Types.FLYING);
            put(11, Types.PSYCHIC);
            put(12, Types.BUG);
            put(13, Types.ROCK);
            put(14, Types.GHOST);
            put(15, Types.DRAGON);
            put(16, Types.DARK);
            put(17, Types.STEEL);
            put(18, Types.FAIRY);
        }
    };

    public static final double SUPER_EFFECTIVE = 2;
    public static final double NOT_VERY_EFFECTIVE = 0.5;
    public static final double NON_EFFECTIVE = 0;

    //returns relevant types depending on generation
    public static HashMap<Integer, Types> getTypes(int gen)    {

        if (gen > 5)    {
            return ALLTYPES;
        }

        try{
            if (gen < 1)    {
                throw new IllegalArgumentException(
                        "generation value is somehow less than 1! " +
                        "Defaulting to the newest generation type list.");
            }
        }   catch(IllegalArgumentException e)   {
                return ALLTYPES;
        }


        if (gen == 1) {

            // remove dark & ghost for RBY
            ALLTYPES.remove(16);
            ALLTYPES.remove(17);
        }

        if (gen <= 5)  {

            // remove fairy for gens 1-5
            ALLTYPES.remove(18);
        }
        return ALLTYPES;
    }

    public static HashMap<Integer, Types> getTypes(Format format)   {
        return getTypes(format.hashCode());
    }
}

