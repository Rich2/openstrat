/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer, math.*, reflect.ClassTag

/** Common trait for [[VecM2]] and [[PtM2]]. */
trait VecPtM2 extends VecPtLen2
{ /** The X component of this 2-dimensional [[Metres]] vector. */
  def x: Metres = Metres(xMetresNum)

  /** The Y component of this 2-dimensional [[Metres]] vector. */
  def y: Metres = Metres(yMetresNum)

  override def xFemtometresNum: Double = xMetresNum * 1e15
  override def yFemtometresNum: Double = yMetresNum * 1e15
  override def xPicometresNum: Double = xMetresNum * 1e12
  override def yPicometresNum: Double = yMetresNum * 1e12
  override def xKilometresNum: Double = xMetresNum * 1e-3
  override def yKilometresNum: Double = yMetresNum * 1e-3
  override def tell1: Double = xMetresNum
  override def tell2: Double = yMetresNum
  override def xPos: Boolean = x.nonNeg
  override def xNeg: Boolean = x.neg
  override def yPos: Boolean = y.nonNeg
  override def yNeg: Boolean = y.neg
}

/** A 2-dimensional point specified in [[Metres]] as units rather than pure scalar numbers. */
final class PtM2 private(val xMetresNum: Double, val yMetresNum: Double) extends PtLen2, VecPtM2, TellElemDbl2
{ override type ThisT = PtM2
  override type LineSegT = LSegM2
  override def typeStr: String = "PtM2"
  override def slate(operand: VecPtLen2): PtM2 = new PtM2(xMetresNum + operand.xMetresNum, yMetresNum + operand.yMetresNum)
  override def slate(xOperand: Length, yOperand: Length): PtM2 = PtM2(xMetresNum + xOperand.metresNum, yMetresNum + yOperand.metresNum)
  override def slateX(xOperand: Length): PtM2 = new PtM2(xMetresNum + xOperand.metresNum, y.metresNum)
  override def slateY(yOperand: Length): PtM2 = new PtM2(xMetresNum, yMetresNum + yOperand.metresNum)
  override def slateFrom(operand: PtLen2): PtM2 = new PtM2(xMetresNum - operand.xMetresNum, yMetresNum - operand.yMetresNum)
  override def scale (operand: Double): PtM2 = new PtM2(xMetresNum * operand, yMetresNum * operand)
  override def mapGeom2(operator: Length): Pt2 = Pt2(xMetresNum / operator.metresNum, yMetresNum / operator.metresNum)
  override def - (operand: VecLen2): PtM2 = new PtM2(xMetresNum - operand.xMetresNum, yMetresNum - operand.yMetresNum)
  override def - (operand: PtLen2): VecM2 = VecM2(xMetresNum - operand.xMetresNum, yMetresNum - operand.yMetresNum)
  override def revY: PtM2 = new PtM2(xMetresNum, -yMetresNum)
  override def revYIf(cond: Boolean): PtM2 = ife(cond, new PtM2(xMetresNum, -yMetresNum), this)
  override def rotate180: PtM2 = new PtM2(-xMetresNum, -yMetresNum)
  override def rotate180If(cond: Boolean): PtM2 = ife(cond, rotate180, this)
  override def rotate180IfNot(cond: Boolean): PtM2 = ife(cond, this, rotate180)
  override def rotate(a: AngleVec): PtM2 =  PtM2.apply(x.metresNum * a.cos - y.metresNum * a.sin, x.metresNum * a.sin + y.metresNum * a.cos)

  override def rotateRadians(r: Double): PtM2 =
  { val newX = xMetresNum * cos(r) - yMetresNum * sin(r)
    val newY =
    { val ya = xMetresNum * sin(r)
      val yb = yMetresNum * cos(r)
      ya + yb
    }
    new PtM2(newX, newY)
  }

  override def lineSegTo(endPt: PtLen2): LSegM2 = LSegM2(xMetresNum, yMetresNum, endPt.xMetresNum, endPt.yMetresNum)
  override def lineSegFrom(startPt: PtLen2): LSegM2 = LSegM2(startPt.xMetresNum, startPt.yMetresNum, xMetresNum, yMetresNum)
}

/** Companion object for [[PtM2]] class contains factory methods. */
object PtM2
{ /** Factory apply method to create 2-dimensional point specified in [[Metres]]. If you want to consttuct from scalars use the metresNum method. */
  def apply(x: Length, y: Length): PtM2 = new PtM2(x.metresNum, y.metresNum)
  
