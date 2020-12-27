/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A quasi Polygon specified in 3D Kilometre points. This is not a proper polygon as the points do not have to lie within the same plane. I'm not
 *  sure how useful this class will prove. It has been created for the intermediary step of converting from [[PolygonLL]]s to [[PolygonKMs]]s on world
 *  maps. */
final class PolygonKMs3(val unsafeArray: Array[Double]) extends AnyVal
{
  /** A 2D view of these 3D quasi polygons from infinity expressed in 2D kilometres. This will distort at the Earth's horizon. */
  def infinityView: PolygonKMs = ???
}