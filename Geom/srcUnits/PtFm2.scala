/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer, math._, reflect.ClassTag

/** Common trait for [[VecFm2]] and [[PtFm2]] */
trait VecPtFm2 extends VecPtLen2, TellElemDbl2
{ /** The X component of this 2-dimensional [[Femtometres]] vector. */
  def x: Femtometres = Femtometres(xFemtometresNum)

  /** The Y component of this 2-dimensional [[Femtometres]] vector. */
  def y: Femtometres = Femtometres(yFemtometresNum)

  override def xPicometresNum: Double = xFemtometresNum * 1e-3
  override def yPicometresNum: Double = yFemtometresNum * 1e-3
  override def xMetresNum: Double = xFemtometresNum * 1e-15
  override def yMetresNum: Double = yFemtometresNum * 1e-15
  override def xKilometresNum: Double = xFemtometresNum * 1e-18
  override def yKilometresNum: Double = yFemtometresNum * 1e-18
  override def tell1: Double = xFemtometresNum
  override def tell2: Double = yFemtometresNum
  override def xPos: Boolean = xFemtometresNum >= 0
  override def xNeg: Boolean = xFemtometresNum <= 0
  override def yPos: Boolean = yFemtometresNum >= 0
  override def yNeg: Boolean = yFemtometresNum <= 0
}

/** A 2-dimensional point specified in [[Femtometres]] as units rather than pure scalar numbers. */
final class PtFm2 private(val xFemtometresNum: Double, val yFemtometresNum: Double) extends PtLen2, VecPtFm2
{ override type ThisT = PtFm2
  override type LineSegT = LineSegFm2
  override def typeStr: String = "PtFm2"
  override def slate(operand: VecPtLen2): PtFm2 = new PtFm2(xFemtometresNum + operand.xFemtometresNum, yFemtometresNum - operand.yFemtometresNum)
  override def slate(xOperand: Length, yOperand: Length): PtFm2 = PtFm2(xFemtometresNum + xOperand.femtometresNum, yFemtometresNum + yOperand.femtometresNum)
  override def slateX(xOperand: Length): PtFm2 = new PtFm2(xFemtometresNum + xOperand.metresNum, y.metresNum)
  override def slateY(yOperand: Length): PtFm2 = new PtFm2(xFemtometresNum, yFemtometresNum + yOperand.metresNum)
  override def slateFrom(operand: PtLen2): PtFm2 = new PtFm2(xFemtometresNum - operand.xFemtometresNum, yFemtometresNum - operand.yFemtometresNum)
  override def scale (operand: Double): PtFm2 = new PtFm2(xFemtometresNum * operand, yFemtometresNum * operand)
  override def mapGeom2(operator: Length): Pt2 = Pt2(xFemtometresNum / operator.femtometresNum, yFemtometresNum / operator.femtometresNum)
  override def - (operand: VecLen2): PtFm2 = new PtFm2(xFemtometresNum - operand.xFemtometresNum, yFemtometresNum - operand.yFemtometresNum)
  override def - (operand: PtLen2): VecFm2 = VecFm2(xFemtometresNum - operand.xFemtometresNum, yFemtometresNum - operand.yFemtometresNum)
  override def revY: PtFm2 = new PtFm2(xFemtometresNum, -yFemtometresNum)
  override def revYIf(cond: Boolean): PtFm2 = ife(cond, new PtFm2(xFemtometresNum, -yFemtometresNum), this)
  override def rotate180: PtFm2 = new PtFm2(-xFemtometresNum, -yFemtometresNum)
  override def rotate180If(cond: Boolean): PtFm2 = ife(cond, rotate180, this)
  override def rotate180IfNot(cond: Boolean): PtFm2 = ife(cond, this, rotate180)
  override def rotate(a: AngleVec): PtFm2 =  new PtFm2(x.metresNum * a.cos - y.metresNum * a.sin, x.metresNum * a.sin + y.metresNum * a.cos)

  override def rotateRadians(r: Double): PtFm2 =
  { val newX = xFemtometresNum * cos(r) - yFemtometresNum * sin(r)
    val newY =
    { val ya = xFemtometresNum * sin(r)
      val yb = yFemtometresNum * cos(r)
      ya + yb
    }
    new PtFm2(newX, newY)
  }

  override def lineSegTo(endPt: PtLen2): LineSegFm2 = LineSegFm2(xFemtometresNum, yFemtometresNum, endPt.xFemtometresNum, endPt.yFemtometresNum)
  override def lineSegFrom(startPt: PtLen2): LineSegFm2 = LineSegFm2(startPt.xFemtometresNum, startPt.yFemtometresNum, xFemtometresNum, yFemtometresNum)
}

/** Companion object for [[PtFm2]] class contains factory methods and various type class instances. The [[GeomLen2Elem]] type class instances are in the
 * [[PtLen2]] companion object. */
object PtFm2
{ /** Factory apply method for creating 2-dimensional points defined in [[Femtometres]] from the X and Y [[Length]] components. There is an apply name overload
   * that takes the [[Double]] values as parameters. */
  def apply(x: Length, y: Length): PtFm2 = new PtFm2(x.femtometresNum, y.femtometresNum)
  
