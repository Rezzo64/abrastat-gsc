package com.abrastat.gsc;

import com.abrastat.general.Type;

import com.google.common.collect.*;

// Copy this chunk into another package for implementation and modify,
// Or just extend this class and override I suppose

public final class GSCTypeEffectiveness    {

    // Don't construct this, it's constant
    private GSCTypeEffectiveness()    {}

    // Below table is a 2D representation of a type effectiveness chart.
    // Row = attacking type
    // Column = defending type
    // Value = damage multiplier

    private static final Table<Type, Type, Double> TYPECHART;

    static {
        TYPECHART = HashBasedTable.create();
        for(int i = 0; i <= 17; i++)    {
            for(int j = 0; j <= 17; j++)    {
                TYPECHART.put(
                        Type.values()[i],
                        Type.values()[j],

                        // deduce attacking type vs
                        // defending type dmg factor
                        CalcEffectivenessHelper(
                                Type.values()[i],
                                Type.values()[j]
                        )
                );
            }
        }
    }

    /*  This is the entry method to using the type effectiveness chart and outputs
    *   the effectiveness given an attacking type and two defending types.
    *   If a Pokémon has no secondary typing, set the secondary type to 'NONE'.
    */

    public static double CalcEffectiveness(Type attackingType, Type defendingType1, Type defendingType2) {

        //TODO fix this warning, maybe even cast it as a 2D array for performance rather than unboxing constantly.
        return TYPECHART.get(attackingType, defendingType1)
               *
               TYPECHART.get(attackingType, defendingType2);
    }

    private static double CalcEffectivenessHelper(Type attackingType, Type defendingType) {
            
        // Manually define each type match-up combination now

        double result = 1.0;
        switch (attackingType) {
            case NONE:
                result = 1.0;
                break;
            case NORMAL:
                result = NormalEffectiveness(defendingType);
                break;
            case FIRE:
                result = FireEffectiveness(defendingType);
                break;
            case WATER:
                result = WaterEffectiveness(defendingType);
                break;
            case ELECTRIC:
                result = ElectricEffectiveness(defendingType);
                break;
            case GRASS:
                result = GrassEffectiveness(defendingType);
                break;
            case ICE:
                result = IceEffectiveness(defendingType);
                break;
            case FIGHTING:
                result = FightingEffectiveness(defendingType);
                break;
            case POISON:
                result = PoisonEffectiveness(defendingType);
                break;
            case GROUND:
                result = GroundEffectiveness(defendingType);
                break;
            case FLYING:
                result = FlyingEffectiveness(defendingType);
                break;
            case PSYCHIC:
                result = PsychicEffectiveness(defendingType);
                break;
            case BUG:
                result = BugEffectiveness(defendingType);
                break;
            case ROCK:
                result = RockEffectiveness(defendingType);
                break;
            case GHOST:
                result = GhostEffectiveness(defendingType);
                break;
            case DRAGON:
                result = DragonEffectiveness(defendingType);
                break;
            case DARK:
                result = DarkEffectiveness(defendingType);
                break;
            case STEEL:
                result = SteelEffectiveness(defendingType);
                break;
            case FAIRY:
                System.out.println("using Fairy type attack in an old gen! " +
                        "is this an error?");
                return 1.0;
            default:
                return 1.0;
        }
        return result;
    }

    private static double NormalEffectiveness(Type defendingType) {
            switch(defendingType)   {
                case ROCK:
                case STEEL:
                    return 0.5;

                case GHOST:
                    return 0;
            }
        return 1.0;
    }

    private static double FireEffectiveness(Type defendingType) {
            switch(defendingType)   {
                case GRASS:
                case ICE:
                case BUG:
                case STEEL:
                    return 2.0;

                case FIRE:
                case WATER:
                case ROCK:
                case DRAGON:
                    return 0.5;
            }
        return 1.0;
    }

    private static double WaterEffectiveness(Type defendingType) {
        switch(defendingType) {
            case FIRE:
            case GROUND:
            case ROCK:
                return 2.0;

            case WATER:
            case GRASS:
            case DRAGON:
                return 0.5;
        }
        return 1.0;
    }

