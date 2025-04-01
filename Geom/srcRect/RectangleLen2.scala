/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A Rectangle defined in [[Length]] units. */
trait RectangleLen2[+VT <: PtLen2] extends PolygonLen2[VT]
{ override def slate(operand: VecPtLen2): RectangleLen2[VT]
  override def slate(xOperand: Length, yOperand: Length): RectangleLen2[VT]
  override def slateX(xOperand: Length): RectangleLen2[VT]
  override def slateY(yOperand: Length): RectangleLen2[VT]
  override def scale(operand: Double): RectangleLen2[VT]
  override def mapGeom2(operand: Length): Rectangle
}

/** A polygon graphic where the point are specified in [[Length]] units. */
trait RectangleLen2Graphic extends PolygonLen2Graphic
{ override def shape: RectangleLen2[PtLen2]
  override def slate(operand: VecPtLen2): RectangleLen2Graphic
  override def slate(xOperand: Length, yOperand: Length): RectangleLen2Graphic
  override def slateX(xOperand: Length): RectangleLen2Graphic
  override def slateY(yOperand: Length): RectangleLen2Graphic
  override def scale(operand: Double): RectangleLen2Graphic
  override def mapGeom2(operand: Length): RectangleGraphic
}

trait RectangleLen2Fill extends RectangleLen2Graphic, PolygonLen2Fill
{ override def slate(operand: VecPtLen2): RectangleLen2Fill
  override def slate(xOperand: Length, yOperand: Length): RectangleLen2Fill
  override def slateX(xOperand: Length): RectangleLen2Fill
  override def slateY(yOperand: Length): RectangleLen2Fill
  override def scale(operand: Double): RectangleLen2Fill
  override def mapGeom2(operand: Length): RectangleFill
}