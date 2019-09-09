package ostrat
package pGames.pChess
import geom._, pGrid._, Colour._

sealed trait Piece
object Pawn extends Piece
object Rook extends Piece
{
  def rtLine: Coods = Coods(0 cc 100, 5 cc 100, 30 cc 100, 30 cc 80, 20 cc 80, 30 cc 0, 0 cc 0)
}
object Knight extends Piece
object Bishop extends Piece
object Queen extends Piece
object King extends Piece

sealed trait Player extends WithColour

object PWhite extends Player
{ def colour = White
}

object PBlack extends Player
{ def colour = Black
}

/** Player Piece */
case class PPiece(player: Player, piece: Piece)

class ChessGrid (val array: Array[Option[PPiece]]) extends AnyVal with SqSqArr[Option[PPiece]]
{ type GridT = ChessGrid
  override def thisFac = new ChessGrid(_)
  def size = 8
}
