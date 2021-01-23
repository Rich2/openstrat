/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package prid
import geom._

/** A Hex tile centre HexGrid coordinate. */
class Hcen(val r: Int, val c: Int) extends HCoordReg
{
  def v1: HVert = HVert(1, 2)
  def v2: HVert = HVert(-1, 2)
  def v3: HVert = HVert(-1, 0)
  def v4: HVert = HVert(-1, -2)
  def v5: HVert = HVert(1, -2)
  def v6: HVert = HVert(1, 0)
  def verts: HVerts = Hcen.vertsOfHex00.map(hv => hv + this)
  def polygon: HexYlign = HexYlign(2, toPt2)
  def fill(colour: Colour): PolygonFill = polygon.fill(colour)
  def active(id: Any = this): PolygonActive = polygon.active(id)
  override def typeStr: String = "HCen"

  /** Step to adjacent hex tile. */
  def step(st: HcenStep): Hcen = Hcen(r + st.r, c + st.c)

  /** Returns a co0rdinate for this hex along with a step to an adjacent hex. */
  def andStep(hcs: HcenStep): HCAndStep = HCAndStep(r, c, hcs)

  /** Optionally returns the Step value of the HCen if it is an adjacent HCen. */
  def optStep(operand: Hcen): OptRef[HcenStep] = hcStepSomes.optFind(_.hcen == operand - this)
  def -(operand: Hcen): Hcen = Hcen(r - operand.r, c - operand.c)
}

object Hcen
{
  def apply(r: Int, c: Int): Hcen = r %% 4 match
  { case 0 if c.div4Rem0 => new Hcen(r, c)
    case 2 if c.div4Rem2 => new Hcen(r, c)
    case _ => excep(s"$r, $c is not a valid Hex centre tile coordinate.")
  }

  def unapply(input: HCoord): Option[(Int, Int)] = input match {
    case hc: Hcen => Some((hc.r, hc.c))
    case _ => None
  }

  val h00v1: HVert = HVert(1, 2)
  val h00v2: HVert = HVert(-1, 2)
  val h00v3: HVert = HVert(-1, 0)
  val h00v4: HVert = HVert(-1, -2)
  val h00v5: HVert = HVert(1, -2)
  val h00v6: HVert = HVert(1, 0)
  val vertsOfHex00: HVerts = HVerts(h00v1, h00v2, h00v3, h00v4, h00v5, h00v6)

  implicit val showImplicit: ShowT[Hcen] = new ShowT[Hcen]
  { override def typeStr: String = "HCen"
    override def strT(obj: Hcen): String = obj.str
    override def showT(obj: Hcen, way: Show.Way, decimalPlaces: Int): String = obj.show(way, decimalPlaces)
    override def syntaxDepthT(obj: Hcen): Int = 2
  }
}

trait HexMem[A]
{ val hc: Hcen
  val value: A
}
