/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pGrid
import geom._

/** A Cood represents a 2 dimensional integer coordinate within a tile grid system. This has current been implemented for Hexs and Squares, while
 *  triangles is the third possible regular tile system. A SqGrid Cood represents either a tile centre, a tile side or a tile vertex. This is the same
 *  for a Hex Grid except that not all values are legal Cood values on a HexGrid. This system allows river and naval units to move along the tile
 *  sides. The axis are named xi and yi to distinguish them from the x and y of a Vec2. On a Hex grid there is not a simple 1 to 1 mapping between the
 *  Cood components and the Vec2 components. */
final class Cood(val xi: Int, val yi: Int) extends ShowElemInt2
{
  override def typeStr: String = "Cood"
  override def name1: String = "xi"
  override def name2: String = "yi"
  override def show1: Int = xi
  override def show2: Int = yi

  def xyStr: String = xi.toString + ", " + yi.toString
  def yxStr: String = yi.toString + ", " + xi.toString
  def base32: String = yi.base32 + ", " + xi.base32
  def fXY[A](f: (Int, Int) => A): A = f(xi, yi)

  override def equals(that: Any): Boolean = that match
  { case op: Cood if xi == op.xi & yi == op.yi => true
    case _ => false
  }
  
  def canEqual(a: Any) = a.isInstanceOf[Cood]
  def eqXY(x: Int, y: Int): Boolean = this == Cood(x, y)
  def +(operand: Cood): Cood = Cood(xi + operand.xi, yi + operand.yi)
  def -(operand: Cood): Cood = Cood(xi - operand.xi, yi - operand.yi)
  def *(operand: Int): Cood = Cood(xi * operand, yi * operand)
  def /(operand: Int): Cood = Cood(xi / operand, yi / operand)

  def addXY(xOff: Int, yOff: Int): Cood = Cood(xi + xOff, yi + yOff)
  def subXY(xOff: Int, yOff: Int): Cood = Cood(xi - xOff, yi - yOff)
  def addX(operand: Int): Cood = Cood(xi + operand, yi)
  def addY(operand: Int): Cood = Cood(xi, yi + operand)
  def subX(operand: Int): Cood = Cood(xi - operand, yi)
  def subY(operand: Int): Cood = Cood(xi, yi - operand)
  def toSqVec2: Pt2 = Pt2(xi, yi)
  /** x.isOdd & y.isOdd | x.isEven & y.isEven */
  def evenSum: Boolean = xi.isOdd & yi.isOdd | xi.isEven & yi.isEven
  /** x.isOdd & y.isEven | x.isEven & y.isOdd */
  def oddSum: Boolean = xi.isOdd & yi.isEven | xi.isEven & yi.isOdd

}

object CoodNew
{ def apply(yi: Int, xi: Int): Cood = new Cood(yi, xi)
  def unapply(tc: Cood): Option[(Int, Int)] = Some((tc.xi, tc.yi))
}

object Cood
{ def apply(x: Int, y: Int): Cood = new Cood(x, y)
  def unapply(tc: Cood): Option[(Int, Int)] = Some((tc.xi, tc.yi))
  def list(inp: (Int, Int)*): List[Cood] = inp.toList.map(p => Cood(p._1, p._2))
  implicit object CoodPersist extends PersistShowInt2[Cood]("Cood", "x", "y", apply)

  implicit val coodsBuildImplicit: ArrInt2sBuilder[Cood, Coods] = new ArrInt2sBuilder[Cood, Coods]
  { type BuffT = CoodBuff
    override def fromIntArray(array: Array[Int]): Coods = new Coods(array)
    override def fromIntBuffer(inp: Buff[Int]): CoodBuff = new CoodBuff(inp)
  }
}