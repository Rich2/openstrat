/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer, math._, reflect.ClassTag

/** Common trait for [[VecFm2]] and [[PtFm2]] */
trait VecPtFm2 extends VecPtLength2, TellElemDbl2
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
final class PtFm2 private(val xFemtometresNum: Double, val yFemtometresNum: Double) extends PtLength2, VecPtFm2
{ override type ThisT = PtFm2
  override type LineSegT = LineSegFm2
  override def typeStr: String = "PtFm2"

  override def slate(operand: PtLength2): PtFm2 = new PtFm2(xFemtometresNum + operand.xFemtometresNum, yFemtometresNum - operand.yFemtometresNum)
  override def slateFrom(operand: PtLength2): PtFm2 = new PtFm2(xFemtometresNum - operand.xFemtometresNum, yFemtometresNum - operand.yFemtometresNum)
  override def + (operand: VecLength2): PtFm2 = new PtFm2(xFemtometresNum + operand.xFemtometresNum, yFemtometresNum + operand.yFemtometresNum)
  override def - (operand: VecLength2): PtFm2 = new PtFm2(xFemtometresNum - operand.xFemtometresNum, yFemtometresNum - operand.yFemtometresNum)
  override def addXY (otherX: Length, otherY: Length): PtFm2 = new PtFm2(xFemtometresNum + otherX.metresNum, yFemtometresNum + otherY.metresNum)
  override def subXY (otherX: Length, otherY: Length): PtFm2 = new PtFm2(xFemtometresNum - otherX.metresNum, yFemtometresNum - otherY.metresNum)
  override def addX(operand: Length): PtFm2 = new PtFm2(xFemtometresNum + operand.metresNum, y.metresNum)
  override def addY(operand: Length): PtFm2 = new PtFm2(xFemtometresNum, yFemtometresNum + operand.metresNum)
  override def subX(operand: Length): PtFm2 = new PtFm2(xFemtometresNum - operand.metresNum, yFemtometresNum)
  override def subY(operand: Length): PtFm2 = new PtFm2(xFemtometresNum, yFemtometresNum - operand.metresNum)
  override def * (operand: Double): PtFm2 = new PtFm2(xFemtometresNum * operand, yFemtometresNum * operand)
  override def / (operator: Double): PtFm2 = new PtFm2(xFemtometresNum / operator, yFemtometresNum / operator)
  override def divByLength(operator: Length): Pt2 = Pt2(xFemtometresNum / operator.metresNum, yFemtometresNum / operator.metresNum)
  override def revY: PtFm2 = new PtFm2(xFemtometresNum, -yFemtometresNum)
  override def revYIf(cond: Boolean): PtFm2 = ife(cond, new PtFm2(xFemtometresNum, -yFemtometresNum), this)
  override def magnitude: Metres = Metres(math.sqrt(xFemtometresNum.squared + yFemtometresNum.squared))
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

  override def lineSegTo(endPt: PtLength2): LineSegFm2 = LineSegFm2.kilometresNum(xFemtometresNum, yFemtometresNum, endPt.xFemtometresNum, endPt.yFemtometresNum)

  override def lineSegFrom(startPt: PtLength2): LineSegFm2 =
    LineSegFm2.kilometresNum(startPt.xFemtometresNum, startPt.yFemtometresNum, xFemtometresNum, yFemtometresNum)
}

/** Companion object for [[PtFm2]] class contains factory methods. */
object PtFm2
{ /** Factory method for creating a 2-dimensional point measured in metres from the scalar [[Double]] values. */
  def picometresNum(xFemtometres: Double, yFemtometres: Double): PtFm2 = new PtFm2(xFemtometres, yFemtometres)

  def apply(x: Femtometres, y: Femtometres): PtFm2 = new PtFm2(x.kilometresNum, y.kilometresNum)

  def origin: PtFm2 = new PtFm2(0, 0)

  implicit class Femtometres2Implicit(thisMetres2: PtFm2)
  { def / (operator: LengthMetric): Pt2 = Pt2(thisMetres2.x.metresNum/ operator.metresNum, thisMetres2.y.metresNum / operator.metresNum)
  }

  /** [[Show]] type class instance / evidence for [[PTFm2]]. */
  implicit val persistEv: ShowTellDbl2[PtFm2] = ShowTellDbl2[PtFm2]("PtFm2")

  /** [[Unshow]] type class instance / evidence for [[PTFm2]]. */
  implicit val unShowEv: UnshowDbl2[PtFm2] = UnshowDbl2[PtFm2]("PtFm2", "x", "y", new PtFm2(_, _))

  implicit val builderImplicit: BuilderArrDbl2Map[PtFm2, PtFm2Arr] = new BuilderArrDbl2Map[PtFm2, PtFm2Arr]
  { type BuffT = BuffPtFm2
    override def fromDblArray(array: Array[Double]): PtFm2Arr = new PtFm2Arr(array)
    def buffFromBufferDbl(buffer: ArrayBuffer[Double]): BuffPtFm2 = new BuffPtFm2(buffer)
  }

  implicit val linePathBuildImplicit: LinePathDbl2Builder[PtFm2, LinePathFm2] = new LinePathDbl2Builder[PtFm2, LinePathFm2]
  { override type BuffT = BuffPtFm2
    override def fromDblArray(array: Array[Double]): LinePathFm2 = new LinePathFm2(array)
    override def buffFromBufferDbl(inp: ArrayBuffer[Double]): BuffPtFm2 = new BuffPtFm2(inp)
  }