  /** Factory apply method for creating 2-dimensional vectors defined in [[Femtometres]] from the 2 [[Double]] value. There is an apply name overload that takes
   * X and Y [[Length]]s as parameters. */
  def apply(xFemtometresNum: Double, yFemtometresNum: Double): PtFm2 = new PtFm2(xFemtometresNum, yFemtometresNum)

  /** Origin of 2-dimensional space specified in femtometres. */
  def origin: PtFm2 = new PtFm2(0, 0)

  /** [[Show]] type class instance / evidence for [[PTFm2]]. */
  implicit val persistEv: ShowTellDbl2[PtFm2] = ShowTellDbl2[PtFm2]("PtFm2")

  /** [[Unshow]] type class instance / evidence for [[PTFm2]]. */
  implicit val unShowEv: UnshowDbl2[PtFm2] = UnshowDbl2[PtFm2]("PtFm2", "x", "y", new PtFm2(_, _))

  /** Implicit [[BuilderMapArr]] type class instance / evidence for [[PtFm2]] and [[PtFm2Arr]]. */
  implicit val arrMapbuilderEv: BuilderMapArrDbl2[PtFm2, PtFm2Arr] = new BuilderMapArrDbl2[PtFm2, PtFm2Arr]
  { type BuffT = BuffPtFm2
    override def fromDblArray(array: Array[Double]): PtFm2Arr = new PtFm2Arr(array)
    def buffFromBufferDbl(buffer: ArrayBuffer[Double]): BuffPtFm2 = new BuffPtFm2(buffer)
  }

  /** Implicit [[LinePathBuilder]] type class instance / evidence for [[PtFm2]] and [[LinePathPtFm2]]. */
  implicit val linePathBuildEv: LinePathDbl2Builder[PtFm2, LinePathFm2] = new LinePathDbl2Builder[PtFm2, LinePathFm2]
  { override type BuffT = BuffPtFm2
    override def fromDblArray(array: Array[Double]): LinePathFm2 = new LinePathFm2(array)
    override def buffFromBufferDbl(inp: ArrayBuffer[Double]): BuffPtFm2 = new BuffPtFm2(inp)
  }

  /** Implicit [[PolygonLikeBuilderMap]] type class instance / evidence for [[PtFm2]] and [[PolygonPtFm2]]. */
  implicit val polygonBuildImplicit: PolygonDbl2BuilderMap[PtFm2, PolygonFm2] = new PolygonDbl2BuilderMap[PtFm2, PolygonFm2]
  { override type BuffT = BuffPtFm2
    override def fromDblArray(array: Array[Double]): PolygonFm2 = new PolygonFm2(array)
    override def buffFromBufferDbl(inp: ArrayBuffer[Double]): BuffPtFm2 = new BuffPtFm2(inp)
  }

  /** Implicit instance for the [[PolygonFm2Pair]] builder. This has to go in the [[PtFm2]] companion object so it can be found by an A => B function where
   * [[PtFm2]] is the type B parameter. */
  implicit def polygonPairBuildImplicit[A2](implicit ct: ClassTag[A2]): PolygonFm2PairBuilder[A2] = new PolygonFm2PairBuilder[A2]
}

trait PtFm2SeqLike extends Any, SlImutDbl2[PtFm2]
{ final override def elemFromDbls(d1: Double, d2: Double): PtFm2 = PtFm2(d1, d2)
  final override def fElemStr: PtFm2 => String = _.str
}

/** Specialised immutable Array based collection class for [[PtFm2]]s. */
class PtFm2Arr(val arrayUnsafe: Array[Double]) extends AnyVal, PtFm2SeqLike, ArrDbl2[PtFm2]
{ type ThisT = PtFm2Arr
  override def fromArray(array: Array[Double]): PtFm2Arr = new PtFm2Arr(array)
  override def typeStr: String = "PtFm2Arr"
  
}

/** Companion object for the [[PtFm2Arr]] class. Contains implicit Instance for Persist type class. */
object PtFm2Arr extends CompanionSeqLikeDbl2[PtFm2, PtFm2Arr]
{ override def fromArray(array: Array[Double]): PtFm2Arr = new PtFm2Arr(array)

  /** [[Show]] type class instance / evidence for [[PtFm2Arr]]. */
  implicit lazy val showEv: ShowSequ[PtFm2, PtFm2Arr] = ShowSequ[PtFm2, PtFm2Arr]()

  /** [[Unshow]] type class instance / evidence for [[PtFm2Arr]]. */
  implicit lazy val unshowEv: UnshowSeq[PtFm2, PtFm2Arr] = UnshowSeq[PtFm2, PtFm2Arr]()
}

/** A specialised flat ArrayBuffer[Double] based class for [[PtFm2]]s collections. */
final class BuffPtFm2(val bufferUnsafe: ArrayBuffer[Double]) extends AnyVal with BuffDbl2[PtFm2]
{ override def typeStr: String = "BuffPtMetre2"
  def elemFromDbls(d1: Double, d2: Double): PtFm2 = PtFm2(d1, d2)
}

