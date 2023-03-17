package com.abrastat.general

import org.slf4j.LoggerFactory
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.util.*
import javax.json.Json
import javax.json.JsonArray
import javax.json.JsonObject

abstract class Species protected constructor(speciesName: String) {
    //Json mapper variables
    var species: String? = null
        private set
    val allowedAbilities = arrayOfNulls<String>(3)
    var baseHp = 0
        private set
    var baseAttack = 0
        private set
    var baseDefense = 0
        private set
    var baseSpecialAttack = 0
        private set
    var baseSpecialDefense = 0
        private set
    var baseSpeed = 0
        private set
    var baseSpecial = 0
        private set
    var height = 0.0
        private set
    var weight = 0.0
        private set
    @JvmField
    val types = arrayOfNulls<Type>(2)
    private val genderRatio = 0.0

    init {
        val pokedex = "./src/main/resources/pokedex.json"
        var jsonObject: JsonObject? = null
        try {
            val reader = Json.createReader(FileInputStream(pokedex))
            jsonObject = reader.readObject()
        } catch (e: FileNotFoundException) {
            LOGGER.error("Error reading Pokedex file at: {}", pokedex)
        }
        val jsonPokemon = jsonObject!!.getJsonObject(speciesName)
        setSpecies(speciesName)
        val jsonAbilities = jsonPokemon.getJsonObject("abilities")
        val jsonBaseStats = jsonPokemon.getJsonObject("baseStats")
        val jsonTypesArray = jsonPokemon.getJsonArray("types")

        //  this.setAbilities(jsonAbilities);
        setBaseStats(jsonBaseStats)
        setHeight(jsonPokemon)
        setWeight(jsonPokemon)
        setTypes(jsonTypesArray)
    }

    private fun setSpecies(speciesName: String) {
        species = speciesName.substring(0, 1).uppercase(Locale.getDefault()) + speciesName.substring(1).lowercase(Locale.getDefault())
    }

    fun setAllowedAbilities(allowedAbilities: JsonObject) {
        this.allowedAbilities[0] = allowedAbilities.getValue("0").toString()
        this.allowedAbilities[1] = allowedAbilities.getValue("1").toString()
        this.allowedAbilities[2] = allowedAbilities.getValue("H").toString()
    }

    private fun setBaseStats(baseStats: JsonObject) {
        baseHp = baseStats.getInt("hp")
        baseAttack = baseStats.getInt("attack")
        baseDefense = baseStats.getInt("defense")
        baseSpecialAttack = baseStats.getInt("special_attack")
        baseSpecialDefense = baseStats.getInt("special_defense")
        baseSpeed = baseStats.getInt("speed")
        baseSpecial = if (baseStats.isNull("special")) 0 else
            when (baseStats.getString("special")) {
                "special_attack" -> baseSpecialAttack
                "special_defense" -> baseSpecialDefense
                else -> 0
            }
    }

    private fun setHeight(jsonHeight: JsonObject) {
        height = jsonHeight.getJsonNumber("height").doubleValue()
    }

    private fun setWeight(weight: JsonObject) {
        this.weight = weight.getJsonNumber("weight").doubleValue()
    }

    private fun setTypes(types: JsonArray) {
        val type0 = types.getJsonString(0)
                .toString()
                .replace("\"", "")
                .uppercase(Locale.getDefault())
        this.types[0] = Type.valueOf(type0)
        try {
            val type1 = types.getJsonString(1)
                    .toString()
                    .replace("\"", "")
                    .uppercase(Locale.getDefault())
            this.types[1] = Type.valueOf(type1)
        } catch (e: Exception) {
            println("no secondary typing for $species.")
            this.types[1] = Type.NONE
        }
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(Species::class.java)
    }
}