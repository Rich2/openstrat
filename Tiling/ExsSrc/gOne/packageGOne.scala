/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** This is the package for the first example of an extremely simple simultaneous turn tile based game. The game is created purely to illustrate and
 * test fundamental algorithm and software patterns. It is not intended as an interesting game to play. Each player can move one hex tile step. Any
 * move to a tile already containing a player or that one more than one player is attempting to move to fails. */
package object gOne
{
  /** Package description [[String]]. */
  def aboutStr: String = """This is the package for the first example of an extremely simple simultaneous turn tile based game. The game is created
  purely to illustrate and test fundamental algorithm and software patterns. It is not intended as an interesting game to play. Each player can move
  one hex tile step. Any move to a tile already containing a player or that one more than one player is attempting to move to fails.This is the
  package for the second example of simple simultaneous turn tile based games. It differs from the first in that it is on a square grid and adjacent
  moves take priority over diagonal tile steps."""
}