/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer

trait length2M extends TellElemDbl2

/** A 2 dimensional vector specified in metres as units rather than pure scalar numbers. */
final class Vec2M (val xMetresNum: Double, val yMetresNum: Double) extends length2M
{ override def typeStr: String = "Vec2M"

  /** The X component of this 2 dimensional [[Metres]] vector. */
  def x: Metres = Metres(xMetresNum)

  /** The Y component of this 2 dimensional [[Metres]] vector. */
  def y: Metres = Metres(yMetresNum)

  /** the name of the 1st element of this 2 element product. */
  override def name1: String = "x"

  /** the name of the 2nd element of this 2 element product. */
  override def name2: String = "y"

  /** Element 1 of this Show 2 element product. */
  override def tell1: Double = ???

  /** Element 2 of this Show 2 element product. */
  override def tell2: Double = ???

  def + (op: Vec2M): Vec2M = new Vec2M(xMetresNum + op.xMetresNum, yMetresNum + op.yMetresNum)
  def - (op: Vec2M): Vec2M = new Vec2M(xMetresNum - op.xMetresNum, yMetresNum - op.yMetresNum)
  def * (operator: Double): Vec2M = new Vec2M(xMetresNum * operator, yMetresNum * operator)
  def / (operator: Double): Vec2M = new Vec2M(xMetresNum / operator, yMetresNum / operator)
  def magnitude: Metres = Metres(math.sqrt(xMetresNum.squared + yMetresNum.squared))
  /** Produces the dot product of this 2 dimensional distance Vector and the operand. */
  @inline def dot(operand: Vec2M): Area = x * operand.x + y * operand.y
}

object Vec2M
{
  val buildImplicit: BuilderArrMap[Vec2M, Vec2MArr] = new BuilderArrDbl2Map[Vec2M, Vec2MArr]
  { override type BuffT = Vec2MBuff
    override def fromDblArray(array: Array[Double]): Vec2MArr = new Vec2MArr(array)
    override def buffFromBufferDbl(buffer: ArrayBuffer[Double]): Vec2MBuff = new Vec2MBuff(buffer)
  }
}

class Vec2MArr(override val arrayUnsafe: Array[Double]) extends ArrDbl2[Vec2M]
{ override type ThisT = Vec2MArr
  override def seqDefElem(d1: Double, d2: Double): Vec2M = new Vec2M(d1, d2)
  override def fromArray(array: Array[Double]): Vec2MArr = new Vec2MArr(array)
  override def typeStr: String = "Vec2Ms"
  override def fElemStr: Vec2M => String = _.str
}

/** A specialised flat ArrayBuffer[Double] based class for [[Pt2]]s collections. */
final class Vec2MBuff(val unsafeBuffer: ArrayBuffer[Double]) extends AnyVal with BuffDbl2[Vec2M]
{ override def typeStr: String = "Vec2MBuff"
  def newElem(d1: Double, d2: Double): Vec2M = new Vec2M(d1, d2)
}