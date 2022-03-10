/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import geom._

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
    val res: SqVerts = SqCen.vertsOfSq00.map(sv => sv + this)
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
  def polygonReg: Polygon = verts.mapPolygon(_.toPt2)

  def fill(colour: Colour): PolygonFill = polygonReg.fill(colour)

  def active(id: AnyRef = this): PolygonActive = polygonReg.active(id)
}

object SqCen
{
  implicit val persistImplicit: Persist[SqCen] = new PersistShowInt2[SqCen]("SqCen", "r", "c", SqCen(_, _))

  val s00v1: SqVert = SqVert(1, 1)
  val s00v2: SqVert = SqVert(-1, 1)
  val s00v3: SqVert = SqVert(-1, -1)
  val s00v4: SqVert = SqVert(1, -1)

  val vertsOfSq00: SqVerts = SqVerts(s00v1, s00v2, s00v3, s00v4)

  /** Implicit [[ArrBuilder]] type class instance / evidence for [[SqCen]] and [[SqCens]]. */
  implicit val buildEv: ArrInt2sBuilder[SqCen, SqCens] = new ArrInt2sBuilder[SqCen, SqCens]
  { type BuffT = SqCenBuff
    override def fromIntArray(array: Array[Int]): SqCens = new SqCens(array)
    override def fromIntBuffer(inp: Buff[Int]): SqCenBuff = new SqCenBuff(inp)
  }
}

trait SqMem[A]
{ val sc: SqCen
  val value: A
}