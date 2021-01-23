/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A class that can preserve its type through all the Prolign, proportionate XY axes aligned transformations, using a [[Pt2]] => [[Pt2]] function.
 *  These are translate [[Slate]], [[Scale]] and negX and negY, the [[TransAxes]], transformations.
 *  */
trait ProlignPreserve extends GeomElem
{
  /** The most narrow type preserved in some 2d geometric transformations. */
  type ThisT <: ProlignPreserve

  /** A method to perform all the [[ProlignPreserve]] transformations with a function from PT2 => PT2. */
  def fTrans(f: Pt2 => Pt2): ThisT
  override def slateXY(xOffset: Double, yOffset: Double): ThisT = fTrans(_.addXY(xOffset, yOffset))
  override def scale(operand: Double): ThisT = fTrans(_.scale(operand))
  override def negX: ThisT = fTrans(_.negX)
  override def negY: ThisT = fTrans(_.negY)
  override def prolign(matrix: ProlignMatrix): ThisT = fTrans(_.prolign(matrix))
}