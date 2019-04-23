/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** Array[Double] based collection class for a LinePath. Conversion to and from the Vec2s class and Polygon class should not entail a runtime
 *  cost. */
class LinePath(val arr: Array[Double]) extends AnyVal with ProductD2s[Vec2]  with Transable[LinePath] with Vec2sLike
{
  override def typeStr: String = "LinePath"
  override def toString: String = LinePath.LinePathPersist.show(this)
  override def newElem(d1: Double, d2: Double): Vec2 = Vec2.apply(d1, d2)
  @inline def lengthFull: Int = arr.length / 2  
  @inline def xStart: Double = arr(0)
  @inline def yStart: Double = arr(1)
  @inline def pStart: Vec2 = Vec2(xStart, yStart)
 
  def fTrans(f: Vec2 => Vec2): LinePath =  new LinePath(arrTrans(f))
  
  def foreachEnd(f: (Double, Double) => Unit): Unit =
  { var count = 1
    while (count < lengthFull)
    { f(arr(count *2), arr( count * 2 + 1))
      count += 1      
    }
  }
  
  def draw(lineWidth: Double, colour: Colour = Colour.Black): LinePathDraw = LinePathDraw(this, lineWidth, colour)
}

object LinePath extends ProductD2sCompanion[Vec2, LinePath]
{
  implicit val factory: Int => LinePath = i => new LinePath(new Array[Double](i * 2))
  
  implicit object LinePathPersist extends ProductD2sBuilder[Vec2, LinePath]("LinePath")
  {
    override def fromArray(value: Array[Double]): LinePath = new LinePath(value)
  }
  def apply(pStart: Vec2, pEnds: Vec2 *): LinePath =
  { val arr = new Array[Double](pEnds.length * 2 + 2)
    arr(0) = pStart.x
    arr(1) = pStart.y
    var count = 0
    while (count < pEnds.length)
    { arr(count * 2 + 2) = pEnds(count).x
      arr(count * 2 + 3) = pEnds(count).y
      count += 1
    }
    new LinePath(arr)
  }
  
}

/** Companion object for the Vec2s collection class. */
//object LineSegs
//{
//  
//}