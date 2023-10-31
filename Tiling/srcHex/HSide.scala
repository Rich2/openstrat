/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import collection.mutable.ArrayBuffer, geom._, pgui._, reflect.ClassTag

/** A Hex side coordinate in a Hex Grid.
 * So Side 1 on its primary Hex tile goes from Vert 6 to 1 while it is Side 4 on its secondary Hex tile and goes from Vertex 4 to vertex 3
 * So Side 2 on its primary Hex tile goes from Vert 1 to 2 while it is Side 5 on its secondary Hex tile and goes from Vertex 5 to vertex 4
 * So Side 3 on its primary Hex tile goes from Vert 2 to 3 while it is Side 4 on its secondary Hex tile and goes from Vertex 6 to vertex 4 */
trait HSide extends HCenOrSide with TSide
{ override def typeStr: String = "HSide"

  /** Is a side that goes from top left to bottom right. */
  def isTypeA: Boolean

  /** Is a side that is vertical. Sides 4 for the left hand hex and side 1 for the right hand hex */
  def isTypeB: Boolean

  /** Is a side that goes from top right to bottom left. */
  def isTypeC: Boolean

  /** Returns the hex coordinate Line segment for this Hex Side. */
  def lineSegHC: LineSegHC

  /** Returns the upper vertex of this hex side. */
  def vertUpper: HVert

  /** Returns the lower vertex of this hex side. */
  def vertLower: HVert

  /** Returns the 2 adjacent [[HCen]] coordinates of this hex Side. Both tiles may not exist in the [[HGridSysy]].  */
  def unsafeTiles: (HCen, HCen)

  /** Returns the [[HCen]] of the right hand tile needs modification. */
  def tileRt(implicit sys: HGridSys): HCen = sys.sideTileRtUnsafe(this)

  /** Returns the [[HCen]] of the left hand tile needs modification. */
  def tileLt(implicit sys: HGridSys): HCen = sys.sideTileLtUnsafe(this)

  /** Returns the tile and upper vertex index for this side. */
  def tileLtAndVert: (HCen, Int)

  def tileLtAndVertFromRt(rtCenR: Int)(implicit gSys: HGridSys): (HCen, Int)

  /** Returns the [[HCen]] of the right tile and the index of the upper vertex it follows the sides in clockwise direction. */
  def tileRtAndVert: (HCen, Int)

  def tileLtOpt(implicit sys: HGridSys): Option[HCen] = sys.sideTileLtOpt(this)

  def tileRtOpt(implicit sys: HGridSys): Option[HCen] = sys.sideTileRtOpt(this)

  /** The left tile in the [[HGrid]]. It may not exist in the [[HGridSys]], but guaranteed to exist if the side is a link / inner side of an [[HGrid]]. */
  def tileLtReg: HCen

  /** The right tile in the [[HGrid]]. It may not exist in the [[HGridSys]] but guaranteed to exist if the side is a link / inner side of an [[HGrid]]. */
  def tileRtReg: HCen

  /** Not sure about this method. */
  def cornerNums(implicit sys: HGridSys): (HCen, Int, Int)

  def leftCorners(corners: HCornerLayer)(implicit sys: HGridSys): LineSegHVAndOffset

  def rightCorners(corners: HCornerLayer)(implicit sys: HGridSys): LineSegHVAndOffset

  def sideLineHVAndOffSet(corners: HCornerLayer)(implicit sys: HGridSys): LineSegHVAndOffset = {
    val cs: (HCen, Int, Int) = cornerNums
    corners.sideLineHVAndOffset(cs._1, cs._2, cs._3)
  }

  def projCornersSideLine(proj: HSysProjection, corners: HCornerLayer): Option[LineSeg] = {
    val ls1: LineSegHVAndOffset = sideLineHVAndOffSet(corners)(proj.parent)
    ls1.mapOpt(proj.transOptHVOffset(_))
  }
}

/** Companion object for the HSide class, provides an apply factory method that throws an exception for an invalid Hex side coordinate. */
object HSide
{ /** Factory method for HSide that throws an exception for an invalid Hex side coordinate. */
  def apply(r: Int, c: Int): HSide = r %% 4 match
  { case 1 if c.div4Rem1 => new HSideA(r, c)
    case 3 if c.div4Rem3 => new HSideA(r, c)
    case 0 if c.div4Rem2 => new HSideB(r, c)
    case 2 if c.div4Rem0 => new HSideB(r, c)
    case 1 if c.div4Rem3 => new HSideC(r, c)
    case 3 if c.div4Rem1 => new HSideC(r, c)
    case _ => excep(s"$r, $c is not a valid hex side tile coordinate.")
  }

