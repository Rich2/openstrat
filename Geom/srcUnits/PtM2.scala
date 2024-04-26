/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer, math._, reflect.ClassTag

/** A 2 dimensional point specified in [[Metres]] as units rather than pure scalar numbers. */
final class PtM2 private(val xMetresNum: Double, val yMetresNum: Double) extends PtLength2 with VecPtM2 with TellElemDbl2
{ override type ThisT = PtM2
  override type LineSegT = LineSegM2
  override def typeStr: String = "PtM2"

  override def slate(operand: PtLength2): PtM2 = new PtM2(xMetresNum + operand.xMetresNum, yMetresNum - operand.yMetresNum)
  override def slateFrom(operand: PtLength2): PtM2 = new PtM2(xMetresNum - operand.xMetresNum, yMetresNum - operand.yMetresNum)
  override def + (operand: VecLength2): PtM2 = new PtM2(xMetresNum + operand.xMetresNum, yMetresNum + operand.yMetresNum)
  override def - (operand: VecLength2): PtM2 = new PtM2(xMetresNum - operand.xMetresNum, yMetresNum - operand.yMetresNum)
  override def addXY (otherX: Length, otherY: Length): PtM2 = new PtM2(xMetresNum + otherX.metresNum, yMetresNum + otherY.metresNum)
  override def subXY (otherX: Length, otherY: Length): PtM2 = new PtM2(xMetresNum - otherX.metresNum, yMetresNum - otherY.metresNum)
  override def addX(operand: Length): PtM2 = new PtM2(xMetresNum + operand.metresNum, y.metresNum)
  override def addY(operand: Length): PtM2 = new PtM2(xMetresNum, yMetresNum + operand.metresNum)
  override def subX(operand: Length): PtM2 = new PtM2(xMetresNum - operand.metresNum, yMetresNum)
  override def subY(operand: Length): PtM2 = new PtM2(xMetresNum, yMetresNum - operand.metresNum)
  override def * (operand: Double): PtM2 = new PtM2(xMetresNum * operand, yMetresNum * operand)
  override def / (operator: Double): PtM2 = new PtM2(xMetresNum / operator, yMetresNum / operator)
  override def divByLength(operator: Length): Pt2 = Pt2(xMetresNum / operator.metresNum, yMetresNum / operator.metresNum)
  override def revY: PtM2 = new PtM2(xMetresNum, -yMetresNum)
  override def revYIf(cond: Boolean): PtM2 = ife(cond, new PtM2(xMetresNum, -yMetresNum), this)
  override def magnitude: Metres = Metres(math.sqrt(xMetresNum.squared + yMetresNum.squared))
  override def rotate180: PtM2 = new PtM2(-xMetresNum, -yMetresNum)
  override def rotate180If(cond: Boolean): PtM2 = ife(cond, rotate180, this)
  override def rotate180IfNot(cond: Boolean): PtM2 = ife(cond, this, rotate180)
  override def rotate(a: AngleVec): PtM2 =  PtM2.metresNum(x.metresNum * a.cos - y.metresNum * a.sin, x.metresNum * a.sin + y.metresNum * a.cos)

  override def rotateRadians(r: Double): PtM2 =
  { val newX = xMetresNum * cos(r) - yMetresNum * sin(r)
    val newY =
    { val ya = xMetresNum * sin(r)
      val yb = yMetresNum * cos(r)
      ya + yb
    }
    new PtM2(newX, newY)
  }

  override def lineSegTo(endPt: PtM2): LineSegM2 = LineSegM2(this, endPt)
  override def lineSegFrom(startPt: PtM2): LineSegM2 = LineSegM2(startPt, this)
}

/** Companion object for [[PtM2]] class contains factory methods. */
object PtM2
{ /** Factory method for creating a 2 dimensional point measured in metres from the scalar [[Double]] values. */
  def metresNum(xMetres: Double, yMetres: Double): PtM2 = new PtM2(xMetres, yMetres)

  def apply(x: Metres, y: Metres): PtM2 = new PtM2(x.metresNum, y.metresNum)

