/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A class that can preserve its type through all the Prolign, proportionate XY axes aligned transformations, using a [[Pt2]] => [[Pt2]] function.
 *  These are translate [[Slate]], [[Scale]] and negX and negY, the [[TransAxes]], transformations.
 *  */
trait ProlignPreserve extends Any with GeomElem
{
  /** The most narrow type preserved in some 2d geometric transformations. */
  type ThisT <: ProlignPreserve

  /** A method to perform all the [[ProlignPreserve]] transformations with a function from PT2 => PT2. */
  def ptsTrans(f: Pt2 => Pt2): ThisT
  override def slateXY(xDelta: Double, yDelta: Double): ThisT = ptsTrans(_.addXY(xDelta, yDelta))
  override def scale(operand: Double): ThisT = ptsTrans(_.scale(operand))
  override def negX: ThisT = ptsTrans(_.negX)
  override def negY: ThisT = ptsTrans(_.negY)
  override def prolign(matrix: ProlignMatrix): ThisT = ptsTrans(_.prolign(matrix))
}