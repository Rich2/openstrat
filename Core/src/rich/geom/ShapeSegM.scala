/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */package rich
package geom

trait ShapeSegM
{
   def endPt: Dist2
   def toVec2s(f: Dist2 => Vec2): ShapeSeg
}

case class LineSegM(endPt: Dist2) extends ShapeSegM
{
   override def toVec2s(f: Dist2 => Vec2): ShapeSeg = LineSeg(f(endPt))   
}

case class ArcSegM(endPt: Dist2, cenPt: Dist2) extends ShapeSegM
{
   def toVec2s(f: Dist2 => Vec2): ShapeSeg = ArcSeg(f(endPt), f(cenPt))
}

//case class Dist2Arc(cen: Dist2, radius: Dist, start: Angle, end: Angle) extends Dist2Seg)

/** represents a polygon on a globe's (eg the Earth) surface. If all the points are visible returns a straght line polygon. If none are
 *  visible it returns a none, the polygon is over the horizon. If some are visbile inserts curves along horizon. */
sealed trait GlobedArea
case class GlobedAll(d2s: Dist2s) extends GlobedArea
case class GlobedSome(segs: Seq[ShapeSegM]) extends GlobedArea
case object GlobedNone extends GlobedArea


