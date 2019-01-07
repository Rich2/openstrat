/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package pChess
import Colour._
import pGrid._

//trait CGrid[T](val arr: Array[T]) extends AnyVal
//{
//  
//}

//object CGrid
//{  
//  def start[T <: GridElem](implicit m: reflect.ClassTag[T]): CGrid[T] = 
//  {
//    val arr = new Array[T](64)
//    new CGrid[T](arr)
//  }
//}

sealed trait Draught extends AnyRef
object NoD extends Draught
object WhiteD extends Draught
object BlackD extends Draught

class DGrid(val arr: Array[Draught]) extends AnyVal
{
  def get(row: Int, col: Int): Draught = arr((row - 1) * 8 + col - 1)
  def set(row: Int, col: Int, value: Draught): Unit = arr((row - 1) * 8 + col - 1) = value
  def copy: DGrid = 
  {
    val newArr = new Array[Draught](64)
    var count = 0
    while( count < 64){ newArr(count) = arr(count); count += 1 }
    new DGrid(newArr)
  }
}

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

case object WhitePiece extends DraughtsPiece { override def colour = White }
case object BlackPiece extends DraughtsPiece { override def colour = Black }

