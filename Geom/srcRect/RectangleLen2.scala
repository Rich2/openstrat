/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A Rectangle defined in [[Length]] units. */
trait RectangleLen2 extends ShapeLen2
{ override def slate(operand: VecPtLen2): RectangleLen2
  override def slate(xOperand: Length, yOperand: Length): RectangleLen2
  override def slateX(xOperand: Length): RectangleLen2
  override def slateY(yOperand: Length): RectangleLen2
  override def scale(operand: Double): RectangleLen2
  override def mapGeom2(operand: Length): Rectangle
}
/** A polygon graphic where the point are specified in [[Length]] units. */
trait RectangleLen2Graphic extends ShapeLen2Graphic
{ override def shape: RectangleLen2
  override def slate(operand: VecPtLen2): RectangleLen2Graphic
  override def slate(xOperand: Length, yOperand: Length): RectangleLen2Graphic
  override def slateX(xOperand: Length): RectangleLen2Graphic
  override def slateY(yOperand: Length): RectangleLen2Graphic
  override def scale(operand: Double): RectangleLen2Graphic
  override def mapGeom2(operand: Length): RectangleGraphic
}