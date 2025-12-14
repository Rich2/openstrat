/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** ShapeFill element that is a [[CanvElem]] */
trait CanvShapeFill extends ShapeFill, CanvElem
{ override def slate(operand: VecPt2): CanvShapeFill
  override def slate(xOperand: Double, yOperand: Double): CanvShapeFill
  override def slateX(xOperand: Double): CanvShapeFill
  override def slateY(yOperand: Double): CanvShapeFill
  override def scale(operand: Double): CanvShapeFill
  override def negY: CanvShapeFill
  override def negX: CanvShapeFill
  override def prolign(matrix: AxlignMatrix): CanvShapeFill
  override def rotate90: CanvShapeFill
  override def rotate180: CanvShapeFill
  override def rotate270: CanvShapeFill
  override def rotate(rotation: AngleVec): CanvShapeFill
  override def mirror(lineLike: LineLike): CanvShapeFill
  override def scaleXY(xOperand: Double, yOperand: Double): CanvShapeFill
  override def shearX(operand: Double): CanvShapeFill
  override def shearY(operand: Double): CanvShapeFill
}