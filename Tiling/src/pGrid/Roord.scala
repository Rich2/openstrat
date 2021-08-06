/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pGrid
import geom._

/** A Roord Row-ordinate represents a 2 dimensional integer coordinate within a tile grid system. The row or y value comes first. This is different to
 * a Vec2 or Vec3 where the y vlue comes 2nd after the x valu. This has current been implemented for Hexs and Squares, while triangles is the third
 * possible regular tile system. A SqGrid Cood represents either a tile centre, a tile side or a tile vertex. This is the same for a Hex Grid except
 * that not all values are legal Cood values on a HexGrid. This system allows river and naval units to move along the tile sides. The axis are named
 * y and c to distinguish them from the x and y of a Vec2. On a Hex grid there is not a simple 1 to 1 mapping between the Cood components and the
 * Vec2 components. */
final class Roord private(val bLong: Long) extends AnyVal with ElemInt2
{ @inline def y: Int = bLong.>>(32).toInt
  @inline def c: Int = bLong.toInt
  @inline def int1 = y
  @inline def int2 = c
  def canEqual(a: Any) = a.isInstanceOf[Roord]
  @inline def yStr: String = y.toString
  @inline def cStr: String = c.toString
  override def toString: String = "Roord".appendSemicolons(yStr, cStr)
  def ycStr: String = y.str.appendCommas(c.str)
  def + (operand: Roord): Roord = Roord(y + operand.y, c + operand.c)
  def -(operand: Roord): Roord = Roord(y - operand.y, c - operand.c)
  def *(operand: Int): Roord = Roord(y * operand, c * operand)
  def /(operand: Int): Roord = Roord(y / operand, c / operand)

  def addYC(yOff: Int, cOff: Int): Roord = Roord(y + yOff, c + cOff)
  def subYC(yOff: Int, cOff: Int): Roord = Roord(y - yOff, c - cOff)
  def addY(operand: Int): Roord = Roord(y + operand, c)
  def addC(operand: Int): Roord = Roord(y, c + operand)
  def subY(operand: Int): Roord = Roord(y - operand, c)
  def subC(operand: Int): Roord = Roord(y, c - operand)

  def toHexTile: HexTile = HexTile(y, c)
  def toSqTile: SqTile = SqTile(y, c)
  def tilePoly(implicit tileGrid: TileGridOld): Polygon = tileGrid.roordToPolygon(this)
  def gridPt2(implicit tileGrid: TileGridOld): Pt2 = tileGrid.roordToPt2(this)
  def gridVec(implicit tileGrid: TileGridOld): Vec2 = tileGrid.roordToVec(this)

  def andStep(st: HTStep): HTileAndStep = HTileAndStep(y, c, st)
  def step(st: HTStep): Roord = this + st.roord
  def stepBack(st: HTStep): Roord = this - st.roord
  def yPlusC: Int = y + c
}

object Roord
{ def apply(y: Int, c: Int): Roord = new Roord(y.toLong.<<(32) | (c & 0xFFFFFFFFL))
  def fromLong(value: Long): Roord = new Roord(value)
  def unapply(rd: Roord): Option[(Int, Int)] = Some((rd.y, rd.c))

  implicit val roordsBuildImplicit: ArrInt2sBuilder[Roord, Roords] = new ArrInt2sBuilder[Roord, Roords]
  { type BuffT = RoordBuff
    override def fromIntArray(array: Array[Int]): Roords = new Roords(array)
    override def fromIntBuffer(inp: Buff[Int]): RoordBuff = new RoordBuff(inp)
  }
}