object BuffPtFm2
{ /** Factory apply method for empty efficent buffer class for [[PtFm2]]s. */
  def apply(initSize: Int = 4): BuffPtFm2 = new BuffPtFm2(new ArrayBuffer[Double](initSize * 2))
}

/** A 2-dimensional vector specified in metres as units rather than pure scalar numbers. */
final class VecFm2 private(val xFemtometresNum: Double, val yFemtometresNum: Double) extends VecPtFm2 with VecLen2
{ override def typeStr: String = "VecFm2"
  override def + (operand: VecLen2): VecFm2 = new VecFm2(xFemtometresNum + operand.xFemtometresNum, yFemtometresNum + operand.yFemtometresNum)
  override def - (operand: VecLen2): VecFm2 = new VecFm2(xFemtometresNum - operand.xFemtometresNum, yFemtometresNum - operand.yFemtometresNum)
  override def unary_- : VecFm2 = VecFm2(-xFemtometresNum, -yFemtometresNum)
  override def * (operator: Double): VecFm2 = new VecFm2(xFemtometresNum * operator, yFemtometresNum * operator)
  override def / (operator: Double): VecFm2 = new VecFm2(xFemtometresNum / operator, yFemtometresNum / operator)
  override def magnitude: Femtometres = Femtometres(math.sqrt(xFemtometresNum.squared + yFemtometresNum.squared))
  override def slate(operand: VecPtLen2): VecFm2 = VecFm2(xFemtometresNum + operand.xFemtometresNum, yFemtometresNum + operand.yFemtometresNum)
  override def slate(xOperand: Length, yOperand: Length): VecFm2 = VecFm2(xFemtometresNum + xOperand.femtometresNum, yFemtometresNum + yOperand.femtometresNum)
  override def slateX(xOperand: Length): VecFm2 = VecFm2(xFemtometresNum + xOperand.femtometresNum, yFemtometresNum)
  override def slateY(yOperand: Length): VecFm2 = VecFm2(xFemtometresNum, yFemtometresNum + yOperand.femtometresNum)
  override def scale(operand: Double): VecFm2 = VecFm2(xFemtometresNum * operand, yFemtometresNum * operand)
  override def mapGeom2(operator: Length): Vec2 = Vec2(xFemtometresNum / operator.femtometresNum, yFemtometresNum / operator.femtometresNum)

  /** This returns [[Picares]] as there isn't an [[AreaMetric]] class for [[Femtometres]]. */
  @inline override def dot(operand: VecLen2): Picares = Picares(xPicometresNum * operand.xPicometresNum + yPicometresNum * operand.yPicometresNum)
}

object VecFm2
{ /** Factory method for creating 2-dimensional vectors defined in [[Femtometres]] from the 2 [[Length]] components. There is an apply name overload that takes
   * the [[Double]] values as parameters. */
  def apply(x: Length, y: Length): VecFm2 = new VecFm2(x.femtometresNum, y.femtometresNum)

  /** Factory apply method for creating 2-dimensional vectors defined in [[Femtometres]] from the 2 [[Double]] value. There is an apply name overload that takes
   * X and Y [[Length]]s as parameters. */
  def apply(xFemtometresNum: Double, yFemtometresNum: Double): VecFm2 = new VecFm2(xFemtometresNum, yFemtometresNum)

  /** Implicit [[BuilderMapArr]] type class instance / evidence for [[VecFm2]] and [[VecFm2Arr]]. */
  implicit val arrMapBuilderEv: BuilderMapArr[VecFm2, VecFm2Arr] = new BuilderMapArrDbl2[VecFm2, VecFm2Arr]
  { override type BuffT = VecFm2Buff
    override def fromDblArray(array: Array[Double]): VecFm2Arr = new VecFm2Arr(array)
    override def buffFromBufferDbl(buffer: ArrayBuffer[Double]): VecFm2Buff = new VecFm2Buff(buffer)
  }
}

/** Efficient Specialised [[Arr]] for [[VecFm2]]s. */
class VecFm2Arr(override val arrayUnsafe: Array[Double]) extends ArrDbl2[VecFm2]
{ override type ThisT = VecFm2Arr
  override def elemFromDbls(d1: Double, d2: Double): VecFm2 = VecFm2(d1, d2)
  override def fromArray(array: Array[Double]): VecFm2Arr = new VecFm2Arr(array)
  override def typeStr: String = "VecFm2Arr"
  override def fElemStr: VecFm2 => String = _.str
}

/** A specialised flat ArrayBuffer[Double] based class for [[VecFm2]] collections. */
final class VecFm2Buff(val bufferUnsafe: ArrayBuffer[Double]) extends AnyVal with BuffDbl2[VecFm2]
{ override def typeStr: String = "VecFm2Buff"
  def elemFromDbls(d1: Double, d2: Double): VecFm2 = VecFm2(d1, d2)
}