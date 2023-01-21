/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pnap
import pGrid._

class NapScenld extends EarthAllMap[NapTileOld, ESideOnyAncient](NapTileOld.apply, ESideOnyAncient.apply)
{
  val fCorp: (NapTileOld, Polity) => Unit = (tile, p: Polity) => tile.lunits = CorpsOld(tile, p) %: tile.lunits
}

object Nap1Old extends NapScenld
{ fTiles[Polity](fCorp, (198, 466, Britain), (210, 458, France))
}