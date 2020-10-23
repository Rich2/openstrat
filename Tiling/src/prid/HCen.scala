/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package prid
import geom._

/** A Hex tile centre HexGrid coordinate. */
class HCen(val r: Int, val c: Int) extends HCoordReg
{
  def v1: HVert = HVert(1, 2)
  def v2: HVert = HVert(-1, 2)
  def v3: HVert = HVert(-1, 0)
  def v4: HVert = HVert(-1, -2)
  def v5: HVert = HVert(1, -2)
  def v6: HVert = HVert(1, 0)
  def verts: HVerts = HCen.vertsOfHex00.map(hv => hv + this)
  def polygon: Polygon = verts.map(_.toVec2).toPolygon
  def fill(colour: Colour): PolygonFill = polygon.fill(colour)
  def active(id: Any = this): PolygonActive = polygon.active(id)
  override def typeStr: String = "HCen"
}

object HCen
{
  def apply(r: Int, c: Int): HCen = r %% 4 match
  { case 0 if c.div4Rem0 => new HCen(r, c)
    case 2 if c.div4Rem2 => new HCen(r, c)
    case _ => excep(s"$r, $c is not a valid Hex centre tile coordinate.")
  }

  val h00v1: HVert = HVert(1, 2)
  val h00v2: HVert = HVert(-1, 2)
  val h00v3: HVert = HVert(-1, 0)
  val h00v4: HVert = HVert(-1, -2)
  val h00v5: HVert = HVert(1, -2)
  val h00v6: HVert = HVert(1, 0)
  val vertsOfHex00: HVerts = HVerts(h00v1, h00v2, h00v3, h00v4, h00v5, h00v6)
}

trait HexMem[A]
{ val hc: HCen
  val value: A
}