  def unapply(inp: Any): Option[(Int, Int)] = inp match{
    case hs: HSide => Some(hs.r, hs.c)
    case _ => None
  }

  /** Implicit [[BuilderArrMap]] type class instance / evidence for [[HSide]] and [[HSideArr]]. */
  implicit val arrMapBuilderEv: BuilderArrInt2Map[HSide, HSideArr] = new BuilderArrInt2Map[HSide, HSideArr]
  { type BuffT = HSideBuff
    override def fromIntArray(array: Array[Int]): HSideArr = new HSideArr(array)
    override def fromIntBuffer(buffer: ArrayBuffer[Int]): HSideBuff = new HSideBuff(buffer)
  }

  implicit def pairArrMapBuilder[B2](implicit ct: ClassTag[B2]): HSidePairArrMapBuilder[B2] = new HSidePairArrMapBuilder[B2]
}

/** A hex side that slants down from left to right. r.div4Rem1 & c.div4Rem1 | r.div4Rem3 & c.div4Rem3 */
class HSideA(val r: Int, val c: Int) extends HSide
{ override def isTypeA: Boolean = true
  override def isTypeB: Boolean = false
  override def isTypeC: Boolean = false
  override def vertUpper: HVert = HVert(r, c - 1)
  override def vertLower: HVert = HVert(r, c + 1)
  override def tileLtReg: HCen = HCen(r - 1, c - 1)
  override def tileRtReg: HCen = HCen(r + 1, c + 1)
  override def tileLtAndVert: (HCen, Int) = (HCen(r - 1, c - 1), 0)

  override def tileLtAndVertFromRt(rtCenR: Int)(implicit gSys: HGridSys): (HCen, Int) =
  { val hCen = tileLt
    val i = ife(hCen.r == rtCenR - 2, 0, 2)
    (hCen, i)
  }

  override def tileRtAndVert: (HCen, Int) = (HCen(r + 1, c + 1), 4)
  override def lineSegHC: LineSegHC = LineSegHC(r, c - 1, r, c + 1)
  override def unsafeTiles: (HCen, HCen) = (HCen(r - 1, c - 1), HCen(r + 1, c + 1))
  override def cornerNums(implicit sys: HGridSys): (HCen, Int, Int) = ife(sys.hCenExists(tileLt), (tileLt, 0, 1), (tileRt, 3, 4))

  override def leftCorners(corners: HCornerLayer)(implicit sys: HGridSys): LineSegHVAndOffset =
    if(sys.hCenExists(tileLt)) LineSegHVAndOffset(corners.cornerV1(tileLt, 0), corners.cornerV1(tileLt, 1))
    else LineSegHVAndOffset(tileLt.v0Exact, tileLt.v1Exact)

  override def rightCorners(corners: HCornerLayer)(implicit sys: HGridSys): LineSegHVAndOffset =
    if (sys.hCenExists(tileRt)) LineSegHVAndOffset(corners.cornerV1(tileRt, 3), corners.cornerV1(tileRt, 4))
    else LineSegHVAndOffset(tileRt.v3Exact, tileRt.v4Exact)
}

object HSideA
{
  def unapply(inp: Any): Option[(Int, Int)] = inp match
  { case hs: HSideA => Some(hs.r, hs.c)
    case _ => None
  }
}

/** A hex side that slants straight down. r.div4Rem0 & c.div4Rem2 | r.div4Rem2 & c.div4Rem0 */
class HSideB(val r: Int, val c: Int) extends HSide
{ override def isTypeA: Boolean = false
  override def isTypeB: Boolean = true
  override def isTypeC: Boolean = false
  override def vertUpper: HVert = HVert(r + 1, c)
  override def vertLower: HVert = HVert(r - 1, c)
  override def tileLtReg: HCen = HCen(r, c - 2)
  override def tileRtReg: HCen = HCen(r, c + 2)
  override def tileLtAndVert: (HCen, Int) = (HCen(r, c - 2), 1)
  override def tileLtAndVertFromRt(rtCenR: Int)(implicit gSys: HGridSys): (HCen, Int) = (tileLt, 1)

  override def tileRtAndVert: (HCen, Int) = (HCen(r, c + 2), 5)
  override def lineSegHC: LineSegHC = LineSegHC(r + 1, c, r - 1, c)
  override def unsafeTiles: (HCen, HCen) = (HCen(r, c - 2), HCen(r, c + 2))
  override def cornerNums(implicit sys: HGridSys): (HCen, Int, Int) = ife(sys.hCenExists(tileRt), (tileRt, 4, 5), (tileLt, 1, 2))

