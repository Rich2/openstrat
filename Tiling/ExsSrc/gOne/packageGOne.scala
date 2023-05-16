/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** This is the package for the first example of an extremely simple simultaneous turn tile based game. The game is created purely to illustrate and
 * test fundamental algorithm and software patterns. It is not intended as an interesting game to play. Each Counter can move one hex tile step. Any
 * move to a tile already containing a counter or that one more than one counter is attempting to move to fails. there are hex and square tile
 * versions. The square tile version is identical except that in the square tile world horizontal moves take precedence over perpendicular moves. */
package object gOne
{
  /** Package description [[String]]. */
  def aboutStr: String = """This is the package for the first example of an extremely simple simultaneous turn tile based game. The game is created
  purely to illustrate and test fundamental algorithm and software patterns. It is not intended as an interesting game to play. Each Counter can move
  one hex tile step. Any move to a tile already containing a Counter or that one more than one Counters is attempting to move to fails."""
}