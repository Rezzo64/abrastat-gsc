package com.abrastat.runners;

import com.abrastat.database.GSCDatabaseConnection;
import com.abrastat.general.Pokemon;
import com.abrastat.gsc.GSCMove;
import com.abrastat.gsc.GSCPokemon;
import org.apache.commons.lang3.ArrayUtils;

import static com.abrastat.general.Item.LEFTOVERS;
import static com.abrastat.general.Item.THICK_CLUB;
import static com.abrastat.gsc.GSCMove.STRUGGLE;

public class StruggleWars {
    private static final String[] relevantOuPokemon = {
        "snorlax", "zapdos", "cloyster", "raikou", "gengar", "tyranitar", "skarmory",
        "nidoking,", "golem", "exeggutor", "starmie", "machamp", "forretress", "vaporeon",
        "jynx", "steelix", "marowak", "suicune", "umbreon", "rhydon", "misdreavus",
        "heracross", "miltank", "blissey", "tentacruel", "espeon", "alakazam", "jolteon",
        "charizard", "moltres", "smeargle", "dragonite", "quagsire", "houndoom", "porygon2",
        "muk", "meganium", "clefable", "piloswine", "scizor", "kangaskhan", "articuno",
        "shuckle", "aerodactyl", "donphan", "entei", "venusaur", "kingdra", "sandslash",
        "ampharos", "omastar", "tauros", "qwilfish", "ursaring", "typhlosion", "pikachu",
        "jumpluff", "nidoqueen", "gligar", "hitmonlee"
    },
    relevantUuPokemon = {
        "nidoqueen", "granbull", "scyther", "qwilfish", "hypno", "mrmime", "blastoise",
        "electabuzz", "haunter", "slowbro", "jumpluff", "slowking", "omastar", "quagsire",
        "gyarados", "bellossom", "kabutops", "dodrio", "chansey", "magneton", "piloswine",
        "ampharos", "kadabra", "gligar", "pinsir", "sandslash", "politoed", "victreebel",
        "lanturn", "crobat", "girafarig", "electrode", "pikachu", "arcanine", "vileplume",
        "feraligatr", "graveler", "shuckle", "poliwhirl", "magmar", "flareon", "wigglytuff",
        "weezing", "lickitung", "raichu", "poliwrath", "pineco", "sudowoodo", "xatu",
        "raticate", "persian", "dewgong", "dunspare", "ariados"
    },
    relevantNuPokemon = {
        "xatu", "weezing", "primeape", "kingler", "pineco", "gloom", "chinchou",
        "dugtrio", "magnemite", "rapidash", "hitmonlee", "dewgong", "ninetales", "stantler",
        "wigglytuff", "sudowoodo", "octillery", "magmar", "azumarill", "flareon", "lickitung",
        "fearow", "dunsparce", "graveler", "farfetchd", "arbok", "dragonair", "porygon",
        "shuckle", "pupitar", "persian", "houndour", "pidgeot", "ledian", "raticate",
        "exeggcute", "sneasel", "seaking", "mantine", "hitmonchan", "gastly", "corsola",
        "cubone", "slowpoke", "poliwhirl", "bayleef", "machoke", "parasect", "flaaffy",
        "seadra", "furret", "hitmontop", "elekid", "delibird", "sunflora", "tangela",
        "voltorb", "drowzee", "ariados", "venomoth", "murkrow", "tentacool", "noctowl",
        "staryu", "snubbull", "wartortle", "rhyhorn", "bedrill", "omanyte", "charmeleon",
        "abra", "croconaw"
    },
    relevantUbersPokemon = ArrayUtils.addAll(new String[]{
            "mew", "mewtwo", "lugia", "hooh", "celebi"
        }, relevantOuPokemon);


    public static void main (String[] args) {
        GSCDatabaseConnection gscDatabaseConnection = new GSCDatabaseConnection("abrastat-strugglewars");
        for (String speciesPlayerOne : relevantOuPokemon) {
            for (String speciesPlayerTwo : relevantOuPokemon) {

                GSCPokemon pokemonPlayerOne = (GSCPokemon) new GSCPokemon.Builder(speciesPlayerOne)
                        .moves(STRUGGLE)
                        .item(speciesPlayerOne.equals("marowak") ? THICK_CLUB : LEFTOVERS)
                        .build();
                GSCPokemon pokemonPlayerTwo = (GSCPokemon) new GSCPokemon.Builder(speciesPlayerTwo)
                        .moves(STRUGGLE)
                        .item(speciesPlayerTwo.equals("marowak") ? THICK_CLUB : LEFTOVERS)
                        .build();

                

                gscDatabaseConnection.addPokemon(pokemonPlayerOne);
                gscDatabaseConnection.addPokemon(pokemonPlayerTwo);

            }
        }
    }

}
