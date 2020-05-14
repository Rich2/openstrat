/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** A matrix for proportionate and aligned to X and Y axes transformations. This transformation set preserves Circles and Squares. It also
 * preserves the alignment of Squares and Rectangle to the axes. */
final case class ProlignMatrix(vFactor: Double, negY: Boolean, negX: Boolean, xDelta: Double, yDelta: Double)
{ def vDelta: Vec2 = Vec2(xDelta, yDelta)
  def scale(operand: Double): ProlignMatrix = ProlignMatrix(vFactor * operand, negY, negX, xDelta * operand, yDelta * operand)
  def slate(operand: Vec2): ProlignMatrix = ProlignMatrix(vFactor, negY, negX, xDelta + operand.x, yDelta + operand.y)
  def slate(xOperand: Double, yOperand: Double): ProlignMatrix = ProlignMatrix(vFactor, negY, negX, xDelta + xOperand, yDelta + yOperand)

  /** Reflect, mirror across a line parallel to the X axis. */
  def mirrorXOffset(yOffset: Double): ProlignMatrix = ProlignMatrix(vFactor, !negY, negX, xDelta, yDelta + 2 * yOffset)

  /** Reflect, mirror across a line parallel to the X axis. */
  def mirrorYOffset(xOffset: Double): ProlignMatrix = ProlignMatrix(vFactor, negY, !negX, xDelta + 2 * xOffset, yDelta)

  def mirrorX: ProlignMatrix = ProlignMatrix(vFactor, !negY, negX, xDelta, yDelta)

  def mirrorY: ProlignMatrix = ProlignMatrix(vFactor, negY, !negX, xDelta, yDelta)
}

object ProlignMatrix
{
  def mirrorY: ProlignMatrix = ProlignMatrix(1, true, false, 0, 0)
}

