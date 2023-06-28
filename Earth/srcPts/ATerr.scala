/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import Colour._

trait ATerr extends Coloured

object Plain extends ATerr
{ override def colour: Colour = LightGreen
  override def toString: String = "Plain"
}

object Desert extends ATerr
{ override def colour: Colour = LemonChiffon
  override def toString: String = "Desert"
}

object Taiga extends ATerr
{ override def colour: Colour = DarkCyan
  override def toString: String = "Taiga"
}

object Tundra extends ATerr
{ override def colour: Colour = LightCyan
  override def toString: String = "Tundra"
}

object Hilly extends ATerr
{ override def colour: Colour = Chocolate
  override def toString: String = "Hilly"
}

object Jungles extends ATerr
{ override def colour: Colour = DarkGreen
  override def toString: String = "Jungle"
}

object Mtains extends ATerr
{ override def colour: Colour = Gray
  override def toString: String = "Mountains"
}

object Seas extends ATerr
{ override def colour: Colour = DarkBlue
  override def toString: String = "Seas"
}

object Lake extends ATerr
{ override def colour: Colour = Blue
  override def toString: String = "Lake"
}

object Icy extends ATerr
{ override def colour: Colour = White
  override def toString: String = "Ice"
}