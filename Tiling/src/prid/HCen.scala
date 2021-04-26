/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid
import geom._

/** A Hex tile centre HexGrid coordinate. */
class HCen(val r: Int, val c: Int) extends HCenOrSide
{
  def v1: HVert = HVert(1, 2)
  def v2: HVert = HVert(-1, 2)
  def v3: HVert = HVert(-1, 0)
  def v4: HVert = HVert(-1, -2)
  def v5: HVert = HVert(1, -2)
  def v6: HVert = HVert(1, 0)
  def verts: HVerts = HCen.vertsOfHex00.map(hv => hv + this)
  def polygon: Polygon = verts.map(_.toPt2).toPolygon
  def fill(colour: Colour): PolygonFill = polygon.fill(colour)
  def active(id: Any = this): PolygonActive = polygon.active(id)
  override def typeStr: String = "HCen"

  /** Step to adjacent hex tile. */
  def step(st: HexStep): HCen = HCen(r + st.r, c + st.c)

  /** Returns a co0rdinate for this hex along with a step to an adjacent hex. */
  def andStep(hcs: HexStep): HexAndStep = HexAndStep(r, c, hcs)

  /** Optionally returns the Step value of the HCen if it is an adjacent HCen. */
  def optStep(operand: HCen): OptRef[HexStep] = hcStepSomes.optFind(_.hCen == operand - this)

  def -(operand: HCen): HCen = HCen(r - operand.r, c - operand.c)
}

object HCen
{
  def apply(r: Int, c: Int): HCen = r %% 4 match
  { case 0 if c.div4Rem0 => new HCen(r, c)
    case 2 if c.div4Rem2 => new HCen(r, c)
    case _ => excep(s"$r, $c is not a valid Hex centre tile coordinate.")
  }

  def unapply(input: HCoord): Option[(Int, Int)] = input match {
    case hc: HCen => Some((hc.r, hc.c))
    case _ => None
  }

  val h00v1: HVert = HVert(1, 2)
  val h00v2: HVert = HVert(-1, 2)
  val h00v3: HVert = HVert(-1, 0)
  val h00v4: HVert = HVert(-1, -2)
  val h00v5: HVert = HVert(1, -2)
  val h00v6: HVert = HVert(1, 0)
  val vertsOfHex00: HVerts = HVerts(h00v1, h00v2, h00v3, h00v4, h00v5, h00v6)

  implicit val showImplicit: ShowT[HCen] = new Show2T[Int, Int, HCen]
  { override def typeStr: String = "HCen"
    //override def strT(obj: HCen): String = obj.str
    override def showT(obj: HCen, way: Show.Way, decimalPlaces: Int): String = obj.show(way, decimalPlaces)
    //override def syntaxDepthT(obj: HCen): Int = 2

    override def name1: String = "r"

    override def fArg1: HCen => Int = _.r

    override def name2: String = "c"

    override def fArg2: HCen => Int = _.c

    override def opt2: Option[Int] = None

    override def opt1: Option[Int] = None

    override implicit def ev1: ShowT[Int] = ShowT.base32

    override implicit def ev2: ShowT[Int] = ShowT.base32
  }
}

trait HexMem[A]
{ val hc: HCen
  val value: A
}