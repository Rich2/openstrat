/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, Colour.Black

/** A Hex side coordinate in a Hex Grid.
 * So Side 1 on its primary Hex tile goes from Vert 6 to 1 while it is Side 4 on its secondary Hex tile and goes from Vertex 4 to vertex 3
 * So Side 2 on its primary Hex tile goes from Vert 1 to 2 while it is Side 5 on its secondary Hex tile and goes from Vertex 5 to vertex 4
 * So Side 3 on its primary Hex tile goes from Vert 2 to 3 while it is Side 4 on its secondary Hex tile and goes from Vertex 6 to vertex 4 */
class HSide(val r: Int, val c: Int) extends HCenOrSide with TileSide
{ override def typeStr: String = "HSide"

  /** Returns the Hex coordinate Line segment for this Hex Side.  */
  def coordLine: LineSegHC = r %% 4 match
  { case 3 => LineSegHC(r, c - 1, r, c + 1)
    case 0 | 2 => LineSegHC(r + 1, c, r - 1, c)
    case 1 => LineSegHC(r, c + 1, r, c - 1)
  }

  /** Returns the 2 adjacent [[HCen]]s of this hex Side.  */
  def tiles: (HCen, HCen) = r %% 4 match
  { case 0 | 2 => (HCen(r, c - 2), HCen(r, c + 2))
    case 1 if c.div4Rem3 => (HCen(r - 1, c + 1), HCen(r + 1, c - 1))
    case 3 if c %% 4 == 1 => (HCen(r - 1, c + 1), HCen(r + 1, c - 1))
    case 1 if c %% 4 == 1 => (HCen(r - 1, c - 1), HCen(r + 1, c + 1))
    case 3 if c %% 4 == 3 => (HCen(r - 1, c - 1), HCen(r + 1, c + 1))
    case _ => excep("Invalid hex side.")
  }

  /** Returns a [[LineSeg]] for a regular Hex tile grid. */
  def lineSeg: LineSeg = coordLine.lineSeg

  /** Draws a [[LineSeg]], returning a [[LineSegDraw]] for a regular Hex tile grid. */
  def draw(colour: Colour= Black, lineWidth: Double = 2) : LineSegDraw = lineSeg.draw(colour, lineWidth)
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

  /** Implicit [[ArrBuilder]] type class instance / evidence for [[HSide]] and [[HSides]]. */
  implicit val buildEv: ArrInt2sBuilder[HSide, HSides] = new ArrInt2sBuilder[HSide, HSides]
  { type BuffT = HSideBuff
    override def fromIntArray(array: Array[Int]): HSides = new HSides(array)
    override def fromIntBuffer(inp: Buff[Int]): HSideBuff = new HSideBuff(inp)
  }
}