/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** The purpose of this trait is to provide the helper method for Vec2 transformations. */
trait Vec2sLikeProdDbl2 extends ArrProdDbl2[Vec2]
{ def arrTrans(f: Vec2 => Vec2): Array[Double] =
  { val newArray = new Array[Double](arrayUnsafe.length)
    var count = 0
    while (count < arrayUnsafe.length)
    {
      val newVec = f(arrayUnsafe(count) vv arrayUnsafe(count + 1))
      newArray(count) = newVec.x
      newArray(count + 1) = newVec.y
      count += 2
    }
    newArray
  }
  override def fElemStr: Vec2 => String = _.str
  final override def elemBuilder(d1: Double, d2: Double): Vec2 = Vec2.apply(d1, d2)
  override def foldLeft[B](initial: B)(f: (B, Vec2) => B): B = super.foldLeft(initial)(f)
}