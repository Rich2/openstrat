package ostrat
package pGames.pChess
import pGrid._

sealed trait Piece

class ChessGrid (val array: Array[Option[Piece]]) extends AnyVal with SqSqArr[Option[Piece]]
{ type GridT = ChessGrid
  override def thisFac = new ChessGrid(_)
  def size = 8
}
