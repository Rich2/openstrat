/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

case class AlignMatrix(scaleDelta: Double, xDelta: Double, yDelta: Double)
{ def vDelta: Vec2 = Vec2(xDelta, yDelta)
  def scale(operand: Double): AlignMatrix = AlignMatrix(scaleDelta * operand, xDelta * operand, yDelta * operand)
  def slate(operand: Vec2) : AlignMatrix = AlignMatrix(scaleDelta, xDelta + operand.x, yDelta + operand.y)
  def slate(xOperand: Double, yOperand: Double) : AlignMatrix = AlignMatrix(scaleDelta, xDelta + xOperand, yDelta + yOperand)
}
