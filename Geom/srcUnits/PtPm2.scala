/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer, math._, reflect.ClassTag

/** Common trait for [[VecPm2]] and [[PtPm2]] */
trait VecPtPm2 extends VecPtLen2, TellElemDbl2
{ /** The X component of this 2-dimensional [[Picometres]] vector. */
  def x: Picometres = Picometres(xPicometresNum)

  /** The Y component of this 2-dimensional [[Picometres]] vector. */
  def y: Picometres = Picometres(yPicometresNum)

  override def xFemtometresNum: Double = xPicometresNum * 1e-15
  override def yFemtometresNum: Double = yPicometresNum * 1e-15
  override def xMetresNum: Double = xPicometresNum * 1e-12
  override def yMetresNum: Double = yPicometresNum * 1e-12
  override def xKilometresNum: Double = xPicometresNum * 1e-15
  override def yKilometresNum: Double = yPicometresNum * 1e-15
  override def tell1: Double = xPicometresNum
  override def tell2: Double = yPicometresNum
  override def xPos: Boolean = xPicometresNum >= 0
  override def xNeg: Boolean = xPicometresNum <= 0
  override def yPos: Boolean = yPicometresNum >= 0
  override def yNeg: Boolean = yPicometresNum <= 0
}

/** A 2-dimensional point specified in [[Picometres]] as units rather than pure scalar numbers. */
final class PtPm2 private(val xPicometresNum: Double, val yPicometresNum: Double) extends PtLen2, VecPtPm2
{ override type ThisT = PtPm2
  override type LineSegT = LineSegPm2
  override def typeStr: String = "PtPm2"

  override def slate(xDelta: Length, yDelta: Length): PtFm2 = PtFm2(xPicometresNum + xDelta.picometresNum, yPicometresNum + yDelta.picometresNum)
  override def slate(operand: VecPtLen2): PtPm2 = new PtPm2(xPicometresNum + operand.xPicometresNum, yPicometresNum - operand.yPicometresNum)
  override def slateX(operand: Length): PtPm2 = new PtPm2(xPicometresNum + operand.metresNum, y.metresNum)
  override def slateY(operand: Length): PtPm2 = new PtPm2(xPicometresNum, yPicometresNum + operand.metresNum)
  override def slateFrom(operand: PtLen2): PtPm2 = new PtPm2(xPicometresNum - operand.xPicometresNum, yPicometresNum - operand.yPicometresNum)  
  override def scale (operand: Double): PtPm2 = new PtPm2(xPicometresNum * operand, yPicometresNum * operand)
  override def + (operand: VecLen2): PtPm2 = new PtPm2(xPicometresNum + operand.xPicometresNum, yPicometresNum + operand.yPicometresNum)
  override def - (operand: VecLen2): PtPm2 = new PtPm2(xPicometresNum - operand.xPicometresNum, yPicometresNum - operand.yPicometresNum)
  
  override def / (operator: Double): PtPm2 = new PtPm2(xPicometresNum / operator, yPicometresNum / operator)
  override def mapScalars(operator: Length): Pt2 = Pt2(xPicometresNum / operator.picometresNum, yPicometresNum / operator.picometresNum)
  override def revY: PtPm2 = new PtPm2(xPicometresNum, -yPicometresNum)
  override def revYIf(cond: Boolean): PtPm2 = ife(cond, new PtPm2(xPicometresNum, -yPicometresNum), this)
  override def magnitude: Metres = Metres(math.sqrt(xPicometresNum.squared + yPicometresNum.squared))
  override def rotate180: PtPm2 = new PtPm2(-xPicometresNum, -yPicometresNum)
  override def rotate180If(cond: Boolean): PtPm2 = ife(cond, rotate180, this)
  override def rotate180IfNot(cond: Boolean): PtPm2 = ife(cond, this, rotate180)
  override def rotate(a: AngleVec): PtPm2 =  new PtPm2(x.metresNum * a.cos - y.metresNum * a.sin, x.metresNum * a.sin + y.metresNum * a.cos)

  def angleTo(angle: Angle, delta: Length): PtPm2 =
    PtPm2(xPicometresNum + delta.picometresNum * angle.cos, yPicometresNum + delta.picometresNum * angle.sin)

  override def rotateRadians(r: Double): PtPm2 =
  { val newX = xPicometresNum * cos(r) - yPicometresNum * sin(r)
    val newY =
    { val ya = xPicometresNum * sin(r)
      val yb = yPicometresNum * cos(r)
      ya + yb
    }
    new PtPm2(newX, newY)
  }

  override def lineSegTo(endPt: PtLen2): LineSegPm2 = LineSegPm2(xPicometresNum, yPicometresNum, endPt.xPicometresNum, endPt.yPicometresNum)

