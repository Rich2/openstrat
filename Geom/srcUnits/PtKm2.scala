/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer, math._, reflect.ClassTag

/** Common trait for [[VecKm2]] and [[PtKm2]] */
trait VecPtKm2 extends VecPtLen2, TellElemDbl2
{ /** The X component of this 2-dimensional [[Metres]] vector. */
  def x: Kilometres = Kilometres(xKilometresNum)

  /** The Y component of this 2-dimensional [[Metres]] vector. */
  def y: Kilometres = Kilometres(yKilometresNum)

  override def xFemtometresNum: Double = xKilometresNum * 1e18
  override def yFemtometresNum: Double = yKilometresNum * 1e18
  override def xPicometresNum: Double = xKilometresNum * 1e15
  override def yPicometresNum: Double = yKilometresNum * 1e15
  override def xMetresNum: Double = xKilometresNum * 1e3
  override def yMetresNum: Double = yKilometresNum * 1e3
  override def tell1: Double = xKilometresNum
  override def tell2: Double = yKilometresNum
  override def xPos: Boolean = xKilometresNum >= 0
  override def xNeg: Boolean = xKilometresNum <= 0
  override def yPos: Boolean = yKilometresNum >= 0
  override def yNeg: Boolean = yKilometresNum <= 0
}

/** A 2-dimensional point specified in [[Kilometres]] as units rather than pure scalar numbers. */
final class PtKm2 private(val xKilometresNum: Double, val yKilometresNum: Double) extends PtLen2, VecPtKm2
{ override type ThisT = PtKm2
  override type LineSegT = LineSegKm2
  override def typeStr: String = "PtKm2"
  override def slate(xOperand: Length, yOperand: Length): PtKm2 = PtKm2(xKilometresNum + xOperand.kilometresNum, yKilometresNum + yOperand.kilometresNum)
  override def slate(operand: VecPtLen2): PtKm2 = new PtKm2(xKilometresNum + operand.xKilometresNum, yKilometresNum + operand.yKilometresNum)
  override def slateFrom(operand: PtLen2): PtKm2 = new PtKm2(xKilometresNum - operand.xKilometresNum, yKilometresNum - operand.yKilometresNum)
  override def slateX(xOperand: Length): PtKm2 = new PtKm2(xKilometresNum + xOperand.metresNum, y.metresNum)
  override def slateY(yOperand: Length): PtKm2 = new PtKm2(xKilometresNum, yKilometresNum + yOperand.metresNum)
  override def scale (operand: Double): PtKm2 = new PtKm2(xKilometresNum * operand, yKilometresNum * operand)
  override def mapGeom2(operator: Length): Pt2 = Pt2(xKilometresNum / operator.metresNum, yKilometresNum / operator.metresNum)
  
  override def - (operand: VecLen2): PtKm2 = new PtKm2(xKilometresNum - operand.xKilometresNum, yKilometresNum - operand.yKilometresNum)
  override def - (operand: PtLen2): VecKm2 = VecKm2(xKilometresNum - operand.xKilometresNum, yKilometresNum - operand.yKilometresNum)
  override def revY: PtKm2 = new PtKm2(xKilometresNum, -yKilometresNum)
  override def revYIf(cond: Boolean): PtKm2 = ife(cond, new PtKm2(xKilometresNum, -yKilometresNum), this)
  override def rotate180: PtKm2 = new PtKm2(-xKilometresNum, -yKilometresNum)
  override def rotate180If(cond: Boolean): PtKm2 = ife(cond, rotate180, this)
  override def rotate180IfNot(cond: Boolean): PtKm2 = ife(cond, this, rotate180)
  override def rotate(a: AngleVec): PtKm2 =  new PtKm2(x.metresNum * a.cos - y.metresNum * a.sin, x.metresNum * a.sin + y.metresNum * a.cos)

  override def rotateRadians(r: Double): PtKm2 =
  { val newX = xKilometresNum * cos(r) - yKilometresNum * sin(r)
    val newY =
    { val ya = xKilometresNum * sin(r)
      val yb = yKilometresNum * cos(r)
      ya + yb
    }
    new PtKm2(newX, newY)
  }

  override def lineSegTo(endPt: PtLen2): LineSegKm2 = LineSegKm2(xKilometresNum, yKilometresNum, endPt.xKilometresNum, endPt.yKilometresNum)
  override def lineSegFrom(startPt: PtLen2): LineSegKm2 = LineSegKm2(startPt.xKilometresNum, startPt.yKilometresNum, xKilometresNum, yKilometresNum)
}

