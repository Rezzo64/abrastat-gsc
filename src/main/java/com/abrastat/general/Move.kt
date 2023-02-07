package com.abrastat.general

interface Move {
//    val moveName: String
    var isAttack: Boolean
    val type: Type
    val maxPp: Int
    val accuracy: Int
    val basePower: Int
    val effect: MoveEffect
}