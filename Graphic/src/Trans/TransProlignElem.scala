/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait TransProlignElem extends TransElem
{ type ThisT <: TransProlignElem
  def fTrans(f: Vec2 => Vec2): ThisT
  def slate(offset: Vec2): ThisT = fTrans(_ + offset)
  def slate(xOffset: Double, yOffset: Double): ThisT = fTrans(_.addXY(xOffset, yOffset))
  def scale(operand: Double): ThisT = fTrans(_ * operand)
  override def mirrorX: ThisT = fTrans(_.mirrorX)
  override def mirrorY: ThisT = fTrans(_.mirrorY)
  def mirrorXOffset(yOffset: Double): ThisT = fTrans(_.mirrorXOffset(yOffset))
  def mirrorYOffset(xOffset: Double): ThisT = fTrans(_.mirrorYOffset(xOffset))
  /** Rotates 90 degrees or Pi / 2 radians anticlockwise. */
  override def rotate90: ThisT = fTrans(_.rotate90)

  /** Rotates 180 degrees or + Pi radians. */
  override def rotate180: ThisT = fTrans(_.rotate180)

  /** Rotates 90 degrees or Pi/2 radians clockwise. */
  def rotate270: ThisT = fTrans(_.rotate270)

  override def prolign(matrix: ProlignMatrix): ThisT = fTrans(_.prolignTrans(matrix))
}