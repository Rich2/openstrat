package ostrat
package pGames.pTic
import pGrid._

sealed trait Player
object Cross extends Player
object Naught extends Player

class TicGrid(val array: Array[Option[Player]]) extends AnyVal with SqSqArr[Option[Player]]
{ def size = 3
}
