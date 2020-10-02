/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** An infinite length 2 dimensional straight line trait. Note this is the mathematical definition of a line. InSVg and other APIs the name line is
 * used for a line segment, which in openstrat is called a [[LineSeg]] */
sealed trait Line extends Curve with LineLike
{ /** Reflects, mirrors a point across this line. */
  def reflectPt(pt: Vec2): Vec2

  /** Translate 2D geometric transformation. This abstract method returns a [[Line]]. The Return type will be narrowed in sub traits. */
  override def slate(offset: Vec2): Line = ???

  /** Translate 2D geometric transformation. This abstract method returns a [[Line]]. The Return type will be narrowed in sub traits. */
  override def slate(xOffset: Double, yOffset: Double): Line = ???

  /** Uniform 2D scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles
   * and Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): Line = ???

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negY: Line = ???

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negX: Line = ???

  /** Transforms this Line using a [[ProlignMatrix]]. */
  override def prolign(matrix: ProlignMatrix): Line = ???

  override def rotateRadians(radians: Double): Line = ???

  override def reflect(line: Line): Line = ???

  override def reflect(line: LineSeg): Line = ???

  override def xyScale(xOperand: Double, yOperand: Double): Line = ???

  override def xShear(operand: Double): Line = ???

  override def yShear(operand: Double): Line = ???

  override def productArity: Int = ???

  override def productElement(n: Int): Any = ???

  override def canEqual(that: Any): Boolean = ???
}

/** An infinite length 2 dimensional straight line defined in terms of its X value and and an offset. It is defined for all values of Y, but not
 * for all values of x if the xFactor is 0. */
sealed case class YLine(xFactor: Double, offset: Double) extends Line
{ def y(x: Double): Double = xFactor * x + offset

  /** The point at which the line crosses the Y Axis, unless this YLine is the YAxis in which case it is merely a point where the line intersects the
   * Y axis. */
  def yIntersection: Vec2 = Vec2(0, offset)

  override def reflectPt(pt: Vec2): Vec2 =
  {
    val v2: Vec2 = Vec2(1, xFactor + offset)
    val lineDelta: Vec2 = v2 - yIntersection
    val lineUnitVector = lineDelta / lineDelta.magnitude
    2 * yIntersection - pt - 2 * (yIntersection - pt).dot(lineUnitVector) * lineUnitVector
  }
}

/** An infinite length 2 dimensional straight line defined in terms of its Y value and and an offset. It is defined for all values of X, but not
 * for all values of x if the xFactor is 0. */
sealed case class XLine(yFactor: Double, offset: Double) extends Line
{ def x(y: Double): Double = yFactor * y + offset

  /** The point at which the line crosses the Y Axis, unless this YLine is the YAxis in which case it is merely a point where the line intersects the
   * Y axis. */
  def xIntersection: Vec2 = Vec2(offset, 0)

  override def reflectPt(pt: Vec2): Vec2 =
  {
    val v2: Vec2 = Vec2(yFactor + offset, 1)
    val lineDelta: Vec2 = v2 - xIntersection
    val lineUnitVector = lineDelta / lineDelta.magnitude
    2 * xIntersection - pt - 2 * (xIntersection - pt).dot(lineUnitVector) * lineUnitVector
  }
}

/** An infinite length 2 dimensional straight line that is parrael to the X Axis. It is defined for all values of Y, but for only 1 value of X. */
sealed class YParallel(offset: Double) extends YLine(0, offset )

/** An infinite length 2 dimensional straight line that is parrael to the X Axis. It is defined for all values of X, but for only 1 value of Y. */
sealed class XParallel(offset: Double) extends XLine(0, offset )

sealed trait XorYAxis extends Line

/** The Y Axis in 2 dimensional space. */
object YAxis extends XParallel( 0) with XorYAxis

/** The X Axis in 2 dimensional space. */
object XAxis extends XParallel( 0) with XorYAxis