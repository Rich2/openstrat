/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, collection.mutable.ArrayBuffer

/** A hex tile vertex coordinate. */
sealed trait HVert extends Any with HCoord with TCoord
{ def bLong: Long
  @inline def r: Int = bLong.>>(32).toInt
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

  def noOffset: HVOffset = HVOffset.none(this)

  def dirnTo(hvDirn: HVDirn): HCoord
}

object HVert
{
  def apply(r: Int, c: Int): HVert =
  { def value: Long = r.toLong.<<(32) | (c & 0xFFFFFFFFL)
    r %% 4 match
    { case 3 if c.div4Rem2 => new HVertHigh(value)
      case 1 if c.div4Rem0 => new HVertHigh(value)
      case 3 if c.div4Rem0 => new HVertLow(value)
      case 1 if c.div4Rem2 => new HVertLow(value)
      case _ => excep(s"$r, $c is not a valid Hex vertex tile coordinate.")
    }
  }

  def rcISHigh(r: Int, c: Int): Boolean = r %% 4 match
  { case 3 if c.div4Rem2 => true
    case 1 if c.div4Rem0 => true
    case 3 if c.div4Rem0 => false
    case 1 if c.div4Rem2 => false
    case _ => excep(s"$r, $c is not a valid Hex vertex tile coordinate.")
  }


  implicit val hVertsBuildImplicit: Int2ArrMapBuilder[HVert, HVertArr] = new Int2ArrMapBuilder[HVert, HVertArr]
  { type BuffT = HVertBuff
    override def fromIntArray(array: Array[Int]): HVertArr = new HVertArr(array)
    override def fromIntBuffer(buffer: ArrayBuffer[Int]): HVertBuff = new HVertBuff(buffer)
  }
}

/** An [[HVert]] hex vert where (r.div4Rem1 & c.div4Rem0) | (r.div4Rem3 & c.div4Rem2). */
final class HVertHigh(val bLong: Long) extends AnyVal with HVert
{ override def hexIsUp: Boolean = false
  override def hexIsDown: Boolean = true

  override def dirnToCen(dirn: HVDirnOpt): Boolean = dirn match {
    case HVUR | HVDn | HVUL => true
    case _ => false
  }

  override def dirnTo(hvDirn: HVDirn): HCoord = hvDirn match
  { case HVUp => HVertLow(r + 2, c)
    case HVUR => HCen(r + 1, c + 2)
    case HVDR => HVertLow(r, c + 2)
    case HVDn => HCen(r - 1, c)
    case HVDL => HVertLow(r, c - 2)
    case HVUL => HCen(r + 1, c - 2)
    case HVRt => HVertHigh(r, c + 4)
    case HVLt => HVertHigh(r, c - 4)
  }
}

object HVertHigh
{
  def apply(r: Int, c: Int): HVertLow = r %% 4 match {
    case 1 if c.div4Rem0 => new HVertLow(r.toLong.<<(32) | (c & 0xFFFFFFFFL))
    case 3 if c.div4Rem2 => new HVertLow(r.toLong.<<(32) | (c & 0xFFFFFFFFL))
    case _ => excep("Invalid values for HVertLow")
  }

  def unapply(inp: HCoord): Option[(Int, Int)] = inp.r %% 4 match
  {
    case 1 if inp.c.div4Rem0 => Some((inp.r, inp.c))
    case 3 if inp.c.div4Rem2 => Some((inp.r, inp.c))
    case _ => None
  }
}

/** An [[HVert]] hex vert where (r.div4Rem1 & c.div4Rem0) | (r.div4Rem3 & c.div4Rem2). */
final class HVertLow(val bLong: Long) extends AnyVal with  HVert
{ override def hexIsUp: Boolean = true
  override def hexIsDown: Boolean = false

  override def dirnToCen(dirn: HVDirnOpt): Boolean = dirn match
  { case HVUp | HVDL | HVDR if hexIsUp => true
    case _ => false
  }

  override def dirnTo(hvDirn: HVDirn): HCoord = hvDirn match
  { case HVUp => HCen(r + 1, c)
    case HVUR => HVertHigh(r, c + 2)
    case HVDR => HCen(r - 1, c + 2)
    case HVDn => HVertHigh(r - 2, c)
    case HVDL => HCen(r - 1, c - 2)
    case HVUL => HVertHigh(r, c - 2)
    case HVRt => HVertLow(r, c + 4)
    case HVLt => HVertLow(r, c - 4)
  }
}

object HVertLow
{
  def apply(r: Int, c: Int): HVertLow = r %% 4 match
  { case 1 if c.div4Rem2 => new HVertLow(r.toLong.<<(32) | (c & 0xFFFFFFFFL))
    case 3 if c.div4Rem0 => new HVertLow(r.toLong.<<(32) | (c & 0xFFFFFFFFL))
    case _ => excep("Invalid values for HVertLow")
  }

  def unapply(inp: HCoord): Option[(Int, Int)] = inp.r %% 4 match
  { case 1 if inp.c.div4Rem2 => Some((inp.r, inp.c))
    case 3 if inp.c.div4Rem0 => Some((inp.r, inp.c))
    case _ => None
  }
}

/** Common trait for [[Hverts]] and [[PolygonHC]] */
trait HVertSeqLike extends Any with SeqLikeInt2[HVert]
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

object HVertArr extends CompanionSeqLikeInt2[HVert, HVertArr]
{ def fromArray(array: Array[Int]): HVertArr = new HVertArr(array)

  /** Implicit [[Show]] type class instance / evidence for [[HVertArr]].  */
  implicit val showEv: ShowSequ[HVert, HVertArr] = ShowSequ[HVert, HVertArr]()

  /** Implicit [[Unshow]] type class instance / evidence for [[HVertArr]].  */
  implicit val unshowEv: UnshowArrIntN[HVert, HVertArr] = UnshowArrIntN[HVert, HVertArr](fromArray)

  implicit val arrArrayImplicit: BuilderArrFlat[HVertArr] = new Int2ArrFlatBuilder[HVertArr]
  { type BuffT = HVertBuff
    override def fromIntArray(array: Array[Int]): HVertArr = new HVertArr(array)
    override def fromIntBuffer(buffer: ArrayBuffer[Int]): HVertBuff = new HVertBuff(buffer)
  }
}

class HVertBuff(val unsafeBuffer: ArrayBuffer[Int] = BufferInt()) extends AnyVal with BuffInt2[HVert]
{ type ArrT = HVertArr
  override def typeStr: String = "HVertBuff"
  override def newElem(i1: Int, i2: Int): HVert = HVert(i1, i2)
}