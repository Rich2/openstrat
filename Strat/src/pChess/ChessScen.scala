package ostrat
package pChess
import pGrid._

trait ChessScen extends ChessLikeScen
{ val turnSeg: Int
  implicit def grid: SquareGridSimple
  def pieces: TilesArrOpt[PPiece]
}

object ChessStart extends ChessScen
{
  val turnSeg = 0

  val pieces = grid.newTileArrOpt[PPiece]
  val rp = Arr(Rook, Knight, Bishop, King, Queen, Bishop, Knight, Rook)
  rp.iForeach{(p, i) => pieces.mutSetSome(2, i * 2 + 2, PPiece(PWhite, p)) }
  iToForeach(2, 16, 2)(c => pieces.mutSetSome(4, c, PPiece(PWhite, Pawn)))
  rp.iForeach{(p, i) => pieces.mutSetSome(16, i * 2 + 2, PPiece(PBlack, p)) }
  iToForeach(2, 16, 2)(c => pieces.mutSetSome(14, c, PPiece(PBlack, Pawn)))
}