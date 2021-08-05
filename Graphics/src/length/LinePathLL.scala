/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A latitude-longitude line path. A quasi line path where the points are stored as points of latitude and longitude.Once the points are converted into a
 *  view, ie into pixel positions an actual polygon can be drawn or filled as desired. Do not create line paths that span an arc of greater than 90
 *  degrees as this may break the algorithms. */
class LinePathLL(val arrayUnsafe: Array[Double]) extends AnyVal with LatLongsLike with Dbl2sLinePath[LatLong]
{ override type ThisT = LinePathLL
  override def unsafeFromArray(array: Array[Double]): LinePathLL = new LinePathLL(array)
  override def typeStr: String = "LinePathLL"

  /** closes this LinePathLL into a [[PolygonLL]] with a line Segment from the last point to the first point. */
  @inline def close: PolygonLL = new PolygonLL(arrayUnsafe)

  def +: (newElem: LatLong): LinePathLL =
  { val res = LinePathLL.uninitialised(elemsNum + 1)
    res.unsafeSetElem(0, newElem)
    dataIForeach{ (ll, i) => res.unsafeSetElem(i + 1, ll) }
    res
  }

  def close(newElems: LatLong*): PolygonLL =
  { val res = PolygonLL.uninitialised(elemsNum + newElems.length)
    arrayUnsafe.copyToArray(res.arrayUnsafe)
    newElems.iForeach((ll, i) => res.unsafeSetElem(elemsNum + i, ll))
    res
  }

  /** Reverses the line path so its end point becomes its start point. */
  def reverse: LinePathLL = reverseData
}

object LinePathLL extends Dbl2sDataCompanion[LatLong, LinePathLL]
{ override def fromArrayDbl(array: Array[Double]): LinePathLL = new LinePathLL(array)
}