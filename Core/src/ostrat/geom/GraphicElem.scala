/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import Colour.Black

/** The base trait for all objects on a canvas / panel. The objects are recomposed for each frame. The Canvas objects must be recomposed
 *  each time there is a change within the application state or the user view of that application state. */
trait GraphicElem[A] extends Any with Transable[A]
{ def layer: Int
}

/* Base trait for all passive objects  on a canvas / panel */
trait PaintElem[A] extends Any with GraphicElem[A]

case class ShapeFill(shape: Shape, colour: Colour, layer: Int = 0) extends PaintElem[ShapeFill]// with Stringer
{ def typeSym = 'ShapeFill
  //def str = persist2(shape, colour)
  override def fTrans(f: Vec2 => Vec2) = ShapeFill(shape.fTrans(f), colour, layer)  
}

case class ShapeDraw(segs: Shape, lineWidth: Double, colour: Colour = Black, layer: Int = 0) extends PaintElem[ShapeDraw]
{ override def fTrans(f: Vec2 => Vec2) = ShapeDraw(segs.fTrans(f), lineWidth, colour, layer) }

case class ShapeFillDraw(segs: Shape, fillColour: Colour, lineWidth: Double, lineColour: Colour = Black, layer: Int = 0) extends
PaintElem[ShapeFillDraw]
{
  override def fTrans(f: Vec2 => Vec2) = ShapeFillDraw(segs.fTrans(f), fillColour, lineWidth, lineColour, layer)
}

case class LineDraw(xStart: Double, yStart: Double, xEnd: Double, yEnd: Double, lineWidth: Double, colour: Colour, layer: Int) extends
  PaintElem[LineDraw] with CurveLike
{
  def typeSym = 'LineDraw
  def str = persist4(xStart, xEnd, lineWidth, colour)
  override def fTrans(f: Vec2 => Vec2) = LineDraw(f(pStart), f(pEnd), lineWidth, colour, layer)  
}

object LineDraw
{
  def apply(pStart: Vec2, pEnd: Vec2, lineWidth: Double, colour: Colour = Black, layer: Int = 0): LineDraw =
    new LineDraw(pStart.x, pStart.y, pEnd.x, pEnd.y, lineWidth, colour, layer)
}

case class LinesDraw(lineSegs: Line2s, lineWidth: Double, colour: Colour = Black, layer: Int = 0) extends PaintElem[LinesDraw]
{ override def fTrans(f: Vec2 => Vec2) = LinesDraw(lineSegs.fTrans(f), lineWidth, colour, layer)
}

object LinesDraw
{
  def apply(lineWidth: Double, colour: Colour, layer: Int, lineSegs: Line2 *): LinesDraw =
    LinesDraw(lineSegs.valueProducts[Line2s], lineWidth, colour, layer)
}

