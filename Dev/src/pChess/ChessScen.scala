/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pChess
import pGrid._

trait ChessScen extends ChessLikeScen
{ val turnSeg: Int
  def pieces: TilesArrOpt[PPiece]
}

object ChessStart extends ChessScen
{
  val turnSeg = 0

  val pieces = grid.newTileArrOpt[PPiece]
  val rp = Arr(Rook, Knight, Bishop, King, Queen, Bishop, Knight, Rook)
  rp.iForeach({(i, p) => pieces.mutSetSome(2, i * 2 + 2, PPiece(PWhite, p)) })
  iToForeach(2, 16, 2)(c => pieces.mutSetSome(4, c, PPiece(PWhite, Pawn)))
  rp.iForeach({(i, p) => pieces.mutSetSome(16, i * 2 + 2, PPiece(PBlack, p)) })
  iToForeach(2, 16, 2)(c => pieces.mutSetSome(14, c, PPiece(PBlack, Pawn)))
}