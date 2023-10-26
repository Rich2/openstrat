/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, collection.mutable.ArrayBuffer

/** A coordinate with in a Hex grid. It may be a Hex tile centre [[HCen]], a HexSide [[HSide]] or Hex tile vertice [[HVert]]. */
trait HCoord extends Any with TCoord with Ordered[HCoord]
{ override type ThisT = HCoord
  override type LineSegT = LineSegHC

  /** [[LineSegHC]] from this [[HCoord]] to the parameter [[HCoord]]. */
  override def lineSegTo(endPt: HCoord): LineSegHC = LineSegHC(this, endPt)

  /** [[LinSegHC]] from the parameter [[HCoord]] to this [[HCoord]]. */
  override def lineSegFrom(startPt: HCoord): LineSegHC = LineSegHC(startPt, this)

  override def equals(obj: Any): Boolean = obj match {
    case hc: HCoord if r == hc.r & c == hc.c => true
    case _ => false
  }

  def canEqual(a: Any) = a.isInstanceOf[HCoord]

  override def hashCode: Int =
  { val prime = 31
    val f = 1
    val result1 = (prime * f) + r
    prime * result1 + c * 17
  }

  override def compare(that: HCoord): Int = r match
  { case r if r > that.r => 1
    case r if r < that.r => -1
    case r if r.isOdd & c.div4Rem0 & that.c.div4 != 0 => 1
    case r if r.isOdd & c.isOdd & that.c.div4Rem2 => 1
    case r if that.r.isOdd & that.c.div4Rem0 & c.div4 != 0 => -1
    case r if that.r.isOdd & that.c.isOdd & c.div4Rem2 => -1
    case _ if c > that.c => 1
    case _ if c < that.c => -1
    case _ => 0
  }

  def subR(rDelta: Int): HCoord = HCoord(r -rDelta, c)

  def view(pxScale: Double = 30): HGView = HGView(r, c, pxScale)

  /** Uses the implicit [[HGridSysFlat]] to convert to [[Pt2]]. */
  def toPt2(implicit proj: HSysProjection): Pt2 = proj.transCoord(this)

  def isCen: Boolean = (r.div4Rem0 & c.div4Rem0) | (r.div4Rem2 & c.div4Rem2)
}

/** Companion object for Hex coordinate trait, contains apply factory method and persist and PolygonBuilder implicit instances. */
object HCoord
{ /** Factory apply method for creating [[HCoord]]s .Creates an [[HCen]], an [[HSide]], an [[HVert]] or an [[HCoordOther]] depending on the values of
   *  r and c. */
  def apply(r: Int, c: Int): HCoord = r %% 4 match
  { case 0 if c.div4Rem0 => new HCen(r, c)
    case 1 if c.div4Rem1 => new HSideA(r, c)
    case 3 if c.div4Rem3 => new HSideA(r, c)
    case 0 if c.div4Rem2 => new HSideB(r, c)
    case 2 if c.div4Rem0 => new HSideB(r, c)
    case 1 if c.div4Rem3 => new HSideC(r, c)
    case 3 if c.div4Rem1 => new HSideC(r, c)
    case 2 if c.div4Rem2 => new HCen(r, c)
    case 1 | 3 => HVert(r, c)
    case _ => new HCoordOther(r, c)
  }

  /** Implicit type class instance / evidence for the [[HCoord]] type class instance of [[BuilderArrMap]]. */
  implicit val arrBuildEv: Int2ArrMapBuilder[HCoord, HCoordArr] = new Int2ArrMapBuilder[HCoord, HCoordArr] {
    override type BuffT = HCoordBuff
    override def fromIntArray(array: Array[Int]): HCoordArr = new HCoordArr(array)
    override def fromIntBuffer(buffer: ArrayBuffer[Int]): HCoordBuff = HCoordBuff()
  }

  /** Implicit [[Show]] type class instance /evidence for [[HCoord]]. */
  implicit val persistImplicit: ShowTellInt2[HCoord] = ShowTellInt2[HCoord]("HCoord")

  /** Implicit [[Unshow]] type class instance /evidence for [[HCoord]]. */
  implicit val unshowEv: UnshowInt2[HCoord] = UnshowInt2[HCoord]("HCoord", "r", "c", HCoord(_, _))

