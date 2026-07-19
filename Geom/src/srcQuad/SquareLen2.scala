/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait SquareLen2[+VT <: PtLen2] extends RectangleLen2[VT]
{ override def slate(operand: VecPtLen2): SquareLen2[VT]
  override def slate(xOperand: Length, yOperand: Length): SquareLen2[VT]
  override def slateX(xOperand: Length): SquareLen2[VT]
  override def slateY(yOperand: Length): SquareLen2[VT]
  override def scale(operand: Double): SquareLen2[VT]
  override def mapGeom2(operand: Length): Square
}

trait SquareLen2Graphic extends RectangleLen2Graphic
{
  override def shape: SquareLen2[PtLen2]
}

trait SquareLen2Fill extends ShapeLen2Graphic, RectangleLen2Graphic