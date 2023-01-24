/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, collection.mutable.ArrayBuffer

/** A hex tile vertex coordinate. */
class HVert private(val bLong: Long) extends AnyVal with HCoord with TCoord
{ @inline def r: Int = bLong.>>(32).toInt
  @inline def c: Int = bLong.toInt
  override def typeStr: String = "HVert"
  override def canEqual(that: Any): Boolean = ???

  /** is there a hex directly above this [[HVert]]. If false there is a hex directly below. */
  def hexIsUp: Boolean = (r.div4Rem1 & c.div4Rem2) | (r.div4Rem3 & c.div4Rem0)
  def hexIsDown: Boolean = (r.div4Rem1 & c.div4Rem0) | (r.div4Rem3 & c.div4Rem2)
  override def toVecReg: Vec2 = ife(hexIsUp, Vec2(c, r * Sqrt3 - 1.0/Sqrt3), Vec2(c, r  * Sqrt3 + 1.0/Sqrt3))
  override def toPt2Reg: Pt2 = ife(hexIsUp, Pt2(c, r * Sqrt3 - 1.0/Sqrt3), Pt2(c, r  * Sqrt3 + 1.0/Sqrt3))

  def addHCen (hCen: HCen): HVert = HVert(r + hCen.r, c + hCen.c)

  def adjHCenDirns: HVDirnArr = ife(hexIsUp, HVDirnArr(HVUp, HVDR, HVDL), HVDirnArr(HVUR, HVDn, HVUL))
  def adjHCenCorners(implicit sys: HGridSys): RArr[(HCen, Int)] = adjHCenDirns.optMap{dirn =>
    val hCen = HCen(r + dirn.dCenR, c + dirn.dCenC)
    ife(sys.hCenExists(hCen), Some((hCen, dirn.corner(this))), None)
  }

  def dirnToCen(dirn: HVDirnOpt): Boolean = dirn match {
    case HVUp | HVDL | HVDR if hexIsUp => true
    case HVUR | HVDn | HVUL if hexIsDown => true
    case _ => false
  }

  def noOffset: HVAndOffset = HVAndOffset.none(this)
}

object HVert
{
  def apply(r: Int, c: Int): HVert = if (r.isOdd & c.isEven)
    new HVert(r.toLong.<<(32) | (c & 0xFFFFFFFFL))
    else excep(s"$r, $c is not a valid Hex vertex tile coordinate.")

 // implicit object persistImplicit extends Persist2Ints[HVert]("Rood", "r", "c", apply)

  implicit val hVertsBuildImplicit: Int2ArrMapBuilder[HVert, HVertArr] = new Int2ArrMapBuilder[HVert, HVertArr]
  { type BuffT = HVertBuff
    override def fromIntArray(array: Array[Int]): HVertArr = new HVertArr(array)
    override def fromIntBuffer(buffer: ArrayBuffer[Int]): HVertBuff = new HVertBuff(buffer)
  }
}

/** Common trait for [[Hverts]] and [[PolygonHC]] */
trait HVertSeqLike extends Any with Int2SeqLike[HVert]
{ override def newElem(int1: Int, int2: Int): HVert = HVert.apply(int1, int2)
  override def fElemStr: HVert => String = _.str
  def vertNum: Int = unsafeArray.length / 2
}

/** An array[Int] based collection for HVert. */
class HVertArr(val unsafeArray: Array[Int]) extends AnyVal with HVertSeqLike with Int2Arr[HVert]
{ type ThisT = HVertArr
  override def fromArray(array: Array[Int]): HVertArr = new HVertArr(array)
  override def typeStr: String = "HVerts" + foldLeft("")(_ + "; " + _.rcStr)

  def toPolygon: PolygonHC = new PolygonHC(unsafeArray)
}

object HVertArr extends Int2SeqLikeCompanion[HVert, HVertArr]
{ def fromArray(array: Array[Int]): HVertArr = new HVertArr(array)

  implicit object PersistImplicit extends PersistArrInt2s[HVert, HVertArr]("HVerts")
  { override def fromArray(value: Array[Int]): HVertArr = new HVertArr(value)

    override def showDecT(obj: HVertArr, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???
  }

  implicit val arrArrayImplicit: ArrFlatBuilder[HVertArr] = new Int2ArrFlatBuilder[HVertArr]
  { type BuffT = HVertBuff
    override def fromIntArray(array: Array[Int]): HVertArr = new HVertArr(array)
    override def fromIntBuffer(buffer: ArrayBuffer[Int]): HVertBuff = new HVertBuff(buffer)
  }
}

class HVertBuff(val unsafeBuffer: ArrayBuffer[Int] = BuffInt()) extends AnyVal with Int2Buff[HVert]
{ type ArrT = HVertArr
  override def typeStr: String = "HVertBuff"
  override def newElem(i1: Int, i2: Int): HVert = HVert(i1, i2)
}