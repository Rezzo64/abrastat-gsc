package com.abrastat.general

interface Move {
    fun name(): String
    var isAttack: Boolean
    fun type(): Type
    fun maxPp(): Int
    fun accuracy(): Int
    fun basePower(): Int
    fun effect(): MoveEffect
}