/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import collection.mutable.ArrayBuffer

/** A Hex side coordinate in a Hex Grid.
 * So Side 1 on its primary Hex tile goes from Vert 6 to 1 while it is Side 4 on its secondary Hex tile and goes from Vertex 4 to vertex 3
 * So Side 2 on its primary Hex tile goes from Vert 1 to 2 while it is Side 5 on its secondary Hex tile and goes from Vertex 5 to vertex 4
 * So Side 3 on its primary Hex tile goes from Vert 2 to 3 while it is Side 4 on its secondary Hex tile and goes from Vertex 6 to vertex 4 */
class HSide(val r: Int, val c: Int) extends HCenOrSide with TSide
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

  def fHSide[B](fUR: (Int, Int) => B)(fRt: (Int, Int) => B)(fDR: (Int, Int) => B): B = r %% 4 match {
    case 1 if c.div4Rem1 => fUR(r, c)
    case 3 if c.div4Rem3 => fUR(r, c)
    case 0 if c.div4Rem2 => fRt(r, c)
    case 2 if c.div4Rem0 => fRt(r, c)
    case 1 if c.div4Rem3 => fDR(r, c)
    case 3 if c.div4Rem1 => fDR(r, c)
    case _ => excep(s"$r, $c is an invalid HSide coordinate.")
  }

  def isVertical: Boolean = r.div4Rem0 & c.div4Rem2 | r.div4Rem2 & c.div4Rem0

  /** Returns the hex coordinate Line segment for this Hex Side. */
  def lineSegHC: LineSegHC = fHSide((r, c) => LineSegHC(r, c - 1, r, c + 1))((r, c) => LineSegHC(r + 1, c, r - 1, c))((r, c) => LineSegHC(r, c + 1, r, c - 1))

  /** Returns the upper vertex of this hex side. */
  def vert1: HVert = vSide(HVert(r, c - 1), HVert(r + 1, c), HVert(r, c + 1))

  /** Returns the lower vertex of this hex side. */
  def vert2: HVert = vSide(HVert(r, c + 1), HVert(r - 1, c), HVert(r, c - 1))

  /** Returns the 2 adjacent [[HCen]] coordinates of this hex Side. Both tiles may not exist in the [[HGridSysy]].  */
  def unsafeTiles: (HCen, HCen) = fHSide{ (r, c) => (HCen(r - 1, c - 1), HCen(r + 1, c + 1)) }{ (r, c) => (HCen(r, c - 2), HCen(r, c + 2)) }{ (r, c) => (HCen(r + 1, c - 1), HCen(r - 1, c + 1)) }

  def tile1(implicit sys: HGridSys): HCen = sys.sideTile1(this)
  def tile2(implicit sys: HGridSys): HCen = sys.sideTile2(this)

  def tile1AndVert: (HCen, Int) = vSide((HCen(r - 1, c - 1), 0), (HCen(r, c - 2), 1), (HCen(r + 1, c - 1), 2))
  def tile2AndVert: (HCen, Int) = vSide((HCen(r + 1, c + 1), 4), (HCen(r, c + 2), 5), (HCen(r - 1, c + 1), 0))

  def tile1Opt(implicit sys: HGridSys): Option[HCen] = sys.sideTile1Opt(this)

  def tile2Opt(implicit sys: HGridSys): Option[HCen] = sys.sideTile2Opt(this)

  /** Tile 1 if the side is a link / inner side of an [[HGrid]]. */
  def tile1Reg: HCen = fHSide{ (r, c) => HCen(r - 1, c - 1) }{ (r, c) => HCen(r, c - 2) }{ (r, c) => HCen(r + 1, c - 1) }

  /** Tile 2 if the side is a link  / inner side of an [[HGrid]]. */
  def tile2Reg: HCen = fHSide{ (r, c) => HCen(r + 1, c + 1) }{ (r, c) => HCen(r, c + 2) }{ (r, c) => HCen(r - 1, c + 1) }

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
  { case 0 if c.div4Rem2 => new HSide(r, c)
    case 1 | 3 if c.isOdd => new HSide(r, c)
    case 2 if c.div4Rem0 => new HSide(r, c)
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