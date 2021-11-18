/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package pglobe

/** A latitude-longitude line path. A quasi line path where the points are stored as points of latitude and longitude.Once the points are converted into a
 *  view, ie into pixel positions an actual polygon can be drawn or filled as desired. Do not create line paths that span an arc of greater than 90
 *  degrees as this may break the algorithms. */
class LinePathLL(val arrayUnsafe: Array[Double]) extends AnyVal with LatLongsLike with LinePathDbl2s[LatLong]
{ override type ThisT = LinePathLL
  override def unsafeFromArray(array: Array[Double]): LinePathLL = new LinePathLL(array)
  override def typeStr: String = "LinePathLL"

  /** Alias for prepend. Prepends element, returning a new [[LatLong]]. */
  inline def %: (newElem: LatLong): LinePathLL = prepend(newElem)

  /** Prepends element, returning a new [[LatLong]]. Aliased by %: operator. */
  def prepend (newElem: LatLong): LinePathLL =
  { val res = LinePathLL.uninitialised(elemsNum + 1)
    res.unsafeSetElem(0, newElem)
    dataIForeach{ (i, ll) => res.unsafeSetElem(i + 1, ll) }
    res
  }

  /** Aliased by concat. Concatenate elements of the operand [[LinePathLL]] returning a new [[LinePathLL]]. An immutable append. */
  inline def ++ (operand: LinePathLL): LinePathLL = concat(operand)

  /** Concatenate elements of the operand [[LinePathLL]] returning a new [[LinePathLL]]. An immutable append. Aliased by ++ operator. */
  def concat (operand: LinePathLL): LinePathLL =
  { val res = LinePathLL.uninitialised(elemsNum + operand.elemsNum)
    dataIForeach{ (i, ll) => res.unsafeSetElem(i, ll) }
    operand.dataIForeach(elemsNum) { (i, ll) => res.unsafeSetElem(i, ll) }
    res
  }

  /** Alias for concat. Concatenate repeat [[LatLong]] elements, returning a new [[LinePathLL]]. An immutable append. */
  inline def ++ (elems: LatLong *): LinePathLL = concat(elems :_*)

  /** Concatenate repeat [[LatLong]] elements returning a new [[LinePathLL]]. An immutable append. Aliased by ++ operator. */
  def concat (elems: LatLong *): LinePathLL =
  { val res = LinePathLL.uninitialised(elemsNum + elems.length)
    dataIForeach{ (i, ll) => res.unsafeSetElem(i, ll) }
    elems.iForeach(elemsNum){ (i, ll) => res.unsafeSetElem(i, ll) }
    res
  }


  /** closes this LinePathLL into a [[PolygonLL]] with a line Segment from the last point to the first point. */
  @inline def close: PolygonLL = new PolygonLL(arrayUnsafe)

  /** Alias for concatClose. Concatenates the operand [[LatLong]] and closes into a PolyonLL. */
  inline def +!(newElem: LatLong): PolygonLL = concatClose(newElem)

  /** Concatenates the operand [[LatLong]] and closes into a [[PolyonLL]]. */
  def concatClose(newElem: LatLong): PolygonLL =
  { val res = PolygonLL.uninitialised(elemsNum + 1)
    arrayUnsafe.copyToArray(res.arrayUnsafe)
    res.unsafeSetElem(elemsNum, newElem)
    res
  }

  /** Alias for prependClose. Prepend the left hand operand element and close into a [[PolygonLL]].  */
  inline def +!:(newElem: LatLong): PolygonLL = prependClose(newElem)

  /** Prepend the left hand operand element and close into a [[PolygonLL]].  */
  def prependClose(newElem: LatLong): PolygonLL =
  { val res = PolygonLL.uninitialised(elemsNum + 1)
    res.unsafeSetElem(0, newElem)
    dataIForeach{ (i, ll) => res.unsafeSetElem(i + 1, ll) }
    res
  }

  /** Alias for concatClose. Concatenate the operand [[LatLong]]s and closes the line path into a [[PolyognLL]]. */
  inline def ++!(newElems: LatLong*): PolygonLL = concatClose(newElems :_*)

  /** Concatenate the operand [[LatLong]]s and closes the line path into a [[PolyognLL]]. */
  def concatClose(newElems: LatLong*): PolygonLL =
  { val res = PolygonLL.uninitialised(elemsNum + newElems.length)
    arrayUnsafe.copyToArray(res.arrayUnsafe)
    newElems.iForeach((i, ll) => res.unsafeSetElem(elemsNum + i, ll))
    res
  }

  /** Alias for concatClose. Concatenate the operand [[LinePathLL]] and close into a [[PolyognLL]]. */
  inline def ++!(operand: LinePathLL): PolygonLL = concatClose(operand)

  /** Concatenate the operand [[LinePathLL]] and closes the line path into a [[PolyognLL]]. */
  def concatClose(operand: LinePathLL): PolygonLL =
  { val res = PolygonLL.uninitialised(elemsNum + operand.elemsNum)
    arrayUnsafe.copyToArray(res.arrayUnsafe)
    operand.vertsIForeach{ (i, ll) => res.unsafeSetElem(elemsNum + i, ll) }
    res
  }

  /** Reverses the line path so its end point becomes its start point. */
  def reverse: LinePathLL = reverseData

  /** Reverses this [[LinePathLL]] and closes it returning a [[PolygonLL]] */
  def reverseClose: PolygonLL = new PolygonLL(unsafeReverseArray)

  /** Alias for concatTailInit. Concatenates the elements of the operand [[LinePathLL]] minus the head and the last element of the operand. Immutable
   * operation returns a new [[LinePathLL]]. */
  def +--(operand: LinePathLL): LinePathLL = new LinePathLL(arrayAppendTailInit(operand))

  /** Concatenates the elements of the operand [[LinePathLL]] minus the head and the last element of the operand. */
  def concatTailInit(operand: LinePathLL): LinePathLL = new LinePathLL(arrayAppendTailInit(operand))

  /** Alias for concatTailInitClose. Concatenates the elements of the operand [[LinePathLL]] minus the head and the last element of the operand. And
   *  then closes into a [[PolygonLL]]. */
  def +--!(operand: LinePathLL): PolygonLL = new PolygonLL(arrayAppendTailInit(operand))

  /** Concatenates the elements of the operand [[LinePathLL]] minus the head and the last element of the operand. And then closes into a
   *  [[PolygonLL]]. */
  def concatTailInitClose(operand: LinePathLL): PolygonLL = new PolygonLL(arrayAppendTailInit(operand))

  /** Creates a new backing Array[Double] with the elements of this [[LinePathLL]], with the elements of the operand
   * minus the head and last element of the operand. */
  def arrayAppendTailInit(operand: LinePathLL): Array[Double] =
  { val array = new Array[Double]((elemsNum + (operand.elemsNum - 2).max(0)) * 2)
    arrayUnsafe.copyToArray(array)
    iUntilForeach(2, operand.arrLen - 2) { i => array(elemsNum * 2 + i - 2) = operand.arrayUnsafe(i) }
    array
  }

  def vertsNum: Int = elemsNum

  /** Performs the side effecting function on the [[LatLong]] value of each vertex. */
  def vertsForeach[U](f: LatLong => U): Unit = dataForeach(f)

  def vertsIForeach[U](f: (Int, LatLong) => U): Unit = dataIForeach(f)
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