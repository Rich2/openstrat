/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer

/** Specialised Array based immutable collection class for [[Polygon]]s.  */
final class PolygonArr(val unsafeArrayOfArrays: Array[Array[Double]]) extends AnyVal with ArrayDblArr[Polygon]
{ override type ThisT = PolygonArr
  override def typeStr: String = "PolygonArr"
  override def unsafeFromArrayArray(aad: Array[Array[Double]]): PolygonArr = new PolygonArr(aad)
  override def apply(index: Int): PolygonGen = new PolygonGen(unsafeArrayOfArrays(index))
  override def fElemStr: Polygon => String = _.toString

  override def sdIndex(index: Int): PolygonGen = new PolygonGen(unsafeArrayOfArrays(index))
}

/** Companion object for the [[PolygonArr]] class. */
object PolygonArr
{
  def apply(input: PolygonGen*): PolygonArr =
  {
    val array: Array[Array[Double]] = new Array[Array[Double]](input.length)
    var count = 0

    while (count < input.length)
    { array(count) = input(count).unsafeArray
      count += 1
    }
    new PolygonArr(array)
  }

  implicit val eqImplicit: EqT[PolygonArr] = ArrArrayDblEq[Polygon, PolygonArr]
}

class PolygonBuff(val unsafeBuff: ArrayBuffer[Array[Double]]) extends AnyVal with ArrayDoubleBuff[Polygon]
{
  override type ThisT = PolygonBuff
  override def typeStr: String = "PolygonBuff"
 // override def apply(index: Int): PolygonGen = new PolygonGen(unsafeBuff(index))

  override def length: Int = unsafeBuff.length
  override def unsafeSetElem(i: Int, value: Polygon): Unit = unsafeBuff(i) = value.unsafeArray

  override def fElemStr: Polygon => String = ???

  override def sdIndex(index: Int): Polygon = ???
}

object PolygonBuff{
  def apply(initLen: Int = 4): PolygonBuff = new PolygonBuff(new ArrayBuffer[Array[Double]](initLen))
}