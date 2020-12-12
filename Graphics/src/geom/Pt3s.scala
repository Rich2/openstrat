/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** An immutable flat efficient Array backed sequence collection class of 3 dimensional points. */
class Pt3s(val arrayUnsafe: Array[Double]) extends AnyVal with ArrProdDbl3[Pt3]
{ type ThisT = Pt3s
  override def typeStr: String = "Vec3s"
  //override def elemBuilder(d1: Double, d2: Double, d3): Vec2 = Vec2.apply(d1, d2)
  def newElem(d1: Double, d2: Double, d3: Double): Pt3 = Pt3(d1, d2, d3)
  def unsafeFromArray(array: Array[Double]): Pt3s = new Pt3s(array)
  override def fElemStr: Pt3 => String = _.str
}
