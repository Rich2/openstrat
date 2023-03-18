/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import collection.mutable.ArrayBuffer

/** A Hex side coordinate in a Hex Grid.
 * So Side 1 on its primary Hex tile goes from Vert 6 to 1 while it is Side 4 on its secondary Hex tile and goes from Vertex 4 to vertex 3
 * So Side 2 on its primary Hex tile goes from Vert 1 to 2 while it is Side 5 on its secondary Hex tile and goes from Vertex 5 to vertex 4
 * So Side 3 on its primary Hex tile goes from Vert 2 to 3 while it is Side 4 on its secondary Hex tile and goes from Vertex 6 to vertex 4 */
trait HSide extends HCenOrSide with TSide
{ override def typeStr: String = "HSide"

  def isTypeA: Boolean
  def isTypeB: Boolean
  def isTypeC: Boolean

  /** Returns the hex coordinate Line segment for this Hex Side. */
  def lineSegHC: LineSegHC

  /** Returns the upper vertex of this hex side. */
  def vert1: HVert

  /** Returns the lower vertex of this hex side. */
  def vert2: HVert

  /** Returns the 2 adjacent [[HCen]] coordinates of this hex Side. Both tiles may not exist in the [[HGridSysy]].  */
  def unsafeTiles: (HCen, HCen)

  def tileLt(implicit sys: HGridSys): HCen = sys.sideTileLtUnsafe(this)
  def tileRt(implicit sys: HGridSys): HCen = sys.sideTileRtUnsafe(this)

  /** Not precisely sure what this method is doing. */
  def tileLtAndVert: (HCen, Int)
  def tileLtAndVertUnsafe(implicit gSys: HGridSys) = gSys.sideTileLtAndVertUnsafe(this)

  /** Not precisely sure what this method is doing. */
  def tileRtAndVert: (HCen, Int)

  def tileLtOpt(implicit sys: HGridSys): Option[HCen] = sys.sideTileLtOpt(this)

  def tileRtOpt(implicit sys: HGridSys): Option[HCen] = sys.sideTileRtOpt(this)

  /** The left tile in the [[HGrid]]. It may not exist in the [[HGridSys]], but guaranteed to exist if the side is a link / inner side of an [[HGrid]]. */
  def tileLtReg: HCen

  /** The right tile in the [[HGrid]]. It may not exist in the [[HGridSys]] but guaranteed to exist if the side is a link / inner side of an [[HGrid]]. */
  def tileRtReg: HCen

  def corners(implicit sys: HGridSys): (HCen, Int, Int)
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

  /** Implicit [[ArrMapBuilder]] type class instance / evidence for [[HSide]] and [[HSideArr]]. */
  implicit val buildEv: Int2ArrMapBuilder[HSide, HSideArr] = new Int2ArrMapBuilder[HSide, HSideArr]
  { type BuffT = HSideBuff
    override def fromIntArray(array: Array[Int]): HSideArr = new HSideArr(array)
    override def fromIntBuffer(buffer: ArrayBuffer[Int]): HSideBuff = new HSideBuff(buffer)
  }
}

/** A hex side that slants down from left to right. r.div4Rem1 & c.div4Rem1 | r.div4Rem3 & c.div4Rem3 */
class HSideA(val r: Int, val c: Int) extends HSide
{ override def isTypeA: Boolean = true
  override def isTypeB: Boolean = false
  override def isTypeC: Boolean = false
  override def vert1: HVert = HVert(r, c - 1)
  override def vert2: HVert = HVert(r, c + 1)
  override def tileLtReg: HCen = HCen(r - 1, c - 1)
  override def tileRtReg: HCen = HCen(r + 1, c + 1)
  override def tileLtAndVert: (HCen, Int) = (HCen(r - 1, c - 1), 0)
  override def tileRtAndVert: (HCen, Int) = (HCen(r + 1, c + 1), 4)
  override def lineSegHC: LineSegHC = LineSegHC(r, c - 1, r, c + 1)
  override def unsafeTiles: (HCen, HCen) = (HCen(r - 1, c - 1), HCen(r + 1, c + 1))
  override def corners(implicit sys: HGridSys): (HCen, Int, Int) =  ife(sys.hCenExists(tileLt), (tileLt, 0, 1), (tileRt, 3, 4))
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
  override def vert1: HVert = HVert(r + 1, c)
  override def vert2: HVert = HVert(r - 1, c)
  override def tileLtReg: HCen = HCen(r, c - 2)
  override def tileRtReg: HCen = HCen(r, c + 2)
  override def tileLtAndVert: (HCen, Int) = (HCen(r, c - 2), 1)
  override def tileRtAndVert: (HCen, Int) = (HCen(r, c + 2), 5)
  override def lineSegHC: LineSegHC = LineSegHC(r + 1, c, r - 1, c)
  override def unsafeTiles: (HCen, HCen) = (HCen(r, c - 2), HCen(r, c + 2))
  override def corners(implicit sys: HGridSys): (HCen, Int, Int) = ife(sys.hCenExists(tileRt), (tileRt, 4, 5), (tileLt, 1, 2))
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
  override def vert1: HVert = HVert(r, c + 1)
  override def vert2: HVert = HVert(r, c - 1)
  override def tileLtReg: HCen = HCen(r + 1, c - 1)
  override def tileRtReg: HCen = HCen(r - 1, c + 1)
  override def tileLtAndVert: (HCen, Int) = (HCen(r + 1, c - 1), 2)
  override def tileRtAndVert: (HCen, Int) = (HCen(r - 1, c + 1), 0)
  override def lineSegHC: LineSegHC = LineSegHC(r, c + 1, r, c - 1)
  override def unsafeTiles: (HCen, HCen) = (HCen(r + 1, c - 1), HCen(r - 1, c + 1))
  override def corners(implicit sys: HGridSys): (HCen, Int, Int) = ife(sys.hCenExists(tileRt),(tileRt, 5, 0),  (tileLt, 2, 3))
}

object HSideC
{
  def unapply(inp: Any): Option[(Int, Int)] = inp match
  { case hs: HSideC => Some(hs.r, hs.c)
    case _ => None
  }
}