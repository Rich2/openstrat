/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._

/** A 2d line defined by its start and end Tile Cood. */
case class CoodLine(x1: Int, y1: Int, x2: Int, y2: Int) extends ProdI4
{
  def _1 = x1
  def _2 = y1
  def _3 = x2
  def _4 = y2
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
