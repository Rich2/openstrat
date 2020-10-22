/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package prid
import geom._

/** A coordinate with in a Hex grid. It may be a Hex tile centre [[HCen]], a HexSide [[HSide]] or Hex tile vertice [[HVert]]. */
trait HCoord extends TileCoord
{ def toVec2: Vec2 = ???
}

/** A 2d line upon a HexGrid defined by its start and end [[HGrid]] [[HCoord]]s. */
case class HoordLine(r1: Int, c1: Int, r2: Int, c2: Int) extends ProdInt4
{
  def _1: Int = r1
  def _2: Int = c1
  def _3: Int = r2
  def _4: Int = c2
//  def toLine2(f: Roord => Vec2): LineSeg =
//  { val v1 = f(Roord(y1, c1))
//    val v2 = f(Roord(y2, c2))
//    LineSeg(v1, v2)
//  }
  //def gridLine2(implicit grid: TileGrid): LineSeg = toLine2(grid.roordToVec2)
}

class HCen(val r: Int, val c: Int) extends HCoord

object HCen
{
  def apply(r: Int, c: Int): HCen = r %% 4 match
  { case 0 if c.div4Rem0 => new HCen(r, c)
    case 2 if c.div4Rem2 => new HCen(r, c)
    case _ => excep(s"$r, $c is not a valid Hex centre tile coordinate.")
  }
}

/** A Hex side coordinate in a Hex Grid. */
class HSide(val r: Int, val c: Int) extends HCoord

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

object HVert
{
  def apply(r: Int, c: Int): HVert = if (r.isOdd & c.isEven) new HVert(r, c) else excep(s"$r, $c is not a valid Hex vertex tile coordinate.")
}

trait HexMem[A]
{ val hc: HCen
  val value: A
}