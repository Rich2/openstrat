/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat; package pWW2
import pEarth._

class WWIIScen extends EarthAllMap[W2TileOld, W2SideOld](W2TileOld.apply, W2SideOld.apply)
{
  val fArmy: (W2TileOld, Polity) => Unit = (tile, p: Polity) => tile.lunits = Army(tile, p) +: tile.lunits
}

object WW1940 extends WWIIScen
{
  fTiles[Polity](fArmy, (212, 464, Germany), (216, 464, Germany), (210, 462, Britain), (218, 462, Germany), (214, 462, Britain), (216, 460, Britain),
      (218, 458, France))   
}