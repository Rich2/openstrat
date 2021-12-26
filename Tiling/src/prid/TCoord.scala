/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid
import geom._

/** A coordinate in a tile grid [[TGrid]]. The row is the first field, the column is the second. */
trait TCoord extends Any with Show2Base32s
{ /** Row number for the coordinate of a tile grid. */
  def r: Int

  /** Column number for the coordinate of a tile grid. */
  def c: Int

  @inline override def show1: Int = r
  @inline override def show2: Int = c
  override def name1: String = "r"
  override def name2: String = "c"

  def toPt2: Pt2
  def toVec: Vec2
  def typeStr: String
  def rcStr: String = s"$r, $c"
  def parenthStr: String = rcStr.enParenth
}

/** Companion object for TCoord trait will contain a Show[TCoord] implicit instance in Scala 3, but this produces an error in 2.13.5. */
object TCoord
{
  //implicit val showTImplicit: Show2Base32sT[TCoord] = Show2Base32sT[TCoord]("TCoord")
}

/** A square grid integer tile coordinate. */
trait SqCoord extends Any with TCoord
{ override def toVec: Vec2 = Vec2(c, r)
  override def toPt2: Pt2 = Pt2(c, r)
}

/** A Square tile centre square grid [[SqGrid]] coordinate. */
case class SqCen(val r: Int, val c: Int) extends SqCoord
{ override def typeStr: String = "Sqcen"

  /** Optionally returns the Step value of the SqCen if it is an adjacent SqCen. */
  def optStep(operand: SqCen): OptRef[SqStep] = ??? // hcStepSomes.optFind(_.hCen == operand - this)
}

/** A Square tile side square grid [[SqGrid]] coordinate. */
class SqSide(val r: Int, val c: Int) extends SqCoord
{ override def typeStr: String = "Sqside"
}

/** A Square tile vertex square grid [[SqGrid]] coordinate. */
class SqVert(val r: Int, val c: Int) extends SqCoord
{ override def typeStr: String = "Sqvert"
}

object SqVert
{ val showTImplicit: ShowT[SqVert] = Show2Base32sT("Sqvert")
}

trait SqMem[A]
{ val sc: SqCen
  val value: A
}