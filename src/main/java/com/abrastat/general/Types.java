package com.abrastat.general;

import java.util.HashMap;

public final class Types {

    /* Pokemon types and move types definitions are static and narrow,
    // so I've decided to just implement them in a class rather than
    // reading the JSON and importing them.
    */

    public enum Type {
        NORMAL("Normal"),
        FIRE("Fire"),
        WATER("Water"),
        ELECTRIC("Electric"),
        GRASS("Grass"),
        ICE("Ice"),
        FIGHTING("Fighting"),
        POISON("Poison"),
        GROUND("Ground"),
        FLYING("Flying"),
        PSYCHIC("Psychic"),
        BUG("Bug"),
        ROCK("Rock"),
        GHOST("Ghost"),
        DRAGON("Dragon"),
        DARK("Dark"),
        STEEL("Steel"),
        FAIRY("Fairy");

        Type(String name) {
            this.name();
        }
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

    // Cast types to hashmap for use. TODO Might not need this
    private static final HashMap<Integer, Type> ALLTYPES
            = new HashMap<>() {
        {
            put(0, null);
            put(1, Type.NORMAL);
            put(2, Type.FIRE);
            put(3, Type.WATER);
            put(4, Type.ELECTRIC);
            put(5, Type.GRASS);
            put(6, Type.ICE);
            put(7, Type.FIGHTING);
            put(8, Type.POISON);
            put(9, Type.GROUND);
            put(10, Type.FLYING);
            put(11, Type.PSYCHIC);
            put(12, Type.BUG);
            put(13, Type.ROCK);
            put(14, Type.GHOST);
            put(15, Type.DRAGON);
            put(16, Type.DARK);
            put(17, Type.STEEL);
            put(18, Type.FAIRY);
        }
    };

    static final double SUPER_EFFECTIVE = 2;
    static final double NOT_VERY_EFFECTIVE = 0.5;
    static final double NON_EFFECTIVE = 0;

    //returns relevant types depending on generation
    public static HashMap<Integer, Type> getTypes(int gen)    {

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

    public static HashMap<Integer, Type> getTypes(Format format)   {
        return getTypes(format.hashCode());
    }
}

