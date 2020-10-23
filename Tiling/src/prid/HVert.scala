/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package prid
import geom._

class HVert(val r: Int, val c: Int) extends HCoord with ProdInt2
{
  override def _1: Int = r

  override def _2: Int = c

  override def canEqual(that: Any): Boolean = ???

  override def toVec2: Vec2 = (r %% 4, c %% 4) match
  {
    case (1, 0) | (3, 2)  =>  Vec2(c * xRatio, r + HGrid.yDist / 2)
    case _ => Vec2(c * xRatio, r - HGrid.yDist / 2)
  }
}

object HVert
{
  def apply(r: Int, c: Int): HVert = if (r.isOdd & c.isEven) new HVert(r, c) else excep(s"$r, $c is not a valid Hex vertex tile coordinate.")
}