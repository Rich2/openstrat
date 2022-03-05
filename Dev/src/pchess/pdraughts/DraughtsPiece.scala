/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pchess; package pdraughts
import geom._, Colour._

sealed trait Draught
{ def colour: Colour}
sealed trait WhiteDraught extends Draught { override def colour = White }
sealed trait BlackDraught extends Draught { override def colour = Black }
sealed trait Man extends Draught
sealed trait King extends Draught
object WhiteMan extends Man with WhiteDraught
object BlackMan extends Man with BlackDraught
object WhiteKing extends King with WhiteDraught
object BlackKing extends King with BlackDraught