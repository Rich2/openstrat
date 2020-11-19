/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Angle vector, an angle of rotation. Can in principle have values between + and - infinity. */
class AngleVec private(val milliSecs: Double) extends AngleLike
{
  override def toString: String = degs.toString
  deb((0.1 + 0.2).toString)
}

object AngleVec
{
  def apply(degs: Double): AngleVec = new AngleVec(degs * 360000)
}