/** Companion object for [[PtKm2]] class contains factory apply methods, extension methods and type class instances. */
object PtKm2
{ /** Factory method for creating a 2-dimensional point measured in kilometres from the scalar [[Double]] values. */
  def apply(xKilometresNum: Double, yKilometresNum: Double): PtKm2 = new PtKm2(xKilometresNum, yKilometresNum)

  /** Factory method for creating a 2-dimensional point measured in metres from the X and Y [[Length]]s. */
  def apply(x: Length, y: Length): PtKm2 = new PtKm2(x.kilometresNum, y.kilometresNum)

  /** Origin of a 2-dimensional space measured in [[Kilometres]]. */
  def origin: PtKm2 = new PtKm2(0, 0)

  /** [[Show]] type class instance / evidence for [[PTKm2]]. */
  implicit val persistEv: ShowTellDbl2[PtKm2] = ShowTellDbl2[PtKm2]("PtKm2")

  /** [[Unshow]] type class instance / evidence for [[PTKm2]]. */
  implicit val unShowEv: UnshowDbl2[PtKm2] = UnshowDbl2[PtKm2]("PtKm2", "x", "y", new PtKm2(_, _))

  /** Implicit [[BuilderArr]] type class instance / evidence for [[PtKm2]] and [[LinePathKm2]]. */
  implicit val builderArrEv: BuilderMapArrDbl2[PtKm2, PtKm2Arr] = new BuilderMapArrDbl2[PtKm2, PtKm2Arr]
  { type BuffT = BuffPtKm2
    override def fromDblArray(array: Array[Double]): PtKm2Arr = new PtKm2Arr(array)
    def buffFromBufferDbl(buffer: ArrayBuffer[Double]): BuffPtKm2 = new BuffPtKm2(buffer)
  }

  /** Implicit [[LinePathBuilder]] type class instance / evidence for [[PtKm2]] and [[LinePathKm2]]. */
  implicit val linePathBuildEv: LinePathDbl2Builder[PtKm2, LinePathKm2] = new LinePathDbl2Builder[PtKm2, LinePathKm2]
  { override type BuffT = BuffPtKm2
    override def fromDblArray(array: Array[Double]): LinePathKm2 = new LinePathKm2(array)
    override def buffFromBufferDbl(inp: ArrayBuffer[Double]): BuffPtKm2 = new BuffPtKm2(inp)
  }

  /** Implicit [[PolygonBuilder]] type class instance / evidence for [[PtKm2]] and [[PolygonKm2]]. */
  implicit val polygonBuildEv: PolygonDbl2BuilderMap[PtKm2, PolygonKm2] = new PolygonDbl2BuilderMap[PtKm2, PolygonKm2]
  { override type BuffT = BuffPtKm2
    override def fromDblArray(array: Array[Double]): PolygonKm2 = new PolygonKm2(array)
    override def buffFromBufferDbl(inp: ArrayBuffer[Double]): BuffPtKm2 = new BuffPtKm2(inp)
  }

  /** Implicit instance for the [[PolygonKm2Pair]] builder. This has to go in the [[PtKm2]] companion object so it can be found by an A => B function where
   * [[PtKm2]] is the type B parameter. */
  implicit def polygonPairBuilderEv[A2](implicit ct: ClassTag[A2]): PolygonKm2PairBuilder[A2] = new PolygonKm2PairBuilder[A2]
}

/** Specialised immutable Array based collection class for [[PtKm2]]s. */
class PtKm2Arr(val arrayUnsafe: Array[Double]) extends AnyVal with ArrDbl2[PtKm2]
{ type ThisT = PtKm2Arr
  override def fromArray(array: Array[Double]): PtKm2Arr = new PtKm2Arr(array)
  override def typeStr: String = "PtKm2Arr"
  override def elemFromDbls(d1: Double, d2: Double): PtKm2 = PtKm2.apply(d1, d2)
  override def fElemStr: PtKm2 => String = _.str
}

/** Companion object for the [[PtKm2Arr]] class. Contains implicit Instance for Persist type class. */
object PtKm2Arr extends CompanionSeqLikeDbl2[PtKm2, PtKm2Arr]
{
  override def fromArray(array: Array[Double]): PtKm2Arr = new PtKm2Arr(array)

  /** [[Show]] type class instance / evidence for [[PtKm2Arr]]. */
  implicit lazy val showEv: ShowSequ[PtKm2, PtKm2Arr] = ShowSequ[PtKm2, PtKm2Arr]()

  /** [[Unshow]] type class instance / evidence for [[PtKm2Arr]]. */
  implicit lazy val unshowEv: UnshowSeq[PtKm2, PtKm2Arr] = UnshowSeq[PtKm2, PtKm2Arr]()
}

