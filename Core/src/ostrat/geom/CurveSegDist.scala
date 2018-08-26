/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** CNeeds renaming */
trait CurveSegDist
{
   def endPt: Dist2
   def toVec2s(f: Dist2 => Vec2): CurveSeg
}

case class LineSegDist(endPt: Dist2) extends CurveSegDist
{
   override def toVec2s(f: Dist2 => Vec2): CurveSeg = LineSeg(f(endPt))   
}

case class ArcSegDist(endPt: Dist2, cenPt: Dist2) extends CurveSegDist
{
   def toVec2s(f: Dist2 => Vec2): CurveSeg = ArcSeg(f(endPt), f(cenPt))
}

/** represents a polygon on a globe's (eg the Earth) surface. If all the points are visible returns a straight line polygon. If none are
 *  visible it returns a none, the polygon is over the horizon. If some are visible inserts curves along horizon. */
sealed trait GlobedArea
case class GlobedAll(d2s: Dist2s) extends GlobedArea
case class GlobedSome(segs: List[CurveSegDist]) extends GlobedArea
case object GlobedNone extends GlobedArea