    private static double ElectricEffectiveness(Type defendingType) {
        switch(defendingType)   {
            case WATER:
            case FLYING:
                return 2.0;

            case ELECTRIC:
            case GRASS:
            case DRAGON:
                return 0.5;

            case GROUND:
                return 0;
        }
        return 1.0;
    }

    private static double GrassEffectiveness(Type defendingType) {
        switch(defendingType) {
            case WATER:
            case GROUND:
            case ROCK:
                return 2.0;

            case FIRE:
            case GRASS:
            case POISON:
            case FLYING:
            case BUG:
            case DRAGON:
                return 0.5;
        }
        return 1.0;
    }

    private static double IceEffectiveness(Type defendingType) {
        switch(defendingType)   {
            case GRASS:
            case GROUND:
            case FLYING:
            case DRAGON:
                return 2.0;

            case FIRE:
            case WATER:
            case ICE:
            case STEEL:
                return 0.5;
        }
        return 1.0;
    }

    private static double FightingEffectiveness(Type defendingType) {
        switch(defendingType)   {
            case NORMAL:
            case ICE:
            case ROCK:
            case DARK:
            case STEEL:
                return 2.0;

            case POISON:
            case FLYING:
            case PSYCHIC:
            case BUG:
                return 0.5;

            case GHOST:
                return 0;
        }
        return 1.0;
    }

    private static double PoisonEffectiveness(Type defendingType) {
        switch(defendingType)   {
            case GRASS:
                return 2.0;

            case POISON:
            case GROUND:
            case ROCK:
            case GHOST:
                return 0.5;

            case STEEL:
                return 0;
        }
        return 1.0;
    }

    private static double GroundEffectiveness(Type defendingType) {
        switch(defendingType)   {
            case FIRE:
            case ELECTRIC:
            case POISON:
            case ROCK:
            case STEEL:
                return 2.0;

            case GRASS:
            case BUG:
                return 0.5;

            case FLYING:
                return 0;
        }
        return 1.0;
    }

    private static double FlyingEffectiveness(Type defendingType) {
        switch(defendingType)   {
            case GRASS:
            case FIGHTING:
            case BUG:
                return 2.0;

            case ELECTRIC:
            case ROCK:
            case STEEL:
                return 0.5;
        }
        return 1.0;
    }

    private static double PsychicEffectiveness(Type defendingType) {
        switch(defendingType)  {
            case FIGHTING:
            case POISON:
                return 2.0;

            case PSYCHIC:
            case STEEL:
                return 0.5;

            case DARK:
                return 0;
        }
        return 1.0;
    }

    private static double BugEffectiveness(Type defendingType) {
        switch(defendingType)   {
            case GRASS:
            case PSYCHIC:
            case DARK:
                return 2.0;

            case FIRE:
            case FIGHTING:
            case POISON:
            case FLYING:
            case GHOST:
            case STEEL:
                return 0.5;
        }
        return 1.0;
    }

    private static double RockEffectiveness(Type defendingType) {
        switch (defendingType)  {
            case FIRE:
            case ICE:
            case FLYING:
            case BUG:
                return 2.0;

            case FIGHTING:
            case GROUND:
            case STEEL:
                return 0.5;
        }
        return 1.0;
    }

    private static double GhostEffectiveness(Type defendingType) {
        switch (defendingType)  {
            case PSYCHIC:
            case GHOST:
                return 2.0;

            case DARK:
                return 0.5;

            case NORMAL:
                return 0;
        }
        return 1.0;
    }

    private static double DragonEffectiveness(Type defendingType) {
        switch (defendingType)  {
            case DRAGON:
                return 2.0;

            case DARK:
            case STEEL:
                return 0.5;
        }
        return 1.0;
    }

    private static double DarkEffectiveness(Type defendingType) {
        switch (defendingType)  {
            case PSYCHIC:
            case GHOST:
                return 2.0;

            case FIGHTING:
            case DARK:
            case STEEL:
                return 0.5;
        }
        return 1.0;
    }

    private static double SteelEffectiveness(Type defendingType) {
        switch(defendingType)   {
            case ICE:
            case ROCK:
                return 2.0;

            case FIRE:
            case WATER:
            case ELECTRIC:
            case STEEL:
                return 0.5;
        }
        return 1.0;
    }

}
