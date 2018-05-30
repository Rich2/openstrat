/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */package rich
package p1783
import geom._
import pEarth._

class NapScen extends EarthAllMap[NTile](NTile.apply(_, _, _))
{
   val fCorp: (NTile, Polity) => Unit = (tile, p: Polity) =>
      {
         tile.lunits = Corps(p, tile.cood) :: tile.lunits
      }
}

object Nap1 extends NapScen
{  
   fTiles[Polity](fCorp, (198, 466, Britain), (210, 458, France))
}
