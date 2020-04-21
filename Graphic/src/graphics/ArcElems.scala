/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import Colour.Black

/** Circular Arc Draw PaintElem. */
final case class CArcDraw(arc: CArc, lineWidth: Double = 2.0, colour: Colour = Black) extends TransSimer
{ type ThisT = CArcDraw
  def mirrorXOffset(yOffset: Double): CArcDraw = ???
  def mirrorYOffset(xOffset: Double): CArcDraw = ???
  def rotateRadians(radians: Double): CArcDraw = ???
  def slate(offset: Vec2): CArcDraw = ???
  def scale(operand: Double): CArcDraw#ThisT = ???
}