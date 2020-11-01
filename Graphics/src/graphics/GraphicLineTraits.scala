/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pCanv._, Colour.Black

/** A Graphic for a straight line. It is defined by its start and end points, the line width or thickness and the colour of the line. */
case class LineSegDraw(curveSeg: LineSeg, width: Double, colour: Colour) extends CurveSegGraphic with AffinePreserve
{ override type ThisT = LineSegDraw
  def typeStr: String = "LineDraw"
  override def fTrans(f: Vec2 => Vec2): LineSegDraw = LineSegDraw(curveSeg.fTrans(f), colour, width)
  def dashed(dashLength: Double, gapLength: Double): DashedLineDraw = DashedLineDraw(curveSeg, width, dashLength, gapLength, colour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.lineDraw(this)
  def startPt: Vec2 = xStart vv yStart
  def endPt: Vec2 = xEnd vv yEnd
  def arrow: LinesDraw = Arrow.draw(startPt, endPt, 30.degs, 20, width, colour)
}

object LineSegDraw
{
  def apply(lineSeg: LineSeg, colour: Colour, lineWidth: Double) = new LineSegDraw(lineSeg, lineWidth, colour)
  def apply(pStart: Vec2, pEnd: Vec2, colour: Colour = Black, lineWidth: Double = 2.0): LineSegDraw = LineSeg(pStart, pEnd).draw(colour, lineWidth)

 // implicit val persistImplicit: Persist4[Vec2, Vec2, Double, Colour, LineDraw] =
  //  Persist4("LineDraw", "pStart", _.pStart, "pEnd", _.pEnd, "width", _.width, "colour", _.colour, apply, Some(Black), Some(1.0))
}

/** I think its to better to use the mame lineWidth consistently. */
case class LinesDraw(lines: LineSegs, lineWidth: Double, colour: Colour = Black) extends GraphicAffineElem
{ override type ThisT = LinesDraw
  override def fTrans(f: Vec2 => Vec2): LinesDraw = LinesDraw(lines.fTrans(f), lineWidth, colour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.linesDraw(this)
}

object LinesDraw
{
  implicit val persistImplicit: Persist3[LineSegs, Double, Colour, LinesDraw] =
    Persist3("LinesDraw", "lines", _.lines, "lineWidth", _.lineWidth, "colour", _.colour, apply)
}

case class LinePathDraw(path: LinePath, lineWidth: Double, colour: Colour = Black) extends GraphicAffineElem
{ override type ThisT = LinePathDraw
  def length = path.elemsLen - 1
  def xStart = path.xStart
  def yStart = path.yStart
  override def fTrans(f: Vec2 => Vec2): LinePathDraw = LinePathDraw(path.fTrans(f), lineWidth, colour)
  @inline def foreachEnd(f: (Double, Double) => Unit): Unit = path.foreachEnd(f)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.linePathDraw(this)
}

case class DashedLineDraw(curveSeg: LineSeg, lineWidth: Double, colour: Colour, dashArr: Array[Double])
  extends CurveSegGraphic with AffinePreserve
{ override type ThisT = DashedLineDraw

  //override def curveSeg: CurveSeg = ???
  def typeStr: String = "DashedLineDraw"
  override def fTrans(f: Vec2 => Vec2): DashedLineDraw = DashedLineDraw.array(f(pStart), f(pEnd), lineWidth, dashArr, colour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.dashedLineDraw(this)
}

object DashedLineDraw
{
  def apply(curveSeg: LineSeg, lineWidth: Double, dashLength: Double, gapLength: Double, colour: Colour = Black):
  DashedLineDraw = new DashedLineDraw(curveSeg, lineWidth, colour, Array[Double](dashLength, gapLength))

  /*implicit val persistImplicit: Persist6[Vec2, Vec2, Double, Double, Double, Colour, DashedLineDraw] =
    Persist6("DashedLineDraw", "pStart", _.pStart, "pEnd", _.pEnd, "lineWidth", _.lineWidth, "dashLength", _.dashLength, "gapLength", _.gapLength,
    "colour", _.colour, apply)*/

  def array(pStart: Vec2, pEnd: Vec2, lineWidth: Double, dashArr: Array[Double], colour: Colour = Black): DashedLineDraw = ???
   // new DashedLineDraw(pStart.x, pStart.y, pEnd.x, pEnd.y, lineWidth, colour, dashArr)
}
