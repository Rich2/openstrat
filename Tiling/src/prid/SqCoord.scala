/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid
import geom._

trait SqBaseCoord extends Any with TileCoord
{ override def toVec: Vec2 = Vec2(c, r)
  override def toPt2: Pt2 = Pt2(c, r)
}

/** A square grid integer tile coordinate. */
trait SqCoord extends Any with SqBaseCoord

object SqCoord{
  def apply(r: Int, c: Int): SqCoord = ???
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

  /** Step to adjacent hex tile. Will throw exception on illegal value. */
  def step(st: SqStep): SqCen = SqCen(r + st.r, c + st.c)

  /** Optionally returns the Step value of the SqCen if it is an adjacent SqCen. */
  def optStep(operand: SqCen): OptRef[SqStep] = ??? // hcStepSomes.optFind(_.hCen == operand - this)

  /** Optionally returns the Step value of the HCen if it is an adjacent HCen else returns the safe null value. */
  def findStep(operand: SqCen): OptRef[SqStep] = scSteps.optFind(_.sqCen == operand - this)

  def -(operand: SqCen): SqCen = SqCen(r - operand.r, c - operand.c)

  /** Step to adjacent hex tile. */
  def stepOpt(st: SqStep)(implicit grid: SqGrid): Option[SqCen] = {
    val target = SqCen(r + st.r, c + st.c)
    ife(grid.sqCenExists(target), Some(target), None)
  }
}

object SqCen
{
  implicit val persistImplicit: Persist[SqCen] = new PersistShowInt2[SqCen]("SqCen", "r", "c", SqCen(_, _))
}

/** A Square tile side square grid [[SqGrid]] coordinate. */
class SqSide(val r: Int, val c: Int) extends SqCenOrSide with TileSide
{ override def typeStr: String = "Sqside"
}

/** A Square tile vertex square grid [[SqGrid]] coordinate. */
class SqVert(val r: Int, val c: Int) extends SqCoord with TileCoord
{ override def typeStr: String = "Sqvert"
}

object SqVert
{ val showTImplicit: ShowT[SqVert] = Show2Base32sT("Sqvert")
}

trait SqMem[A]
{ val sc: SqCen
  val value: A
}
