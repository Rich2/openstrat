/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** An immutable trait defined by  a collection of homogeneous value products. The underlying array is Array[Double], Array[Int] etc. The descendant
 *  classes include both [[ValueNscollection]]s and classes like polygons and lines. */
trait ValueNsData[A <: ValueNElem] extends Any with ArrayLikeBacked[A]
{ type ThisT <: ValueNsData[A]

  /** The number of atomic values, Ints, Doubles, Longs etc that specify / construct an element of this immutable flat Array based collection
   *  class. */
  def elemProdSize: Int

  /** The total  number of atomic values, Ints, Doubles, Longs etc in the backing Array. */
  def arrLen: Int

  /** The number of product elements in this collection. For example in a [[PolygonImp], this is the number of [[Pt2]]s in the [[Polygon]] */
  final override def elemsNum: Int = arrLen / elemProdSize

  /** Maps the dat elements that specify the final class. */
  def dataMap[B <: ValueNElem, N <: ValueNsData[B]](f: A => B)(implicit factory: Int => N): N =
  { val res = factory(elemsNum)
    var count: Int = 0
    while (count < elemsNum) {
      val newValue: B = f(indexData(count))
      res.unsafeSetElem(count, newValue)
      count += 1
    }
    res
  }
}