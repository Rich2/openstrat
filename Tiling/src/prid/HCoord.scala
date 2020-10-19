/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package prid

trait HCoord
{ def r: Int
  def c: Int
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

class HEdge(val r: Int, val c: Int) extends HCoord

object HEdge
{
  def apply(r: Int, c: Int): HEdge = r %% 4 match
  { case 0 if c.div4Rem0 => new HEdge(r, c)
    case 1 | 3 if c.isOdd => new HEdge(r, c)
    case 2 if c.div4Rem2 => new HEdge(r, c)
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

