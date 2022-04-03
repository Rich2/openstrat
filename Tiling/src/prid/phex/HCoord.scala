/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, collection.mutable.ArrayBuffer

/** A coordinate with in a Hex grid. It may be a Hex tile centre [[HCen]], a HexSide [[HSide]] or Hex tile vertice [[HVert]]. */
trait HCoord extends Any with TCoord
{
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

  def subR(rDelta: Int): HCoord = HCoord(r -rDelta, c)

  def view(pxScale: Double = 50): HGridView = HGridView(r, c, pxScale)

  /** Uses the implicit [[HGriderFlat]] to convert to [[Pt2]]. */
  def toPt2(implicit grider: HGriderFlat): Pt2 = grider.hCoordToPt2(this)
}

/** Companion object for Hex coordinate trait, contains apply factory method and persist and PolygonBuilder implicit instances. */
object HCoord
{ /** Factory apply method for creating [[HCoord]]s .Creates an [[HCen]], an [[HSide]], an [[HVert]] or an [[HCoordOther]] depending on the values of
   *  r and c. */
  def apply(r: Int, c: Int): HCoord = r %% 4 match
  { case 0 if c.div4Rem0 => new HCen(r, c)
    case 0 if c.div4Rem2 => new HSide(r, c)
    case 2 if c.div4Rem0 => new HSide(r, c)
    case 2 if c.div4Rem2 => new HCen(r, c)
    case 1 | 3 if c.isOdd => new HSide(r, c)
    case 1 | 3 => HVert(r, c)
    case _ => new HCoordOther(r, c)
  }

  implicit val arrBuild: Int2ArrBuilder[HCoord, HCoordArr] = new Int2ArrBuilder[HCoord, HCoordArr] {
    override type BuffT = HCoordBuff
    override def fromIntArray(array: Array[Int]): HCoordArr = new HCoordArr(array)
    override def fromIntBuffer(buffer: Buff[Int]): HCoordBuff = HCoordBuff()
  }

  implicit val persistImplicit: Persist[HCoord] = PersistShowInt2[HCoord]("HCoord", "r", "c", HCoord(_, _))

  //val unshow32Ev: Unshow2[Int, Int, HCoord] = Unshow
  //val persist32: Persist[HCoord] =
    //Persist2[Int, Int, HCoord]("HCoord", "r", _.r, "c", _.c, HCoord(_, _))()

  implicit val polygonBuildImplicit: PolygonInt2sBuilder[HCoord, PolygonHC] = new PolygonInt2sBuilder[HCoord, PolygonHC]
  { override type BuffT = HCoordBuff
    override def fromIntArray(array: Array[Int]): PolygonHC = new PolygonHC(array)
    override def fromIntBuffer(inp: ArrayBuffer[Int]): HCoordBuff = new HCoordBuff(inp)
  }

  implicit val linePathBuildEv: LinePathInt2sBuilder[HCoord, LinePathHC] = new LinePathInt2sBuilder[HCoord, LinePathHC]
  { override type BuffT = HCoordBuff
    override def fromIntArray(array: Array[Int]): LinePathHC = new LinePathHC(array)
    override def fromIntBuffer(inp: ArrayBuffer[Int]): HCoordBuff = new HCoordBuff(inp)
  }
}

trait HCoordSeqDef extends Any with Int2SeqDef[HCoord]
{ final override def sdElem(int1: Int, int2: Int): HCoord = HCoord(int1, int2)
  final override def fElemStr: HCoord => String = _.toString
}

class HCoordArr(val unsafeArray: Array[Int]) extends AnyVal with Int2Arr[HCoord] with HCoordSeqDef
{ type ThisT = HCoordArr
  override def typeStr: String = "HCoords"
  override def fromArray(array: Array[Int]): HCoordArr = new HCoordArr(array)
}

class HCoordBuff(val unsafeBuffer: Buff[Int] = buffInt()) extends AnyVal with Int2Buff[HCoord]
{ type ArrT = HCoordArr
  override def typeStr: String = "HCoordBuff"
  override def intsToT(i1: Int, i2: Int): HCoord = HCoord(i1, i2)
}

object HCoordBuff extends Int2BuffCompanion[HCoord, HCoordBuff]
{ override def fromBuffer(buffer: Buff[Int]): HCoordBuff = new HCoordBuff(buffer)
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
    case 0 if c.div4Rem2 => new HSide(r, c)
    case 1 | 3 if c.isOdd => new HSide(r, c)
    case 2 if c.div4Rem0 => new HSide(r, c)
    case _ => excep(s"$r, $c is not a valid HCenOrSide hex grid coordinate.")
  }

  implicit val persistImplicit: PersistShowInt2[HCenOrSide] = new PersistShowInt2[HCenOrSide]("HCenOrSide", "r", "c", HCenOrSide(_, _))
}

/** The only purpose of this class is to ensure that all [[HCoord]] combinations of r and c are valid. Thisis for the combinations where r is even and
 *  c is odd. */
class HCoordOther(val r: Int, val c: Int) extends HNotVert
{ override def typeStr: String = "HCoordOther"
}