/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Array[Double] based collection class for a LinePath. Conversion to and from the Vec2s class and Polygon class should not entail a runtime
 *  cost. */
class LinePath(val array: Array[Double]) extends AnyVal with ArrProdDbl2[Vec2] with Transer with Vec2sLike
{ type ThisT = LinePath
  type AlignT = LinePath
  def unsafeFromArray(array: Array[Double]): LinePath = new LinePath(array)
  override def typeStr: String = "LinePath"
  //override def toString: String = LinePath.LinePathPersist.show(this)
 // override def elemBuilder(d1: Double, d2: Double): Vec2 = Vec2.apply(d1, d2)
  @inline def lengthFull: Int = array.length / 2
  @inline def xStart: Double = array(0)
  @inline def yStart: Double = array(1)
  @inline def pStart: Vec2 = Vec2(xStart, yStart)
 
  def fTrans(f: Vec2 => Vec2): LinePath =  new LinePath(arrTrans(f))
  
  def foreachEnd(f: (Double, Double) => Unit): Unit =
  { var count = 1
    while (count < lengthFull)
    { f(array(count *2), array( count * 2 + 1))
      count += 1      
    }
  }
  
  def draw(lineWidth: Double, colour: Colour = Colour.Black): LinePathDraw = LinePathDraw(this, lineWidth, colour)
}

object LinePath extends ProdDbl2sCompanion[Vec2, LinePath]
{
  implicit val persistImplicit: ArrProdDbl2Persist[Vec2, LinePath] = new ArrProdDbl2Persist[Vec2, LinePath]("LinePath")
  { override def fromArray(value: Array[Double]): LinePath = new LinePath(value)
  }

  def apply(pStart: Vec2, pEnds: Vec2 *): LinePath =
  { val array = new Array[Double](pEnds.length * 2 + 2)
    array(0) = pStart.x
    array(1) = pStart.y
    var count = 0
    while (count < pEnds.length)
    { array(count * 2 + 2) = pEnds(count).x
      array(count * 2 + 3) = pEnds(count).y
      count += 1
    }
    new LinePath(array)
  }
  
}

/** Companion object for the Vec2s collection class. */
//object LineSegs
//{
//  
//}