/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** Efficient Immutable Array[Double] based collection class, with the Angle values stored as arc seconds. */
final class Angles(val unsafeArray: Array[Double]) extends AnyVal with Dbl1Arr[Angle]
{ override type ThisT = Angles
  override def typeStr: String = "Angles"
  override def newElem(dblValue: Double): Angle = Angle.secs(dblValue)
  override def unsafeFromArray(array: Array[Double]): Angles = new Angles(array)
  override def fElemStr: Angle => String = _.toString

  /** Not sure about this method. */
  override def foreachArr(f: Dbls => Unit): Unit = ???
}

/** Companion object for [[Angles]] class. */
object Angles
{
  def apply(elems: Angle*): Angles =
  { val array: Array[Double] = new Array[Double](elems.length)
    elems.iForeach((i, a) => array(i) = a.secs)
    new Angles(array)
  }

  /** Sequence of the four cardinal angles, 0, -90, 180, 90 degrees in clockwise order. */
  val cross: Angles = Angles(0.angle, 270.angle, 180.angle, 90.angle)

  /** Sequence of the four cardinal angles rotated by 45 degrees, 45, -45, -135, 135 degrees in clockwise order. */
  val cross45: Angles = Angles(45.angle, 315.angle, 225.angle, 135.angle)
}