  override def lineSegFrom(startPt: PtLen2): LineSegPm2 =
    LineSegPm2(startPt.xPicometresNum, startPt.yPicometresNum, xPicometresNum, yPicometresNum)
}

/** Companion object for [[PtPm2]] class contains factory methods. */
object PtPm2
{ /** Factory method for creating a 2-dimensional point measured in picometres from the X and Y [[Length]]s. */
  def apply(x: Length, y: Length): PtPm2 = new PtPm2(x.picometresNum, y.picometresNum)

  /** Factory method for creating a 2-dimensional point measured in metres from the scalar [[Double]] values. */
  def apply(xPicometresNum: Double, yPicometresNum: Double): PtPm2 = new PtPm2(xPicometresNum, yPicometresNum)

  /** The origin of 2-dimensional coordinate space defined in picometres. */
  def origin: PtPm2 = new PtPm2(0, 0)

  implicit class Picometres2Implicit(thisPicometres2: PtPm2)
  { def / (operator: LengthMetric): Pt2 = thisPicometres2.mapScalars(operator)
  }

  /** [[Show]] type class instance / evidence for [[PTPm2]]. */
  implicit val persistEv: ShowTellDbl2[PtPm2] = ShowTellDbl2[PtPm2]("PtPm2")

  /** [[Unshow]] type class instance / evidence for [[PTPm2]]. */
  implicit val unShowEv: UnshowDbl2[PtPm2] = UnshowDbl2[PtPm2]("PtPm2", "x", "y", new PtPm2(_, _))

  implicit val builderImplicit: BuilderArrDbl2Map[PtPm2, PtPm2Arr] = new BuilderArrDbl2Map[PtPm2, PtPm2Arr]
  { type BuffT = BuffPtPm2
    override def fromDblArray(array: Array[Double]): PtPm2Arr = new PtPm2Arr(array)
    def buffFromBufferDbl(buffer: ArrayBuffer[Double]): BuffPtPm2 = new BuffPtPm2(buffer)
  }

  implicit val linePathBuildImplicit: LinePathDbl2Builder[PtPm2, LinePathPm2] = new LinePathDbl2Builder[PtPm2, LinePathPm2]
  { override type BuffT = BuffPtPm2
    override def fromDblArray(array: Array[Double]): LinePathPm2 = new LinePathPm2(array)
    override def buffFromBufferDbl(inp: ArrayBuffer[Double]): BuffPtPm2 = new BuffPtPm2(inp)
  }

  implicit val polygonBuildImplicit: PolygonDbl2BuilderMap[PtPm2, PolygonPm2] = new PolygonDbl2BuilderMap[PtPm2, PolygonPm2]
  { override type BuffT = BuffPtPm2
    override def fromDblArray(array: Array[Double]): PolygonPm2 = new PolygonPm2(array)
    override def buffFromBufferDbl(inp: ArrayBuffer[Double]): BuffPtPm2 = new BuffPtPm2(inp)
  }

  /** Implicit instance for the [[PolygonPm2Pair]] builder. This has to go in the [[PtPm2]] companion object so it can be found by an A => B function where
   * [[PtPm2]] is the type B parameter. */
  implicit def polygonPairBuildImplicit[A2](implicit ct: ClassTag[A2]): PolygonPm2PairBuilder[A2] = new PolygonPm2PairBuilder[A2]
}

/** Specialised immutable Array based collection class for [[PtPm2]]s. */
class PtPm2Arr(val arrayUnsafe: Array[Double]) extends AnyVal, ArrDbl2[PtPm2]
{ type ThisT = PtPm2Arr
  override def fromArray(array: Array[Double]): PtPm2Arr = new PtPm2Arr(array)
  override def typeStr: String = "PtPm2Arr"
  override def seqDefElem(d1: Double, d2: Double): PtPm2 = PtPm2(d1, d2)
  override def fElemStr: PtPm2 => String = _.str
}

/** Companion object for the [[PtPm2Arr]] class. Contains implicit Instance for Persist type class. */
object PtPm2Arr extends CompanionSeqLikeDbl2[PtPm2, PtPm2Arr]
{ override def fromArray(array: Array[Double]): PtPm2Arr = new PtPm2Arr(array)

  /** [[Show]] type class instance / evidence for [[PtPm2Arr]]. */
  implicit lazy val showEv: ShowSequ[PtPm2, PtPm2Arr] = ShowSequ[PtPm2, PtPm2Arr]()

  /** [[Unshow]] type class instance / evidence for [[PtPm2Arr]]. */
  implicit lazy val unshowEv: UnshowSeq[PtPm2, PtPm2Arr] = UnshowSeq[PtPm2, PtPm2Arr]()
}

