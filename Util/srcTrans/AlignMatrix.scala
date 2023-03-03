/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat; package geom

case class AlignMatrix(xFactor: Double, yFactor: Double, xDelta: Double, yDelta: Double)
{ def vDelta: Pt2 = Pt2(xDelta, yDelta)
  def scale(operand: Double): AlignMatrix = AlignMatrix(xFactor * operand, yFactor * operand, xDelta * operand, yDelta * operand)
  def slate(operand: Pt2): AlignMatrix = AlignMatrix(xFactor, yFactor, xDelta + operand.x, yDelta + operand.y)
  def slate(xOperand: Double, yOperand: Double): AlignMatrix = AlignMatrix(xFactor, yFactor, xDelta + xOperand, yDelta + yOperand)
  def xyScale(xOperand: Double, yOperand: Double): AlignMatrix = AlignMatrix(xFactor * xOperand, yFactor * yOperand, xDelta * xOperand, yDelta * yOperand)
}
