/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pDraughts
import Colour._

sealed class Draught(val colour: Colour) extends AnyRef
object WhiteD extends Draught(White)
object BlackD extends Draught(Black)

sealed trait DraughtsPiece
{
   def colour: Colour
}

case object WhitePiece extends DraughtsPiece { override def colour = White }
case object BlackPiece extends DraughtsPiece { override def colour = Black }