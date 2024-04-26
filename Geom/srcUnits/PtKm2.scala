/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer, math._, reflect.ClassTag

/** A 2 dimensional point specified in [[Kilometres]] as units rather than pure scalar numbers. */
final class PtKm2 private(val xKilometresNum: Double, val yKilometresNum: Double) extends PtLength2 with VecPtKm2 with TellElemDbl2
{ override type ThisT = PtKm2
  override type LineSegT = LineSegKm2
  override def typeStr: String = "PtKm2"

  override def slate(operand: PtLength2): PtKm2 = new PtKm2(xKilometresNum + operand.xKilometresNum, yKilometresNum - operand.yKilometresNum)
  override def slateFrom(operand: PtLength2): PtKm2 = new PtKm2(xKilometresNum - operand.xKilometresNum, yKilometresNum - operand.yKilometresNum)
  override def + (operand: VecLength2): PtKm2 = new PtKm2(xKilometresNum + operand.xKilometresNum, yKilometresNum + operand.yKilometresNum)
  override def - (operand: VecLength2): PtKm2 = new PtKm2(xKilometresNum - operand.xKilometresNum, yKilometresNum - operand.yKilometresNum)
  override def addXY (otherX: Length, otherY: Length): PtKm2 = new PtKm2(xKilometresNum + otherX.metresNum, yKilometresNum + otherY.metresNum)
  override def subXY (otherX: Length, otherY: Length): PtKm2 = new PtKm2(xKilometresNum - otherX.metresNum, yKilometresNum - otherY.metresNum)
  override def addX(operand: Length): PtKm2 = new PtKm2(xKilometresNum + operand.metresNum, y.metresNum)
  override def addY(operand: Length): PtKm2 = new PtKm2(xKilometresNum, yKilometresNum + operand.metresNum)
  override def subX(operand: Length): PtKm2 = new PtKm2(xKilometresNum - operand.metresNum, yKilometresNum)
  override def subY(operand: Length): PtKm2 = new PtKm2(xKilometresNum, yKilometresNum - operand.metresNum)
  override def * (operand: Double): PtKm2 = new PtKm2(xKilometresNum * operand, yKilometresNum * operand)
  override def / (operator: Double): PtKm2 = new PtKm2(xKilometresNum / operator, yKilometresNum / operator)
  override def revY: PtKm2 = new PtKm2(xKilometresNum, -yKilometresNum)
  override def revYIf(cond: Boolean): PtKm2 = ife(cond, new PtKm2(xKilometresNum, -yKilometresNum), this)
  override def magnitude: Metres = Metres(math.sqrt(xKilometresNum.squared + yKilometresNum.squared))
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

  override def lineSegTo(endPt: PtKm2): LineSegKm2 = LineSegKm2(this, endPt)
  override def lineSegFrom(startPt: PtKm2): LineSegKm2 = LineSegKm2(startPt, this)
}

/** Companion object for [[PtKm2]] class contains factory methods. */
object PtKm2
{ /** Factory method for creating a 2 dimensional point measured in metres from the scalar [[Double]] values. */
  def kilometresNum(xKilometres: Double, yKilometres: Double): PtKm2 = new PtKm2(xKilometres, yKilometres)

  def apply(x: Kilometres, y: Kilometres): PtKm2 = new PtKm2(x.kilometresNum, y.kilometresNum)

  def origin: PtKm2 = new PtKm2(0, 0)

  implicit class Metres2Implicit(thisMetres2: PtKm2)
  { def / (operator: MetricLength): Pt2 = Pt2(thisMetres2.x.metresNum/ operator.metresNum, thisMetres2.y.metresNum / operator.metresNum)
  }

  /** [[Show]] type class instance / evidence for [[PTKm2]]. */
  implicit val persistEv: ShowTellDbl2[PtKm2] = ShowTellDbl2[PtKm2]("PtKm2")

  /** [[Unshow]] type class instance / evidence for [[PTKm2]]. */
  implicit val unShowEv: UnshowDbl2[PtKm2] = UnshowDbl2[PtKm2]("PtKm2", "x", "y", new PtKm2(_, _))

  implicit val builderImplicit: BuilderArrDbl2Map[PtKm2, PtKm2Arr] = new BuilderArrDbl2Map[PtKm2, PtKm2Arr]
  { type BuffT = BuffPtKm2
    override def fromDblArray(array: Array[Double]): PtKm2Arr = new PtKm2Arr(array)
    def buffFromBufferDbl(buffer: ArrayBuffer[Double]): BuffPtKm2 = new BuffPtKm2(buffer)
  }

  /*implicit val linePathBuildImplicit: LinePathDbl2Builder[PtKm2, LinePathKm2] = new LinePathDbl2Builder[PtKm2, LinePathKm2]
  { override type BuffT = BuffPtKm2
    override def fromDblArray(array: Array[Double]): LinePathKm2 = new LinePathKm2(array)
    override def buffFromBufferDbl(inp: ArrayBuffer[Double]): BuffPtKm2 = new BuffPtKm2(inp)
  }*/

  implicit val polygonBuildImplicit: PolygonDbl2MapBuilder[PtKm2, PolygonKm2] = new PolygonDbl2MapBuilder[PtKm2, PolygonKm2]
  { override type BuffT = BuffPtKm2
    override def fromDblArray(array: Array[Double]): PolygonKm2 = new PolygonKm2(array)
    override def buffFromBufferDbl(inp: ArrayBuffer[Double]): BuffPtKm2 = new BuffPtKm2(inp)
  }

  /** Implicit instance for the [[PolygonKm2Pair]] builder. This has to go in the [[PtKm2]] companion object so it can be found by an A => B function
   * where PtKm2 is the type B parameter. */
  //implicit def polygonPairBuildImplicit[A2](implicit ct: ClassTag[A2]): PolygonKm2PairBuilder[A2] = new PolygonKm2PairBuilder[A2]
}

/** Specialised immutable Array based collection class for [[PtKm2]]s. */
class PtKm2Arr(val arrayUnsafe: Array[Double]) extends AnyVal with ArrDbl2[PtKm2]
{ type ThisT = PtKm2Arr
  override def fromArray(array: Array[Double]): PtKm2Arr = new PtKm2Arr(array)
  override def typeStr: String = "Metres2s"
  override def seqDefElem(d1: Double, d2: Double): PtKm2 = PtKm2.kilometresNum(d1, d2)
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
final class BuffPtKm2(val unsafeBuffer: ArrayBuffer[Double]) extends AnyVal with BuffDbl2[PtKm2]
{ override def typeStr: String = "BuffPtMetre2"
  def newElem(d1: Double, d2: Double): PtKm2 = PtKm2.kilometresNum(d1, d2)
}

object BuffPtKm2
{ def apply(initSize: Int = 4): BuffPtKm2 = new BuffPtKm2(new ArrayBuffer[Double](initSize * 2))
}