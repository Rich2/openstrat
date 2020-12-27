/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A quasi polygon on the Earths surface where the points are defined in latitude and longitude. Do not create Polygons that span an arc of greater
 *  than 90 degrees.  */
final class PolygonLL(val unsafeArray: Array[Double]) extends AnyVal
{

}
