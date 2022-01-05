/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid
import geom._

trait SqBaseCoord extends Any with TileCoord
{ override def toVec: Vec2 = Vec2(c, r)
  override def toPt2: Pt2 = Pt2(c, r)
}

/** A square grid integer tile coordinate. */
trait SqCoord extends Any with SqBaseCoord

object SqCoord
{ /** Apply factory method for [[SqCoord]] trait, returns a [[SqCen]], [[SqSide]] or [[SqVert]] depending on the coordinates. */
  def apply(r: Int, c: Int): SqCoord = None match {
    case _ if r.isEven & c.isEven => SqCen(r, c)
    case _ if r.isOdd & c.isOdd => SqSide(r, c)
    case _ => SqVert(r, c)
  }
}

trait SqCenOrSide extends Any with SqCoord with TileCenOrSide

object SqCenOrSide
{
  def apply(r: Int, c:Int): SqCenOrSide = r match {
    case r if r.isEven & c.isEven => new SqCen(r, c)
    case r if r.isEven => new SqSide(r, c)
    case r if c.isEven => new SqSide(r, c)
    case _ => debexc(s"$r, $c is not a valid SqCenOrSide coordinate it is a SqVert Square grid vertex coordinate.")
  }
}

/** A Square tile centre square grid [[SqGrid]] coordinate. */
case class SqCen(val r: Int, val c: Int) extends SqCenOrSide with TileCen
{ override def typeStr: String = "Sqcen"

  def v1: SqVert = SqVert(1, 1)
  def v2: SqVert = SqVert(-1, 1)
  def v3: SqVert = SqVert(-1, -1)
  def v4: SqVert = SqVert(-1, -2)


  /** The vertex sequence in [[HVert]] coordinates. This starts with the upper right vertex and proceeds clockwise to the upper vertex. */
  def verts: SqVerts =
  {
    val res = SqCen.vertsOfSq00.map(sv => sv + this)
    res
  }

  /** Step to adjacent hex tile. Will throw exception on illegal value. */
  def step(st: SqStep): SqCen = SqCen(r + st.r, c + st.c)

  /** Optionally returns the Step value of the SqCen if it is an adjacent SqCen. */
  def optStep(operand: SqCen): OptRef[SqStep] = ??? // hcStepSomes.optFind(_.hCen == operand - this)

  /** Optionally returns the Step value of the SqCen if it is an adjacent SqCen else returns the safe null value. */
  def findStep(operand: SqCen): OptRef[SqStep] = scSteps.optFind(_.sqCen == operand - this)

  def -(operand: SqCen): SqCen = SqCen(r - operand.r, c - operand.c)

  /** Step to adjacent hex tile. */
  def stepOpt(st: SqStep)(implicit grid: SqGrid): Option[SqCen] = {
    val target = SqCen(r + st.r, c + st.c)
    ife(grid.sqCenExists(target), Some(target), None)
  }

  /** The polygon of this hex tile if it is part of a regular grid. */
  def polygonReg: Polygon = verts.map(_.toPt2).toPolygon

  def fill(colour: Colour): PolygonFill = polygonReg.fill(colour)

  def active(id: AnyRef = this): PolygonActive = polygonReg.active(id)
}

object SqCen
{
  implicit val persistImplicit: Persist[SqCen] = new PersistShowInt2[SqCen]("SqCen", "r", "c", SqCen(_, _))

  val s00v1: SqVert = SqVert(1, 1)
  val s00v2: SqVert = SqVert(-1, 1)
  val s00v3: SqVert = SqVert(-1, -1)
  val s00v4: SqVert = SqVert(-1, 1)

  val vertsOfSq00: SqVerts = SqVerts(s00v1, s00v2, s00v3, s00v4)
}

/** A Square tile side square grid [[SqGrid]] coordinate. */
class SqSide(val r: Int, val c: Int) extends SqCenOrSide with TileSide
{ override def typeStr: String = "Sqside"
}

/** A Square tile vertex square grid [[SqGrid]] coordinate. */
class SqVert private(val bLong: Long) extends AnyVal with SqCoord with TileCoord
{ override def typeStr: String = "Sqvert"
  @inline def r: Int = bLong.>>(32).toInt
  @inline def c: Int = bLong.toInt

  def + (sqCen: SqCen): SqVert = SqVert(r + sqCen.r, c + sqCen.c)
}

object SqVert
{ val showTImplicit: ShowT[SqVert] = Show2Base32sT("Sqvert")
  def apply(r: Int, c: Int): SqVert = if (r.isOdd & c.isOdd)
    new SqVert(r.toLong.<<(32) | (c & 0xFFFFFFFFL))
  else excep(s"$r, $c is not a valid Hex vertex tile coordinate.")

  implicit val sqVertsBuildImplicit: ArrInt2sBuilder[SqVert, SqVerts] = new ArrInt2sBuilder[SqVert, SqVerts]
  { type BuffT = SqVertBuff
    override def fromIntArray(array: Array[Int]): SqVerts = new SqVerts(array)
    override def fromIntBuffer(inp: Buff[Int]): SqVertBuff = new SqVertBuff(inp)
  }
}

trait SqMem[A]
{ val sc: SqCen
  val value: A
}
