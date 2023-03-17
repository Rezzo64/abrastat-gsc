abrastat-rby
============

This outlines the differences between GSC and RBY
versions of Pokemon. As well as what needs to be done
and what has been finished.

---

Status: DONE

A single Special stat that is used in place of both
Sp. Atk and Sp. Def. Amnesia raises this stat by two
stages, Growth  raises it by one, and Psychic's
secondary effect lowers the opponent's Special one stage.

---

Status: TODO

The base probability of a critical hit is
(base speed)/512. If the move has a high critical hit
ratio (Slash, Razor Leaf, Crabhammer), the probability
is instead (base speed)/64, capped at 511/512. Also, the
damage of a critical hit is based on double the user's
base Attack or Special. That is, if the move is physical
and the user's Attack has been altered up or down any
number of stages, the damage of the critical hit ignores
this change, and same for special moves and changes to
the user's Special.

---

Status: TODO

If a Pokemon is frozen, it does not thaw unless the
frozen Pokemon is hit by a Fire type attack.

---

Status: TODO

If a Pokemon is asleep from an effect other than Rest,
the Pokemon will wake up between turn 1 to turn 7 of
being asleep, inclusive. Also, on the turn of waking up
from sleep (from any effect), the Pokemon uses its full
turn to wake up and doesn't move.

---

Status: TODO

A Pokemon hit by a trapping move (Wrap, Fire Spin, Bind,
Clamp) is incapable of moving for 2-5 turns, including
the current turn if the trapped Pokemon is slower. A
slow victim may be unable to move indefinitely, so this
is a rather significant part of strategy in RBY.

---

Status: TODO

If a Pokemon with paralysis uses Rest, its reduction to
Speed persists. Similarly, if a Pokemon with a burn uses
Rest, its reduction to Attack persists.

---

Status: TODO

If a stage increase to a stat (such as Amnesia adding +2
stages to the Special stat) would increase that stat to
more than 999, it is increased to 999 instead.

---

Status: TODO

Moves with secondary effects cannot affect Pokémon of
the same type, meaning that Normal-types cannot be
paralyzed by Body Slam, Electric-types can't be
paralyzed by Thunderbolt, Ice-types can't be frozen by
Ice Beam and Blizzard, and Fire-types cannot be burned
by Fire Blast. I assume this also means that the
secondary effect of Psychic doesn't affect
Psychic-types. I am not sure what other secondary
effects this does and does not apply to.

---

Status: TODO

Ice deals neutral damage against Fire, Poison deals 2x
damage against Bug, Bug deals 2x damage against Poison,
and Ghost deals no damage against Psychic.

---

Status: TODO

The chip damage from burn is 1/16 the victim's max HP.

---

Status: TODO

Poison is blocked by Substitute, but sleep and
paralysis are not. Also, if I have a Substitute up and
my opponent uses Selfdestruct or Explosion, my opponent
isn't knocked out.

---

Status: TODO

If a Pokemon tries to use Recover, Softboiled, or Rest
while (maxHP - currentHP) % 256 == 255, the move has no
effect.

---

Status: TODO

Double Kick and Twineedle are competitively relevant in
RBY. These moves hit twice, with the second hit dealing
exactly the same amount of damage as the first
(including critical hit calculation). Since the move
ends if the first hit breaks a Substitute, there is no
reason why we can't double the damage of these moves
after crit calculation.

---

Status: DONE, except HyperBeam

Moves to be changed
* Explosion: power is 170
* Selfdestruct: power is 130
* Double-Edge: power is 100
* Blizzard: accuracy is 90%(?)
* Fire Blast: 30% burn chance
* Rock Slide: no secondary effect
* Seismic Toss and Night Shade: damage is fully
  typeless (ST hits Ghost types; NS hits Normal and
  Psychic types)
* (Thunder: reduced paralysis chance. Thunder is not
  competitively viable in RBY, as it's always
  outclassed by Thunderbolt, so we can just remove it)
* (Hyper Beam: user does not have to recharge if
  opponent is KO'd. It's not clear to me yet that the
  program needs to incorporate this.)

---

Status: TODO

Moves to be implemented that are competitively viable
in RBY and not GSC.
* trapping moves Wrap, Fire Spin, Bind, and Clamp
* two-hit moves Double Kick and Twineedle (Twineedle
  also has a 20% chance of poisoning)
* boosted critical hit chance moves Slash, Razor Leaf,
  and Crabhammer
* Counter
* Bubblebeam, a 65 power, 100%(?) accurate Water-type
  attack with a 33.2% chance to lower the target's
  Speed.
* Hi Jump Kick, an 85 power, 90%(?) accurate
  Fighting-type attack that deals 1HP of damage to the
  user if it misses.
* Light Screen, which, when the user is damaged by a
  Special attack, modifies the user's Special stat as
  Special = (2*Special)%1024, including against the
  triggering attack. Critical hits ignore this modifier.
* Reflect, which is as Light Screen, but with Defense
  and triggered against a Physical attack.
* Screech, a non-damaging 85%(?) accurate move to lower
* opponent's Defense by two stages
* Glare (paralyzes opponent)
* Spore (renders opponent asleep)
* Submission, an 80 power, 80%(?) accurate Fighting
  type version of Double Edge
* Mega Drain (absorb effect, 40 power)
* Super Fang (deals typeless damage equal to half of
  opponent's current HP, rounded down)
* Leech seed, (Low priority) rarely used competitively
  in RBY and thankfully never used competitively on the
  same set as Toxic, because it has a wild interaction
  with Toxic in these games.

---
