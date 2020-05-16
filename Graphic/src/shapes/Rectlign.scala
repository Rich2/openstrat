/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait Rectlign
{ def width: Double
  def height: Double
  def xCen: Double
  def yCen: Double
  def cen: Vec2 = Vec2(xCen, yCen)
}

case class RectlignGen(width: Double, height: Double, xCen: Double, yCen: Double) extends Rectlign

object Rectlign
{

}
