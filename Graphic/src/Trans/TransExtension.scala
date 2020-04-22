/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** Extension methods for an object that can transform itself in 2d geometry via the function f: Vec2 => Vec2. There is an implicit conversion of
 *  any object that has a Trans instance. Trans instances are founded on objects that inherit the Transer trait. Eg Polygon inherits from Transer.
 *  There is a Trans[Polygon]instance and a Trans[List[Polygon]] instance. The TransDistExtension class provides similar extension methods for
 *  objects that can perform the Dist2 => Dist2 transformation. */
class TransExtension[T](value: T, ev: TransAll[T]) extends TransGenExtension[T]
{
  /** General Vec2 to Vec2 transformation. */
  def trans(f: Vec2 => Vec2):  T = ev.trans(value, f)

  /** Translate in 2 dimensional space. */
// def slate(offset: Vec2): T = trans(_ + offset)

  /** Translate in 2 dimensional space. */
 //def slate(xOffset: Double, yOffset: Double): T = trans(_.addXY(xOffset, yOffset))


  /** The scale transformation on 2 dimensional vectors. */
  def scale(factor: Double): T = trans(_ * factor)

  /** The scale transformation on 2 dimensional vectors. */
  def scaleSlate(factor: Double, addVec: Vec2): T = trans(_ * factor + addVec)

  /** Applies scale transformation and adds x on 2 dimensional vectors. */
  def scaleSlateX(factor: Double, xDelta: Double): T = trans(v => (v * factor).addX(xDelta))

  /** The scale transformation on 2 dimensional vectors. */
  def scaleSlateY(factor: Double, yDelta: Double): T = trans(v => (v * factor).addY(yDelta))

  override def rotate(angle: Angle): T = trans(_.rotate(angle))
  override def rotateRadians(r: Double): T = trans(_.rotateRadians(r))
  def scaleY(factor: Double): T = trans(_.scaleY(factor))
  def scaleX(factor: Double): T = trans(_.scaleX(factor))

  /** this.asInstanceOf[T] */
  def identity: T = this.asInstanceOf[T]

  /** Mirrors along the Y axis by negating X. */
  def negX: T = trans(_.mirrorY)

  /** Mirrors along the X axis by negating Y. */
  def negY: T = trans(_.mirrorX)

  /** Vec2 transformation that negates x and y values */
  def negXY: T = trans(- _)
}



