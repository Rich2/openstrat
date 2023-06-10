/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pchess; package pdraughts
import Colour._

sealed trait Draught extends ShowSimple
{ def colour: Colour
  override def typeStr: String = "Draught"
}

sealed trait LightDraught extends Draught
{ override def colour = White
}

sealed trait DarkDraught extends Draught
{ override def colour = Black
}

sealed trait Man extends Draught
sealed trait King extends Draught

object LightMan extends Man with LightDraught
{ override def str: String = "LightMan"
}

object DarkMan extends Man with DarkDraught
{ override def str: String = "DarkMan"
}

object LightKing extends King with LightDraught
{ override def str: String = "LightKing"
}

object DarkKing extends King with DarkDraught
{ override def str: String = "DarkKing"
}