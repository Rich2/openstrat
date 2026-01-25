/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pchess
import prid.psq.*

object ChessBoard extends SqGrid(2, 16, 2, 16)

trait ChessLikeScen extends SqGridScen
{ override def title: String = "Chess"

  def turn: Int
  implicit val gridSys: ChessBoard.type = ChessBoard
  def playerSeg: Player = ife(turn.isOdd, PWhite, PBlack)
}

trait ChessScen extends ChessLikeScen
{ val turn: Int

  /** Layer of optional Chess pieces. */
  def oPieces: SqCenOptLayer[PPiece]
}

object ChessStart extends ChessScen
{
  val turn = 0

  val oPieces: SqCenOptLayer[PPiece] = gridSys.newSCenOptDGrider[PPiece]
  val rp: RArr[MirrorPiece] = RArr(Rook, Knight, Bishop, King, Queen, Bishop, Knight, Rook)
  rp.iForeach({(i, p) => oPieces.unsafeSetSome(2, i * 2 + 2, PPiece(PWhite, p)) })
  iToForeach(2, 16, 2)(c => oPieces.unsafeSetSome(4, c, PPiece(PWhite, Pawn)))
  rp.iForeach({(i, p) => oPieces.unsafeSetSome(16, i * 2 + 2, PPiece(PBlack, p)) })
  iToForeach(2, 16, 2)(c => oPieces.unsafeSetSome(14, c, PPiece(PBlack, Pawn)))
}