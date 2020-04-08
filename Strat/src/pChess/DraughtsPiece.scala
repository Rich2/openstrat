/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pChess
import Colour._, pGrid._

sealed class Draught(val colour: Colour) extends AnyRef
object WhiteD extends Draught(White)
object BlackD extends Draught(Black)
sealed trait CheckersSq extends GridElemOld
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

case object WhitePiece extends DraughtsPiece { override def colour = White }
case object BlackPiece extends DraughtsPiece { override def colour = Black }