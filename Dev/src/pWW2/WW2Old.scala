/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWW2
import pEarth._

case class ArmyOld(tile: W2TileAncient, polity: Polity) extends Coloured
{
  def colour = polity.colour
  override def toString = "Army" + (polity.toString).enParenth

  override def equals(other: Any): Boolean = other match
  { case that: ArmyOld => polity == that.polity
    case _ => false
  }
}

/** Primitive WWII scenario using ancient deprecated tile grid system. */
class WWIIScenOld extends EarthAllMap[W2TileAncient, W2SideAncient](W2TileAncient.apply, W2SideAncient.apply)
{
  val fArmy: (W2TileAncient, Polity) => Unit = (tile, p: Polity) => tile.lunits = ArmyOld(tile, p) %: tile.lunits
}

object WW1940Old extends WWIIScenOld
{
  fTiles[Polity](fArmy, (212, 464, Germany), (216, 464, Germany), (210, 462, Britain), (218, 462, Germany), (214, 462, Britain), (216, 460, Britain),
    (218, 458, France))
}