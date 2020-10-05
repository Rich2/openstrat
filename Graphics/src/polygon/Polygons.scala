/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
//import collection.mutable.ArrayBuffer

/** Specialised Array based immutable collection class for [[Polygon]]s.  */
/*final class Polygons(val array: Array[Array[Double]]) extends AnyVal with ArrArrayDbl[PolygonGen]
{ override type ThisT = Polygons
  override def typeStr: String = "Polygons"
  override def unsafeFromArrayArray(aad: Array[Array[Double]]): Polygons = new Polygons(aad)
  def apply(index: Int): PolygonGen = new PolygonGen(array(index))
  override def fElemStr: PolygonGen => String = _.str
}*/

/** Companion object for the [[Polygons]] class. */
object Polygons
/*{
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
