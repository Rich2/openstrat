/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, Colour.Black, collection.mutable.ArrayBuffer

/** A Hex tile centre hex grid [[HGrid]] coordinate. */
class HCen(val r: Int, val c: Int) extends HCenOrSide with TCen
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
  def verts: HVertArr = HCen.vertsOfHex00.map(hv => hv + this)

  /** The polygon of this tile, specified in [[HVert]] coordinates. */
  def hVertPolygon: PolygonHC = verts.toPolygon

  /** The polygon of this hex tile if it is part of a regular grid. */
  def polygonReg: Polygon = verts.mapPolygon(_.toPt2Reg)

  def fill(colour: Colour): PolygonFill = polygonReg.fill(colour)
  def active(id: AnyRef = this): PolygonActive = polygonReg.active(id)
  override def typeStr: String = "HCen"

  /** Step to adjacent hex tile. Will throw exception on illegal value. */
  def unsafeStep(st: HDirn)(implicit grider: HGridSys): HCen = grider.unsafeStepEnd(this, st)// HCen(r + st.r, c + st.c)

  /** Step to adjacent hex tile. */
  def stepOpt(st: HDirn)(implicit grider: HGridSys): Option[HCen] = {

    /*val target =*/ grider.findStepEnd(this, st)//  HCen(r + st.r, c + st.c)
    //ife(grider.hCenExists(target), Some(target), None)
  }

  /** Returns a coordinate for this hex along with a step to an adjacent hex. */
  def andStep(hcs: HDirn): HCenStep = HCenStep(r, c, hcs)

  /** Returns the [[LineSegHC]], a line segment specified in [[HCoord]]s, given by the step. */
  def segStepTo(st: HDirn): LineSegHC = new LineSegHC(r, c, r + st.tr, c + st.tc)

  def -(operand: HCen): HCen = HCen(r - operand.r, c - operand.c)

 // def text32(fontSize: Double = 12, colour: Colour = Black) = this.strComma.toTextGraphic(fontSize, toPt2Reg, colour)
  def oldDecText(fontSize: Double = 12, colour: Colour = Black) = this.rcStr.toTextGraphic(fontSize, toPt2Reg, colour)

  def neibs: HCenArr = HCen.neibs00.map(n => HCen(r + n.r, c + n.c))
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
  val vertsOfHex00: HVertArr = HVertArr(h00v1, h00v2, h00v3, h00v4, h00v5, h00v6)

  val neibs00: HCenArr = HCenArr(HCen(2, 2), HCen(0, 4), HCen(-2, 2), HCen(-2, -2), HCen(0, -4), HCen(2, -2))

  /** implicit [[Persist]] instance / evidence for [[HCen]]. */
  implicit val persistEv: Persist[HCen] = new PersistShowInt2[HCen]("HCen", "r", "c", HCen(_, _))

  /** Implicit [[ArrMapBuilder]] type class instance / evidence for [[HCen]] and [[HCenArr]]. */
  implicit val buildEv: Int2ArrMapBuilder[HCen, HCenArr] = new Int2ArrMapBuilder[HCen, HCenArr]
  { type BuffT = HCenBuff
    override def fromIntArray(array: Array[Int]): HCenArr = new HCenArr(array)
    override def fromIntBuffer(buffer: ArrayBuffer[Int]): HCenBuff = new HCenBuff(buffer)
  }
}

/** An efficient array[Int] based collection for [[HCen]]s hex grid centre coordinates. */
class HCenArr(val unsafeArray: Array[Int]) extends AnyVal with Int2Arr[HCen]
{ type ThisT = HCenArr

  override def newElem(int1: Int, int2: Int): HCen = HCen(int1, int2)

  override def fromArray(array: Array[Int]): HCenArr = new HCenArr(array)

  override def typeStr: String = "HCens"

  override def fElemStr: HCen => String = _.toString

  /** Converts a sequence of adjacent hex tiles to hex directions. Only call this method if you are certain the tiles are adjacent. */
  def unsafeToHDirns(implicit grider: HGridSys): HDirnArr = ???
}

/** Companion object for [[HCenArr]] trait efficient array[Int] based collection for [[HCen]]s hex grid centre coordinates, contains factory apply and uninitialised methods.. */
object HCenArr extends Int2SeqLikeCompanion[HCen, HCenArr]
{
  //override def buff(initialSize: Int): HCenBuff = new HCenBuff(buffInt(initialSize * 2))
  def fromArray(array: Array[Int]): HCenArr = new HCenArr(array)

  implicit object PersistImplicit extends PersistArrInt2s[HCen, HCenArr]("HCens")
  { override def fromArray(value: Array[Int]): HCenArr = new HCenArr(value)

    override def showDecT(obj: HCenArr, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???
  }

  /** Implicit flatMap builder instance / evidence for [[HCenArr]]. */
  implicit val flatBuilderEv: Int2ArrFlatBuilder[HCenArr] = new Int2ArrFlatBuilder[HCenArr]
  { type BuffT = HCenBuff
    override def fromIntArray(array: Array[Int]): HCenArr = new HCenArr(array)
    override def fromIntBuffer(buffer: ArrayBuffer[Int]): HCenBuff = new HCenBuff(buffer)
  }
}

class HCenBuff(val unsafeBuffer: ArrayBuffer[Int] = BuffInt()) extends AnyVal with Int2Buff[HCen]
{ type ArrT = HCenArr
  override def typeStr: String = "HCenBuff"
  override def intsToT(i1: Int, i2: Int): HCen = HCen(i1, i2)
}