/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import collection.mutable.ArrayBuffer

/** Array[Double] based collection class for Vec2s. Use Polygon or LinePath to represent those structures. Conversion to and from Polygon class and
 *  LinePath class should not entail a runtime cost. */
class Vec2s(val array: Array[Double]) extends TransAller with Vec2sLike with ArrProdDbl2[Vec2]
{ type ThisT = Vec2s
  type AlignT = Vec2s
  def unsafeFromArray(array: Array[Double]): Vec2s = new Vec2s(array)
  override def typeStr: String = "Vec2s"

  //override def toString: String = Vec2s.Vec2sPersist.show(this)
  // override def elemBuilder(d1: Double, d2: Double): Vec2 = Vec2.apply(d1, d2)
  override def canEqual(that: Any): Boolean = ???

  override def productArity: Int = ???

  override def productElement(n: Int): Any = ???
  
  @inline def lengthFull: Int = array.length / 2
  @inline def xStart: Double = array(0)
  @inline def yStart: Double = array(1)
  @inline def pStart: Vec2 = Vec2(xStart, yStart)
  @inline def toPolygon: PolygonGen = new PolygonGen(array)
  @inline def toLinePath: LinePath = new LinePath(array)

  /** Geometric transformation by the function from a 2 dimensional Vector value to a 2 dimensional vector value. */
  def fTrans(f: Vec2 => Vec2): Vec2s =  new Vec2s(arrTrans(f))

  /** Closes the line Path into a Polygon, by mirroring across the yAxis. This is useful for describing symetrical across the y Axis polygons, with
   * the minimum number of points. The implementation is efficient, but is logical equivalent of myVec2s ++ myVec2s.reverse.negX. */
  def yMirrorClose: PolygonGen =
  { val acc = appendArray(length)
    var count = arrLen

    foreachReverse { orig =>
      acc(count) = - orig.x
      acc(count + 1) = orig.y
      count += 2
    }
    new PolygonGen(acc)
  }

  def toPathDraw(lineWidth: Double, colour: Colour = Colour.Black): LinePathDraw = LinePathDraw(this.toLinePath, lineWidth, colour)
}

object Vec2s extends ProdDbl2sCompanion[Vec2, Vec2s]
{
  implicit val persistImplicit: ArrProdDbl2Persist[Vec2, Vec2s] = new ArrProdDbl2Persist[Vec2, Vec2s]("Vec2s")
  { override def fromArray(value: Array[Double]): Vec2s = new Vec2s(value)
  }

  implicit val arrArrayImplicit: ArrFlatBuild[Vec2s] = Vec2.vec2sBuildImplicit
}

class Vec2Buff(val buffer: ArrayBuffer[Double]) extends AnyVal with BuffProdDbl2[Vec2]
{ def dblsToT(d1: Double, d2: Double): Vec2 = Vec2(d1, d2)
}

