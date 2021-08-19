package com.abrastat;

import java.util.ArrayList;

public final class Type {

    public static final Type NORMAL = new Type();
    public static final Type FIRE = new Type();
    public static final Type WATER = new Type();
    public static final Type ELECTRIC = new Type();
    public static final Type GRASS = new Type();
    public static final Type ICE = new Type();
    public static final Type FIGHTING = new Type();
    public static final Type POISON = new Type();
    public static final Type GROUND = new Type();
    public static final Type FLYING = new Type();
    public static final Type PSYCHIC = new Type();
    public static final Type BUG = new Type();
    public static final Type ROCK = new Type();
    public static final Type GHOST = new Type();
    public static final Type DRAGON = new Type();
    public static final Type DARK = new Type();
    public static final Type STEEL = new Type();
    public static final Type FAIRY = new Type();

    //Cast types to arrayList for use
    private static final ArrayList<Type> ALLTYPES = new ArrayList<Type>()   {
        {
            add(NORMAL);add(FIRE);add(WATER);
            add(ELECTRIC);add(GRASS);add(ICE);
            add(FIGHTING);add(POISON);add(GROUND);
            add(FLYING);add(PSYCHIC);add(BUG);
            add(ROCK);add(GHOST);add(DRAGON);
            add(DARK);add(STEEL);add(FAIRY);
        }};

    public static final int SUPER_EFFECTIVE = 2;
    public static final int NOT_VERY_EFFECTIVE = 1;
    public static final int NON_EFFECTIVE = 0;

    //returns relevant types depending on generation
    public ArrayList<Type> getTypes(int gen)    {
        if (gen < 1)    {
            throw new IllegalArgumentException(
                    "generation value is somehow less than zero! " +
                    "Defaulting to newest generation type chart...");
        }
        if (gen == 1) {
            return new ArrayList<Type>(ALLTYPES.subList(0, 14));
        }
        else if (gen <= 5)  {
            return new ArrayList<Type>(ALLTYPES.subList(0, 14));
        }
        return ALLTYPES;
    }
}
