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

class Vec2s(val arr: Array[Double]) extends AnyVal with Transable[Vec2s] with Vec2sLike
{
  @inline def lengthFull: Int = arr.length / 2
  @inline def length: Int = lengthFull - 1
  @inline def xStart: Double = arr(0)
  @inline def yStart: Double = arr(1)
  @inline def pStart: Vec2 = Vec2(xStart, yStart)
 
  def fTrans(f: Vec2 => Vec2): Vec2s =  new Vec2s(arrTrans(f))
  
  def foreachEnd(f: (Double, Double) => Unit): Unit =
  { var count = 1
    while (count < lengthFull)
    { f(arr(count *2), arr( count * 2 + 1))
      count += 1      
    }
  }
  
  def draw(lineWidth: Double, colour: Colour = Colour.Black): Vec2sDraw = Vec2sDraw(this, lineWidth, colour)
}

object LineSegs
{
  def apply(pStart: Vec2, pEnds: Vec2 *): Vec2s =
  { val arr = new Array[Double](pEnds.length * 2 + 2)
    arr(0) = pStart.x
    arr(1) = pStart.y
    var count = 0
    while (count < pEnds.length)
    { arr(count * 2 + 2) = pEnds(count).x
      arr(count * 2 + 3) = pEnds(count).y
      count += 1
    }
    new Vec2s(arr)
  }
}
