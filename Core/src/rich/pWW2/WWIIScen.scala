/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */package rich
package pWW2
import geom._
import pEarth._

class WWIIScen extends EarthAllMap[W2Tile](W2Tile.apply)
{
   val fArmy: (W2Tile, Polity) => Unit = (tile, p: Polity) =>
      {
         tile.lunits = Army(p, tile.cood) :: tile.lunits
      }
}

object WW1940 extends WWIIScen
{
   fTiles[Polity](fArmy, (212, 464, Germany), (216, 464, Germany), (210, 462, Britain), (218, 462, Germany), (214, 462, Britain),
         (216, 460, Britain),  (218, 458, France))
   
}




