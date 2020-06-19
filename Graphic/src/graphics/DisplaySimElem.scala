/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A DisplayElem for which all leaf elements of this trait maintain their type through all similar transformation. This type is purely for the 
 * convenience of using the fTrans method to perform all the Similar transformations. It is not a useful user type hence it has no type class
 * instances associated with it. */
trait DisplaySimElem extends TransSimElem with DisplayElem
{ type ThisT <: DisplaySimElem
  override def slate(offset: Vec2): ThisT
  override def slate(xOffset: Double, yOffset: Double): ThisT
  override def scale(operand: Double): ThisT
  def mirrorX: ThisT
  def mirrorY: ThisT
  def mirrorXOffset(yOffset: Double): ThisT
  def mirrorYOffset(xOffset: Double): ThisT
  override def mirror(line: Line2): ThisT
  override def rotate90: ThisT
  override def rotate180: ThisT
  override def rotate270: ThisT
  override def rotateRadians(radians: Double): ThisT
}

/** A DisplayElem for which all leaf elements of this trait maintain their type through all affine transformation. This type is purely for the 
 * convenience of using the fTrans method to perform all the affine transformations. It is not a useful user type hence it has no type class
 * instances associated with it. */
trait DisplayAffineElem extends DisplaySimElem with TransAffElem
{ type ThisT <: DisplayAffineElem
  override def scaleXY(xOperand: Double, yOperand: Double): ThisT
}