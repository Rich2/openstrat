/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import ostrat.pWeb.*

/** Base trait for a compound shape graphic in various geometries */
trait ShapeGeomlessCompound
{ /** Graphical facet such as fills, drawing lines and active. */
  def facets: RArr[GraphicFacet]
}

/** A shape based compound graphic. The return types of methods will be narrowed in subclasses. */
trait ShapeCompound extends ShapeGeomlessCompound, ShapeGraphic, NoCanvElem
{ final override def attribs: RArr[XmlAtt] = shapeAttribs ++ facets.flatMap(_.attribs)
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

  override def prolign(matrix: AxlignMatrix): ShapeCompound
  override def rotate90: ShapeCompound
  override def rotate180: ShapeCompound
  override def rotate270: ShapeCompound

  /** Functionally adds more child [[Graphic2Elem]] graphics. These child graphics will be at placed in front of previous children. */
  def addChildren(newChildren: Arr[Graphic2Elem]): ShapeCompound
}

/** Companion object for the [[ShapeCompound]] trait, contains implicit instances for 2D geometric transformation type classes for common collection and other
 * container classes. */
object ShapeCompound
{ /** Implicit [[Slate2]] type class instance / instance for [[ShapeCompound]]. */
  given slate2Ev: Slate2[ShapeCompound] = new Slate2[ShapeCompound]
  { override def slate(obj: ShapeCompound, operand: VecPt2): ShapeCompound = obj.slate(operand)
    override def slateXY(obj: ShapeCompound, xOperand: Double, yOperand: Double): ShapeCompound = ???
  }
  
  /** Implicit [[Scale]] type class instance / instance for [[ShapeCompound]]. */
  given scaleEv: Scale[ShapeCompound] = (obj: ShapeCompound, operand: Double) => obj.scale(operand)
  
  /** Implicit [[Rotate]] type class instance / instance for [[ShapeCompound]]. */
  given rotateEv: Rotate[ShapeCompound] = (obj: ShapeCompound, angle: AngleVec) => obj.rotate(angle)
  
  /** Implicit [[TrabsAxes]] type class instance / instance for [[ShapeCompound]]. */
  given transAxesEv: TransAxes[ShapeCompound] = new TransAxes[ShapeCompound]
  { override def negYT(obj: ShapeCompound): ShapeCompound = obj.negY
    override def negXT(obj: ShapeCompound): ShapeCompound = obj.negX
    override def rotate90(obj: ShapeCompound): ShapeCompound = obj.rotate90
    override def rotate180(obj: ShapeCompound): ShapeCompound = obj.rotate180
    override def rotate270(obj: ShapeCompound): ShapeCompound = obj.rotate270
  }
  
  /** Implicit [[Prolign]] type class instance / instance for [[ShapeCompound]]. */
  given prolignEv: Prolign[ShapeCompound] = (obj, matrix) => obj.prolign(matrix)  
}

/** A compound shape graphic specified in [[Length]] units. */
trait ShapeLen2Compound extends ShapeGeomlessCompound, ShapeLen2Graphic