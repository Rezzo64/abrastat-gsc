package com.abrastat.general;

import java.util.HashMap;

public final class Types {

    /* Pokemon types and move types definitions are static and narrow,
    // so I've decided to just implement them in a class rather than
    // reading the JSON and importing them.
    */


    private static final Types NORMAL = new Types();
    private static final Types FIRE = new Types();
    private static final Types WATER = new Types();
    private static final Types ELECTRIC = new Types();
    private static final Types GRASS = new Types();
    private static final Types ICE = new Types();
    private static final Types FIGHTING = new Types();
    private static final Types POISON = new Types();
    private static final Types GROUND = new Types();
    private static final Types FLYING = new Types();
    private static final Types PSYCHIC = new Types();
    private static final Types BUG = new Types();
    private static final Types ROCK = new Types();
    private static final Types GHOST = new Types();
    private static final Types DRAGON = new Types();
    private static final Types DARK = new Types();
    private static final Types STEEL = new Types();
    private static final Types FAIRY = new Types();

    // Cast types to hashmap for use. TODO Might not need this
    private static final HashMap<Integer, Types> ALLTYPES
            = new HashMap<>() {
        {
            put(0, null);
            put(1, NORMAL);
            put(2, FIRE);
            put(3, WATER);
            put(4, ELECTRIC);
            put(5, GRASS);
            put(6, ICE);
            put(7, FIGHTING);
            put(8, POISON);
            put(9, GROUND);
            put(10, FLYING);
            put(11, PSYCHIC);
            put(12, BUG);
            put(13, ROCK);
            put(14, GHOST);
            put(15, DRAGON);
            put(16, DARK);
            put(17, STEEL);
            put(18, FAIRY);
        }
    };

    static final double SUPER_EFFECTIVE = 2;
    static final double NOT_VERY_EFFECTIVE = 0.5;
    static final double NON_EFFECTIVE = 0;

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

