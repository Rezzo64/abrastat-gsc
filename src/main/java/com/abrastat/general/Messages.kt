package com.abrastat.general

enum class Messages {
    INSTANCE;

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

        private fun logIssue(pokemon: Pokemon, move: Move) {
            // TODO write this to a file or something
            // messageBuffer.write();
        }

        @JvmStatic
        fun announceTeam(player: Player) {
            val s = StringBuilder(player.name + ": " + System.lineSeparator())
            for (pokemon in player.currentTeam) {
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
            when (status) {
                Status.FAINT -> return
                Status.BURN -> messageBuffer = pokemon.species + " is already burned!"
                Status.FREEZE -> messageBuffer = pokemon.species + " is already frozen!"
                Status.PARALYSIS -> messageBuffer = pokemon.species + " is already paralysed!"
                Status.POISON, Status.TOXIC -> messageBuffer = pokemon.species + " is already poisoned!"
                Status.SLEEP -> messageBuffer = pokemon.species + " is already asleep!"
                Status.CONFUSION, Status.FATIGUE -> messageBuffer = pokemon.species + " is already confused!"
                Status.ATTRACT -> messageBuffer = pokemon.species + " is already in love!"
                else -> messageBuffer = "But it failed!"
            }
            handleMessage()
        }

        @JvmStatic
        fun cantAttack(pokemon: Pokemon, status: Status) {
            when (status) {
                Status.PARALYSIS -> messageBuffer = pokemon.species + " is fully paralysed!"
                Status.SLEEP -> messageBuffer = pokemon.species + " is fast asleep!"
                Status.FREEZE -> messageBuffer = pokemon.species + " is frozen solid!"
                Status.CONFUSION, Status.FATIGUE -> messageBuffer = (pokemon.species + " hurt itself in confusion! ("
                        + pokemon.currentHP + "/" + pokemon.statHP + " HP)")

                Status.ATTRACT -> messageBuffer = pokemon.species + " is immobilised by love!"
                else -> messageBuffer = pokemon.species + " can't attack because of its " + status + "!"
            }
            handleMessage()
        }

        @JvmStatic
        fun statusChanged(pokemon: Pokemon, status: Status) {
            when (status) {
                Status.SLEEP -> messageBuffer = pokemon.species + " woke up!"
                Status.FREEZE -> messageBuffer = pokemon.species + " thawed out!"
                Status.CONFUSION, Status.FATIGUE -> messageBuffer = pokemon.species + " snapped out of its confusion!"
                else -> messageBuffer = pokemon.species + "is no longer affected by " + status + "!"
            }
            handleMessage()
        }

        fun logEffect(pokemon: Pokemon, status: Status) {
            when (status) {
                Status.BURN -> messageBuffer = (pokemon.species + " is hurt by its burn! ("
                        + pokemon.currentHP + "/" + pokemon.statHP + " HP)")

                Status.POISON, Status.TOXIC -> messageBuffer = (pokemon.species + " is hurt by poison! ("
                        + pokemon.currentHP + "/" + pokemon.statHP + " HP)")

                Status.CONFUSION, Status.FATIGUE -> messageBuffer = pokemon.species + " is confused!"
                Status.ATTRACT -> messageBuffer = pokemon.species + " is in love with its opponent!"
                else -> messageBuffer = pokemon.species + " is affected by " + status + "!"
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
            if (typeEffectiveness == 0) {
                messageBuffer = "It didn't affect the opponent!"
            } else if (typeEffectiveness < 10) {
                messageBuffer = "It's not very effective..."
            } else if (typeEffectiveness > 10) {
                messageBuffer = "It's super effective!"
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
            when (status) {
                Status.PARALYSIS -> messageBuffer = pokemon.species + " is paralysed! It may be unable to move!"
                Status.SLEEP -> messageBuffer = pokemon.species + " has fallen asleep!"
                Status.FREEZE -> {
                    messageBuffer = pokemon.species + " was frozen!"
                    messageBuffer = pokemon.species + " was burned by the attack!"
                    messageBuffer = pokemon.species + " became confused!"
                    messageBuffer = pokemon.species + " is poisoned!"
                    messageBuffer = pokemon.species + " is badly poisoned!"
                    messageBuffer = pokemon.species + " is afflicted by " + status + "!"
                }

                Status.BURN -> {
                    messageBuffer = pokemon.species + " was burned by the attack!"
                    messageBuffer = pokemon.species + " became confused!"
                    messageBuffer = pokemon.species + " is poisoned!"
                    messageBuffer = pokemon.species + " is badly poisoned!"
                    messageBuffer = pokemon.species + " is afflicted by " + status + "!"
                }

                Status.FATIGUE, Status.CONFUSION -> {
                    messageBuffer = pokemon.species + " became confused!"
                    messageBuffer = pokemon.species + " is poisoned!"
                    messageBuffer = pokemon.species + " is badly poisoned!"
                    messageBuffer = pokemon.species + " is afflicted by " + status + "!"
                }

                Status.POISON -> {
                    messageBuffer = pokemon.species + " is poisoned!"
                    messageBuffer = pokemon.species + " is badly poisoned!"
                    messageBuffer = pokemon.species + " is afflicted by " + status + "!"
                }

                Status.TOXIC -> {
                    messageBuffer = pokemon.species + " is badly poisoned!"
                    messageBuffer = pokemon.species + " is afflicted by " + status + "!"
                }

                else -> messageBuffer = pokemon.species + " is afflicted by " + status + "!"
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
            logIssue(pokemon, move)
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
            messageBuffer = pokemon.toString() + "'s move " + move + "has no defined behaviour, this move will not be used in battle."
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