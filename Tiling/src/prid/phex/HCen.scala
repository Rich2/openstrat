/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, Colour.Black

/** A Hex tile centre hex grid [[HGrid]] coordinate. */
class HCen(val r: Int, val c: Int) extends HCenOrSide with TileCen
{
  override def equals(that: Any): Boolean = that match {
    case that: HCen if r == that.r & c == that.c => true
    case _ => false
    }

  def v1: HVert = HVert(1, 2)
  def v2: HVert = HVert(-1, 2)
  def v3: HVert = HVert(-1, 0)
  def v4: HVert = HVert(-1, -2)
  def v5: HVert = HVert(1, -2)
  def v6: HVert = HVert(1, 0)

  /** The vertex sequence in [[HVert]] coordinates. This starts with the upper right vertex and proceeds clockwise to the upper vertex. */
  def verts: HVerts = HCen.vertsOfHex00.map(hv => hv + this)

  /** The polygon of this tile, specified in [[HVert]] coordinates. */
  def hVertPolygon: PolygonHC = verts.toPolygon

  /** The polygon of this hex tile if it is part of a regular grid. */
  def polygonReg: Polygon = verts.mapPolygon(_.toPt2Reg)

  def fill(colour: Colour): PolygonFill = polygonReg.fill(colour)
  def active(id: AnyRef = this): PolygonActive = polygonReg.active(id)
  override def typeStr: String = "HCen"

  /** Step to adjacent hex tile. Will throw exception on illegal value. */
  def step(st: HStep): HCen = HCen(r + st.r, c + st.c)

  /** Step to adjacent hex tile. */
  def stepOpt(st: HStep)(implicit grider: HGrider): Option[HCen] = {
    val target = HCen(r + st.r, c + st.c)
    ife(grider.hCenExists(target), Some(target), None)
  }

  /** Returns a coordinate for this hex along with a step to an adjacent hex. */
  def andStep(hcs: HStep): HexAndStep = HexAndStep(r, c, hcs)

  /** Optionally returns the Step value of the HCen if it is an adjacent HCen else returns the safe null value. */
  def findStep(operand: HCen): OptRef[HStep] = hcSteps.optFind(_.hCen == operand - this)

  def -(operand: HCen): HCen = HCen(r - operand.r, c - operand.c)

  def text32(fontSize: Double = 12, colour: Colour = Black) = this.strComma.toTextGraphic(fontSize, toPt2Reg, colour)
  def decText(fontSize: Double = 12, colour: Colour = Black) = this.rcStr.toTextGraphic(fontSize, toPt2Reg, colour)

  def neibs: HCens = HCen.neibs00.map(n => HCen(r + n.r, c + n.c))
}

/** Companion object of HCen trait, contains HVert values for hex tile 0, 0. As well as apply method and Show implicit. */
object HCen
{
  def apply(r: Int, c: Int): HCen = r %% 4 match
  { case 0 if c.div4Rem0 => new HCen(r, c)
    case 2 if c.div4Rem2 => new HCen(r, c)
    case _ => debexc(s"$r, $c is not a valid Hex centre tile coordinate.")
  }

  def unapply(input: HCoord): Option[(Int, Int)] = input match
  { case hc: HCen => Some((hc.r, hc.c))
    case _ => None
  }

  val h00v1: HVert = HVert(1, 2)
  val h00v2: HVert = HVert(-1, 2)
  val h00v3: HVert = HVert(-1, 0)
  val h00v4: HVert = HVert(-1, -2)
  val h00v5: HVert = HVert(1, -2)
  val h00v6: HVert = HVert(1, 0)
  val vertsOfHex00: HVerts = HVerts(h00v1, h00v2, h00v3, h00v4, h00v5, h00v6)

  val neibs00: HCens = HCens(HCen(2, 2), HCen(0, 4), HCen(-2, 2), HCen(-2, -2), HCen(0, -4), HCen(2, -2))

  /** implicit [[Persist]] instance / evidence for [[HCen]]. */
  implicit val persistEv: Persist[HCen] = new PersistShowInt2[HCen]("HCen", "r", "c", HCen(_, _))

  /** Implicit [[ArrBuilder]] type class instance / evidence for [[HCen]] and [[HCens]]. */
  implicit val buildEv: ArrInt2sBuilder[HCen, HCens] = new ArrInt2sBuilder[HCen, HCens]
  { type BuffT = HCenBuff
    override def fromIntArray(array: Array[Int]): HCens = new HCens(array)
    override def fromIntBuffer(inp: Buff[Int]): HCenBuff = new HCenBuff(inp)
  }

  implicit val linePathbuildEv: LinePathInt2sBuilder[HCen, LinePathHC] = new LinePathInt2sBuilder[HCen, LinePathHC]{
    override type BuffT = HCenBuff
    override def fromIntArray(array: Array[Int]): LinePathHC = new LinePathHC(array)
    override def fromIntBuffer(inp: Buff[Int]): HCenBuff = new HCenBuff(inp)
  }
}

/** Not sure about this trait for occupants of a hex tile. */
trait HexMem[A]
{ val hc: HCen
  val value: A
}

object HexMem
{
  def apply[A](hc: HCen, value: A): HexMem[A] = HexMemImp[A](hc, value)
  case class HexMemImp[A](hc: HCen, value: A) extends HexMem[A]
}

trait HexMemShow[A] extends HexMem[A] with Show2[HCen, A]
{ override def show1: HCen = hc
  override def name1: String = "hCen"
  override implicit def showT1: ShowT[HCen] = HCen.persistEv
  override def show2: A = value
}