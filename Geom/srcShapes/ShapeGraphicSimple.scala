/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb.*

trait ShapeGraphicSimple extends ShapeGraphic, GraphicSimple, GraphicSvgElem
{ final def svgJustElem: SvgOwnLine = svgElem

  final def svgInline: HtmlSvg = HtmlSvg.bounds(shape.boundingRect, RArr(svgJustElem))
  def nonShapeAttribs: RArr[XAtt]
  final override def attribs: RArr[XAtt] = shapeAttribs ++ nonShapeAttribs
  override def slate(operand: VecPt2): ShapeGraphicSimple
  override def slate(xOperand: Double, yOperand: Double): ShapeGraphicSimple
  override def slateX(xOperand: Double): ShapeGraphicSimple
  override def slateY(yOperand: Double): ShapeGraphicSimple
  override def scale(operand: Double): ShapeGraphicSimple
  override def negY: ShapeGraphicSimple
  override def negX: ShapeGraphicSimple
  override def rotate90: ShapeGraphicSimple
  override def rotate180: ShapeGraphicSimple
  override def rotate270: ShapeGraphicSimple
  override def prolign(matrix: AxlignMatrix): ShapeGraphicSimple
  override def rotate(rotation: AngleVec): ShapeGraphicSimple
  override def reflect(lineLike: LineLike): ShapeGraphicSimple
  override def scaleXY(xOperand: Double, yOperand: Double): ShapeGraphicSimple
}

trait ShapeLen2GraphicSimple extends ShapeLen2Graphic, GraphicLen2Simple