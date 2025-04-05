/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pgui.*, Colour.Black, pWeb.SvgElem

/** To be phased out. */
case class ShapeGenDrawOld(shape: ShapeGenOld, lineColour: Colour = Black, lineWidth: Double = 2) extends CanvElem with AxisFree
{ override type ThisT = ShapeGenDrawOld  
  override def rendToCanvas(cp: CanvasPlatform): Unit = { deb("Not implemented.") }
  override def slate(operand: VecPt2): ShapeGenDrawOld = ShapeGenDrawOld(shape.slate(operand), lineColour, lineWidth)
  override def slate(xOperand: Double, yOperand: Double): ShapeGenDrawOld = ShapeGenDrawOld(shape.slate(xOperand, yOperand), lineColour, lineWidth)
  override def slateX(xOperand: Double): ShapeGenDrawOld = ShapeGenDrawOld(shape.slateX(xOperand), lineColour, lineWidth)
  override def slateY(yOperand: Double): ShapeGenDrawOld = ShapeGenDrawOld(shape.slateY(yOperand), lineColour, lineWidth)
  override def scale(operand: Double): ShapeGenDrawOld = ShapeGenDrawOld(shape.scale(operand), lineColour, lineWidth)
  override def prolign(matrix: ProlignMatrix): ShapeGenDrawOld = ShapeGenDrawOld(shape.prolign((matrix)), lineColour, lineWidth)
  override def rotate(rotation: AngleVec): ShapeGenDrawOld = ShapeGenDrawOld(shape.rotate(rotation), lineColour, lineWidth)
  override def reflect(lineLike: LineLike): ShapeGenDrawOld = ShapeGenDrawOld(shape.reflect(lineLike), lineColour, lineWidth)
  override def scaleXY(xOperand: Double, yOperand: Double): ShapeGenDrawOld = ShapeGenDrawOld(shape.scaleXY(xOperand, yOperand), lineColour, lineWidth)
  override def shearX(operand: Double): ShapeGenDrawOld = ShapeGenDrawOld(shape.shearX(operand), lineColour, lineWidth)
  override def shearY(operand: Double): ShapeGenDrawOld = ShapeGenDrawOld(shape.shearY(operand), lineColour, lineWidth)
  override def svgElems: RArr[SvgElem] = ???
}