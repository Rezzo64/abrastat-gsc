## ![abra][logo] [Abrastat RBY][master] ![abra][logo]
Simulate Pokémon Red, Blue, and Yellow
using Java and Kotlin
*****************************************************
### Contributors
* BJ Resultay
* Nathan Arnold
* Sean Roche

File: ```rby/README.md``` <br />
Author: BJ Resultay <br />
Last Updated: 07 Apr 2023
*****************************************************
### About
This package simulates Pokémon RBY, including many
bugs and interactions, as long as the bug did not
crash the game. Specifically only one vs one battles
can be carried out, with each pokemon choosing moves
using a greedy algorithm.

Abrastat RBY was created for Professor Judy Goldsmith
and Nathan Arnold of the University of Kentucky, for
their paper about k-tiered coalition formation games.

### Run Program
If you created a custom program in ```runners```,
plug in ```RBYGameRunner```. If you are the person
after me, just use ```runners/RBYMatchupsMain```.

### TODO
If you are the person after me, basically any
strategy is not implemented at all besides finding
the most damaging move. As I understand, you will be
using machine learning. First, you will need to
actually allow the AI to choose the move, but only
if no move is forced (Go to ```RBYPlayer``` line 49).
I basically did not touch anything with Behaviours.

Several move effects are not implemented: conversion,
disable, metronome, mimic, and mirror move. As I
understand, these moves are not competitive and do
not see play. You can implement them if you want.

Pokemon types use the common Pokédex, so the types
may have changed since Generation 1, specifically
dual types.

Any relevant resources will be in
```resources/rby/matchup```, including the pokemon in
the round-robin and several useful? alternative move
sets.

I added my own wisdom under this. Good luck.
*****************************************************
### Intellij
If you are new to Java and Kotlin, you might find
that compiling the program might be quite the hassle.
Might I recommend [Intellij IDEA Community Edition]
[intellij] for all your Java and Kotlin needs. While
you should play with tools yourself, here are some
you might find helpful to keep your code clean.

#### Auto Fill Names
If you have already declared a variable using lower
camel case, ex: ```val lowerCamelCase```, you can
access this variable by typing ```lCC``` which will
speed up coding. In Java, ```sout``` expands to
```System.out.println();``` if you find typing that
every time annoying.

#### Problems
If you have any syntax or other problems, check there
to clean and optimize your code.

#### Refactor
You can rename files and functions safely over
multiple files. You can also move function arguments
and delete references in strings.

#### Run
Run it normally or debug it. Edit the configuration
to add line arguments.

#### Sort Lines
Found under Edit > Sort Lines, it sorts highlighted
lines in alphabetical order. If you have a list, it
keeps things organized. In Kotlin, a trailing comma
can allow you to sort without worry. If you use this
frequently, try key binding it.

#### TODO
Any time you type ```TODO()``` or ```// TODO```, it
will turn yellow and help you not miss anything from
planning. Comments can be multi lined.
*****************************************************
### GitHub
If you are new to GitHub in general, well you
probably should research that yourself. These are
several commands that I use frequently. Just plug it
into the terminal.

#### git fetch
Finds any changes between remote and local.

#### git pull
Apply changes from remote to local. If you have any
conflicts, fix them yourself.

#### git add .
You could add individual files by replacing ```.```
with the file path, but if you changed twenty files,
it becomes tedious.

#### git status
It outputs a list of files that have been modified.
If you staged files for a commit, it shows untracked
files, so new or deleted files. It's not necessary,
but it gives a physical checklist to double-check you
did not modify a file you didn't mean to.

#### git commit -m "Your message here" -m "Secondary message"
It commits any staged commits with your first
message. The second message is for a longer
description when necessary. It's fine to just use
```git commit -m "Your message here"```

#### git push
Pushes local commits to remote branch. Please
remember not to push to master directly, it makes a
mess.

[logo]: https://user-images.githubusercontent.com/34076225/152662755-8bf4f541-dcdb-456b-9594-dbd5e5b91607.png "abra"
[master]: https://github.com/Rezzo64/abrastat-gsc/tree/master/src/main/java/com/abrastat/rby
[intellij]: https://www.jetbrains.com/idea/
