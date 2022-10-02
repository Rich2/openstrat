/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import geom._, collection.mutable.ArrayBuffer

/** A Square tile centre square grid [[SqGrid]] coordinate. */
case class SqCen(r: Int, c: Int) extends SqCenOrSide with TCen
{ override def typeStr: String = "Sqcen"

  def v1: SqVert = SqVert(1, 1)
  def v2: SqVert = SqVert(-1, 1)
  def v3: SqVert = SqVert(-1, -1)
  def v4: SqVert = SqVert(-1, -2)

  /** The vertex sequence in [[SqVert]] coordinates. This starts with the upper right vertex and proceeds clockwise to the upper vertex. */
  def verts: SqVertArr = SqCen.vertsOfSq00.map(sv => sv + this)

  /** The polygon of this tile, specified in [[SqVert]] coordinates. */
  def sqVertPolygon: PolygonSqC = verts.toPolygon

  /** Step to adjacent hex tile. Will throw exception on illegal value. */
  def step(st: SqDirn): SqCen = SqCen(r + st.tr, c + st.tc)

  /** Optionally returns the Step value of the SqCen if it is an adjacent SqCen. */
  def optStep(operand: SqCen): OptRef[SqDirn] = ??? // hcStepSomes.optFind(_.hCen == operand - this)

  /** Optionally returns the Step value of the SqCen if it is an adjacent SqCen else returns the safe null value. */
  def findStep(operand: SqCen): Option[SqDirn] = scSteps.optFind(_.sqCen == operand - this)

  def -(operand: SqCen): SqCen = SqCen(r - operand.r, c - operand.c)

  /** Step to adjacent hex tile. */
  def stepOpt(st: SqDirn)(implicit gridSys: SqGridSys): Option[SqCen] = {
    val target = SqCen(r + st.tr, c + st.tc)
    ife(gridSys.sqCenExists(target), Some(target), None)
  }

  /** The polygon of this hex tile if it is part of a regular grid. */
  def polygonReg: Polygon = verts.mapPolygon(_.toPt2Reg)

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

  val vertsOfSq00: SqVertArr = SqVertArr(s00v1, s00v2, s00v3, s00v4)

  /** Implicit [[ArrBuilder]] type class instance / evidence for [[SqCen]] and [[SqCens]]. */
  implicit val buildEv: Int2ArrBuilder[SqCen, SqCens] = new Int2ArrBuilder[SqCen, SqCens]
  { type BuffT = SqCenBuff
    override def fromIntArray(array: Array[Int]): SqCens = new SqCens(array)
    override def fromIntBuffer(buffer: ArrayBuffer[Int]): SqCenBuff = new SqCenBuff(buffer)
  }
}

trait SqMem[A]
{ val sc: SqCen
  val value: A
}