/** A specialised flat ArrayBuffer[Double] based class for [[PtPm2]]s collections. */
final class BuffPtPm2(val unsafeBuffer: ArrayBuffer[Double]) extends AnyVal, BuffDbl2[PtPm2]
{ override def typeStr: String = "BuffPtMetre2"
  def newElem(d1: Double, d2: Double): PtPm2 = PtPm2(d1, d2)
}

object BuffPtPm2
{ def apply(initSize: Int = 4): BuffPtPm2 = new BuffPtPm2(new ArrayBuffer[Double](initSize * 2))
}

/** A 2-dimensional vector specified in metres as units rather than pure scalar numbers. */
final class VecPm2 private(val xPicometresNum: Double, val yPicometresNum: Double) extends VecPtPm2, VecLen2
{ override def typeStr: String = "VecPm2"
  override def + (operand: VecLen2): VecPm2 = new VecPm2(xPicometresNum + operand.xPicometresNum, yPicometresNum + operand.yPicometresNum)
  override def - (operand: VecLen2): VecPm2 = new VecPm2(xPicometresNum - operand.xPicometresNum, yPicometresNum - operand.yPicometresNum)
  override def * (operator: Double): VecPm2 = new VecPm2(xPicometresNum * operator, yPicometresNum * operator)
  override def / (operator: Double): VecPm2 = new VecPm2(xPicometresNum / operator, yPicometresNum / operator)
  override def magnitude: Picometres = Picometres(math.sqrt(xPicometresNum.squared + yPicometresNum.squared))
  @inline override def dot(operand: VecLen2): Picares = Picares(xPicometresNum * operand.xPicometresNum + yPicometresNum * operand.yPicometresNum)
  override def slate(operand: VecPtLen2): VecPm2 = VecPm2(xPicometresNum + operand.xPicometresNum, yPicometresNum + operand.yPicometresNum)
  override def scale(operand: Double): VecPm2 = VecPm2(xPicometresNum * operand, yPicometresNum * operand)
  override def slate(xDelta: Length, yDelta: Length): VecPm2 = VecPm2(xPicometresNum + xDelta.picometresNum, yPicometresNum + yDelta.picometresNum)
  override def slateX(operand: Length): VecPm2 = VecPm2(xPicometresNum + operand.picometresNum, yPicometresNum)
  override def slateY(operand: Length): VecPm2 = VecPm2(xPicometresNum, yPicometresNum + operand.picometresNum)
}

object VecPm2
{ /** Factory apply method for creating 2-dimensional vectors defined in [[Picometres]] from the 2 [[Picometres]] components. */
  def spply(x: Picometres, y: Picometres): VecPm2 = new VecPm2(x.picometresNum, y.picometresNum)

  /** Factory method for creating 2-dimensional vectors defined in [[Picometres]] from the 2 [[Length]] components. */
  def lengths(x: Length, y: Length): VecPm2 = new VecPm2(x.picometresNum, y.picometresNum)

  /** Factory method for creating 2-dimensional vectors defined in [[Picometres]] from the scalars of the components. */
  def picometresNum(xPicometresNum: Double, yPicometresNum: Double): VecPm2 = new VecPm2(xPicometresNum, yPicometresNum)

  val buildImplicit: BuilderArrMap[VecPm2, VecPm2Arr] = new BuilderArrDbl2Map[VecPm2, VecPm2Arr]
  { override type BuffT = VecPm2Buff
    override def fromDblArray(array: Array[Double]): VecPm2Arr = new VecPm2Arr(array)
    override def buffFromBufferDbl(buffer: ArrayBuffer[Double]): VecPm2Buff = new VecPm2Buff(buffer)
  }
}

/** Efficient Specialised [[Arr]] for [[VecPm2]]s. */
class VecPm2Arr(override val arrayUnsafe: Array[Double]) extends ArrDbl2[VecPm2]
{ override type ThisT = VecPm2Arr
  override def seqDefElem(d1: Double, d2: Double): VecPm2 = VecPm2.picometresNum(d1, d2)
  override def fromArray(array: Array[Double]): VecPm2Arr = new VecPm2Arr(array)
  override def typeStr: String = "VecPm2Arr"
  override def fElemStr: VecPm2 => String = _.str
}

/** A specialised flat ArrayBuffer[Double] based class for [[VecPm2]] collections. */
final class VecPm2Buff(val unsafeBuffer: ArrayBuffer[Double]) extends AnyVal, BuffDbl2[VecPm2]
{ override def typeStr: String = "VecPm2Buff"
  def newElem(d1: Double, d2: Double): VecPm2 = VecPm2.picometresNum(d1, d2)
}