/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait PhiRectangle extends Rectangle

object PhiRectangle
{
  case class PhiRect(xCen: Double, yCen: Double, xLs3Cen: Double, yLs3Cen: Double)
  { def ls3Cen: Vec2 = Vec2(xLs3Cen, yLs3Cen)
  }
}

case class PhiRect(xCen: Double, yCen: Double, height: Double) extends Rect with PhiRectangle
{
  override def width: Double = height * Phi
  override def ls3Cen: Vec2 = Vec2(0, yCen + height / 2)
  override def slateTo(newCen: Vec2): PhiRect = ???
}
