package com.abrastat.gsc;

import static org.junit.jupiter.api.Assertions.*;

import com.abrastat.general.Pokemon;
import com.abrastat.gsc.GSCPokemon;
import com.abrastat.gsc.GSCPokemon.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.json.*;

class GSCPokemonTest {


    @Test
    @DisplayName("Bulbasaur Species Name Test")
    void bulbasaurSpeciesTest()    {
        GSCPokemon bulbasaur = new GSCPokemon("bulbasaur");
        assertEquals("bulbasaur", bulbasaur.getSpecies());
    }
}