  def origin: PtM2 = new PtM2(0, 0)



  /** [[Show]] type class instance / evidence for [[PTM2]]. */
  implicit val persistEv: ShowTellDbl2[PtM2] = ShowTellDbl2[PtM2]("Metres2")

  /** [[Unshow]] type class instance / evidence for [[PTM2]]. */
  implicit val unShowEv: UnshowDbl2[PtM2] = UnshowDbl2[PtM2]("Metres2", "x", "y", new PtM2(_, _))

  implicit val builderImplicit: BuilderArrDbl2Map[PtM2, PtM2Arr] = new BuilderArrDbl2Map[PtM2, PtM2Arr]
  { type BuffT = BuffPtM2
    override def fromDblArray(array: Array[Double]): PtM2Arr = new PtM2Arr(array)
    def buffFromBufferDbl(buffer: ArrayBuffer[Double]): BuffPtM2 = new BuffPtM2(buffer)
  }

  implicit val linePathBuildImplicit: LinePathDbl2Builder[PtM2, LinePathM2] = new LinePathDbl2Builder[PtM2, LinePathM2]
  { override type BuffT = BuffPtM2
    override def fromDblArray(array: Array[Double]): LinePathM2 = new LinePathM2(array)
    override def buffFromBufferDbl(inp: ArrayBuffer[Double]): BuffPtM2 = new BuffPtM2(inp)
  }

  implicit val polygonBuildImplicit: PolygonDbl2MapBuilder[PtM2, PolygonM2] = new PolygonDbl2MapBuilder[PtM2, PolygonM2]
  { override type BuffT = BuffPtM2
    override def fromDblArray(array: Array[Double]): PolygonM2 = new PolygonM2(array)
    override def buffFromBufferDbl(inp: ArrayBuffer[Double]): BuffPtM2 = new BuffPtM2(inp)
  }

  /** Implicit instance for the [[PolygonM2Pair]] builder. This has to go in the [[PtM2]] companion object so it can be found by an A => B function
   * where PtM2 is the type B parameter. */
  implicit def polygonPairBuildImplicit[A2](implicit ct: ClassTag[A2]): PolygonM2PairBuilder[A2] = new PolygonM2PairBuilder[A2]
}

/** Specialised immutable Array based collection class for [[PtM2]]s. */
class PtM2Arr(val arrayUnsafe: Array[Double]) extends AnyVal with ArrDbl2[PtM2]
{ type ThisT = PtM2Arr
  override def fromArray(array: Array[Double]): PtM2Arr = new PtM2Arr(array)
  override def typeStr: String = "Metres2s"
  override def seqDefElem(d1: Double, d2: Double): PtM2 = PtM2.metresNum(d1, d2)
  override def fElemStr: PtM2 => String = _.str
}

/** Companion object for the [[PtM2Arr]] class. Contains implicit Instance for Persist type class. */
object PtM2Arr extends CompanionSeqLikeDbl2[PtM2, PtM2Arr]
{
  override def fromArray(array: Array[Double]): PtM2Arr = new PtM2Arr(array)

  /** [[Show]] type class instance / evidence for [[PtM2Arr]]. */
  implicit lazy val showEv: ShowSequ[PtM2, PtM2Arr] = ShowSequ[PtM2, PtM2Arr]()

  /** [[Unshow]] type class instance / evidence for [[PtM2Arr]]. */
  implicit lazy val unshowEv: UnshowSeq[PtM2, PtM2Arr] = UnshowSeq[PtM2, PtM2Arr]()
}

/** A specialised flat ArrayBuffer[Double] based class for [[PtM2]]s collections. */
final class BuffPtM2(val unsafeBuffer: ArrayBuffer[Double]) extends AnyVal with BuffDbl2[PtM2]
{ override def typeStr: String = "BuffPtMetre2"
  def newElem(d1: Double, d2: Double): PtM2 = PtM2.metresNum(d1, d2)
}

object BuffPtM2
{ def apply(initSize: Int = 4): BuffPtM2 = new BuffPtM2(new ArrayBuffer[Double](initSize * 2))
}