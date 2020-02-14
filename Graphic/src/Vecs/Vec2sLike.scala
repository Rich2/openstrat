/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** The purpose of this trait is to provide the helper method for Vec2 transformations */
trait Vec2sLike extends Any with ArrProdDbl2[Vec2]
{ def arrTrans(f: Vec2 => Vec2): Array[Double] =
  { val newArray = new Array[Double](array.length)
    var count = 0
    while (count < array.length)
    {
      val newVec = f(array(count) vv array(count + 1))
      newArray(count) = newVec.x
      newArray(count + 1) = newVec.y
      count += 2
    }
    newArray
  }
}