  override def leftCorners(corners: HCornerLayer)(implicit sys: HGridSys): LineSegHVAndOffset =
    if (sys.hCenExists(tileLt)) LineSegHVAndOffset(corners.cornerV1(tileLt, 1), corners.cornerV1(tileLt, 2))
    else LineSegHVAndOffset(tileLt.v1Exact, tileLt.v2Exact)

  override def rightCorners(corners: HCornerLayer)(implicit sys: HGridSys): LineSegHVAndOffset =
    if (sys.hCenExists(tileRt)) LineSegHVAndOffset(corners.cornerV1(tileRt, 4), corners.cornerV1(tileRt, 5))
    else LineSegHVAndOffset(tileRt.v4Exact, tileRt.v5Exact)
}

object HSideB
{
  def unapply(inp: Any): Option[(Int, Int)] = inp match
  { case hs: HSideB => Some(hs.r, hs.c)
    case _ => None
  }
}

/** A hex side that slants down from top right to bottom left. r.div4Rem1 & c.div4Rem3 | r.div4Rem3 & c.div4Rem1 */
class HSideC(val r: Int, val c: Int) extends HSide
{ override def isTypeA: Boolean = false
  override def isTypeB: Boolean = false
  override def isTypeC: Boolean = true
  override def vertUpper: HVert = HVert(r, c + 1)
  override def vertLower: HVert = HVert(r, c - 1)
  override def tileLtReg: HCen = HCen(r + 1, c - 1)
  override def tileRtReg: HCen = HCen(r - 1, c + 1)
  override def tileLtAndVert: (HCen, Int) = (HCen(r + 1, c - 1), 2)

  override def tileLtAndVertFromRt(rtCenR: Int)(implicit gSys: HGridSys): (HCen, Int) =
  { val hCen = tileLt
    val i = ife(hCen.r == rtCenR + 2, 2, 0)
    (hCen, i)
  }

  override def tileRtAndVert: (HCen, Int) = (HCen(r - 1, c + 1), 0)
  override def lineSegHC: LineSegHC = LineSegHC(r, c + 1, r, c - 1)
  override def unsafeTiles: (HCen, HCen) = (HCen(r + 1, c - 1), HCen(r - 1, c + 1))
  override def cornerNums(implicit sys: HGridSys): (HCen, Int, Int) = ife(sys.hCenExists(tileRt),(tileRt, 5, 0),  (tileLt, 2, 3))

  override def leftCorners(corners: HCornerLayer)(implicit sys: HGridSys): LineSegHVAndOffset =
    if (sys.hCenExists(tileLt)) LineSegHVAndOffset(corners.cornerV1(tileLt, 2), corners.cornerV1(tileLt, 3))
    else LineSegHVAndOffset(tileLt.v2Exact, tileLt.v3Exact)

  override def rightCorners(corners: HCornerLayer)(implicit sys: HGridSys): LineSegHVAndOffset =
    if (sys.hCenExists(tileRt)) LineSegHVAndOffset(corners.cornerV1(tileRt, 5), corners.cornerV1(tileRt, 0))
    else LineSegHVAndOffset(tileRt.v5Exact, tileRt.v0Exact)
}

object HSideC
{
  def unapply(inp: Any): Option[(Int, Int)] = inp match
  { case hs: HSideC => Some(hs.r, hs.c)
    case _ => None
  }
}

/** [[PairElem]] class for [[HSide]]s. Allows for the efficient storage of sequences in [[HSidePairArr]]s. */
class HSidePair[A2](val a1Int1: Int, val a1Int2: Int, val a2: A2) extends PairInt2Elem[HSide, A2] with Selectable
{ override def a1: HSide = HSide(a1Int1, a1Int2)
  override def toString: String = s"$a2; $a1Int1, $a1Int2"

  /** The [[String]] to be displayed in the status bar in a GUI when selected. */
  override def selectStr: String =
  { val s1 = a2 match
  { case sel: Selectable => sel.selectStr
    case st: Tell => st.str
    case a => a.toString
  }
    s"$s1; ${a1.rcStr}"
  }
}

/** Companion object for [[HSidePair]] trait, provides apply and unapply methods. */
object HSidePair
{ def apply[A2](hc: HSide, a2: A2): HSidePair[A2] = new HSidePair[A2](hc.int1, hc.int2, a2)
  def unapply(inp: Any): Option[(HSide, Any)] = inp match
  { case hcp: HSidePair[_] => Some((hcp.a1, hcp.a2))
    case _ => None
  }
}