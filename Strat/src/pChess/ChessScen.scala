package ostrat
package pChess
import pGrid._

trait ChessScen extends ChessLikeScen
{ val turnSeg: Int
  implicit def grid: SquareGrid
  def pieces: OptRefs[PPiece]
}

object ChessStart extends ChessScen
{
  val turnSeg = 0

  val pieces = grid.newOptRefs[PPiece]
  val rp = Refs(Rook, Knight, Bishop, King, Queen, Bishop, Knight, Rook)
  rp.iForeach{(p, i) => pieces.gridUnsafeSetSome(2, i * 2 + 2, PPiece(PWhite, p)) }
  iToForeach(2, 16, 2)(c => pieces.gridUnsafeSetSome(4, c, PPiece(PWhite, Pawn)))
  rp.iForeach{(p, i) => pieces.gridUnsafeSetSome(16, i * 2 + 2, PPiece(PBlack, p)) }
  iToForeach(2, 16, 2)(c => pieces.gridUnsafeSetSome(14, c, PPiece(PBlack, Pawn)))
}