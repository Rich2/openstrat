/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pgui._, Colour.Black, pWeb._

/** A Graphic for a straight line. It is defined by its start and end points, the line width or thickness and the colour of the line. */
case class LineSegDraw(curveSeg: LineSeg, width: Double, colour: Colour) extends CurveSegGraphic with AffinePreserve with CanvElem with GraphicSvgElem
{ override type ThisT = LineSegDraw
  def typeStr: String = "LineDraw"
  override def ptsTrans(f: Pt2 => Pt2): LineSegDraw = LineSegDraw(curveSeg.ptsTrans(f), width, colour)
  def dashed(dashLength: Double, gapLength: Double): DashedLineDraw = DashedLineDraw(curveSeg, width, dashLength, gapLength, colour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.lineSegDraw(this)
  def startPt: Pt2 = xStart pp yStart
  def endPt: Pt2 = xEnd pp yEnd
  def arrow: GraphicElems = Arrow.paint(startPt, endPt, 30.degsVec, 20, colour, width)

  override def svgElem: SvgElem = SvgLine(xStart, yStart, xEnd, yEnd, colour, width)
}

object LineSegDraw
{
  def apply(lineSeg: LineSeg, lineWidth: Double, colour: Colour) = new LineSegDraw(lineSeg, lineWidth, colour)
  def apply(pStart: Pt2, pEnd: Pt2, lineWidth: Double = 2.0, colour: Colour = Black): LineSegDraw = LineSeg(pStart, pEnd).draw(lineWidth, colour)

  implicit val showEv: Show4[Pt2, Pt2, Double, Colour, LineSegDraw] =
    Show4("LineDraw", "pStart", _.pStart, "pEnd", _.pEnd, "width", _.width, "colour", _.colour, Some(Black), Some(1.0))

  implicit val unshowEv: Unshow4[Pt2, Pt2, Double, Colour, LineSegDraw] =
    Unshow4("LineDraw", "pStart", "pEnd", "width", "colour", apply, Some(Black), Some(1.0))
}

/** I think its to better to use the mame lineWidth consistently. */
case class LinesDraw(lines: LineSegArr, lineWidth: Double, colour: Colour = Black) extends GraphicAffineElem with CanvElem
{ override type ThisT = LinesDraw
  override def ptsTrans(f: Pt2 => Pt2): LinesDraw = LinesDraw(lines.ptsTrans(f), lineWidth, colour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.lineSegsDraw(this)
}

object LinesDraw
{
  implicit val persistEv: Persist3Both[LineSegArr, Double, Colour, LinesDraw] =
    Persist3Both[LineSegArr, Double, Colour, LinesDraw]("LinesDraw", "lines", _.lines, "lineWidth", _.lineWidth, "colour", _.colour, apply)
}

case class LinePathDraw(path: LinePath, lineWidth: Double, colour: Colour = Black) extends GraphicAffineElem with CanvElem
{ override type ThisT = LinePathDraw
  def length = path.ssLength - 1
  def xStart: Double = path.xStart
  def yStart: Double = path.yStart
  override def ptsTrans(f: Pt2 => Pt2): LinePathDraw = LinePathDraw(path.ptsTrans(f), lineWidth, colour)
  @inline def foreachEnd(f: (Double, Double) => Unit): Unit = path.vertsTailForeach(f)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.linePathDraw(this)
}

/** This class will be replaced but extends [[CanvElem]] as a temporary measure. */
case class DashedLineDraw(curveSeg: LineSeg, lineWidth: Double, colour: Colour, dashArr: Array[Double]) extends CurveSegGraphic with
  AffinePreserve with CanvElem
{ override type ThisT = DashedLineDraw

  //override def curveSeg: CurveSeg = ???
  def typeStr: String = "DashedLineDraw"
  override def ptsTrans(f: Pt2 => Pt2): DashedLineDraw = DashedLineDraw.array(f(pStart), f(pEnd), lineWidth, dashArr, colour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.dashedLineDraw(this)
}

object DashedLineDraw
{
  def apply(curveSeg: LineSeg, lineWidth: Double, dashLength: Double, gapLength: Double, colour: Colour = Black):
  DashedLineDraw = new DashedLineDraw(curveSeg, lineWidth, colour, Array[Double](dashLength, gapLength))

  /*implicit val persistImplicit: Persist6[Vec2, Vec2, Double, Double, Double, Colour, DashedLineDraw] =
    Persist6("DashedLineDraw", "pStart", _.pStart, "pEnd", _.pEnd, "lineWidth", _.lineWidth, "dashLength", _.dashLength, "gapLength", _.gapLength,
    "colour", _.colour, apply)*/

  def array(pStart: Pt2, pEnd: Pt2, lineWidth: Double, dashArr: Array[Double], colour: Colour = Black): DashedLineDraw =
    new DashedLineDraw(pStart.lineSegTo(pEnd), lineWidth, colour, dashArr)
}