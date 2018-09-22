/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._

/** A Cood represents an integer coordinate within a tile grid system. This has current been implemented for Hexs and Squares, while
 *  triangles is the third possible regular tile system. A SqCood represents either a tile centre, a tile side or a tile vertex. This system
 *  allows river and naval units to move along the tile sides. */
final case class Cood(val x: Int, val y: Int) extends ProdI2
{
   def _1 =x
   def _2 = y
   def xyStr: String = x.toString - ", " - y.toString
   def yxStr: String = y.toString - ", " -x.toString
   //def xyStr: String = x.commas(y)
   override def toString = "cood" -- xyStr
   def fXY[A](f: (Int, Int) => A): A = f(x, y)
   def canEqual(a: Any) = a.isInstanceOf[Cood]
   def eqXY(x: Int, y: Int): Boolean = this == Cood(x, y)
   def +(operand: Cood): Cood = Cood(x + operand.x, y + operand.y)
   def -(operand: Cood): Cood = Cood(x - operand.x, y - operand.y)
   def *(operand: Int): Cood = Cood(x * operand, y * operand)
   
   def addXY(xOff: Int, yOff: Int): Cood = Cood(x + xOff, y + yOff)
   def subXY(xOff: Int, yOff: Int): Cood = Cood(x - xOff, y - yOff)
   def addX(operand: Int): Cood = Cood(x + operand, y)
   def addY(operand: Int): Cood = Cood(x, y + operand)
   def subX(operand: Int): Cood = Cood(x - operand, y)
   def subY(operand: Int): Cood = Cood(x, y - operand)
   def toSqVec2: Vec2 = Vec2(x, y)
   /** x.isOdd & y.isOdd | x.isEven & y.isEven */
   def oddsOrEvens: Boolean = x.isOdd & y.isOdd | x.isEven & y.isEven
}

object Cood
{
   def apply(x: Int, y: Int): Cood = new Cood(x, y)
   def unapply(tc: Cood): Option[(Int, Int)] = Some((tc.x, tc.y))
   def list(inp: (Int, Int)*): List[Cood] = inp.toList.map(p => Cood(p._1, p._2))   
   implicit object CoodPersist extends Persist2[Int, Int, Cood]('Cood, c => (c.x, c.y), apply)   
}

case class CoodLine(x1: Int, y1: Int, x2: Int, y2: Int)
{   
   def toLine2(f: Cood => Vec2): Line2 =
   {  val v1 = f(Cood(x1, y1))
      val v2 = f(Cood(x2, y2))
      Line2(v1, v2)
   }
}

object CoodLine
{
   def apply(c1: Cood, c2: Cood): CoodLine = CoodLine(c1.x, c1.y, c2.x, c2.y)
}

trait GridBuilder
{
   val xRatio: Double
   val xRadius: Double
   val yRadius: Double
}