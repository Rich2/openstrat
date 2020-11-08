/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

class Rotation(val milliSecs: Double)
{
  def degs: Double = milliSecs / 360000
  override def toString: String = degs.toString
  deb((0.1 + 0.2).toString)
}

object Rotation
{
  def apply(degs: Double): Rotation = new Rotation(degs * 360000)
}