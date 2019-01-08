/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import Colour.Black

/** The base trait for all objects on a canvas / panel. The objects are recomposed for each frame. The Canvas objects must be recomposed
 *  each time there is a change within the application state or the user view of that application state. */
trait GraphicElem[A] extends Any with Transable[A]
{ def zOrder: Int
}

/* Base trait for all passive objects  on a canvas / panel */
trait PaintElem[A] extends Any with GraphicElem[A]

trait ShapeElem[A] extends Any with PaintElem[A]
{
  def segs: Shape
  def segsLen: Int = segs.length
}

case class ShapeFill(segs: Shape, colour: Colour, zOrder: Int = 0) extends ShapeElem[ShapeFill]
{ def typeSym = 'ShapeFill  
  override def fTrans(f: Vec2 => Vec2) = ShapeFill(segs.fTrans(f), colour, zOrder)  
}

case class ShapeDraw(segs: Shape, lineWidth: Double, colour: Colour = Black, zOrder: Int = 0) extends ShapeElem[ShapeDraw]
{ override def fTrans(f: Vec2 => Vec2) = ShapeDraw(segs.fTrans(f), lineWidth, colour, zOrder) }

case class ShapeFillDraw(segs: Shape, fillColour: Colour, lineWidth: Double, lineColour: Colour = Black, zOrder: Int = 0) extends
ShapeElem[ShapeFillDraw]
{
  override def fTrans(f: Vec2 => Vec2) = ShapeFillDraw(segs.fTrans(f), fillColour, lineWidth, lineColour, zOrder)
}

case class LineDraw(xStart: Double, yStart: Double, xEnd: Double, yEnd: Double, lineWidth: Double, colour: Colour, zOrder: Int) extends
  PaintElem[LineDraw] with CurveLike
{
  def typeSym = 'LineDraw
  def str = persist4(xStart, xEnd, lineWidth, colour)
  override def fTrans(f: Vec2 => Vec2): LineDraw = LineDraw(f(pStart), f(pEnd), lineWidth, colour, zOrder)
  def dashed(dashLength: Double, gapLength: Double): DashedLineDraw = DashedLineDraw(pStart, pEnd, lineWidth, dashLength, gapLength, colour, zOrder)
}

object LineDraw
{
  def apply(pStart: Vec2, pEnd: Vec2, lineWidth: Double = 1.0, colour: Colour = Black, zOrder: Int = 0): LineDraw =
    new LineDraw(pStart.x, pStart.y, pEnd.x, pEnd.y, lineWidth, colour, zOrder)  
}

case class LinesDraw(lineSegs: Line2s, lineWidth: Double, colour: Colour = Black, zOrder: Int = 0) extends PaintElem[LinesDraw]
{ override def fTrans(f: Vec2 => Vec2): LinesDraw = LinesDraw(lineSegs.fTrans(f), lineWidth, colour, zOrder)
}

object LinesDraw
{
  def apply(lineWidth: Double, colour: Colour, zOrder: Int, lineSegs: Line2 *): LinesDraw =
    LinesDraw(lineSegs.valueProducts[Line2s], lineWidth, colour, zOrder)
}

case class DashedLineDraw(xStart: Double, yStart: Double, xEnd: Double, yEnd: Double, lineWidth: Double, colour: Colour, dashArr: Array[Double],
    zOrder: Int) extends PaintElem[DashedLineDraw] with CurveLike
{
  def typeSym = 'DashedLineDraw
  def str = persist4(xStart, xEnd, lineWidth, colour)
  override def fTrans(f: Vec2 => Vec2): DashedLineDraw = DashedLineDraw.array(f(pStart), f(pEnd), lineWidth, dashArr, colour, zOrder)  
}

object DashedLineDraw
{
  def apply(pStart: Vec2, pEnd: Vec2, lineWidth: Double, dashLength: Double, gapLength: Double, colour: Colour = Black, zOrder: Int = 0):
    DashedLineDraw = new DashedLineDraw(pStart.x, pStart.y, pEnd.x, pEnd.y, lineWidth, colour, Array[Double](dashLength, gapLength), zOrder)
  
  def array(pStart: Vec2, pEnd: Vec2, lineWidth: Double, dashArr: Array[Double], colour: Colour = Black, zOrder: Int = 0):
    DashedLineDraw = new DashedLineDraw(pStart.x, pStart.y, pEnd.x, pEnd.y, lineWidth, colour, dashArr, zOrder)
}
