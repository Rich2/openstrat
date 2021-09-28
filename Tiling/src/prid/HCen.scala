/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid
import geom._, Colour.Black

/** A Hex tile centre hexG grid [[HGrid]] coordinate. */
class HCen(val r: Int, val c: Int) extends HCenOrSide
{
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
  def polygonReg: Polygon = verts.map(_.toPt2).toPolygon

  def fill(colour: Colour): PolygonFill = polygonReg.fill(colour)
  def active(id: Any = this): PolygonActive = polygonReg.active(id)
  override def typeStr: String = "HCen"

  /** Step to adjacent hex tile. */
  def step(st: HStep): HCen = HCen(r + st.r, c + st.c)

  /** Returns a co0rdinate for this hex along with a step to an adjacent hex. */
  def andStep(hcs: HStep): HexAndStep = HexAndStep(r, c, hcs)

  /** Optionally returns the Step value of the HCen if it is an adjacent HCen. */
  def optStep(operand: HCen): OptRef[HStep] = hcStepSomes.optFind(_.hCen == operand - this)

  def -(operand: HCen): HCen = HCen(r - operand.r, c - operand.c)

  def text32(fontSize: Double = 12, colour: Colour = Black) = this.strComma.toTextGraphic(fontSize, toPt2, colour)
  def decText(fontSize: Double = 12, colour: Colour = Black) = this.rcStr.toTextGraphic(fontSize, toPt2, colour)
}

/** Companion object of HCen trait, contains HVert values for hex tile 0, 0. As well as apply method and Show implicit. */
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
    override def showT(obj: HCen, way: Show.Way, maxPlaces: Int, minPlaces: Int): String = obj.show(way, maxPlaces, 0)
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

  implicit val hCensBuildImplicit: ArrInt2sBuilder[HCen, HCens] = new ArrInt2sBuilder[HCen, HCens]
  { type BuffT = HCenBuff
    override def fromIntArray(array: Array[Int]): HCens = new HCens(array)
    override def fromIntBuffer(inp: Buff[Int]): HCenBuff = new HCenBuff(inp)
  }
}

trait HexMem[A]
{ val hc: HCen
  val value: A
}