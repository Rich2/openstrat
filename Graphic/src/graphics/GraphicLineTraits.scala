package ostrat
package geom
import pCanv._, Colour.Black

case class LineDraw(xStart: Double, yStart: Double, xEnd: Double, yEnd: Double, width: Double, colour: Colour) extends CurveLikeOldPaintElem
{ override type RigidT = LineDraw
  def typeStr: String = "LineDraw"
  override def fTrans(f: Vec2 => Vec2): LineDraw = LineDraw(f(pStart), f(pEnd), width, colour)
  def dashed(dashLength: Double, gapLength: Double): DashedLineDraw = DashedLineDraw(pStart, pEnd, width, dashLength, gapLength, colour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.lineDraw(this)
}

object LineDraw
{ def apply(pStart: Vec2, pEnd: Vec2, lineWidth: Double = 1.0, colour: Colour = Black): LineDraw =
  new LineDraw(pStart.x, pStart.y, pEnd.x, pEnd.y, lineWidth, colour)
  implicit val persistImplicit: Persist4[Vec2, Vec2, Double, Colour, LineDraw] =
    Persist4("LineDraw", "pStart", _.pStart, "pEnd", _.pEnd, "width", _.width, "colour", _.colour, apply, Some(Black), Some(1.0))
}

/** I think its to better to use the mame lineWidth consistently. */
case class LinesDraw(lines: Line2s, lineWidth: Double, colour: Colour = Black) extends PaintFullElem
{ override type RigidT = LinesDraw
  override def fTrans(f: Vec2 => Vec2): LinesDraw = LinesDraw(lines.fTrans(f), lineWidth, colour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.linesDraw(this)
}

object LinesDraw
{
  implicit val persistImplicit: Persist3[Line2s, Double, Colour, LinesDraw] =
    Persist3("LinesDraw", "lines", _.lines, "lineWidth", _.lineWidth, "colour", _.colour, apply)
}

case class LinePathDraw(path: LinePath, lineWidth: Double, colour: Colour = Black) extends PaintFullElem
{ override type RigidT = LinePathDraw
  def length = path.length - 1
  def xStart = path.xStart
  def yStart = path.yStart
  override def fTrans(f: Vec2 => Vec2): LinePathDraw = LinePathDraw(path.fTrans(f), lineWidth, colour)
  @inline def foreachEnd(f: (Double, Double) => Unit): Unit = path.foreachEnd(f)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.linePathDraw(this)
}

case class DashedLineDraw(xStart: Double, yStart: Double, xEnd: Double, yEnd: Double, lineWidth: Double, colour: Colour, dashArr: Array[Double])
  extends CurveLikeOldPaintElem
{ override type RigidT = DashedLineDraw
  def typeStr: String = "DashedLineDraw"
  override def fTrans(f: Vec2 => Vec2): DashedLineDraw = DashedLineDraw.array(f(pStart), f(pEnd), lineWidth, dashArr, colour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.dashedLineDraw(this)
}

object DashedLineDraw
{
  def apply(pStart: Vec2, pEnd: Vec2, lineWidth: Double, dashLength: Double, gapLength: Double, colour: Colour = Black):
  DashedLineDraw = new DashedLineDraw(pStart.x, pStart.y, pEnd.x, pEnd.y, lineWidth, colour, Array[Double](dashLength, gapLength))

  /*implicit val persistImplicit: Persist6[Vec2, Vec2, Double, Double, Double, Colour, DashedLineDraw] =
    Persist6("DashedLineDraw", "pStart", _.pStart, "pEnd", _.pEnd, "lineWidth", _.lineWidth, "dashLength", _.dashLength, "gapLength", _.gapLength,
    "colour", _.colour, apply)*/

  def array(pStart: Vec2, pEnd: Vec2, lineWidth: Double, dashArr: Array[Double], colour: Colour = Black): DashedLineDraw =
    new DashedLineDraw(pStart.x, pStart.y, pEnd.x, pEnd.y, lineWidth, colour, dashArr)
}
