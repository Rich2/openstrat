/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import collection.mutable.ArrayBuffer

/** A Hex side coordinate in a Hex Grid.
 * So Side 1 on its primary Hex tile goes from Vert 6 to 1 while it is Side 4 on its secondary Hex tile and goes from Vertex 4 to vertex 3
 * So Side 2 on its primary Hex tile goes from Vert 1 to 2 while it is Side 5 on its secondary Hex tile and goes from Vertex 5 to vertex 4
 * So Side 3 on its primary Hex tile goes from Vert 2 to 3 while it is Side 4 on its secondary Hex tile and goes from Vertex 6 to vertex 4 */
trait HSide extends HCenOrSide with TSide
{ override def typeStr: String = "HSide"

  def vSide[B](vUR: => B, vRt: => B, vDR: => B): B = r %% 4 match
  { case 1 if c.div4Rem1 => vUR
    case 3 if c.div4Rem3 => vUR
    case 0 if c.div4Rem2 => vRt
    case 2 if c.div4Rem0 => vRt
    case 1 if c.div4Rem3 => vDR
    case 3 if c.div4Rem1 => vDR
    case _ => excep(s"$r, $c is an invalid HSide coordinate.")
  }

  def istypeB: Boolean = r.div4Rem0 & c.div4Rem2 | r.div4Rem2 & c.div4Rem0

  /** Returns the hex coordinate Line segment for this Hex Side. */
  def lineSegHC: LineSegHC

  /** Returns the upper vertex of this hex side. */
  def vert1: HVert

  /** Returns the lower vertex of this hex side. */
  def vert2: HVert

  /** Returns the 2 adjacent [[HCen]] coordinates of this hex Side. Both tiles may not exist in the [[HGridSysy]].  */
  def unsafeTiles: (HCen, HCen)

  def tile1(implicit sys: HGridSys): HCen = sys.sideTile1(this)
  def tile2(implicit sys: HGridSys): HCen = sys.sideTile2(this)

  /** Not precisely sure what this method is doing. */
  def tile1AndVert: (HCen, Int)

  /** Not precisely sure what this method is doing. */
  def tile2AndVert: (HCen, Int)

  def tile1Opt(implicit sys: HGridSys): Option[HCen] = sys.sideTile1Opt(this)

  def tile2Opt(implicit sys: HGridSys): Option[HCen] = sys.sideTile2Opt(this)

  /** Tile 1 if the side is a link / inner side of an [[HGrid]]. */
  def tile1Reg: HCen

  /** Tile 2 if the side is a link  / inner side of an [[HGrid]]. */
  def tile2Reg: HCen

  def corners(implicit sys: HGridSys): (HCen, Int, Int) =
  { val t1 = tile1
    val t2 = tile2
    if (sys.hCenExists(t1)) vSide((t1, 0, 1), (t1, 1, 2), (t1, 2, 3))
    else vSide((t2, 3, 4), (t2, 4, 5), (t2, 5, 0))
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

    case _ => excep(s"$r, $c is not a valid Hex edge tile coordinate.")
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

/** A hex side that slants down from left to right. */
class HSideA(val r: Int, val c: Int) extends HSide
{
  override def vert1: HVert = HVert(r, c - 1)
  override def vert2: HVert = HVert(r, c + 1)
  override def tile1Reg: HCen = HCen(r - 1, c - 1)
  override def tile2Reg: HCen = HCen(r + 1, c + 1)
  override def tile1AndVert: (HCen, Int) = (HCen(r - 1, c - 1), 0)
  override def tile2AndVert: (HCen, Int) = (HCen(r + 1, c + 1), 4)
  override def lineSegHC: LineSegHC = LineSegHC(r, c - 1, r, c + 1)
  override def unsafeTiles: (HCen, HCen) = (HCen(r - 1, c - 1), HCen(r + 1, c + 1))
}

/** A hex side that slants straight down. */
class HSideB(val r: Int, val c: Int) extends HSide
{
  override def vert1: HVert = HVert(r + 1, c)
  override def vert2: HVert = HVert(r - 1, c)
  override def tile1Reg: HCen = HCen(r, c - 2)
  override def tile2Reg: HCen = HCen(r, c + 2)
  override def tile1AndVert: (HCen, Int) = (HCen(r, c - 2), 1)
  override def tile2AndVert: (HCen, Int) = (HCen(r, c + 2), 5)
  override def lineSegHC: LineSegHC = LineSegHC(r + 1, c, r - 1, c)
  override def unsafeTiles: (HCen, HCen) = (HCen(r, c - 2), HCen(r, c + 2))
}

/** A hex side that slants down form top right to bottom left. */
class HSideC(val r: Int, val c: Int) extends HSide
{
  override def vert1: HVert = HVert(r, c + 1)
  override def vert2: HVert = HVert(r, c - 1)
  override def tile1Reg: HCen = HCen(r + 1, c - 1)
  override def tile2Reg: HCen = HCen(r - 1, c + 1)
  override def tile1AndVert: (HCen, Int) = (HCen(r + 1, c - 1), 2)
  override def tile2AndVert: (HCen, Int) = (HCen(r - 1, c + 1), 0)
  override def lineSegHC: LineSegHC = LineSegHC(r, c + 1, r, c - 1)
  override def unsafeTiles: (HCen, HCen) = (HCen(r + 1, c - 1), HCen(r - 1, c + 1))
}