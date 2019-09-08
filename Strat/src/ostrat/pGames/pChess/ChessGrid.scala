package ostrat
package pGames.pChess
import pGrid._

sealed trait Piece

class ChessGrid (val array: Array[Option[Piece]]) extends AnyVal with SqSqArr[Option[Piece]]
{ def size = 8
}
