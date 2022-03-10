/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pchess; package pdraughts
import prid._, psq._

trait DraughtsScen extends ChessLikeScen
{ def draughts: SqCenArrOpt[Draught]
}

object DraughtsStart extends DraughtsScen
{ val turn = 0
  val draughts: SqCenArrOpt[Draught] = grid.newTileArrOpt[Draught]
  def rowCens(y: Int): SqCens = iToMap(ife(y.div4Rem2, 2, 4), 16, 4){c => SqCen(y, c) }
  iToForeach(2, 6, 2){y => rowCens(y).foreach{r => draughts.unsafeSetSome(r, WhiteMan) } }
  iToForeach(16, 12, -2){y => rowCens(y).foreach{r => draughts.unsafeSetSome(r, BlackMan) } }
}
