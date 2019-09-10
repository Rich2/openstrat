/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

trait Vec2sLike extends Any
{
  def arr: Array[Double]
  def arrTrans(f: Vec2 => Vec2): Array[Double] =
  {
    val newArr = new Array[Double](arr.length)
    var count = 0
    while (count < arr.length)
    {
      val newVec = f(arr(count) vv arr(count + 1))
      newArr(count) = newVec.x      
      newArr(count + 1) = newVec.y
      count += 2
    }
    newArr
  }
}
