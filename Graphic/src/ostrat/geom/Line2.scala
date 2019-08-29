/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import Colour.Black

/** In geometry this is a line segment. But in this library a seg refers to shape segemnt with out its start (pt1) point */
case class Line2(xStart: Double, yStart: Double, xEnd: Double, yEnd: Double) extends ProdD4 with Transer with CurveLike
{ 
  override def toString: String = Line2.PersistImplicit.show(this)
  override def _1 = xStart
  override def _2 = yStart
  override def _3 = xEnd
  override def _4 = yEnd
  def func4Dou[T](f: (Double, Double, Double, Double) => T): T = f(xStart, yStart, xEnd, yEnd)
  def fTrans(f: Vec2 => Vec2): Line2 = Line2(f(pStart), f(pEnd))
  def shortArray: Array[Short] = Array(xStart.toShort, yStart.toShort,xEnd.toShort,yEnd.toShort)
  def toLatLongLine(f: Vec2 => LatLong): LatLongLine = LatLongLine(f(pStart), f(pEnd))
  def isHorizontal: Boolean = yStart == yEnd
  def isVertical: Boolean = xStart == xEnd
  /**Checks whether a forward horizontal ray crosses this polygon side. */
  def rayIntersection(pt: Vec2): Boolean = ife3(
    pt.y > yStart & pt.y > yEnd, false, //Check if point is above the polygon side, above beg pt and end pt
    pt.y < yStart & pt.y < yEnd, false, //Check if point is  below the polygon side, below beg pt and end pt
    0.000001 > (yEnd - yStart).abs, false, /* deltaY. If the polygon side is close to horizontal the point is close enough to the perimeter
     of the polygon that the point can measured as outside */
    { val ptDeltaY: Double = pt.y - yStart
      val deltaX: Double = xEnd - xStart //Not entirely sure what's going on here
      val lineX: Double = xStart + (deltaX * ptDeltaY / (yEnd - yStart)) //
      pt.x > lineX
    })

  def angle: Angle = (pEnd - pStart).angle
  def draw(lineWidth: Double, colour: Colour = Black, layer: Int = 0): LineDraw = LineDraw(xStart, yStart, xEnd, yEnd, lineWidth, colour, layer)
}

/** Companion object for the Line2 class */
object Line2
{ /** Factory apply method for Line2. If using Doubles "Line2(x1 vv y1, x2 vv y2)" is the preferred syntax, rather than calling the constructor
* directly. */
  @inline def apply(pStart: Vec2, pEnd: Vec2): Line2 = new Line2(pStart.x, pStart.y, pEnd.x, pEnd.y)
  implicit object PersistImplicit extends Persist2[Vec2, Vec2, Line2]("Line2", _.pStart, _.pEnd, Line2(_, _))
}
object HLine
{ /** Creates a horizontal Line2 */
  @inline def apply(y: Double, xStart: Double, yEnd: Double): Line2 = new Line2(xStart, y, xStart, y)
}
object VLine
{ /** Creates a vertical Line2 */
  @inline def apply(x: Double, yStart: Double, yEnd: Double): Line2 = new Line2(x, yStart, x, yEnd)
}
