/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A common trait for [[Vec2]] and [[Pt2]] it would be good if the methods on this trait could be reduced.  */
trait VecPt2 extends Any with TellElemDbl2 with ApproxDbl
{ /** The x component of this 2D vector / point. */
  def x: Double

  /** The y component of this 2D vector / point. */
  def y: Double

  override def name1: String = "x"
  override def name2: String = "y"
  @inline final override def tell1: Double = x
  @inline final override def tell2: Double = y

  def yScale(factor: Double): VecPt2
  def xScale(factor: Double): VecPt2
  def xyScale(xOperand: Double, yOperand: Double): VecPt2

  @inline def scale(factor: Double): VecPt2

  /** rotates the vector 90 degrees or Pi/2 radians, anticlockwise. */
  @inline def rotate90: VecPt2

  /** Rotates the vector 180 degrees or Pi radians. */
  @inline def rotate180: VecPt2

  /** rotates the vector 90 degrees or Pi/2 radians, clockwise. */
  @inline def rotate270: VecPt2

  /** Rotates this vector through the given angle around the origin. */
  def rotate(a: AngleVec): VecPt2

  /** The dot product of this and the operand vector. */
  @inline def dot(operand: VecPt2): Double = x * operand.x + y * operand.y

  def toPair: (Double, Double) = (x, y)

  /** x >= 0 && y >= 0 */
  def isTopRight: Boolean = x >= 0 && y >= 0

  /** x >= 0 && y <= 0 */
  def isBottomRight: Boolean = x >= 0 && y <= 0

  /** x <= 0 && y <= 0 */
  def isBottomLeft: Boolean = x <= 0 && y <= 0

  /** x <= 0 && y >= 0 */
  def isTopleft: Boolean = x <= 0 && y >= 0
}