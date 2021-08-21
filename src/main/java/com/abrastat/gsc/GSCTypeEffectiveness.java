package com.abrastat.gsc;

import com.abrastat.general.Type;

import com.google.common.collect.*;

// Copy this chunk into another package for implementation and modify,
// Or just extend this class and override I suppose

public class GSCTypeEffectiveness    {

    // Make singleton
    private static GSCTypeEffectiveness TE;
    private GSCTypeEffectiveness()  {}
    static  {TE = new GSCTypeEffectiveness();}
    public static GSCTypeEffectiveness getGSCTypeEffectiveness() {
        return TE;
    }

    // Below table is a 2D representation of a type effectiveness chart.
    // Row = attacking type
    // Column = defending type
    // Value = damage multiplier

    private final Table<Type, Type, Double> typeChart =
        HashBasedTable.create();    {
            for(int i = 0; i <= 17; i++)    {
                for(int j = 0; j <= 17; j++)    {
                    typeChart.put(
                        Type.values()[i],
                        Type.values()[j],

                        // deduce attacking type vs defending type *er
                        CalculateTypeEffectiveness(
                                Type.values()[i],
                                Type.values()[j]
                                )
                        );
                    }
                }
    }

    public final double CalculateTypeEffectiveness(Type attackingType, Type defendingType) {
            
        // Manually define each type match-up combination now

        double result = 1.0;
        switch (attackingType) {
            case NONE:
                result = 1.0;
            case NORMAL:
                result = NormalEffectiveness(defendingType);
            case FIRE:
                result = FireEffectiveness(defendingType);
            case WATER:
                result = WaterEffectiveness(defendingType);
            case ELECTRIC:
                result = ElectricEffectiveness(defendingType);
            case GRASS:
                result = GrassEffectiveness(defendingType);
            case ICE:
                result = IceEffectiveness(defendingType);
            case FIGHTING:
                result = FightingEffectiveness(defendingType);
            case POISON:
                result = PoisonEffectiveness(defendingType);
            case GROUND:
                result = GroundEffectiveness(defendingType);
            case FLYING:
                result = FlyingEffectiveness(defendingType);
            case PSYCHIC:
                result = PsychicEffectiveness(defendingType);
            case BUG:
                result = BugEffectiveness(defendingType);
            case ROCK:
                result = RockEffectiveness(defendingType);
            case GHOST:
                result = GhostEffectiveness(defendingType);
            case DRAGON:
                result = DragonEffectiveness(defendingType);
            case DARK:
                result = DarkEffectiveness(defendingType);
            case STEEL:
                result = SteelEffectiveness(defendingType);
            case FAIRY:
                System.out.println("using Fairy type attack in an old gen! " +
                        "is this an error?");
                return 1.0;
        }
        return result;
    }

    private double NormalEffectiveness(Type defendingType) {
            switch(defendingType)   {
                case ROCK:
                case STEEL:
                    return 0.5;

                case GHOST:
                    return 0;
            }
        return 1.0;
    }

    private double FireEffectiveness(Type defendingType) {
            switch(defendingType)   {
                case GRASS:
                case ICE:
                case BUG:
                case STEEL:
                    return 2;

                case FIRE:
                case WATER:
                case ROCK:
                case DRAGON:
                    return 0.5;
            }
        return 1.0;
    }

    private double WaterEffectiveness(Type defendingType) {
        switch(defendingType) {
            case FIRE:
            case GROUND:
            case ROCK:
                return 2;

            case WATER:
            case GRASS:
            case DRAGON:
                return 0.5;
        }
        return 1.0;
    }

    private double ElectricEffectiveness(Type defendingType) {
        switch(defendingType)   {
            case WATER:
            case FLYING:
                return 2;

            case ELECTRIC:
            case GRASS:
            case DRAGON:
                return 0.5;

            case GROUND:
                return 0;
        }
        return 1.0;
    }

    private double GrassEffectiveness(Type defendingType) {
        switch(defendingType) {
            case WATER:
            case GROUND:
            case ROCK:
                return 2;

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

    //TODO
    private double IceEffectiveness(Type defendingType) {
        return 1.0;
    }

    //TODO
    private double FightingEffectiveness(Type defendingType) {
        return 1.0;
    }

    //TODO
    private double PoisonEffectiveness(Type defendingType) {
        return 1.0;
    }

    //TODO
    private double GroundEffectiveness(Type defendingType) {
        return 1.0;
    }

    //TODO
    private double FlyingEffectiveness(Type defendingType) {
        return 1.0;
    }

    //TODO
    private double PsychicEffectiveness(Type defendingType) {
        return 1.0;
    }

    //TODO
    private double BugEffectiveness(Type defendingType) {
        return 1.0;
    }

    //TODO
    private double RockEffectiveness(Type defendingType) {
        return 1.0;
    }

    //TODO
    private double GhostEffectiveness(Type defendingType) {
        return 1.0;
    }

    //TODO
    private double DragonEffectiveness(Type defendingType) {
        return 1.0;
    }

    //TODO
    private double DarkEffectiveness(Type defendingType) {
        return 1.0;
    }

    //TODO
    private double SteelEffectiveness(Type defendingType) {
        return 1.0;
    }

}