  implicit val polygonBuildImplicit: PolygonDbl2BuilderMap[PtFm2, PolygonFm2] = new PolygonDbl2BuilderMap[PtFm2, PolygonFm2]
  { override type BuffT = BuffPtFm2
    override def fromDblArray(array: Array[Double]): PolygonFm2 = new PolygonFm2(array)
    override def buffFromBufferDbl(inp: ArrayBuffer[Double]): BuffPtFm2 = new BuffPtFm2(inp)
  }

  /** Implicit instance for the [[PolygonFm2Pair]] builder. This has to go in the [[PtFm2]] companion object so it can be found by an A => B function where
   * [[PtFm2]] is the type B parameter. */
  implicit def polygonPairBuildImplicit[A2](implicit ct: ClassTag[A2]): PolygonFm2PairBuilder[A2] = new PolygonFm2PairBuilder[A2]
}

/** Specialised immutable Array based collection class for [[PtFm2]]s. */
class PtFm2Arr(val arrayUnsafe: Array[Double]) extends AnyVal with ArrDbl2[PtFm2]
{ type ThisT = PtFm2Arr
  override def fromArray(array: Array[Double]): PtFm2Arr = new PtFm2Arr(array)
  override def typeStr: String = "PtFm2Arr"
  override def seqDefElem(d1: Double, d2: Double): PtFm2 = PtFm2.picometresNum(d1, d2)
  override def fElemStr: PtFm2 => String = _.str
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
final class BuffPtFm2(val unsafeBuffer: ArrayBuffer[Double]) extends AnyVal with BuffDbl2[PtFm2]
{ override def typeStr: String = "BuffPtMetre2"
  def newElem(d1: Double, d2: Double): PtFm2 = PtFm2.picometresNum(d1, d2)
}

object BuffPtFm2
{ def apply(initSize: Int = 4): BuffPtFm2 = new BuffPtFm2(new ArrayBuffer[Double](initSize * 2))
}

/** A 2-dimensional vector specified in metres as units rather than pure scalar numbers. */
final class VecFm2 private(val xFemtometresNum: Double, val yFemtometresNum: Double) extends VecPtFm2 with VecLength2
{ override def typeStr: String = "VecFm2"
  override def + (operand: VecLength2): VecFm2 = new VecFm2(xFemtometresNum + operand.xFemtometresNum, yFemtometresNum + operand.yFemtometresNum)
  override def - (operand: VecLength2): VecFm2 = new VecFm2(xFemtometresNum - operand.xFemtometresNum, yFemtometresNum - operand.yFemtometresNum)
  override def * (operator: Double): VecFm2 = new VecFm2(xFemtometresNum * operator, yFemtometresNum * operator)
  override def / (operator: Double): VecFm2 = new VecFm2(xFemtometresNum / operator, yFemtometresNum / operator)
  override def magnitude: Femtometres = Femtometres(math.sqrt(xFemtometresNum.squared + yFemtometresNum.squared))

  /** This returns [[Picares]] as there isn't an [[AreaMetric]] class for [[Femtometres]]. */
  @inline override def dot(operand: VecLength2): Picares = Picares(xPicometresNum * operand.xPicometresNum + yPicometresNum * operand.yPicometresNum)
}

object VecFm2
{ /** Factory apply method for creating 2-dimensional vectors defined in [[Femtometres]] from the 2 [[Femtometres]] components. */
  def spply(x: Femtometres, y: Femtometres): VecFm2 = new VecFm2(x.kilometresNum, y.kilometresNum)

  /** Factory method for creating 2-dimensional vectors defined in [[Metres]] from the 2 [[Length]] components. */
  def lengths(x: Length, y: Length): VecFm2 = new VecFm2(x.kilometresNum, y.kilometresNum)

  /** Factory method for creating 2-dimensional vectors defined in [[Metres]] from the scalars of the components. */
  def picometresNum(xFemtometresNum: Double, yFemtometresNum: Double): VecFm2 = new VecFm2(xFemtometresNum, yFemtometresNum)

  val buildImplicit: BuilderArrMap[VecFm2, VecFm2Arr] = new BuilderArrDbl2Map[VecFm2, VecFm2Arr]
  { override type BuffT = VecFm2Buff
    override def fromDblArray(array: Array[Double]): VecFm2Arr = new VecFm2Arr(array)
    override def buffFromBufferDbl(buffer: ArrayBuffer[Double]): VecFm2Buff = new VecFm2Buff(buffer)
  }
}

/** Efficient Specialised [[Arr]] for [[VecFm2]]s. */
class VecFm2Arr(override val arrayUnsafe: Array[Double]) extends ArrDbl2[VecFm2]
{ override type ThisT = VecFm2Arr
  override def seqDefElem(d1: Double, d2: Double): VecFm2 = VecFm2.picometresNum(d1, d2)
  override def fromArray(array: Array[Double]): VecFm2Arr = new VecFm2Arr(array)
  override def typeStr: String = "VecFm2Arr"
  override def fElemStr: VecFm2 => String = _.str
}

/** A specialised flat ArrayBuffer[Double] based class for [[VecFm2]] collections. */
final class VecFm2Buff(val unsafeBuffer: ArrayBuffer[Double]) extends AnyVal with BuffDbl2[VecFm2]
{ override def typeStr: String = "VecFm2Buff"
  def newElem(d1: Double, d2: Double): VecFm2 = VecFm2.picometresNum(d1, d2)
}