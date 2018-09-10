/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** A base trait for CurveSegDist and CurveDist and their associated GraphicElemsDist (these haven't been implemented yet). */
trait CurveEndingDist
{
   def xEndMetres: Double
   def yEndMetres: Double
   /** the x component of the end point */
   def xEnd: Dist = Dist(xEndMetres)
   /** the y component of the end point */
   def yEnd: Dist = Dist(yEndMetres)
   /** The end point. Often called p2 on a line or p4 on a cubic bezier. */
   final def pEnd: Dist2 = Dist2(xEnd, yEnd)  
}

class CurveSegDist(xC1Metres: Double, yC1Metres: Double, xUsesMetres: Double, yUsesMetres: Double, xEndMetres: Double, yEndMetres: Double)
extends ProdD6
{   
   def toCurveSeg(f: Dist2 => Vec2): CurveSeg = ???
   override def canEqual(other: Any): Boolean = other.isInstanceOf[CurveSeg]
   @inline override def _1 = xC1Metres
   @inline override def _2 = yC1Metres
   @inline override def _3 = xUsesMetres
   @inline override def _4 = yUsesMetres
   @inline override def _5 = xEndMetres
   @inline override def _6 = yEndMetres
}

object LineSegDist
{
   def apply(endPt: Dist2): CurveSegDist = new CurveSegDist(Double.NaN, 0, 0, 0, endPt.xMetres, endPt.yMetres)
//   override def toVec2s(f: Dist2 => Vec2): CurveSeg = LineSeg(f(endPt))   
}

object ArcSegDist
{
   def apply(cenPt: Dist2, endPt: Dist2): CurveSegDist =
      new CurveSegDist(Double.PositiveInfinity, 0, cenPt.xMetres, cenPt.yMetres, endPt.xMetres, endPt.yMetres)
//   def toVec2s(f: Dist2 => Vec2): CurveSeg = ArcSeg(f(cenPt), f(endPt))
}


