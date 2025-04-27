/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pgui.*, Colour.Black, pWeb.*

/** A Graphic for a straight line. It is defined by its start and end points, the line width or thickness and the colour of the line. */
case class LSeg2Draw(curveSeg: LSeg2, width: Double, colour: Colour) extends CurveSegGraphic, AffinePreserve, CanvElem, GraphicSvgElem
{ override type ThisT = LSeg2Draw
  def typeStr: String = "LSeg2Draw"
  override def ptsTrans(f: Pt2 => Pt2): LSeg2Draw = LSeg2Draw(curveSeg.ptsTrans(f), width, colour)
  def dashed(dashLength: Double, gapLength: Double): DashedLineDraw = DashedLineDraw(curveSeg, width, dashLength, gapLength, colour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.lineSegDraw(this)
  def startPt: Pt2 = Pt2(xStart, yStart)
  def endPt: Pt2 = Pt2(xEnd, yEnd)
  def arrow: GraphicElems = Arrow.paint(startPt, endPt, 30.degsVec, 20, colour, width)

  override def svgElem: SvgElem = SvgLine(xStart, yStart, xEnd, yEnd, colour, width)
}

object LSeg2Draw
{
  def apply(lineSeg: LSeg2, lineWidth: Double, colour: Colour) = new LSeg2Draw(lineSeg, lineWidth, colour)
  def apply(pStart: Pt2, pEnd: Pt2, lineWidth: Double = 2.0, colour: Colour = Black): LSeg2Draw = LSeg2(pStart, pEnd).draw(lineWidth, colour)
  
  def dbls(xStart: Double, yStart: Double, xEnd: Double, yEnd: Double, lineWidth: Double = 2.0, colour: Colour = Black): LSeg2Draw =
    LSeg2(xStart, yStart, xEnd, yEnd).draw(lineWidth, colour)
  
  implicit val showEv: Show4[Pt2, Pt2, Double, Colour, LSeg2Draw] =
    Show4("LSeg2Draw", "pStart", _.pStart, "pEnd", _.pEnd, "width", _.width, "colour", _.colour, Some(Black), Some(1.0))

  implicit val unshowEv: Unshow4[Pt2, Pt2, Double, Colour, LSeg2Draw] =
    Unshow4("LSeg2Draw", "pStart", "pEnd", "width", "colour", apply, Some(Black), Some(1.0))
}

/** I think it's to better to use the mame lineWidth consistently. */
class LSeg2ArrDraw private(val arrayUnsafe: Array[Double], val lineWidth: Double, val colour: Colour = Black) extends GraphicAffineElem, CanvElem, BoundedElem
{ override type ThisT = LSeg2ArrDraw
  def lines: LSeg2Arr = new LSeg2Arr(arrayUnsafe)
  override def ptsTrans(f: Pt2 => Pt2): LSeg2ArrDraw = LSeg2ArrDraw(lines.ptsTrans(f), lineWidth, colour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.lineSegsDraw(this)

  def svgElem: SvgElem = SvgGroup(lines.map(_.svgElem), StrokeAttrib(colour), StrokeWidthAttrib(lineWidth))
  override def svgElems: RArr[SvgElem] = RArr(svgElem)
  override def boundingRect: Rect = lines.boundingRect
}

object LSeg2ArrDraw
{
  def apply(lines: LSeg2Arr, lineWidth: Double, colour: Colour = Black): LSeg2ArrDraw = new LSeg2ArrDraw(lines.arrayUnsafe, lineWidth, colour)

  implicit val persistEv: Persist3Both[LSeg2Arr, Double, Colour, LSeg2ArrDraw] =
    Persist3Both[LSeg2Arr, Double, Colour, LSeg2ArrDraw]("LSeg2SeqDraw", "lines", _.lines, "lineWidth", _.lineWidth, "colour", _.colour, apply)
}

case class LinePathDraw(path: LinePath, lineWidth: Double, colour: Colour = Black) extends GraphicAffineElem with CanvElem
{ override type ThisT = LinePathDraw
  def length = path.numElems - 1
  def xStart: Double = path.xStart
  def yStart: Double = path.yStart
  override def ptsTrans(f: Pt2 => Pt2): LinePathDraw = LinePathDraw(path.ptsTrans(f), lineWidth, colour)
  @inline def foreachEnd(f: (Double, Double) => Unit): Unit = path.vertsTailForeach(f)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.linePathDraw(this)

  override def svgElems: RArr[SvgElem] = ???
}

/** This class will be replaced but extends [[CanvElem]] as a temporary measure. */
case class DashedLineDraw(curveSeg: LSeg2, lineWidth: Double, colour: Colour, dashArr: Array[Double]) extends CurveSegGraphic with
  AffinePreserve with CanvElem
{ override type ThisT = DashedLineDraw

  //override def curveSeg: CurveSeg = ???
  def typeStr: String = "DashedLineDraw"
  override def ptsTrans(f: Pt2 => Pt2): DashedLineDraw = DashedLineDraw.array(f(pStart), f(pEnd), lineWidth, dashArr, colour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.dashedLineDraw(this)

  override def svgElems: RArr[SvgElem] = ???
}

object DashedLineDraw
{
  def apply(curveSeg: LSeg2, lineWidth: Double, dashLength: Double, gapLength: Double, colour: Colour = Black):
  DashedLineDraw = new DashedLineDraw(curveSeg, lineWidth, colour, Array[Double](dashLength, gapLength))

  def array(pStart: Pt2, pEnd: Pt2, lineWidth: Double, dashArr: Array[Double], colour: Colour = Black): DashedLineDraw =
    new DashedLineDraw(pStart.lineSegTo(pEnd), lineWidth, colour, dashArr)
}