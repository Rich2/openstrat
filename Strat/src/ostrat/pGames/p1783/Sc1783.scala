/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package p1783
import pEarth._
//import pGrid._

class NapScen extends EarthAllMap[NTile, ESideOnly](NTile.apply, ESideOnly.apply)
{
  val fCorp: (NTile, Polity) => Unit = (tile, p: Polity) => tile.lunits = Corps(tile, p) +: tile.lunits
}

object Nap1 extends NapScen
{  
   fTiles[Polity](fCorp, (198, 466, Britain), (210, 458, France))
}
