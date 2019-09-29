/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

trait Vec2sLike extends Any with ProductD2s[Vec2]
{ def arrTrans(f: Vec2 => Vec2): Array[Double] =
  { val newArr = new Array[Double](array.length)
    var count = 0
    while (count < array.length)
    {
      val newVec = f(array(count) vv array(count + 1))
      newArr(count) = newVec.x      
      newArr(count + 1) = newVec.y
      count += 2
    }
    newArr
  }
}