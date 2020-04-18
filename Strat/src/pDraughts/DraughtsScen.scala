/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pDraughts
import pGrid._

trait DraughtsScen extends pChess.ChessLikeScen
{ def draughts: TilesOptRef[Draught]
}

object DraughtsStart extends DraughtsScen
{ val turnSeg = 0
  val draughts: TilesOptRef[Draught] = grid.newTileArrOpt[Draught]
  def rowRoords(y: Int): Roords = iToMap(ife(y.div4Rem2, 2, 4), 16, 4){c => y rr c}
  iToForeach(2, 6, 2){y => rowRoords(y).foreach{r => draughts.mutSetSome(r, WhiteMan) } }
  iToForeach(16, 12, -2){y => rowRoords(y).foreach{r => draughts.mutSetSome(r, BlackMan) } }
}