  /** Factory method for creating a 2-dimensional point measured in metres from the scalar [[Double]] values. */
  def apply(xMetresNum: Double, yMetresNum: Double): PtM2 = new PtM2(xMetresNum, yMetresNum)

  /** The origin of this 2-dimensional space. */
  def origin: PtM2 = new PtM2(0, 0)

  /** [[Show]] type class instance / evidence for [[PtM2]]. */
  given persistEv: ShowTellDbl2[PtM2] = ShowTellDbl2[PtM2]("Metres2")

  /** [[Unshow]] type class instance / evidence for [[PtM2]]. */
  given unShowEv: UnshowDbl2[PtM2] = UnshowDbl2[PtM2]("Metres2", "x", "y", new PtM2(_, _))

  /** Implicit [[BuilderArrMap]] instance / evidence for [[PtM2]] and [[PtM2Arr]]. */
  given builderMapEv: BuilderMapArrDbl2[PtM2, PtM2Arr] = new BuilderMapArrDbl2[PtM2, PtM2Arr]
  { type BuffT = PtM2Buff
    override def fromDblArray(array: Array[Double]): PtM2Arr = new PtM2Arr(array)
    def buffFromBufferDbl(buffer: ArrayBuffer[Double]): PtM2Buff = new PtM2Buff(buffer)
  }

  /** Implicit [[BuilderLinePathLikeMap]] instance / evidence for [[PtM2]] and [[LinePathM2]]. */
  given buildLinePathMapEv: BuilderLinePathDbl2Map[PtM2, LinePathM2] = new BuilderLinePathDbl2Map[PtM2, LinePathM2]
  { override type BuffT = PtM2Buff
    override def fromDblArray(array: Array[Double]): LinePathM2 = new LinePathM2(array)
    override def buffFromBufferDbl(inp: ArrayBuffer[Double]): PtM2Buff = new PtM2Buff(inp)
  }

  /** Implicit [[BuilderPolygonLikeMap]] instance / evidence for [[PtM2]] and [[PolygonM2]]. */
  given polygonBuildMapEv: BuilderPolygonDbl2Map[PtM2, PolygonM2Gen] = new BuilderPolygonDbl2Map[PtM2, PolygonM2Gen]
  { override type BuffT = PtM2Buff
    override def fromDblArray(array: Array[Double]): PolygonM2Gen = new PolygonM2Gen(array)
    override def buffFromBufferDbl(inp: ArrayBuffer[Double]): PtM2Buff = new PtM2Buff(inp)
  }

  /** Implicit instance for the [[PolygonM2Pair]] builder. This has to go in the [[PtM2]] companion object so it can be found by an A => B function where
   * [[PtM2]] is the type B parameter. */
  given polygonPairBuildEv[A2](using ctA: ClassTag[A2]): PolygonM2PairBuilder[A2] = new PolygonM2PairBuilder[A2]
}

/** Specialised immutable Array based collection class for [[PtM2]]s. */
class PtM2Arr(val arrayUnsafe: Array[Double]) extends AnyVal, ArrDbl2[PtM2]
{ type ThisT = PtM2Arr
  override def fromArray(array: Array[Double]): PtM2Arr = new PtM2Arr(array)
  override def typeStr: String = "PtM2Arr"
  override def elemFromDbls(d1: Double, d2: Double): PtM2 = PtM2.apply(d1, d2)
  override def fElemStr: PtM2 => String = _.str
}

/** Companion object for the [[PtM2Arr]] class. Contains implicit Instance for Persist type class. */
object PtM2Arr extends CompanionSlDbl2[PtM2, PtM2Arr]
{ override def fromArray(array: Array[Double]): PtM2Arr = new PtM2Arr(array)

  /** [[Show]] type class instance / evidence for [[PtM2Arr]]. */
  given showEv: ShowSequ[PtM2, PtM2Arr] = ShowSequ[PtM2, PtM2Arr]()

  /** [[Unshow]] type class instance / evidence for [[PtM2Arr]]. */
  given unshowEv: UnshowSeq[PtM2, PtM2Arr] = UnshowSeq[PtM2, PtM2Arr]()
}

/** A specialised flat ArrayBuffer[Double] based class for [[PtM2]]s collections. */
final class PtM2Buff(val bufferUnsafe: ArrayBuffer[Double]) extends AnyVal, BuffDbl2[PtM2]
{ override def typeStr: String = "BuffPtMetre2"
  def elemFromDbls(d1: Double, d2: Double): PtM2 = PtM2.apply(d1, d2)
}