  /** Implicit type class instance / evidence for the [[HCoord]] type class instance of [[PolygonLikeMapBuilder]]. */
  implicit val polygonBuildEv: PolygonInt2MapBuilder[HCoord, PolygonHC] = new PolygonInt2MapBuilder[HCoord, PolygonHC]
  { override type BuffT = HCoordBuff
    override def fromIntArray(array: Array[Int]): PolygonHC = new PolygonHC(array)
    override def fromIntBuffer(inp: ArrayBuffer[Int]): HCoordBuff = new HCoordBuff(inp)
  }

  /** Implicit type class instance / evidence for the [[HCoord]] type class instance of [[LinePathBuilder]]. */
  implicit val linePathMapBuildEv: LinePathInt2MapBuilder[HCoord, LinePathHC] = new LinePathInt2MapBuilder[HCoord, LinePathHC]
  { override type BuffT = HCoordBuff
    override def fromIntArray(array: Array[Int]): LinePathHC = new LinePathHC(array)
    override def fromIntBuffer(inp: ArrayBuffer[Int]): HCoordBuff = new HCoordBuff(inp)
  }
}

trait HCoordSeqLike extends Any with SeqLikeInt2[HCoord]
{ final override def newElem(int1: Int, int2: Int): HCoord = HCoord(int1, int2)
  final override def fElemStr: HCoord => String = _.toString
}

trait HCoordSeqSpec extends Any with HCoordSeqLike with SeqSpecInt2[HCoord]

/** Specialised sequence class for [[HCoord]]. */
class HCoordArr(val unsafeArray: Array[Int]) extends AnyVal with Int2Arr[HCoord] with HCoordSeqLike
{ type ThisT = HCoordArr
  override def typeStr: String = "HCoordArr"
  override def fromArray(array: Array[Int]): HCoordArr = new HCoordArr(array)
}

/** Specialised sequence buffer class for [[HCoord]]. */
class HCoordBuff(val unsafeBuffer: ArrayBuffer[Int] = BuffInt()) extends AnyVal with BuffInt2[HCoord]
{ type ArrT = HCoordArr
  override def typeStr: String = "HCoordBuff"
  override def newElem(i1: Int, i2: Int): HCoord = HCoord(i1, i2)
}

object HCoordBuff extends Int2BuffCompanion[HCoord, HCoordBuff]
{ override def fromBuffer(buffer: ArrayBuffer[Int]): HCoordBuff = new HCoordBuff(buffer)
}

trait HNotVert extends HCoord
{ override def toVecReg: Vec2 = Vec2(c, r * Sqrt3)
  override def toPt2Reg: Pt2 = Pt2(c, r  * Sqrt3)
}

/** Common trait for hex centre and hex side coordinate. The position of these coordinates is proportional, unlike the Hex vertices positions. */
trait HCenOrSide extends HNotVert with TCenOrSide

/** Companion object for [[HCenOrSide]] trait, contains factory apply method and implicit [[Persist]] instance. */
object HCenOrSide
{ /** Apply factory method for creating [[HCenOrSide]] instances. Will throw exception on illegal values.  */
  def apply(r: Int, c: Int): HCenOrSide = r %% 4 match
  { case 0 if c.div4Rem0 => new HCen(r, c)
    case 2 if c.div4Rem2 => new HCen(r, c)
    case 1 if c.div4Rem1 => new HSideA(r, c)
    case 3 if c.div4Rem3 => new HSideA(r, c)
    case 0 if c.div4Rem2 => new HSideB(r, c)
    case 2 if c.div4Rem0 => new HSideB(r, c)
    case 1 if c.div4Rem3 => new HSideC(r, c)
    case 3 if c.div4Rem1 => new HSideC(r, c)
    case _ => excep(s"$r, $c is not a valid HCenOrSide hex grid coordinate.")
  }

  /** implicit [[Show]] type class instance / evidence for [[HCenOrSide]]s. */
  implicit val showEv: ShowTellInt2[HCenOrSide] = ShowTellInt2[HCenOrSide]("HCenOrSide")

  /** implicit [[Unshow]] type class instance / evidence for [[HCenOrSide]]s. */
  implicit val unshowEv: UnshowInt2[HCenOrSide] = UnshowInt2[HCenOrSide]("HCenOrSide", "r", "c", apply)
}

/** The only purpose of this class is to ensure that all [[HCoord]] combinations of r and c are valid. Thisis for the combinations where r is even and
 *  c is odd. */
class HCoordOther(val r: Int, val c: Int) extends HNotVert
{ override def typeStr: String = "HCoordOther"
}