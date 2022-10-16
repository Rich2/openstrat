/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package p305
import pEarth._, pGrid._

class BcScenOld extends OldWorldMap[BcTileAncient, ESideOnyAncient](BcTileAncient.apply, ESideOnyAncient.apply)
{ val fLegion: (BcTileAncient, Polity) => Unit = (tile, p: Polity) => { tile.lunits = Legion(p, tile.cood) :: tile.lunits }
}

object Bc1Old extends BcScenOld
{ fTiles[Polity](fLegion, (198, 466, Rome), (210, 458, Macedon))
}