/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer

trait VecLength2
{
  def xMetresNum: Double
  def yMetresNum: Double
  def xKilometresNum: Double
  def yKilometresNum: Double
  def + (op: VecLength2): VecLength2
  def - (operand: VecLength2): VecLength2
  def * (operator: Double): VecLength2
  def / (operator: Double): VecLength2
  def magnitude: Length

  /** Produces the dot product of this 2 dimensional distance Vector and the operand. */
  @inline def dot(operand: VecLength2): Area
}

/** A 2 dimensional vector specified in metres as units rather than pure scalar numbers. */
final class VecM2 private(val xMetresNum: Double, val yMetresNum: Double) extends VecLength2 with TellElemDbl2
{ override def typeStr: String = "VecM2"

  /** The X component of this 2 dimensional [[Metres]] vector. */
  def x: Metres = Metres(xMetresNum)

  /** The Y component of this 2 dimensional [[Metres]] vector. */
  def y: Metres = Metres(yMetresNum)

  override def xKilometresNum: Double = xMetresNum / 1000
  override def yKilometresNum: Double = yMetresNum / 1000
  override def name1: String = "x"
  override def name2: String = "y"
  override def tell1: Double = xMetresNum
  override def tell2: Double = yMetresNum
  override def + (operand: VecLength2): VecM2 = new VecM2(xMetresNum + operand.xMetresNum, yMetresNum + operand.yMetresNum)
  override def - (operand: VecLength2): VecM2 = new VecM2(xMetresNum - operand.xMetresNum, yMetresNum - operand.yMetresNum)
  override def * (operator: Double): VecM2 = new VecM2(xMetresNum * operator, yMetresNum * operator)
  override def / (operator: Double): VecM2 = new VecM2(xMetresNum / operator, yMetresNum / operator)
  override def magnitude: Metres = Metres(math.sqrt(xMetresNum.squared + yMetresNum.squared))
  @inline override def dot(operand: VecLength2): MetresSq = MetresSq(xMetresNum * operand.xMetresNum + yMetresNum * operand.yMetresNum)
}

object VecM2
{
  /** Factory apply method for creating 2 dimensional vectors defined in [[Metres]] from the 2 [[Metres]] components. */
  def spply(x: Metres, y: Metres): VecM2 = new VecM2(x.metresNum, y.metresNum)

  /** Factory method for creating 2 dimensional vectors defined in [[Metres]] from the 2 [[Length]] components. */
  def lengths(x: Length, y: Length): VecM2 = new VecM2(x.metresNum, y.metresNum)

  /** Factory method for creating 2 dimensional vectors defined in [[Metres]] from the scalars of the components. */
  def metresNum(xMetresNum: Double, yMetresNum: Double): VecM2 = new VecM2(xMetresNum, yMetresNum)

  val buildImplicit: BuilderArrMap[VecM2, VecM2Arr] = new BuilderArrDbl2Map[VecM2, VecM2Arr]
  { override type BuffT = VecM2Buff
    override def fromDblArray(array: Array[Double]): VecM2Arr = new VecM2Arr(array)
    override def buffFromBufferDbl(buffer: ArrayBuffer[Double]): VecM2Buff = new VecM2Buff(buffer)
  }
}

/** Efficent Specialised [[Arr]] for [[VecM2]]s. */
class VecM2Arr(override val arrayUnsafe: Array[Double]) extends ArrDbl2[VecM2]
{ override type ThisT = VecM2Arr
  override def seqDefElem(d1: Double, d2: Double): VecM2 = VecM2.metresNum(d1, d2)
  override def fromArray(array: Array[Double]): VecM2Arr = new VecM2Arr(array)
  override def typeStr: String = "VecM2Arr"
  override def fElemStr: VecM2 => String = _.str
}

/** A specialised flat ArrayBuffer[Double] based class for [[VecM2]] collections. */
final class VecM2Buff(val unsafeBuffer: ArrayBuffer[Double]) extends AnyVal with BuffDbl2[VecM2]
{ override def typeStr: String = "VecM2Buff"
  def newElem(d1: Double, d2: Double): VecM2 = VecM2.metresNum(d1, d2)
}