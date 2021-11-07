/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package pglobe

/** A latitude-longitude line path. A quasi line path where the points are stored as points of latitude and longitude.Once the points are converted into a
 *  view, ie into pixel positions an actual polygon can be drawn or filled as desired. Do not create line paths that span an arc of greater than 90
 *  degrees as this may break the algorithms. */
class LinePathLL(val arrayUnsafe: Array[Double]) extends AnyVal with LatLongsLike with LinePathDbl2s[LatLong]
{ override type ThisT = LinePathLL
  override def unsafeFromArray(array: Array[Double]): LinePathLL = new LinePathLL(array)
  override def typeStr: String = "LinePathLL"

  /** closes this LinePathLL into a [[PolygonLL]] with a line Segment from the last point to the first point. */
  @inline def close: PolygonLL = new PolygonLL(arrayUnsafe)

  def +: (newElem: LatLong): LinePathLL =
  { val res = LinePathLL.uninitialised(elemsNum + 1)
    res.unsafeSetElem(0, newElem)
    dataIForeach{ (i, ll) => res.unsafeSetElem(i + 1, ll) }
    res
  }

  def ++ (operand: LinePathLL): LinePathLL =
  { val res = LinePathLL.uninitialised(elemsNum + operand.elemsNum)
    dataIForeach{ (i, ll) => res.unsafeSetElem(i, ll) }
    operand.dataIForeach(elemsNum) { (i, ll) => res.unsafeSetElem(i, ll) }
    res
  }

  def ++ (elems: LatLong *): LinePathLL =
  { val res = LinePathLL.uninitialised(elemsNum + elems.length)
    dataIForeach{ (i, ll) => res.unsafeSetElem(i, ll) }
    elems.iForeach(elemsNum){ (i, ll) => res.unsafeSetElem(i, ll) }
    res
  }

  def close(newElems: LatLong*): PolygonLL =
  { val res = PolygonLL.uninitialised(elemsNum + newElems.length)
    arrayUnsafe.copyToArray(res.arrayUnsafe)
    newElems.iForeach((i, ll) => res.unsafeSetElem(elemsNum + i, ll))
    res
  }

  /** Reverses the line path so its end point becomes its start point. */
  def reverse: LinePathLL = reverseData
}

object LinePathLL extends DataDbl2sCompanion[LatLong, LinePathLL]
{ override def fromArrayDbl(array: Array[Double]): LinePathLL = new LinePathLL(array)

  /** Apply factory method for creating Arrs of [[ElemDbl2]]s. */
  override def apply(elems: LatLong*): LinePathLL =
  {
    val length = elems.length
    val res = uninitialised(length)
    var count: Int = 0

    while (count < length)
    { res.arrayUnsafe(count * 2) = elems(count).dbl1
      res.arrayUnsafe(count * 2 + 1) = elems(count).dbl2
      count += 1
    }
    res
  }
}