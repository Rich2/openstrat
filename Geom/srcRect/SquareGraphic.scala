/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait SquareGraphic extends RectangleGraphic
{ override def shape: Square
  override def slate(operand: VecPt2): SquareGraphic
  override def slate(xOperand: Double, yOperand: Double): SquareGraphic
  override def scale(operand: Double): SquareGraphic
  override def negY: SquareGraphic
  override def negX: SquareGraphic
}

trait SquareGraphicSimple extends SquareGraphic, RectangleGraphicSimple
{ override def slate(operand: VecPt2): SquareGraphicSimple
  override def slate(xOperand: Double, yOperand: Double): SquareGraphicSimple
  override def scale(operand: Double): SquareGraphicSimple
  override def negX: SquareGraphicSimple
  override def negY: SquareGraphicSimple
}

trait SquareFill extends SquareGraphicSimple, RectangleFill
{ override def slate(operand: VecPt2): SquareFill
  override def slate(xOperand: Double, yOperand: Double): SquareFill = ???
  override def scale(operand: Double): SquareFill = ???
  override def negX: SquareFill = ???
  override def negY: SquareFill = ???
}