/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** An object that can transform itself in 2d geometry. This is a key trait, the object can be transformed in 2 dimensional space. Leaf classes must implement the single method fTrans(f: Vec2 => Vec2):
 *  T. The related trait TransDistable  does the same for fTrans(f: Dist2 => Dist2):  T. */
class TransExtension[T](value: T, ev: Trans[T])
{ def trans(f: Vec2 => Vec2):  T = ev.trans(value, f)
  /** Translate in 2 dimensional space. */
  def slate(offset: Vec2): T = trans(_ + offset)
  /** Translate in 2 dimensional space. */
  def slate(xOffset: Double, yOffset: Double): T = trans(_.addXY(xOffset, yOffset))
  /** Translate 2 dimensional vectors along the X axis */
  def slateX(xOffset: Double): T = trans(_.addX(xOffset))
  /** Translate 2 dimensional vectors along the Y axis */
  def slateY(yOffset: Double): T = trans(_.addY(yOffset))
  /** The scale transformation on 2 dimensional vectors. */
  def scale(factor: Double): T = trans(_ * factor)

  def rotate(angle: Angle): T = trans(_.rotate(angle))
  def rotateRadians(r: Double): T = trans(_.rotateRadians(r))
  def scaleY(factor: Double): T = trans(_.scaleY(factor))
  def scaleX(factor: Double): T = trans(_.scaleX(factor))
  /** this.asInstanceOf[T] */  
  def identity: T = this.asInstanceOf[T]
  /** Mirrors along the Y axis by negating X. */
  def negX: T = trans(_.negX)
  /** Mirrors along the X axis by negating Y. */
  def negY: T = trans(_.negY)
  /** Negates x and y values */
  def negXY: T = trans(- _)

  import math.Pi
  /** Rotates 30 degrees anti-clockwise or + Pi/3 */
  def anti30: T = rotate(Angle(Pi / 6))
  /** Rotates 45 degrees anti-clockwise or + Pi/4 */
  def anti45: T = rotate(Angle(Pi / 4))
  /** Rotates 60 degrees anti-clockwise or + Pi/3 */
  def anti60: T  = rotate(Angle(Pi / 3))  
  /** Rotates 90 degrees anti-clockwise or + Pi/2 */
  def anti90: T = rotate(Angle(Pi / 2))  
  /** Rotates 120 degrees anti-clockwise or + 2 * Pi/3 */
  def anti120: T = rotate(Angle(2 * Pi / 3))
  /** Rotates 135 degrees anti-clockwise or + 3 * Pi/4 */
  def anti135: T = rotate(Angle(3 * Pi / 4))
  /** Rotates 150 degrees anti-clockwise or + 5 * Pi/6 */
  def anti150: T = rotate(Angle(5 * Pi / 6))
   
  /** Rotates 30 degrees clockwise or - Pi/3 */
  def clk30: T = rotate(Angle(-Pi / 6))
  /** Rotates 45 degrees clockwise or - Pi/4 */
  def clk45: T = rotate(Angle(-Pi / 4))
  /** Rotates 60 degrees clockwise or - Pi/3 */
  def clk60: T  = rotate(Angle(-Pi / 3))  
  /** Rotates 90 degrees clockwise or - Pi / 2 */
  def clk90: T = rotate(Angle(-Pi / 2))  
  /** Rotates 120 degrees clockwise or - 2 * Pi/3 */
  def clk120: T = rotate(Angle(-2 * Pi / 3))
  /** Rotates 135 degrees clockwise or - 3 * Pi/ 4 */
  def clk135: T = rotate(Angle(-3 * Pi / 4))
  /** Rotates 150 degrees clockwise or - 5 * Pi/ 6 */
  def clk150: T = rotate(Angle(-5 * Pi / 6))
   
  /** Produces a regular cross of a sequence of four of the elements rotated */
  def rCross: Seq[T] = (1 to 4).map(i => rotate(deg90 * i))
}
