/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

class CurveSegDist(xC1Metres: Double, xC2Metres: Double, xUsesMetres: Double, yUsesMetres: Double, xEndMetres: Double, yEndMetres: Double)
extends ProdD6
{
   def endPt: Dist2
   def toVec2s(f: Dist2 => Vec2): CurveSeg
}

//case class LineSegDist(endPt: Dist2) extends CurveSegDist
//{
//   override def toVec2s(f: Dist2 => Vec2): CurveSeg = LineSeg(f(endPt))   
//}
//
//case class ArcSegDistAlt(cenPt: Dist2, endPt: Dist2) extends CurveSegDist
//{
//   def toVec2s(f: Dist2 => Vec2): CurveSeg = ArcSeg(f(cenPt), f(endPt))
//}

class CurveSegDists(val arr: Array[Double]) extends AnyVal with DoubleProduct6s[CurveSegDist]


/** represents a polygon on a globe's (eg the Earth) surface. If all the points are visible returns a straight line polygon. If none are
 *  visible it returns a none, the polygon is over the horizon. If some are visible inserts curves along horizon. */
sealed trait GlobedArea
case class GlobedAll(d2s: Dist2s) extends GlobedArea
case class GlobedSome(segs: CurveSegDists) extends GlobedArea
case object GlobedNone extends GlobedArea


