/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait PhiRectangle extends Rectangle
{
  def width2: Double = width1 * Phi
}

object PhiRectangle
{
  case class PhiRectangleImp(xS1Cen: Double, yS1Cen: Double, xS3Cen: Double, yS3Cen: Double)
}

case class PhiRect(xCen: Double, yCen: Double, height: Double) extends Rect with PhiRectangle
{
  override def width: Double = height * Phi

  override def slateTo(newCen: Vec2): PhiRect = ???
}

object PhiRect
{
  case class PhiRectImp(xCen: Double, yCen: Double, xLs3Cen: Double, yLs3Cen: Double)
  { def ls3Cen: Vec2 = Vec2(xLs3Cen, yLs3Cen)
  }
}