package com.abrastat.runners

import com.abrastat.general.Item
import com.abrastat.general.Messages.Companion.gameOver
import com.abrastat.general.Type
import com.abrastat.gsc.GSCMove
import com.abrastat.gsc.GSCPokemon
import kotlin.system.exitProcess

object DedgeLaxVsThunderZapdos {
    @JvmStatic
    fun main(args: Array<String>) {
        val snorlax = GSCPokemon.Builder("snorlax")
                .moves(GSCMove.DOUBLE_EDGE)
                .item(Item.LEFTOVERS)
                .build() as GSCPokemon
        val zapdos = GSCPokemon.Builder("zapdos")
                .moves(GSCMove.THUNDER)
                .item(Item.LEFTOVERS)
                .hiddenPowerType(Type.WATER)
                .build() as GSCPokemon
        GSCGameRunner(snorlax, zapdos, 100000)
        gameOver()
        exitProcess(0)
    }
}