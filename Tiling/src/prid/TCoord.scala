/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package prid
import geom._

/** A coordinate in a TileGrid. Not sure how useful this trait is. */
trait TCoord extends Any
{ def r: Int
  def c: Int
  //def toPt2: Pt2
  def toVec: Vec2
  def typeStr: String
  def rcStr: String = s"$r, $c"
  def parenthStr: String = rcStr.enParenth
  def str: String = typeStr + parenthStr
  override def toString: String = typeStr + parenthStr
}

trait SqCoord extends Any with TCoord
{
  override def toVec: Vec2 = Vec2(c, r)
}

/** A Square tile centre square grid [[SqGrid]] coordinate. */
class Sqcen(val r: Int, val c: Int) extends SqCoord
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