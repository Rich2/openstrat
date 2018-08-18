/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package pChess
import Colour._
import pGrid._

class ChessGrid[TileT <: GridElem](length: Int) extends TileGridLike[TileT](1, length, 1, length)
{
   override def xArrLen: Int = length
   val arr = new Array[AnyRef](length *length)
   override def xToInd(x: Int): Int = (x - xTileMin)
   override def yToInd(y: Int): Int = (y  - yTileMin) * xArrLen
}

sealed trait CheckersSq extends GridElem
{
   def colour: Colour
}
object CheckersSq
{
   implicit object CheckerSsqIsType extends IsType[CheckersSq]
   {
      override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[CheckersSq]
      override def asType(obj: AnyRef): CheckersSq = obj.asInstanceOf[CheckersSq]   
   }   
}

case class LightSq(x: Int, y: Int) extends CheckersSq { def colour = Cornsilk }
case class DarkSq(x: Int, y: Int, piece: Option[DraughtsPiece]) extends CheckersSq { def colour = Green }

object DarkSq
{
   def apply(piece: Option[DraughtsPiece]): (Int, Int) => DarkSq= DarkSq(_, _, piece)
}

sealed trait DraughtsPiece
{
   def colour: Colour
}

object WhitePiece extends DraughtsPiece { override def colour = White }
object BlackPiece extends DraughtsPiece { override def colour = Black }

