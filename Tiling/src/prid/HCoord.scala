/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package prid
import geom._

/** A coordinate with in a Hex grid. It may be a Hex tile centre [[HCen]], a HexSide [[HSide]] or Hex tile vertice [[HVert]]. */
trait HCoord extends TileCoord
{ def toVec2: Vec2
  @inline def xRatio: Double = HGrid.xRatio
}

object HCoord
{
  def apply(r: Int, c: Int): HCoord = r %% 4 match
  { case 0 if c.div4Rem0 => new HCen(r, c)
    case 2 if c.div4Rem2 => new HCen(r, c)
    case 0 if c.div4Rem0 => new HSide(r, c)
    case 1 | 3 if c.isOdd => new HSide(r, c)
    case 2 if c.div4Rem2 => new HSide(r, c)
    case _ if r.isOdd & c.isEven => new HVert(r, c)
    case _ => excep(s"$r, $c is not a valid Hex Grid coordinate.")
  }
}

trait HCoordReg extends HCoord
{ override def toVec2: Vec2 = Vec2(c * xRatio, r)
}

/** A Hex tile centre HexGrid coordinate. */
class HCen(val r: Int, val c: Int) extends HCoordReg

object HCen
{
  def apply(r: Int, c: Int): HCen = r %% 4 match
  { case 0 if c.div4Rem0 => new HCen(r, c)
    case 2 if c.div4Rem2 => new HCen(r, c)
    case _ => excep(s"$r, $c is not a valid Hex centre tile coordinate.")
  }
}

/** A Hex side coordinate in a Hex Grid.
 * So Side 1 on its primary Hex tile goes from Vert 6 to 1 while it is Side 4 on its secondary Hex tile and goes from Vertex 4 to vertex 3
 * So Side 2 on its primary Hex tile goes from Vert 1 to 2 while it is Side 5 on its secondary Hex tile and goes from Vertex 5 to vertex 4
 * So Side 3 on its primary Hex tile goes from Vert 2 to 3 while it is Side 4 on its secondary Hex tile and goes from Vertex 6 to vertex 4 */
class HSide(val r: Int, val c: Int) extends HCoordReg
{
  /** Returns the Hex coordinate Line segment for this Hex Side.  */
  def coordLine: HCoordLineSeg = r % 4 match
  { case 3 => HCoordLineSeg(r, c - 1, r, c + 1)
    case 2 => HCoordLineSeg(r + 1, c, r - 1, c)
    case 1 => HCoordLineSeg(r, c + 1, r, c - 1)
  }
}

/** Companion object for the HSide class, provides an apply factory method that throws an exception for an invalid Hex side coordinate. */
object HSide
{ /** Factory method for HSide that throws an exception for an invalid Hex side coordinate. */
  def apply(r: Int, c: Int): HSide = r %% 4 match
  { case 0 if c.div4Rem0 => new HSide(r, c)
    case 1 | 3 if c.isOdd => new HSide(r, c)
    case 2 if c.div4Rem2 => new HSide(r, c)
    case _ => excep(s"$r, $c is not a valid Hex edge tile coordinate.")
  }
}

class HVert(val r: Int, val c: Int) extends HCoord
{
  override def toVec2: Vec2 = (r %% 4, c %% 4) match
  {
    case (1, 0) | (3, 2)  =>  Vec2(c * xRatio, r + HGrid.yDist / 2)
    case _ => Vec2(c * xRatio, r - HGrid.yDist / 2)
  }
}

object HVert
{
  def apply(r: Int, c: Int): HVert = if (r.isOdd & c.isEven) new HVert(r, c) else excep(s"$r, $c is not a valid Hex vertex tile coordinate.")
}

trait HexMem[A]
{ val hc: HCen
  val value: A
}