/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import ostrat.pWeb.*, pgui.*

/** To be phased out. */
final case class ShapeGenFillOld(shape: ShapeGenOld, colour: Colour) extends CanvElem with AxisFree
{
  override type ThisT = ShapeGenFillOld
  /** Renders this functional immutable GraphicElem, using the imperative methods of the abstract [[pCanv.CanvasPlatform]] interface. */
  override def rendToCanvas(cp: CanvasPlatform): Unit = { deb("Not implemented.")}

  override def slate(operand: VecPt2): ShapeGenFillOld = ShapeGenFillOld(shape.slate(operand), colour)
  override def slateXY(xOperand: Double, yOperand: Double): ShapeGenFillOld = ShapeGenFillOld(shape.slateXY(xOperand, yOperand), colour)
  override def scale(operand: Double): ShapeGenFillOld = ShapeGenFillOld(shape.scale(operand), colour)
  override def prolign(matrix: ProlignMatrix): ShapeGenFillOld = ShapeGenFillOld(shape.prolign(matrix), colour)
  override def rotate(angle: AngleVec): ShapeGenFillOld = ShapeGenFillOld(shape.rotate(angle), colour)
  override def reflect(lineLike: LineLike): ShapeGenFillOld = ShapeGenFillOld(shape.reflect(lineLike), colour)
  override def scaleXY(xOperand: Double, yOperand: Double): ShapeGenFillOld = ???
  override def shearX(operand: Double): ShapeGenFillOld = ???
  override def shearY(operand: Double): ShapeGenFillOld = ???
  override def svgElems: RArr[SvgElem] = ???
}
