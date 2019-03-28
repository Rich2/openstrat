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

/** Array[Double] based collection class for Vec2s which doesn't represent a closed Polygon. Conversion to and from the Polygon class should not
 *  entail a runtime cost. */
class Vec2s(val arr: Array[Double]) extends AnyVal with ProductD2s[Vec2]  with Transable[Vec2s] with Vec2sLike
{
  override def typeName: Symbol = 'Vec2s
  override def toString: String = Vec2s.Vec2sPersist.persist(this)
  override def newElem(d1: Double, d2: Double): Vec2 = Vec2.apply(d1, d2)
  @inline def lengthFull: Int = arr.length / 2  
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

object Vec2s extends ProductD2sCompanion[Vec2, Vec2s]
{
  implicit val factory: Int => Vec2s = i => new Vec2s(new Array[Double](i * 2))
  
  implicit object Vec2sPersist extends ProductD2sBuilder[Vec2, Vec2s]('Vec2s)
  {
    override def fromArray(value: Array[Double]): Vec2s = new Vec2s(value)
  }
}

/** Companion object for the Vec2s collection class. */
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
