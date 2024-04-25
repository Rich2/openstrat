/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer

/** Common trait for [[VecKm2]] and [[PtKm2]] */
trait VecKm2Like extends VecLength2Like
{ /** The X component of this 2 dimensional [[Metres]] vector. */
  def x: Kilometres = Kilometres(xKilometresNum)

  /** The Y component of this 2 dimensional [[Metres]] vector. */
  def y: Kilometres = Kilometres(yKilometresNum)

  override def xMetresNum: Double = xKilometresNum * 1000
  override def yMetresNum: Double = yKilometresNum * 1000
  override def tell1: Double = xKilometresNum
  override def tell2: Double = yKilometresNum
  override def xPos: Boolean = xKilometresNum >= 0
  override def xNeg: Boolean = xKilometresNum <= 0
  override def yPos: Boolean = yKilometresNum >= 0
  override def yNeg: Boolean = yKilometresNum <= 0
}

/** A 2 dimensional vector specified in metres as units rather than pure scalar numbers. */
final class VecKm2 private(val xKilometresNum: Double, val yKilometresNum: Double) extends VecKm2Like with VecLength2// with TellElemDbl2
{ override def typeStr: String = "VecKm2"

  override def + (operand: VecLength2): VecKm2 = new VecKm2(xKilometresNum + operand.xKilometresNum, yKilometresNum + operand.yKilometresNum)
  override def - (operand: VecLength2): VecKm2 = new VecKm2(xKilometresNum - operand.xKilometresNum, yKilometresNum - operand.yKilometresNum)
  override def * (operator: Double): VecKm2 = new VecKm2(xKilometresNum * operator, yKilometresNum * operator)
  override def / (operator: Double): VecKm2 = new VecKm2(xKilometresNum / operator, yKilometresNum / operator)
  override def magnitude: Kilometres = Kilometres(math.sqrt(xKilometresNum.squared + yKilometresNum.squared))
  @inline override def dot(operand: VecLength2): KilometresSq = KilometresSq(xKilometresNum * operand.xKilometresNum + yKilometresNum * operand.yKilometresNum)
}

object VecKm2
{
  /** Factory apply method for creating 2 dimensional vectors defined in [[Kilometres]] from the 2 [[Kilometres]] components. */
  def spply(x: Kilometres, y: Kilometres): VecKm2 = new VecKm2(x.kilometresNum, y.kilometresNum)

  /** Factory method for creating 2 dimensional vectors defined in [[Metres]] from the 2 [[Length]] components. */
  def lengths(x: Length, y: Length): VecKm2 = new VecKm2(x.kilometresNum, y.kilometresNum)

  /** Factory method for creating 2 dimensional vectors defined in [[Metres]] from the scalars of the components. */
  def metresNum(xKilometresNum: Double, yKilometresNum: Double): VecKm2 = new VecKm2(xKilometresNum, yKilometresNum)

  val buildImplicit: BuilderArrMap[VecKm2, VecKm2Arr] = new BuilderArrDbl2Map[VecKm2, VecKm2Arr]
  { override type BuffT = VecKm2Buff
    override def fromDblArray(array: Array[Double]): VecKm2Arr = new VecKm2Arr(array)
    override def buffFromBufferDbl(buffer: ArrayBuffer[Double]): VecKm2Buff = new VecKm2Buff(buffer)
  }
}

/** Efficent Specialised [[Arr]] for [[VecKm2]]s. */
class VecKm2Arr(override val arrayUnsafe: Array[Double]) extends ArrDbl2[VecKm2]
{ override type ThisT = VecKm2Arr
  override def seqDefElem(d1: Double, d2: Double): VecKm2 = VecKm2.metresNum(d1, d2)
  override def fromArray(array: Array[Double]): VecKm2Arr = new VecKm2Arr(array)
  override def typeStr: String = "VecKm2Arr"
  override def fElemStr: VecKm2 => String = _.str
}

/** A specialised flat ArrayBuffer[Double] based class for [[VecKm2]] collections. */
final class VecKm2Buff(val unsafeBuffer: ArrayBuffer[Double]) extends AnyVal with BuffDbl2[VecKm2]
{ override def typeStr: String = "VecKm2Buff"
  def newElem(d1: Double, d2: Double): VecKm2 = VecKm2.metresNum(d1, d2)
}