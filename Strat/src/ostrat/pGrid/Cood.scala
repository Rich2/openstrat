/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._

/** A Cood represents an integer coordinate within a tile grid system. This has current been implemented for Hexs and Squares, while triangles is the
  * third possible regular tile system. A SqCood represents either a tile centre, a tile side or a tile vertex. This system allows river and naval
  * units to move along the tile sides. */
final case class Cood(val x: Int, val y: Int) extends ProdI2
{ def typeSym = 'Cood
  def _1 = x
  def _2 = y
  def xyStr: String = x.toString - ", " - y.toString
  def yxStr: String = y.toString - ", " -x.toString
  def fXY[A](f: (Int, Int) => A): A = f(x, y)
  def canEqual(a: Any) = a.isInstanceOf[Cood]
  def eqXY(x: Int, y: Int): Boolean = this == Cood(x, y)
  def +(operand: Cood): Cood = Cood(x + operand.x, y + operand.y)
  def -(operand: Cood): Cood = Cood(x - operand.x, y - operand.y)
  def *(operand: Int): Cood = Cood(x * operand, y * operand)
  def /(operand: Int): Cood = Cood(x / operand, y / operand)
   
  def addXY(xOff: Int, yOff: Int): Cood = Cood(x + xOff, y + yOff)
  def subXY(xOff: Int, yOff: Int): Cood = Cood(x - xOff, y - yOff)
  def addX(operand: Int): Cood = Cood(x + operand, y)
  def addY(operand: Int): Cood = Cood(x, y + operand)
  def subX(operand: Int): Cood = Cood(x - operand, y)
  def subY(operand: Int): Cood = Cood(x, y - operand)
  def toSqVec2: Vec2 = Vec2(x, y)
  /** x.isOdd & y.isOdd | x.isEven & y.isEven */
  def evenSum: Boolean = x.isOdd & y.isOdd | x.isEven & y.isEven
  /** x.isOdd & y.isEven | x.isEven & y.isOdd */
  def oddSum: Boolean = x.isOdd & y.isEven | x.isEven & y.isOdd
}

object Cood
{ def apply(x: Int, y: Int): Cood = new Cood(x, y)
  def unapply(tc: Cood): Option[(Int, Int)] = Some((tc.x, tc.y))
  def list(inp: (Int, Int)*): List[Cood] = inp.toList.map(p => Cood(p._1, p._2))
  implicit object CoodPersist extends Persist2[Int, Int, Cood]('Cood, c => (c.x, c.y), apply)
}

trait GridBuilder
{
   val xRatio: Double
   val xRadius: Double
   val yRadius: Double
}