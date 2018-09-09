/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** represents a polygon on a globe's (eg the Earth) surface. If all the points are visible returns a straight line polygon. If none are
 *  visible it returns a none, the polygon is over the horizon. If some are visible inserts curves along horizon. */
sealed trait GlobedArea
case class GlobedAll(d2s: Dist2s) extends GlobedArea
case class GlobedSome(segs: CurveSegDists) extends GlobedArea
case object GlobedNone extends GlobedArea