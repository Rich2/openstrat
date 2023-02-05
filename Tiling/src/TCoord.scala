/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid
import geom._

/** A coordinate in a tile grid [[TGrid]]. The row is the first field, the column is the second. */
trait TCoord extends Any with Point with ShowElemInt2
{ /** Row number for the coordinate of a tile grid. */
  def r: Int

  /** Column number for the coordinate of a tile grid. */
  def c: Int

  @inline override def show1: Int = r
  @inline override def show2: Int = c
  override def name1: String = "r"
  override def name2: String = "c"

  def toPt2Reg: Pt2
  def toVecReg: Vec2
  def typeStr: String
  def rcStr: String = s"$r, $c"
  def rcStr32: String = s"${r.base32}, ${c.base32}"
  def parenthStr: String = rcStr.enParenth
}

/** Companion object for [[TCoord]] trait will contain a Show[TCoord] implicit instance in Scala 3, but this produces an error in 2.13.5. */
object TCoord
{
  //implicit val showTImplicit: Show2Base32sT[TCoord] = Show2Base32sT[TCoord]("TCoord")
}

/** A tile centre or a tile side coordinate. */
trait TCenOrSide extends Any with TCoord

/** A tile centre coordinate. */
trait TCen extends Any with TCenOrSide

/** A tile side coordinate. */
trait TSide extends Any with TCenOrSide

/** A tile vertex coordinate. */
trait TVert extends Any with TCoord