/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Angle vector, an angle of rotation. Values may be greater than 360 degrees and less than -360 degrees. Negative values represent clockwise
 *  rotation. So +720 degrees represents to complete anti-clockwise rotations. */
class AngleVec private(val milliSecs: Double) extends AngleLike
{
  override def toString: String = degs.toString

  /** The reverse rotation. */
  def reverse: AngleVec = milliSecs match {
    case ms if ms > 0 => AngleVec.milliSecs(ms - MilliSecsIn360Degs)
    case 0 => this
    case ms => AngleVec.milliSecs(MilliSecsIn360Degs + ms)
  }
}

object AngleVec
{ /** Creates an angle vector, or an angle of rotation from the value in number of degrees. */
  def apply(degs: Double): AngleVec = new AngleVec(degs * 360000)

  /** Creates an angle vector, or an angle of rotation from the value in number of degrees. */
  def radians(radians: Double): AngleVec = new AngleVec(radians.radiansToMilliSecs)

  /** Creates an angle vector, or an angle of rotation from the value in number of degrees. */
  def milliSecs(milliSecs: Double): AngleVec = new AngleVec(milliSecs)
}