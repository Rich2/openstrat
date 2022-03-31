/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pchess
import prid._, psq._

object ChessBoard extends SqGrid(2, 16, 2, 16)

trait ChessLikeScen extends SqGridScen
{ def turn: Int
  implicit val grid: ChessBoard.type = ChessBoard
  def playerSeg: Player = ife(turn.isOdd, PWhite, PBlack)
}

trait ChessScen extends ChessLikeScen
{ val turn: Int
  def pieces: SqCenOptDGrid[PPiece]
}

object ChessStart extends ChessScen
{
  val turn = 0

  val pieces: SqCenOptDGrid[PPiece] = grid.newTileArrOpt[PPiece]
  val rp = Arr(Rook, Knight, Bishop, King, Queen, Bishop, Knight, Rook)
  rp.iForeach({(i, p) => pieces.unsafeSetSome(2, i * 2 + 2, PPiece(PWhite, p)) })
  iToForeach(2, 16, 2)(c => pieces.unsafeSetSome(4, c, PPiece(PWhite, Pawn)))
  rp.iForeach({(i, p) => pieces.unsafeSetSome(16, i * 2 + 2, PPiece(PBlack, p)) })
  iToForeach(2, 16, 2)(c => pieces.unsafeSetSome(14, c, PPiece(PBlack, Pawn)))
}