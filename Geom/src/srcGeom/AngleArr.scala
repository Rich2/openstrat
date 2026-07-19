/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** Efficient Immutable Array[Double] based collection class, with the Angle values stored as arc seconds. */
final class AngleArr(val arrayUnsafe: Array[Double]) extends AnyVal, ArrDbl1[Angle]
{ override type ThisT = AngleArr
  override def typeStr: String = "AngleArr"
  override def elemFromDbl(dblValue: Double): Angle = Angle.secs(dblValue)
  override def fromArray(array: Array[Double]): AngleArr = new AngleArr(array)
  override def fElemStr: Angle => String = _.toString

  /** Not sure about this method. */
  override def foreachArr(f: DblArr => Unit): Unit = ???
}

/** Companion object for [[AngleArr]] class. */
object AngleArr
{
  def apply(elems: Angle*): AngleArr =
  { val array: Array[Double] = new Array[Double](elems.length)
    elems.iForeach((i, a) => array(i) = a.secs)
    new AngleArr(array)
  }

  /** Sequence of the four cardinal angles, 0, -90, 180, 90 degrees in clockwise order. */
  val cross: AngleArr = AngleArr(0.degs, 270.degs, 180.degs, 90.degs)

  /** Sequence of the four cardinal angles rotated by 45 degrees, 45, -45, -135, 135 degrees in clockwise order. */
  val cross45: AngleArr = AngleArr(45.degs, 315.degs, 225.degs, 135.degs)
}