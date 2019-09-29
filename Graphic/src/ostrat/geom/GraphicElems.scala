/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import Colour.Black

/** The base trait for all objects on a canvas / panel. The objects are re-composed for each frame. The Canvas objects must be re-composed
 *  each time there is a change within the application state or the user view of that application state. */
trait GraphicElem extends Any with Transer

/* Base trait for all passive objects  on a canvas / panel */
trait PaintElem extends Any with GraphicElem
{ def rendElem(cp: pCanv.CanvasPlatform): Unit
}

case class LineDraw(xStart: Double, yStart: Double, xEnd: Double, yEnd: Double, width: Double, colour: Colour) extends PaintElem with CurveLike
{ def typeStr: String = "LineDraw"
  override def fTrans(f: Vec2 => Vec2): LineDraw = LineDraw(f(pStart), f(pEnd), width, colour)
  def dashed(dashLength: Double, gapLength: Double): DashedLineDraw = DashedLineDraw(pStart, pEnd, width, dashLength, gapLength, colour)
  override def rendElem(cp: pCanv.CanvasPlatform): Unit = cp.lineDraw(this)
}

object LineDraw
{ def apply(pStart: Vec2, pEnd: Vec2, lineWidth: Double = 1.0, colour: Colour = Black): LineDraw =
    new LineDraw(pStart.x, pStart.y, pEnd.x, pEnd.y, lineWidth, colour)
  implicit val persistImplicit: Persist4[Vec2, Vec2, Double, Colour, LineDraw] =
    Persist4("LineDraw", "pStart", _.pStart, "pEnd", _.pEnd, "width", _.width, "colour", _.colour, apply, Some(Black), Some(1.0))
}

case class LinesDraw(lineSegs: Line2s, lineWidth: Double, colour: Colour = Black) extends PaintElem
{ override def fTrans(f: Vec2 => Vec2): LinesDraw = LinesDraw(lineSegs.fTrans(f), lineWidth, colour)
  override def rendElem(cp: pCanv.CanvasPlatform): Unit = cp.linesDraw(this)
}

object LinesDraw
{ def apply(lineWidth: Double, colour: Colour, lineSegs: Line2 *): LinesDraw =
    LinesDraw(lineSegs.valueProducts[Line2s], lineWidth, colour)
}

case class LinePathDraw(vec2s: LinePath, lineWidth: Double, colour: Colour = Black, zOrder: Int = 0) extends PaintElem
{ def length = vec2s.length - 1
  def xStart = vec2s.xStart
  def yStart = vec2s.yStart
  override def fTrans(f: Vec2 => Vec2): LinePathDraw = LinePathDraw(vec2s.fTrans(f), lineWidth, colour, zOrder) 
  @inline def foreachEnd(f: (Double, Double) => Unit): Unit = vec2s.foreachEnd(f)
  override def rendElem(cp: pCanv.CanvasPlatform): Unit = ??? //cp.linePathDraw(this)
}

case class DashedLineDraw(xStart: Double, yStart: Double, xEnd: Double, yEnd: Double, lineWidth: Double, colour: Colour, dashArr: Array[Double],
    zOrder: Int) extends PaintElem with CurveLike
{
  def typeStr: String = "DashedLineDraw"
  //def str = persist4(xStart, xEnd, lineWidth, colour)
  override def fTrans(f: Vec2 => Vec2): DashedLineDraw = DashedLineDraw.array(f(pStart), f(pEnd), lineWidth, dashArr, colour, zOrder)
  override def rendElem(cp: pCanv.CanvasPlatform): Unit = cp.dashedLineDraw(this)
}

object DashedLineDraw
{
  def apply(pStart: Vec2, pEnd: Vec2, lineWidth: Double, dashLength: Double, gapLength: Double, colour: Colour = Black, zOrder: Int = 0):
    DashedLineDraw = new DashedLineDraw(pStart.x, pStart.y, pEnd.x, pEnd.y, lineWidth, colour, Array[Double](dashLength, gapLength), zOrder)
  
  def array(pStart: Vec2, pEnd: Vec2, lineWidth: Double, dashArr: Array[Double], colour: Colour = Black, zOrder: Int = 0):
    DashedLineDraw = new DashedLineDraw(pStart.x, pStart.y, pEnd.x, pEnd.y, lineWidth, colour, dashArr, zOrder)
}