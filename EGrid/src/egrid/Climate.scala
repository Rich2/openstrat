/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import Colour._

/** Describes climate and biome. */
trait Climate
{ def colour: Colour
  def str: String
  override def toString: String = str
}

/** Temperate climate with out intense dry season. */
case object Temperate extends Climate
{ def colour: Colour = LightGreen
  def str = "Open Ground"
}

/** Desert climate and biome. */
case object Desert extends Climate
{ override def str = "Desert"
  override def colour = LemonChiffon
}

/** Semi Desert climate and biome. */
case object Sahel extends Climate
{ override def str = "Sahel"
  override def colour = LemonChiffon.average(YellowGreen)
}

/** Savannah, steppe and prairie biome. */
case object Savannah extends Climate
{ override def str = "Savannah"
  override def colour = YellowGreen
}

/** Stand in for tropical forest. */
object Tropical extends Climate
{ override def str = "Jungle"
  override def colour = DarkGreen
}

object IceCap extends Climate
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

case object Taiga extends Climate
{ override def str = "Taiga"
  override def colour = DarkCyan
}

case object Tundra extends Climate
{ override def str = "Tundra"
  override def colour = Plum.average(Thistle)
}