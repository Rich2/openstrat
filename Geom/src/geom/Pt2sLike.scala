/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** The purpose of this trait is to provide the helper method for Vec2 transformations. */
trait Pt2sLike extends DataDbl2s[Pt2]
{ def arrTrans(f: Pt2 => Pt2): Array[Double] =
  { val newArray = new Array[Double](unsafeArray.length)
    var count = 0
    while (count < unsafeArray.length)
    {
      val newVec = f(unsafeArray(count) pp unsafeArray(count + 1))
      newArray(count) = newVec.x
      newArray(count + 1) = newVec.y
      count += 2
    }
    newArray
  }
  override def fElemStr: Pt2 => String = _.str
  final override def dataElem(d1: Double, d2: Double): Pt2 = Pt2.apply(d1, d2)
}