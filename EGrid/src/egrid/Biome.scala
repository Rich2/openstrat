/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import Colour._

trait Biome
{ def colour: Colour
  def str: String
  override def toString: String = str
}

case object Temperate extends Biome
{ def colour: Colour = LightGreen
  def str = "Open Ground"
}


case object Desert extends Biome
{ override def str = "Desert"
  override def colour = LemonChiffon
}

/** Savannah, steppe and prairie biome. */
case object Savannah extends Biome
{ override def str = "Savannah"
  override def colour = LightGreen
}

/** Stand in for tropical forest. */
object Tropical extends Biome
{ override def str = "Jungle"
  override def colour = DarkGreen
}

object IceCap extends Biome
{ override def str = "IceCap"
  override def colour = White
}

/** Permanent all year round sea ice. */
object SeaIce extends WTile
{ override def str = "SeaIce"
  override def colour = White
  override def isLand: Boolean = false
}

/** Winter sea ice. */
object WSeaIce extends Water
{ override def str = "WSeaIce"
  override def colour = LightSkyBlue.average(White).average(White)
  override def isLand: Boolean = false
}

case object Taiga extends Biome
{ override def str = "Taiga"
  override def colour = DarkCyan
}

case object Tundra extends Biome
{ override def str = "Tundra"
  override def colour = Plum.average(Thistle)
}