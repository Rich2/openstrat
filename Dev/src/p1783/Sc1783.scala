/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package p1783
import pEarth._

class NapScen extends EarthAllMap[NTileOld, ESideOldOnly](NTileOld.apply, ESideOldOnly.apply)
{
  val fCorp: (NTileOld, Polity) => Unit = (tile, p: Polity) => tile.lunits = Corps(tile, p) +: tile.lunits
}

object Nap1 extends NapScen
{ fTiles[Polity](fCorp, (198, 466, Britain), (210, 458, France))
}
