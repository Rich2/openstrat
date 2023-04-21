/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package pglobe

/** A latitude-longitude line path. A quasi line path where the points are stored as points of latitude and longitude.Once the points are converted into a
 *  view, ie into pixel positions an actual polygon can be drawn or filled as desired. Do not create line paths that span an arc of greater than 90
 *  degrees as this may break the algorithms. */
final class LinePathLL(val unsafeArray: Array[Double]) extends AnyVal with LatLongSeqSpec with LinePathDbl2[LatLong]
{ override type ThisT = LinePathLL
  override type PolygonT = PolygonLL
  override def typeStr: String = "LinePathLL"
  override def fromArray(array: Array[Double]): LinePathLL = new LinePathLL(array)
  override def polygonFromArray(array: Array[Double]): PolygonLL = new PolygonLL(array)
}

object LinePathLL extends Dbl2SeqLikeCompanion[LatLong, LinePathLL]
{ override def fromArray(array: Array[Double]): LinePathLL = new LinePathLL(array)

  /** Apply factory method for creating Arrs of [[Dbl2Elem]]s. */
  override def apply(elems: LatLong*): LinePathLL =
  {
    val length = elems.length
    val res = uninitialised(length)
    var count: Int = 0

    while (count < length)
    { res.unsafeArray(count * 2) = elems(count).dbl1
      res.unsafeArray(count * 2 + 1) = elems(count).dbl2
      count += 1
    }
    res
  }
}