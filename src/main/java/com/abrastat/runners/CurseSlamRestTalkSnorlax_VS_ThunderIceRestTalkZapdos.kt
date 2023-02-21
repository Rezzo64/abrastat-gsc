package com.abrastat.runners

import com.abrastat.general.Item
import com.abrastat.general.Messages.Companion.gameOver
import com.abrastat.general.Type
import com.abrastat.gsc.GSCMove
import com.abrastat.gsc.GSCPokemon
import kotlin.system.exitProcess

object CurseSlamRestTalkSnorlax_VS_ThunderIceRestTalkZapdos {
    @JvmStatic
    fun main(args: Array<String>) {
        val snorlax = GSCPokemon.Builder("snorlax")
                .moves(GSCMove.BODY_SLAM, GSCMove.CURSE, GSCMove.REST, GSCMove.SLEEP_TALK)
                .item(Item.LEFTOVERS)
                .build()
        val zapdos = GSCPokemon.Builder("zapdos")
                .moves(GSCMove.THUNDER, GSCMove.HIDDEN_POWER, GSCMove.REST, GSCMove.SLEEP_TALK)
                .hiddenPowerType(Type.ICE)
                .item(Item.LEFTOVERS)
                .build()
        GSCGameRunner(snorlax, zapdos, 1)
        gameOver()
        exitProcess(0)
    }
}