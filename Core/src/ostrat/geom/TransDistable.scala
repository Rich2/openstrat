/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** The object can be transformed in 2 dimensional space. Leaf classes must implement the single method fTrans(f: Dist2 => Dist2):  T The related
 *   trait Transable  does the same for fTrans(f: Vec2 => Vec2): T. */
trait TransDistable[T] extends Any
{
  def fTrans(f: Dist2 => Dist2):  T  
  def slate(offset: Dist2): T = fTrans(_ + offset)
  def slate(xOffset: Dist, yOffset: Dist): T = fTrans(_.addXY(xOffset, yOffset))
  def slateX(xOffset: Dist): T = fTrans(_.addX(xOffset))
  def slateY(yOffset: Dist): T = fTrans(_.addY(yOffset))
  def scale(factor: Double): T = fTrans(_ * factor)

  def rotate(angle: Angle): T = fTrans(_.rotate(angle))
  def rotateRadians(r: Double): T = fTrans(_.rotateRadians(r))
  
  /** this.asInstanceOf[T] */  
  def identity: T = this.asInstanceOf[T]   
  def inverseY: T = fTrans(v => Dist2(v.x, -v.y))  
}