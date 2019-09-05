/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package p305
import pEarth._
//import pGrid._

class BcScen extends OldWorldMap[BcTile, ESideOnly](BcTile.apply, ESideOnly.apply)
{
   val fLegion: (BcTile, Polity) => Unit = (tile, p: Polity) =>
      {
         tile.lunits = Legion(p, tile.cood) :: tile.lunits
      }
}

object Bc1 extends BcScen
{  
   fTiles[Polity](fLegion, (198, 466, Rome), (210, 458, Macedon))
}