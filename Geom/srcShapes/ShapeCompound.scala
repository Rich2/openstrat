/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import ostrat.pWeb._

/** A shape based compound graphic. The return types of methods will be narrowed in sub classes. */
trait ShapeCompound extends ShapeGraphic with NoCanvElem
{ 
  def facets: RArr[GraphicFacet]
  final override def attribs: RArr[XmlAtt] = shapeAttribs ++ facets.flatMap(_.attribs)
  override def canvElems: RArr[CanvElem] = ???
  def mainSvgElem: SvgElem

  override def svgElems: RArr[SvgElem] = RArr(mainSvgElem) ++ children.flatMap(_.svgElems)
  override def svgInline: HtmlSvg = ???

  /** The [[ShapeCompound]] type will be widened at a later point. */
  def children: RArr[GraphicElem]

  override def slateXY(xDelta: Double, yDelta: Double): ShapeCompound
  override def scale(operand: Double): ShapeCompound
  override def negY: ShapeCompound
  override def negX: ShapeCompound

  override def prolign(matrix: ProlignMatrix): ShapeCompound
  override def rotate90: ShapeCompound
  override def rotate180: ShapeCompound
  override def rotate270: ShapeCompound
  override def rotate(angle: AngleVec): ShapeCompound
  override def reflect(lineLike: LineLike): ShapeCompound
  override def scaleXY(xOperand: Double, yOperand: Double): ShapeCompound
  override def shearX(operand: Double): ShapeCompound
  override def shearY(operand: Double): ShapeCompound

  /** Functionally adds more child [[GraphicElem]] graphics. These child graphics will be at placed in front of previous children. */
  def addChildren(newChildren: Arr[GraphicElem]): ShapeCompound
}

/** Companion object for the [[ShapeCompound]] trait, contains implicit instances for 2D geometric transoframtion type classes for common collection
 *  and other containner classes. */
object ShapeCompound
{
  implicit val slateImplicit: Slate[ShapeCompound] = (obj: ShapeCompound, dx: Double, dy: Double) => obj.slateXY(dx, dy)
  implicit val scaleImplicit: Scale[ShapeCompound] = (obj: ShapeCompound, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[ShapeCompound] = (obj: ShapeCompound, angle: AngleVec) => obj.rotate(angle)

  implicit val reflectAxesImplicit: TransAxes[ShapeCompound] = new TransAxes[ShapeCompound]
  { override def negYT(obj: ShapeCompound): ShapeCompound = obj.negY
    override def negXT(obj: ShapeCompound): ShapeCompound = obj.negX
    override def rotate90(obj: ShapeCompound): ShapeCompound = obj.rotate90
    override def rotate180(obj: ShapeCompound): ShapeCompound = obj.rotate180
    override def rotate270(obj: ShapeCompound): ShapeCompound = obj.rotate270
  }

  implicit val prolignImplicit: Prolign[ShapeCompound] = (obj, matrix) => obj.prolign(matrix)  
}