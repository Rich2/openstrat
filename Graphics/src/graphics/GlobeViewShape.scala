/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** This whole trait and its decenedents needs to be removed and its sue case expressed with non specific graphic objects. It represents the view of a
 *  polygon on a globe's (eg the Earth) surface. If all the points are visible returns a straight line polygon. If none are visible it returns a none,
 *  the polygon is over the horizon. If some are visible inserts curves along horizon. */
sealed trait GlobeViewShape

/** A 2D polygon representation of an area on the Earth's surface, where all of the surface area is on the facing side of the Earth to the viewer. */
case class GlobeViewAll(d2s: Dist2s) extends GlobeViewShape

/** An area on the Earth's surfce where some is is on the facing side of the Earth and some is on the hidden side of the Earth to the viewer. */
case class GlobeViewSome(segs: CurveSegDists) extends GlobeViewShape

/** An area on the Earth's surface all on the hidden side of the Earth from the viewer. */
case object GlobeViewHidden extends GlobeViewShape