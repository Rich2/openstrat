/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import ostrat.pWeb._

/** Base trait for a compound shape graphic in various geometries */
trait ShapeGeomlessCompound
{ /** Graphical facet such as fills, drawing lines and active. */
  def facets: RArr[GraphicFacet]
}

/** A shape based compound graphic. The return types of methods will be narrowed in subclasses. */
trait ShapeCompound extends ShapeGeomlessCompound, ShapeGraphic, NoCanvElem
{
  final override def attribs: RArr[XmlAtt] = shapeAttribs ++ facets.flatMap(_.attribs)
  override def canvElems: RArr[CanvElem] = ???
  def mainSvgElem: SvgElem

  override def svgElems: RArr[SvgElem] = RArr(mainSvgElem) ++ children.flatMap(_.svgElems)
  override def svgInline: HtmlSvg = ???

  /** The [[ShapeCompound]] type will be widened at a later point. */
  def children: RArr[Graphic2Elem]

  override def slate(operand: VecPt2): ShapeCompound
  override def slate(xOperand: Double, yOperand: Double): ShapeCompound
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

  /** Functionally adds more child [[Graphic2Elem]] graphics. These child graphics will be at placed in front of previous children. */
  def addChildren(newChildren: Arr[Graphic2Elem]): ShapeCompound
}

/** Companion object for the [[ShapeCompound]] trait, contains implicit instances for 2D geometric transoframtion type classes for common collection
 *  and other containner classes. */
object ShapeCompound
{
  /** Implicit [[Slate]] type class instance / instance for [[ShapeCompound]]. */
  implicit val slateEv: Slate[ShapeCompound] = (obj, operand) => obj.slate(operand)

  /** Implicit [[SlateXY]] type class instance / instance for [[ShapeCompound]]. */
  implicit val slateXYEv: SlateXY[ShapeCompound] = (obj: ShapeCompound, dx: Double, dy: Double) => obj.slate(dx, dy)
  
  /** Implicit [[Scale]] type class instance / instance for [[ShapeCompound]]. */
  implicit val scaleEv: Scale[ShapeCompound] = (obj: ShapeCompound, operand: Double) => obj.scale(operand)
  
  /** Implicit [[Rotate]] type class instance / instance for [[ShapeCompound]]. */
  implicit val rotateEv: Rotate[ShapeCompound] = (obj: ShapeCompound, angle: AngleVec) => obj.rotate(angle)
  
  /** Implicit [[TrabsAxes]] type class instance / instance for [[ShapeCompound]]. */
  implicit val transAxesEv: TransAxes[ShapeCompound] = new TransAxes[ShapeCompound]
  { override def negYT(obj: ShapeCompound): ShapeCompound = obj.negY
    override def negXT(obj: ShapeCompound): ShapeCompound = obj.negX
    override def rotate90(obj: ShapeCompound): ShapeCompound = obj.rotate90
    override def rotate180(obj: ShapeCompound): ShapeCompound = obj.rotate180
    override def rotate270(obj: ShapeCompound): ShapeCompound = obj.rotate270
  }
  
  /** Implicit [[Prolign]] type class instance / instance for [[ShapeCompound]]. */
  implicit val prolignEv: Prolign[ShapeCompound] = (obj, matrix) => obj.prolign(matrix)  
}

/** A compound shape graphic specified in [[Length]] units. */
trait ShapeLen2Compound extends ShapeGeomlessCompound, ShapeLen2Graphic