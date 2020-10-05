/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A class that preserves its type through all the Prolign, proportionate XY axes aligned transformations. */
trait ProlignPreserve extends GeomElem
{
  /** The most narrow type preserved in some 2d geometric transformations. */
  type ThisT <: ProlignPreserve
  
  def fTrans(f: Vec2 => Vec2): ThisT
  override def slate(offset: Vec2): ThisT = fTrans(_ + offset)
  override def slate(xOffset: Double, yOffset: Double): ThisT = fTrans(_.addXY(xOffset, yOffset))
  override def scale(operand: Double): ThisT = fTrans(_ * operand)
  override def negX: ThisT = fTrans(_.negX)
  override def negY: ThisT = fTrans(_.negY)
  override def rotate90: ThisT = fTrans(_.rotate90)
  override def rotate180: ThisT = fTrans(_.rotate180)
  override def rotate270: ThisT = fTrans(_.rotate270)
  override def prolign(matrix: ProlignMatrix): ThisT = fTrans(_.prolign(matrix))
}