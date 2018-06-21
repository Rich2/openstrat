/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich
package pGames
package p305
import pEarth._
//import pGrid._

class BCScen extends OldWorldMap[BCTile](BCTile.apply(_, _, _))
{
   val fLegion: (BCTile, Polity) => Unit = (tile, p: Polity) =>
      {
         tile.lunits = Legion(p, tile.cood) :: tile.lunits
      }
}

object BC1 extends BCScen
{  
   fTiles[Polity](fLegion, (198, 466, Rome), (210, 458, Macedon))
}