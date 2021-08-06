/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer

/** An immutable flat efficient Array backed sequence collection class of 3 dimensional points. This is the default collection class for [Pt3]s. */
final class Pt3s(val arrayUnsafe: Array[Double]) extends AnyVal with ArrDbl3s[Pt3]
{ type ThisT = Pt3s
  override def typeStr: String = "Vec3s"
  def dataElem(d1: Double, d2: Double, d3: Double): Pt3 = Pt3(d1, d2, d3)
  def unsafeFromArray(array: Array[Double]): Pt3s = new Pt3s(array)
  override def fElemStr: Pt3 => String = _.str
}

/** A specialised flat ArrayBuffer[Double] based class for [[Pt3]]s collections. */
final class Pt3Buff(val unsafeBuff: ArrayBuffer[Double]) extends AnyVal with BuffDbl3s[Pt3]
{ def dblsToT(d1: Double, d2: Double, d3: Double): Pt3 = Pt3(d1, d2, d3)
}