/** A specialised flat ArrayBuffer[Double] based class for [[PtKm2]]s collections. */
final class BuffPtKm2(val bufferUnsafe: ArrayBuffer[Double]) extends AnyVal with BuffDbl2[PtKm2]
{ override def typeStr: String = "BuffPtKm2"
  def elemFromDbls(d1: Double, d2: Double): PtKm2 = PtKm2.apply(d1, d2)
}

object BuffPtKm2
{ def apply(initSize: Int = 4): BuffPtKm2 = new BuffPtKm2(new ArrayBuffer[Double](initSize * 2))
}

/** A 2-dimensional vector specified in metres as units rather than pure scalar numbers. */
final class VecKm2 private(val xKilometresNum: Double, val yKilometresNum: Double) extends VecPtKm2, VecLen2
{ override def typeStr: String = "VecKm2"

  override def + (operand: VecLen2): VecKm2 = new VecKm2(xKilometresNum + operand.xKilometresNum, yKilometresNum + operand.yKilometresNum)
  override def - (operand: VecLen2): VecKm2 = new VecKm2(xKilometresNum - operand.xKilometresNum, yKilometresNum - operand.yKilometresNum)
  override def unary_- : VecKm2 = VecKm2(-xKilometresNum, -yKilometresNum)
  override def * (operator: Double): VecKm2 = new VecKm2(xKilometresNum * operator, yKilometresNum * operator)
  override def / (operator: Double): VecKm2 = new VecKm2(xKilometresNum / operator, yKilometresNum / operator)
  override def magnitude: Kilometres = Kilometres(math.sqrt(xKilometresNum.squared + yKilometresNum.squared))
  @inline override def dot(operand: VecLen2): Kilares = Kilares(xKilometresNum * operand.xKilometresNum + yKilometresNum * operand.yKilometresNum)
  override def slate(operand: VecPtLen2): VecKm2 = VecKm2(xKilometresNum + operand.xKilometresNum, yKilometresNum + operand.yKilometresNum)
  override def slate(xOperand: Length, yOperand: Length): VecKm2 = VecKm2(xKilometresNum + xOperand.kilometresNum, yKilometresNum + yOperand.kilometresNum)
  override def slateX(xOperand: Length): VecKm2 = VecKm2(xKilometresNum + xOperand.kilometresNum, yKilometresNum)
  override def slateY(yOperand: Length): VecKm2 = VecKm2(xKilometresNum, yKilometresNum + yOperand.kilometresNum)
  override def scale(operand: Double): VecKm2 = VecKm2(xKilometresNum * operand, yKilometresNum * operand)
  override def mapGeom2(operator: Length): Vec2 = Vec2(xKilometresNum / operator.metresNum, yKilometresNum / operator.metresNum)
}

object VecKm2
{ /** Factory method for creating 2-dimensional vectors defined in [[Metres]] from the 2 [[Length]] components. There is an apply name overload that takes
 * the [[Double]] values as parameters. */
  def apply(x: Length, y: Length): VecKm2 = new VecKm2(x.kilometresNum, y.kilometresNum)

  /** Factory apply method for creating 2-dimensional vectors defined in [[Kilometres]] from the 2 [[Double]] values. There is an apply name overload that takes
   * the X and Y [[Length]] components as parameters. */
  def apply(xKilometresNum: Double, yKilometresNum: Double): VecKm2 = new VecKm2(xKilometresNum, yKilometresNum)

  val buildImplicit: BuilderMapArr[VecKm2, VecKm2Arr] = new BuilderMapArrDbl2[VecKm2, VecKm2Arr]
  { override type BuffT = VecKm2Buff
    override def fromDblArray(array: Array[Double]): VecKm2Arr = new VecKm2Arr(array)
    override def buffFromBufferDbl(buffer: ArrayBuffer[Double]): VecKm2Buff = new VecKm2Buff(buffer)
  }
}

/** Efficient Specialised [[Arr]] for [[VecKm2]]s. */
class VecKm2Arr(override val arrayUnsafe: Array[Double]) extends ArrDbl2[VecKm2]
{ override type ThisT = VecKm2Arr
  override def elemFromDbls(d1: Double, d2: Double): VecKm2 = VecKm2(d1, d2)
  override def fromArray(array: Array[Double]): VecKm2Arr = new VecKm2Arr(array)
  override def typeStr: String = "VecKm2Arr"
  override def fElemStr: VecKm2 => String = _.str
}

/** A specialised flat ArrayBuffer[Double] based class for [[VecKm2]] collections. */
final class VecKm2Buff(val bufferUnsafe: ArrayBuffer[Double]) extends AnyVal with BuffDbl2[VecKm2]
{ override def typeStr: String = "VecKm2Buff"
  def elemFromDbls(d1: Double, d2: Double): VecKm2 = VecKm2(d1, d2)
}