package com.abrastat.general

class Messages {
    companion object {
        private var messageBuffer: String? = null
        private fun handleMessage() { // handle logging from here and whether it's enabled or not
            println(messageBuffer)
        }

        // For debugging purposes only, records any effects which aren't yet handled.
        @JvmStatic
        fun notImplementedYet(o: Any) {
            messageBuffer = "DEBUG: $o not implemented yet. No effect logged."
            handleMessage()
        }

        @JvmStatic
        fun announceTeam(player: Player) {
            val s = StringBuilder(player.name + ": " + System.lineSeparator())
            for (i in 0 until 6) {
                val pokemon = player.getPokemon(i)
                if (pokemon != null) {
                    s.append(pokemon.species + " ")
                } else {
                    s.append("(-) ")
                }
            }
            s.append(System.lineSeparator())
            messageBuffer = s.toString()
            handleMessage()
        }

        fun announceSwitch(player: Player, pokemon: Pokemon) {
            messageBuffer = player.name + " sent out " + pokemon.species + "!"
            handleMessage()
        }

        @JvmStatic
        fun statusFailed(pokemon: Pokemon, status: Status) {
            messageBuffer = when (status) {
                Status.FAINT -> return
                Status.BURN -> pokemon.species + " is already burned!"
                Status.FREEZE -> pokemon.species + " is already frozen!"
                Status.PARALYSIS -> pokemon.species + " is already paralysed!"
                Status.POISON, Status.TOXIC -> pokemon.species + " is already poisoned!"
                Status.SLEEP -> pokemon.species + " is already asleep!"
                Status.CONFUSION-> pokemon.species + " is already confused!"
                Status.ATTRACT -> pokemon.species + " is already in love!"
                else -> "But it failed!"
            }
            handleMessage()
        }

        @JvmStatic
        fun cantAttack(pokemon: Pokemon, status: Status) {
            messageBuffer = when (status) {
                Status.ATTRACT -> pokemon.species + " is immobilised by love!"
                Status.BIND -> pokemon.species + " is tangled up!"
                Status.CONFUSION -> pokemon.species + " is confused!"
                Status.FLINCH -> pokemon.species + " flinched and couldn't move!"
                Status.FREEZE -> pokemon.species + " is frozen solid!"
                Status.PARALYSIS -> pokemon.species + " is fully paralysed!"
                Status.SLEEP -> pokemon.species + " is fast asleep!"
                else -> pokemon.species + " can't attack because of its " + status + "!"
            }
            handleMessage()
        }

        @JvmStatic
        fun statusChanged(pokemon: Pokemon, status: Status) {
            messageBuffer = when (status) {
                Status.SLEEP -> pokemon.species + " woke up!"
                Status.FREEZE -> pokemon.species + " thawed out!"
                Status.CONFUSION -> pokemon.species + " snapped out of its confusion!"
                else -> pokemon.species + "is no longer affected by " + status + "!"
            }
            handleMessage()
        }

        fun logEffect(pokemon: Pokemon, status: Status) {
            messageBuffer = when (status) {
                Status.ATTRACT -> pokemon.species + " is in love with its opponent!"

                Status.BURN -> (pokemon.species + " is hurt by its burn! ("
                        + pokemon.currentHP + "/" + pokemon.statHP + " HP)")

                Status.LEECHSEED -> (pokemon.species + " is hurt by leech seed! ("
                        + pokemon.currentHP + "/" + pokemon.statHP + " HP)")

                Status.POISON, Status.TOXIC -> (pokemon.species + " is hurt by poison! ("
                        + pokemon.currentHP + "/" + pokemon.statHP + " HP)")

                Status.CONFUSION-> (pokemon.species + " hurt itself in confusion! ("
                        + pokemon.currentHP + "/" + pokemon.statHP + " HP)")

                else -> pokemon.species + " is affected by " + status + "!"
            }
            handleMessage()
        }

        @JvmStatic
        fun leftoversHeal(pokemon: Pokemon) {
            // only display message if there is health actually being restored by leftovers
            if (pokemon.currentHP < pokemon.statHP) {
                messageBuffer = (pokemon.species + " restored health using its Leftovers! ("
                        + pokemon.currentHP + "/" + pokemon.statHP + " HP)")
                handleMessage()
            }
        }

        @JvmStatic
        fun logCriticalHit() {
            messageBuffer = "A critical hit!"
            handleMessage()
        }

        @JvmStatic
        fun logAttack(pokemon: Pokemon, move: Move) {
            messageBuffer = pokemon.species + " used " + move + "!"
            handleMessage()
        }

        @JvmStatic
        fun logTypeEffectiveness(typeEffectiveness: Int) {
            messageBuffer = if (typeEffectiveness == 0) {
                "It didn't affect the opponent!"
            } else if (typeEffectiveness < 1) {
                "It's not very effective..."
            } else if (typeEffectiveness > 1) {
                "It's super effective!"
            } else {
                return
            }
            handleMessage()
        }

        @JvmStatic
        fun logFainted(pokemon: Pokemon) {
            messageBuffer = pokemon.species + " fainted!"
            handleMessage()
        }

        @JvmStatic
        fun logDamageTaken(pokemon: Pokemon, damage: Int) {
            messageBuffer = (pokemon.species + " took " + damage + " hit points of damage. ("
                    + pokemon.currentHP + "/" + pokemon.statHP + " HP)")
            handleMessage()
        }

        @JvmStatic
        fun logMissedAttack(pokemon: Pokemon) {
            messageBuffer = pokemon.species + "'s attack missed!"
            handleMessage()
        }

        @JvmStatic
        fun logNewStatus(pokemon: Pokemon, status: Status) {
            messageBuffer = when (status) {
                Status.BIND -> pokemon.species + " was tangled!"
                Status.BURN -> pokemon.species + " was burned by the attack!"
                Status.CONFUSION -> pokemon.species + " became confused!"
                Status.FREEZE -> pokemon.species + " was frozen!"
                Status.PARALYSIS -> pokemon.species + " is paralysed! It may be unable to move!"
                Status.POISON -> pokemon.species + " is poisoned!"
                Status.SLEEP -> pokemon.species + " has fallen asleep!"
                Status.TOXIC -> pokemon.species + " is badly poisoned!"
                else -> pokemon.species + " is afflicted by " + status + "!"
            }
            handleMessage()
        }

        @JvmStatic
        fun announceTurn(turnNumber: Int) {
            messageBuffer = System.lineSeparator() + "Turn $turnNumber" + System.lineSeparator()
            handleMessage()
        }

        @JvmStatic
        fun gameOver() {
            messageBuffer = "Game over!"
            handleMessage()
        }

        @JvmStatic
        fun displayCurrentHP(pokemon: Pokemon) {
            messageBuffer = pokemon.species +
                    " current HP: " + pokemon.currentHP +
                    " / " + pokemon.statHP
            handleMessage()
        }

        @JvmStatic
        fun ppFailedToDeduct(pokemon: Pokemon, move: Move) {
            messageBuffer = "Failed to identify the move $move on $pokemon. No PP was deducted."
            // TODO log issue
            handleMessage()
        }

        @JvmStatic
        fun logRecoil(pokemon: Pokemon, recoil: Int) {
            messageBuffer = (pokemon.species + " took " + recoil + " hit points of recoil damage! ("
                    + pokemon.currentHP + "/" + pokemon.statHP + " HP)")
            handleMessage()
        }

        @JvmStatic
        fun logNoMoveBehaviourFound(pokemon: Pokemon, move: Move) {
            messageBuffer = "$pokemon's move $move has no defined behaviour."
            handleMessage()
        }

        @JvmStatic
        fun logNoRecoveryMoveFound(pokemon: Pokemon) {
            messageBuffer = ("RECOVER behaviour was passed, but no recovery move was found on "
                    + pokemon + ". Instead, using first move ("
                    + pokemon.moves[0] + ").")
            handleMessage()
        }

        @JvmStatic
        fun logHeal(pokemon: Pokemon) {
            messageBuffer = (pokemon.species + " healed ("
            + pokemon.currentHP + "/" + pokemon.statHP + " HP)")
        }

        @JvmStatic
        fun logRest(pokemon: Pokemon) {
            messageBuffer = "$pokemon slept and became healthy!"
            handleMessage()
        }

        @JvmStatic
        fun cantRestFullHp(pokemon: Pokemon) {
            messageBuffer = "But it failed! ($pokemon's HP is already full)"
            handleMessage()
        }

        @JvmStatic
        fun logCurrentBehaviour(player: Player, behaviour: PlayerBehaviour) {
            messageBuffer = player.toString() + "'s strategy for this simulation with " +
                    player.currentPokemon + " is " + behaviour
            handleMessage()
        }

        @JvmStatic
        fun logStatDrop(pokemon: Pokemon, stat: Stat) {
            messageBuffer = "$pokemon's $stat fell!"
            handleMessage()
        }

        @JvmStatic
        fun logStatRaise(pokemon: Pokemon, stat: Stat) {
            messageBuffer = "$pokemon's $stat rose!"
            handleMessage()
        }

        @JvmStatic
        fun logStatSharplyDrop(pokemon: Pokemon, stat: Stat) {
            messageBuffer = "$pokemon's $stat sharply fell!"
            handleMessage()
        }

        @JvmStatic
        fun logStatSharplyRaise(pokemon: Pokemon, stat: Stat) {
            messageBuffer = "$pokemon's $stat sharply rose!"
            handleMessage()
        }

        @JvmStatic
        fun logStatWontGoAnyLower(pokemon: Pokemon, stat: Stat) {
            messageBuffer = "$pokemon's $stat won't go any lower!"
            handleMessage()
        }

        @JvmStatic
        fun logStatWontGoAnyHigher(pokemon: Pokemon, stat: Stat) {
            messageBuffer = "$pokemon's $stat won't go any higher!"
            handleMessage()
        }
    }
}