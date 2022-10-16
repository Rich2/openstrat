/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package p1783
import pEarth._, pGrid._

class NapScen extends EarthAllMap[NTileAncient, ESideOnyAncient](NTileAncient.apply, ESideOnyAncient.apply)
{
  val fCorp: (NTileAncient, Polity) => Unit = (tile, p: Polity) => tile.lunits = Corps(tile, p) %: tile.lunits
}

object Nap1 extends NapScen
{ fTiles[Polity](fCorp, (198, 466, Britain), (210, 458, France))
}