package ostrat
package pGames.pChess
import pGrid._



sealed trait Player
{
  outer =>
  def isWhite: Boolean
  sealed trait Piece
  { def isWhite: Boolean = outer.isWhite
  }
  object Pawn extends Piece
  object Rook extends Piece
  object Knight extends Piece
  object Bishop extends Piece
  object Queen extends Piece
  object King extends Piece
}

object PWhite extends Player
{
  def isWhite = true
}

object PBlack extends Player
{
  def isWhite: Boolean = false
}

class ChessGrid (val array: Array[Option[Player#Piece]]) extends AnyVal with SqSqArr[Option[Player#Piece]]
{ type GridT = ChessGrid
  override def thisFac = new ChessGrid(_)
  def size = 8
}
