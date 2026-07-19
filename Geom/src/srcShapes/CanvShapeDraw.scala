/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait CanvShapeDraw extends ShapeDraw, CanvElem
{ override def slate(offset: VecPt2): CanvShapeDraw
  override def slate(xOperand: Double, yOperand: Double): CanvShapeDraw
  override def slateFrom(offset: VecPt2): CanvShapeDraw
  override def slateFrom(xOperand: Double, yOperand: Double): CanvShapeDraw
  override def slateX(xOperand: Double): CanvShapeDraw
  override def slateY(yOperand: Double): CanvShapeDraw
  override def scale(operand: Double): CanvShapeDraw
  override def negX: CanvShapeDraw
  override def negY: CanvShapeDraw
  override def prolign(matrix: AxlignMatrix): CanvShapeDraw
  override def rotate90: CanvShapeDraw
  override def rotate180: CanvShapeDraw
  override def rotate270: CanvShapeDraw
  override def rotate(rotation: AngleVec): CanvShapeDraw
  override def mirror(lineLike: LineLike): CanvShapeDraw
  override def scaleXY(xOperand: Double, yOperand: Double): CanvShapeDraw
  override def shearX(operand: Double): CanvShapeDraw
  override def shearY(operand: Double): CanvShapeDraw
}