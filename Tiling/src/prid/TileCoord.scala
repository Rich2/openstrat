/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid
import geom._

/** A coordinate in a tile grid [[TGrid]]. The row is the first field, the column is the second. */
trait TileCoord extends Any with ShowElemInt2
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

/** Companion object for [[TileCoord]] trait will contain a Show[TCoord] implicit instance in Scala 3, but this produces an error in 2.13.5. */
object TileCoord
{
  //implicit val showTImplicit: Show2Base32sT[TCoord] = Show2Base32sT[TCoord]("TCoord")
}

/** A tile centre or a tile side coordinate. */
trait TileCenOrSide extends Any with TileCoord

/** A tile centre coordinate. */
trait TileCen extends Any with TileCenOrSide

/** A tile side coordinate. */
trait TileSide extends Any with TileCenOrSide

/** A tile vertex coordinate. */
trait TileVert extends Any with TileCoord