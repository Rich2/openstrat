/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package prid
import geom._

/** A coordinate in a TileGrid. The row is the first field, the column is the second. */
trait TCoord extends Any with Show2[Int, Int]
{
  /** Row number for the coordinate of a tile grid. */
  def r: Int

  /** Column number for the coordinate of a tile grid. */
  def c: Int

  @inline override def arg1: Int = r
  @inline override def arg2: Int = c
  override def name1: String = "r"
  override def name2: String = "c"
  override implicit def ev1: ShowT[Int] = ShowT.intPersistImplicit
  override implicit def ev2: ShowT[Int] = ShowT.intPersistImplicit
  override def syntaxdepth: Int = 2

  def toPt2: Pt2
  def toVec: Vec2
  def typeStr: String
  def rcStr: String = s"$r, $c"
  def parenthStr: String = rcStr.enParenth
}

trait SqCoord extends Any with TCoord
{
  override def toVec: Vec2 = Vec2(c, r)
  override def toPt2: Pt2 = Pt2(c, r)
}

/** A Square tile centre square grid [[SqGrid]] coordinate. */
case class Sqcen(val r: Int, val c: Int) extends SqCoord
{
  override def typeStr: String = "Sqcen"
}

/** A Square tile side square grid [[SqGrid]] coordinate. */
class Sqside(val r: Int, val c: Int) extends SqCoord
{
  override def typeStr: String = "Sqside"
}

/** A Square tile vertex square grid [[SqGrid]] coordinate. */
class Sqvert(val r: Int, val c: Int) extends SqCoord
{
  override def typeStr: String = "Sqvert"
}