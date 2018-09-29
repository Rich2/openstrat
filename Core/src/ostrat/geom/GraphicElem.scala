/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import Colour.Black

/** The base trait for all objects on a canvas / panel. The objects are recomposed for each frame. The Canvas objects must be recomposed
 *  each time there is a change within the application state or the user view of that application state. */
trait GraphicElem[A] extends Any with Transable[A]

/* Base trait for all passive objects  on a canvas / panel */
trait PaintElem[A] extends Any with GraphicElem[A]

case class ShapeFill(segs: Shape, colour: Colour) extends PaintElem[ShapeFill]
{ override def fTrans(f: Vec2 => Vec2) = ShapeFill(segs.fTrans(f), colour) }

case class ShapeDraw(segs: Shape, lineWidth: Double, colour: Colour = Black) extends PaintElem[ShapeDraw]
{ override def fTrans(f: Vec2 => Vec2) = ShapeDraw(segs.fTrans(f), lineWidth, colour) }

case class ShapeFillDraw(segs: Shape, fillColour: Colour, lineWidth: Double, lineColour: Colour = Black) extends PaintElem[ShapeFillDraw]
{ override def fTrans(f: Vec2 => Vec2) = ShapeFillDraw(segs.fTrans(f), fillColour, lineWidth, lineColour) }

case class LineDraw(xStart: Double, yStart: Double, xEnd: Double, yEnd: Double, lineWidth: Double, colour: Colour) extends
  PaintElem[LineDraw] with CurveLike
{ def typeSym = 'LineDraw
  def str = persist4(xStart, xEnd, lineWidth, colour)
  override def fTrans(f: Vec2 => Vec2) = LineDraw(f(pStart), f(pEnd), lineWidth, colour)  
}
object LineDraw
{
  def apply(pStart: Vec2, pEnd: Vec2, lineWidth: Double, colour: Colour = Black): LineDraw =
    new LineDraw(pStart.x, pStart.y, pEnd.x, pEnd.y, lineWidth, colour)
}

case class LinesDraw(lineSegs: Line2s, lineWidth: Double, colour: Colour = Black) extends PaintElem[LinesDraw]
{ override def fTrans(f: Vec2 => Vec2) = LinesDraw(lineSegs.fTrans(f), lineWidth, colour) }

object LinesDraw
{
  def apply(lineWidth: Double, colour: Colour, lineSegs: Line2 *): LinesDraw = LinesDraw(lineSegs.valueProducts[Line2s], lineWidth, colour)
}

