/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pXml._

/** Rectangle trait. The leaf classes of this class may or may not be squares and may or may not be aligned to the X and Y Axes. */
trait Rectangle extends Rectangular with Polygon
{ final override def length: Int = 4
  def x0: Double
  def y0: Double
  @inline final def v0: Vec2 = x0 vv y0
  def x1: Double
  def y1: Double
  @inline final def v1: Vec2 = x1 vv y1
  @inline final def x2: Double = xBottomLeft
  @inline final def y2: Double = yBottomLeft
  @inline final def v2: Vec2 = bottomLeft
  @inline final def x3: Double = xTopLeft
  @inline final def y3: Double = yTopLeft
  @inline final def v3: Vec2 = topLeft
  
  @inline final override def apply(index: Int): Vec2 = index match 
  { case 0 => v0
    case 1 => v1
    case 2 => v2
    case 3 => v3
    case n => excep("Index: " + n.toString + " out of range. Only 4 vertices in a Rectangle.")  
  }

  final override def elem1sArray: Array[Double] = Array(x0, x1, x2, x3)
  final override def elem2sArray: Array[Double] = Array(y0, y1, y2, y3)
  final override def foreach[U](f: Vec2 => U): Unit = { f(v0); f(v1); f(v2); f(v3); () }
  final override def foreachTail[U](f: Vec2 => U): Unit = { f(v1); f(v2); f(v3) }
  override def foreachPairTail[U](f: (Double, Double) => U): Unit = { f(x1, y1); f(x2, y2); f(x3, y3)}
}

/** This perhaps should be changed to Rectangle. Some methods need renaming or possibly even deleting */
object Rectangle
{
  /** Defaults to a centre of x = 0, y = 0 and then defaults to a height of 1.0. Clockwise, topLeft is vertice 0. */
  def apply(width: Double, height: Double = 1, cen: Vec2 = Vec2Z): PolygonClass =
  { 
    val x = cen.x; val y = cen.y
    PolygonClass(
        x - width / 2 vv y + height / 2,
        x + width / 2 vv y + height / 2,
        x + width / 2 vv y - height / 2,
        x - width / 2 vv y - height / 2)
  }
   
  def scale(widthOverHeightRatio: Double, scale: Double, cen: Vec2 = Vec2Z): PolygonClass = apply(widthOverHeightRatio * scale, scale, cen)
  
  /** A rectangle measured from its top left */
  def fromTL(width: Double, height: Double, tlVec: Vec2 = Vec2Z): PolygonClass = PolygonClass(
      tlVec.x         vv tlVec.y,
      tlVec.x + width vv tlVec.y,
      tlVec.x + width vv tlVec.y - height,
      tlVec.x         vv tlVec.y -height)         
   
  def fromBL(width: Double, height: Double, v: Vec2): PolygonClass = PolygonClass(
      v.x vv v.y + height,
      v.x + width vv v.y + height,
      v.x + width vv v.y,
      v.x vv v.y)

  /** Measured from bottom centre */      
  def fromBC(width: Double, height: Double, bottomCentre: Vec2 = Vec2Z): PolygonClass =
  {
    val x = bottomCentre.x; val y = bottomCentre.y
    PolygonClass(
        x - width / 2 vv y + height ,
        x + width / 2 vv y + height ,
        x + width / 2 vv y,
        x - width / 2 vv y)
  }
 
  
  def gRatio(height: Double): PolygonClass = apply(Phi * height, height)
  //@deprecated def crossOld(width: Double, height: Double, barWidth: Double): ArrOld[Polygon] = ArrOld(apply(width, barWidth), apply(barWidth, height))
  def cross(width: Double, height: Double, barWidth: Double): Polygons = Polygons(apply(width, barWidth), apply(barWidth, height))
  
  def curvedCorners(width: Double, height: Double, radius: Double, cen: Vec2 = Vec2Z): PolyCurve =
  { val w = width / 2
    val h = height / 2
    val s1 = PolyCurve(
        LineTail(w - radius,          h), ArcTail(w - radius vv h - radius, w vv h -radius),
        LineTail(w,          radius - h), ArcTail(w - radius vv radius - h, w - radius vv -h),
        LineTail(radius - w,         -h), ArcTail(radius - w vv radius - h, -w vv radius -h),
        LineTail(- w,        h - radius), ArcTail(radius - w vv h - radius, radius - w vv h))
     s1.slate(cen)
  }  

  def curvedCornersCentred(width: Double, height: Double, radius: Double, posn: Vec2 = Vec2Z): PolyCurveCentred =
    PolyCurveCentred(posn, curvedCorners(width, height, radius).slate(posn))
  def curvedGoldenRatio(height: Double, radius: Double, posn: Vec2 = Vec2Z): PolyCurve =
    curvedCorners(height * Phi, height, radius, posn)  
  def curvedGoldenRatioCentred(height: Double, radius: Double, posn: Vec2 = Vec2Z): PolyCurveCentred =
    curvedCornersCentred(height * Phi, height, radius, posn)
  def colouredBordered(height: Double, colour: Colour, lineWidth: Double = 1): PolygonFillDraw =
    gRatio(height).fillDraw(colour, lineWidth, colour.contrast)
  
  def fromAxis(centreLine: LineSeg, height: Double): PolygonClass =
  { val hAngle: Angle = centreLine.angle
    val offset: Vec2 = hAngle.toVec2(height * 0.5)
    PolygonClass(centreLine.pStart + offset, centreLine.pEnd + offset, centreLine.pEnd - offset, centreLine.pStart - offset)
  }
}