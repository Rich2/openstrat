/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** Angle of inclination. Its particularly important not to use this class to represent Latitudes as the Angle class has a normal range 0 <= a < 360
 *  degrees, while Latitudes have a normal range +- 90 degrees. Unlike [[AngleVec]] this class has no multiply or divide, * or / methods. It has add
 *  and subtract, + and - methods, but these take [[AngleVec]]s as operands not other Angles. To Add,subtract or scale angles of inclination would
 *  make no sense. */
final class Angle private(val milliSecs: Double) extends AnyVal with AngleLike with Ordered[Angle] with ElemDbl1
{ override def typeStr: String = "Angle"

  /** Intended to be a multiple parameter comprehensive Show method. Intended to be paralleled by showT method on [[ShowDecT]] type class instances. */
  override def show(style: ShowStyle, maxPlaces: Int, minPlaces: Int): String = style match
  { case ShowTyped => typeStr + degs.str.enParenth
    case _ =>  degs.str.enParenth
  }

  /** Creates a Vec2 from this Angle for the given scalar magnitude parameter. */
  def toVec2(magnitude: Double): Vec2 = Vec2(math.cos(radians) * magnitude, math.sin(radians) * magnitude)

  def degStr2: String = degs.str2 + "\u00B0"

  def +(other: AngleVec): Angle = Angle.milliSecs(milliSecs + other.milliSecs)
  def -(other: AngleVec): Angle = Angle.milliSecs(milliSecs - other.milliSecs)

  override def approx(that: Any, precision: AngleVec = precisionDefault): Boolean = that match {
    case th: Angle => milliSecs =~(th.milliSecs, precision.milliSecs)
    case _ => false
  }

  override def canEqual(that: Any): Boolean = that match
  { case _: Angle => true
    case _ => false
  }

  override def compare(that: Angle): Int = milliSecs - that.milliSecs match {
    case r if r < 0 => -1
    case 0 => 0
    case r => 1
  }

  def rotationFrom0: AngleVec = AngleVec.milliSecs(milliSecs)
  def rotationFrom90: AngleVec = AngleVec.milliSecs(milliSecs - MilliSecsIn90Degs)

  /** Plus 90, add 90 degrees to this Angle, rotate this angle by 90 degrees in an anti-clockwise direction. */
  def p90: Angle = Angle.milliSecs(milliSecs + MilliSecsIn90Degs)

  /** Minus 90, subtract 90 degrees from this Angle, rotate this angle by 90 degrees in a clockwise direction. */
  def m90: Angle = Angle.milliSecs(milliSecs - MilliSecsIn90Degs)

  /** plus 180, adds / subtracts 180 degrees from this Angle. As an Angle's range is 360 > a >= 0, adding or subtracting 180 degrees gives the same
   *  result. */
  def p180: Angle = Angle.milliSecs(milliSecs + MilliSecsIn180Degs)

  /** Returns the positive [[AngleVec]] from this Angle to the operand Angle. A value from 0 until 360 degrees.  */
  def deltaPosTo(other: Angle): AngleVec = other.milliSecs - milliSecs match
  { case ms if ms < 0 => AngleVec.milliSecs(ms + MilliSecsIn360Degs)
    case ms => AngleVec.milliSecs(ms)
  }

  /** Returns the negative [[AngleVec]] from this Angle to the operand Angle. A value from 0 until -360 degrees.  */
  def deltaNegTo(other: Angle): AngleVec = other.milliSecs - milliSecs match
  { case ms if ms > 0 => AngleVec.milliSecs(ms - MilliSecsIn360Degs)
    case ms => AngleVec.milliSecs(ms)
  }
  
  def addRadians(other: Double): Angle = Angle.radians(radians + other)
  def subRadians(other: Double): Angle = Angle.radians(radians - other)

  /** bisects the positive or anti-clockwise arc between this Angle and the operand Angle. */
  def bisectPos(operand: Angle): Angle = this + deltaPosTo(operand) / 2

  /** bisects the negative or clockwise arc between this Angle and the operand Angle. */
  def bisectNeg(operand: Angle): Angle = this + deltaNegTo(operand) / 2
}

/** Angle Companion object. */
object Angle {
  /** Factory method for Angle from number of degrees */
  @inline def apply(degrees: Double): Angle = new Angle((degrees %% 360) * MilliSecsInDeg)

  /** Factory method for creating Angle from the number of radians. */
  @inline def radians(radians: Double): Angle = new Angle(radians.radiansToMilliSecs %% MilliSecsIn360Degs)// (radians %+- Pi1) * 180 * MilliSecsInDeg / Pi1)

  /** Factory method for creating Angle from the number of angle seconds. */
  @inline def secs(value: Double): Angle = new Angle((value %% SecsIn360Degs) * 1000)

  /** Factory method for creating Angle from the number of thousands of an arc second. */
  @inline def milliSecs(value: Double): Angle = new Angle(value %% MilliSecsIn360Degs)

  implicit val eqTImplicit: EqT[Angle] = (a1, a2) => a1.milliSecs == a2.milliSecs
  implicit val approxTImplicit: ApproxAngleT[Angle] = (a1, a2, precsion) => a1 =~ (a2, precsion)
}