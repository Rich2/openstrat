/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Angle vector, an angle of rotation. Values may be greater than 360 degrees and less than -360 degrees. Negative values represent clockwise
 *  rotation. So +720 degrees represents to complete anti-clockwise rotations. */
class AngleVec private(val milliSecs: Double) extends AngleLike
{
  override def toString: String = degs.toString

  /** Adds the operand AngleVec to this AngleVec. If you want to add an [[Angle]] to this AngleVec use addTo. */
  def +(operand: AngleVec): AngleVec = AngleVec.milliSecs(milliSecs + operand.milliSecs)

  /** Subtracts the operand AngleVec from this AngleVec. */
  def -(operand: AngleVec): AngleVec = AngleVec.milliSecs(milliSecs - operand.milliSecs)

  /** Multiplies this AngleVec by the scalar factor, returns an AngleVec. */
  def * (factor: Double): AngleVec = AngleVec.milliSecs(milliSecs * factor)

  /** Divides this AngleVec by the scalar factor, returns an AngleVec. */
  def / (factor: Double): AngleVec = AngleVec.milliSecs(milliSecs / factor)

  /** Adds this AngleVec to the parameter [[Angle]] returns an [[Angle]]. */
  def addTo(angle: Angle): Angle = Angle.milliSecs(milliSecs + angle.milliSecs)

  /** Subtracts this AngleVec from the parameter [[Angle]] returns an [[Angle]]. This is equivalent to: {{{angle - thisAngleVec}}} */
  def subFrom(angle: Angle): Angle = Angle.milliSecs(milliSecs - angle.milliSecs)

  /** Add the operand in degrees to this AngleVec, returns an AngleVec. */
  def addDegs(operand: Double): AngleVec = AngleVec(degs + operand)

  /** Gives the length of the circumference of the arc. */
  def arcLength(radius: Double): Double = radians * radius

  /** The reverse rotation. */
  def reverse: AngleVec = milliSecs match {
    case ms if ms > 0 => AngleVec.milliSecs(ms - MilliSecsIn360Degs)
    case 0 => this
    case ms => AngleVec.milliSecs(MilliSecsIn360Degs + ms)
  }

  @inline def unary_- : AngleVec = AngleVec.radians(- radians)
}

object AngleVec
{ /** Creates an angle vector, or an angle of rotation from the value in number of degrees. */
  def apply(degs: Double): AngleVec = new AngleVec(degs * MilliSecsInDeg)

  /** Creates an angle vector, or an angle of rotation from the value in number of degrees. */
  def radians(radians: Double): AngleVec = new AngleVec(radians.radiansToMilliSecs)

  /** Creates an angle vector, or an angle of rotation from the value in number of degrees. */
  def milliSecs(milliSecs: Double): AngleVec = new AngleVec(milliSecs)
}