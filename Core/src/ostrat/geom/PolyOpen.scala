/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

class PolyOpen(val arr: Array[Double]) extends AnyVal with Transable[PolyOpen]
{
  def lengthFull: Int = arr.length / 2
  def length: Int = lengthFull - 1
  def xStart: Double = arr(0)
  def yStart: Double = arr(1)
  @inline def pStart: Vec2 = Vec2(xStart, yStart)
  //def xEnd(index: Int): Double = 
  def fTrans(f: Vec2 => Vec2): PolyOpen =
  {
    val newArr = new Array[Double](arr.length)
    var count = 0
    while (count < lengthFull)
    {
      val newVec = f(arr(count * 2) vv arr(count * 2 + 1))
      newArr(count * 2) = newVec.x
      newArr(count *2 + 1) = newVec.y
      count += 1
    }
    new PolyOpen(newArr)
  }
}