object PtM2Buff
{ /** Factory apply method for [[PtM2Buff]] a buffer for 2-dimensional points specified in [[Metres]].  */
  def apply(initSize: Int = 4): PtM2Buff = new PtM2Buff(new ArrayBuffer[Double](initSize * 2))
}

/** A 2-dimensional vector specified in metres as units rather than pure scalar numbers. */
final class VecM2 private(val xMetresNum: Double, val yMetresNum: Double) extends VecLen2, VecPtM2
{ override def typeStr: String = "VecM2"
  override def + (operand: VecLen2): VecM2 = new VecM2(xMetresNum + operand.xMetresNum, yMetresNum + operand.yMetresNum)
  override def - (operand: VecLen2): VecM2 = new VecM2(xMetresNum - operand.xMetresNum, yMetresNum - operand.yMetresNum)
  override def unary_- : VecM2 = VecM2(-xMetresNum, -yMetresNum)
  override def * (operator: Double): VecM2 = new VecM2(xMetresNum * operator, yMetresNum * operator)
  override def / (operator: Double): VecM2 = new VecM2(xMetresNum / operator, yMetresNum / operator)
  override def magnitude: Metres = Metres(math.sqrt(xMetresNum.squared + yMetresNum.squared))
  @inline override def dot(operand: VecLen2): Metrares = Metrares(xMetresNum * operand.xMetresNum + yMetresNum * operand.yMetresNum)
  override def slate(operand: VecPtLen2): VecM2 = VecM2(xMetresNum + operand.xMetresNum, yMetresNum + operand.yMetresNum)
  override def slate(xOperand: Length, yOperand: Length): VecM2 = VecM2(xMetresNum + xOperand.metresNum, yMetresNum + yOperand.metresNum)
  override def slateX(xOperand: Length): VecM2 = VecM2(xMetresNum + xOperand.metresNum, yMetresNum)
  override def slateY(yOperand: Length): VecM2 = VecM2(xMetresNum, yMetresNum + yOperand.metresNum)
  override def scale(operand: Double): VecM2 = VecM2(xMetresNum * operand, yMetresNum * operand)
  override def mapGeom2(operator: Length): Vec2 = Vec2(xMetresNum / operator.metresNum, yMetresNum / operator.metresNum)
}

object VecM2
{ /** Factory apply method for creating 2-dimensional vectors defined in [[Metres]] from the 2 [[Metres]] components. */
  def spply(x: Length, y: Length): VecM2 = new VecM2(x.metresNum, y.metresNum)

  /** Factory method for creating 2-dimensional vectors defined in [[Metres]] from the 2 [[Double]] values. */
  def apply(xMetresNum: Double, yMetresNum: Double): VecM2 = new VecM2(xMetresNum, yMetresNum)
  
  /** [[BuilderArrMap]] type class instance for [[VecM2]] and [[VecM2Arr]]. */
  val builderArrMapEv: BuilderArrMap[VecM2, VecM2Arr] = new BuilderMapArrDbl2[VecM2, VecM2Arr]
  { override type BuffT = VecM2Buff
    override def fromDblArray(array: Array[Double]): VecM2Arr = new VecM2Arr(array)
    override def buffFromBufferDbl(buffer: ArrayBuffer[Double]): VecM2Buff = new VecM2Buff(buffer)
  }
}

/** Efficient Specialised [[Arr]] for [[VecM2]]s. */
class VecM2Arr(override val arrayUnsafe: Array[Double]) extends ArrDbl2[VecM2]
{ override type ThisT = VecM2Arr
  override def elemFromDbls(d1: Double, d2: Double): VecM2 = VecM2(d1, d2)
  override def fromArray(array: Array[Double]): VecM2Arr = new VecM2Arr(array)
  override def typeStr: String = "VecM2Arr"
  override def fElemStr: VecM2 => String = _.str
}

/** A specialised flat ArrayBuffer[Double] based class for [[VecM2]] collections. */
final class VecM2Buff(val bufferUnsafe: ArrayBuffer[Double]) extends AnyVal, BuffDbl2[VecM2]
{ override def typeStr: String = "VecM2Buff"
  def elemFromDbls(d1: Double, d2: Double): VecM2 = VecM2(d1, d2)
}