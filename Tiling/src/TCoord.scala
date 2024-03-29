/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid
import geom._

/** A coordinate in a tile grid [[TGrid]]. The row is the first field, the column is the second. */
trait TCoord extends Any with Point with TellElemInt2
{ /** Row number for the coordinate of a tile grid. */
  def r: Int

  /** Column number for the coordinate of a tile grid. */
  def c: Int

  @inline override def tell1: Int = r
  @inline override def tell2: Int = c
  override def name1: String = "r"
  override def name2: String = "c"

  def toPt2Reg: Pt2
  def toVecReg: Vec2
  def typeStr: String
  def rcStr: String = s"$r, $c"
  def rcStr32: String = s"${r.base32}, ${c.base32}"
  def parenthStr: String = rcStr.enParenth
}

/** Common trait for [[TCen]] and [[TSep]]. A tile centre or a tile separator coordinate. */
trait TCenOrSep extends Any with TCoord

/** A tile centre coordinate. */
trait TCen extends Any with TCenOrSep

/** A tile separator coordinate. */
trait TSep extends Any with TCenOrSep

/** A tile vertex coordinate. */
trait TVert extends Any with TCoord