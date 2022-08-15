/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
//import collection.mutable.ArrayBuffer

/** Specialised Array based immutable collection class for [[Polygon]]s.  */
/*final class PolygonArr(val unsafeArrayOfArrays: Array[Array[Double]]) extends AnyVal with ArrArrayDbl[PolygonGen]
{ override type ThisT = PolygonArr
  override def typeStr: String = "Polygons"
  override def unsafeFromArrayArray(aad: Array[Array[Double]]): PolygonArr = new PolygonArr(aad)
  override def apply(index: Int): PolygonGen = new PolygonGen(unsafeArrayOfArrays(index))
  override def fElemStr: PolygonGen => String = _.str

  /** The length of this Sequence. This will have the same value as the dataLength property inherited from [[SeqDefGen]][A]. */
  override def length: Int = ???

  /** Accesses the sequence-defined element by a 0 based index. */
  override def sdIndex(index: Int): PolygonGen = ???
}*/

/** Companion object for the [[PolygonArr]] class. */
/*object PolygonArr
{
  def apply(input: PolygonGen*): Polygons =
  {
    val array: Array[Array[Double]] = new Array[Array[Double]](input.length)
    var count = 0

    while (count < input.length)
    { array(count) = input(count).arrayUnsafe
      count += 1
    }
    new Polygons(array)
  }

  implicit val eqImplicit: Eq[Polygons] = ArrArrayDblEq[PolygonGen, Polygons]
}

class PolygonBuff(val unsafeBuff: ArrayBuffer[Array[Double]]) extends AnyVal with ArrayDoubleBuff[PolygonGen]
{ def apply(index: Int): PolygonGen = new PolygonGen(unsafeBuff(index))
}*/
