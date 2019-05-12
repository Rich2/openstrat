/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat.geom

/** An object that can transform itself in 2d geometry. This is a key trait, the object can be transformed in 2 dimensional space. Leaf classes must implement the single method fTrans(f: Vec2 => Vec2):
 *  T. The related trait TransDistable  does the same for fTrans(f: Dist2 => Dist2):  T. */
trait Trans[T] extends Any
{ def fTrans(obj: T, f: Vec2 => Vec2):  T
  /** Translate in 2 dimensional space. */
  def slate(obj: T, offset: Vec2): T = fTrans(obj, _ + offset)
  /** Translate in 2 dimensional space. */
  def slate(obj: T, xOffset: Double, yOffset: Double): T = fTrans(obj, _.addXY(xOffset, yOffset))
  /** Translate 2 dimensional vectors along the X axis */
  def slateX(obj: T, xOffset: Double): T = fTrans(obj, _.addX(xOffset))
  /** Translate 2 dimensional vectors along the Y axis */
  def slateY(obj: T, yOffset: Double): T = fTrans(obj, _.addY(yOffset))
  /** The scale transformation on 2 dimensional vectors. */
  def scale(obj: T, factor: Double): T = fTrans(obj, _ * factor)

  def rotate(obj: T, angle: Angle): T = fTrans(obj, _.rotate(angle))
  def rotateRadians(obj: T, r: Double): T = fTrans(obj, _.rotateRadians(r))
  def scaleY(obj: T, factor: Double): T = fTrans(obj, _.scaleY(factor))
  def scaleX(obj: T, factor: Double): T = fTrans(obj, _.scaleX(factor))
  /** this.asInstanceOf[T] */  
  def identity(obj: T): T = obj
  /** Mirrors along the Y axis by negating X. */
  def negX(obj: T): T = fTrans(obj: T,_.negX)
  /** Mirrors along the X axis by negating Y. */
  def negY(obj: T): T = fTrans(obj, _.negY)
  /** Negates x and y values */
  def negXY(obj: T): T = fTrans(obj, - _)

  import math.Pi
  /** Rotates 30 degrees anti-clockwise or + Pi/3 */
  def anti30(obj: T): T = rotate(obj, Angle(Pi / 6))
  /** Rotates 45 degrees anti-clockwise or + Pi/4 */
  def anti45(obj: T): T = rotate(obj, Angle(Pi / 4))
  /** Rotates 60 degrees anti-clockwise or + Pi/3 */
  def anti60(obj: T): T  = rotate(obj, Angle(Pi / 3))  
  /** Rotates 90 degrees anti-clockwise or + Pi/2 */
  def anti90(obj: T): T = rotate(obj, Angle(Pi / 2))  
  /** Rotates 120 degrees anti-clockwise or + 2 * Pi/3 */
  def anti120(obj: T): T = rotate(obj, Angle(2 * Pi / 3))
  /** Rotates 135 degrees anti-clockwise or + 3 * Pi/4 */
  def anti135(obj: T): T = rotate(obj, Angle(3 * Pi / 4))
  /** Rotates 150 degrees anti-clockwise or + 5 * Pi/6 */
  def anti150(obj: T): T = rotate(obj, Angle(5 * Pi / 6))
   
  /** Rotates 30 degrees clockwise or - Pi/3 */
  def clk30(obj: T): T = rotate(obj, Angle(-Pi / 6))
  /** Rotates 45 degrees clockwise or - Pi/4 */
  def clk45(obj: T): T = rotate(obj, Angle(-Pi / 4))
  /** Rotates 60 degrees clockwise or - Pi/3 */
  def clk60(obj: T): T  = rotate(obj, Angle(-Pi / 3))  
  /** Rotates 90 degrees clockwise or - Pi / 2 */
  def clk90(obj: T): T = rotate(obj, Angle(-Pi / 2))  
  /** Rotates 120 degrees clockwise or - 2 * Pi/3 */
  def clk120(obj: T): T = rotate(obj, Angle(-2 * Pi / 3))
  /** Rotates 135 degrees clockwise or - 3 * Pi/ 4 */
  def clk135(obj: T): T = rotate(obj, Angle(-3 * Pi / 4))
  /** Rotates 150 degrees clockwise or - 5 * Pi/ 6 */
  def clk150(obj: T): T = rotate(obj, Angle(-5 * Pi / 6))
   
  /** Produces a regular cross of a sequence of four of the elements rotated */
  def rCross(obj: T): Seq[T] = (1 to 4).map(i => rotate(obj, deg90 * i))
}

object Trans
{
  
//  implicit def TransableListImplicit[TT <: Transable[_]]: Trans[List[TT]] = new Trans[List[TT]]
//  {
//    override def fTrans(obj: List[TT], f: Vec2 => Vec2): List[TT] = obj.map(_.fTrans(f).asInstanceOf[TT]) 
//  }
}
