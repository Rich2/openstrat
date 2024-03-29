/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** Efficient Immutable Array[Double] based collection class, with the Angle values stored as arc seconds. */
final class Angles(val arrayUnsafe: Array[Double]) extends AnyVal with ArrDbl1[Angle]
{ override type ThisT = Angles
  override def typeStr: String = "Angles"
  override def newElem(dblValue: Double): Angle = Angle.secs(dblValue)
  override def fromArray(array: Array[Double]): Angles = new Angles(array)
  override def fElemStr: Angle => String = _.toString

  /** Not sure about this method. */
  override def foreachArr(f: DblArr => Unit): Unit = ???

  /** The total  number of atomic values, Ints, Doubles, Longs etc in the backing Array. */
  //override def dsLen: Int = ???
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
  val cross: Angles = Angles(0.degs, 270.degs, 180.degs, 90.degs)

  /** Sequence of the four cardinal angles rotated by 45 degrees, 45, -45, -135, 135 degrees in clockwise order. */
  val cross45: Angles = Angles(45.degs, 315.degs, 225.degs, 135.degs)
}