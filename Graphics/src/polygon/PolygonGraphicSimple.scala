/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

/** This trait may be removed. */
trait PolygonGraphicSimple extends PolygonGraphic with ShapeGraphicSimple
{ def xHead: Double = shape.x1
  def yHead: Double = shape.y1

  /** The number of vertices. */
  def vertsLen: Int = shape.vertsNum

  /** Checks for 2 or more vertices */
  def ifv2: Boolean = shape.vertsNum >= 2

  /** Checks for 3 or more vertices */
  def ifv3: Boolean = shape.vertsNum >= 3

  def xArray: Array[Double] = shape.xVertsArray//elem1sArray
  def yArray: Array[Double] = shape.yVertsArray//elem2sArray
  override def boundingRect: BoundingRect = shape.boundingRect
  def svgStr: String = tagVoidStr("rect", attribs)
  override def svgElem(bounds: BoundingRect): SvgElem = ???  

  override def xShear(operand: Double): PolygonGraphicSimple

  override def yShear(operand: Double): PolygonGraphicSimple

  override def reflect(lineLike: LineLike): PolygonGraphicSimple

  override def nonShapeAttribs: Arr[XmlAtt] = ???

  /** Translate geometric transformation. */
  override def slate(offset: Vec2Like): PolygonGraphicSimple

  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): PolygonGraphicSimple

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): PolygonGraphicSimple

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negY: PolygonGraphicSimple

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negX: PolygonGraphicSimple

  override def prolign(matrix: ProlignMatrix): PolygonGraphicSimple

  override def rotate(angle: Angle): PolygonGraphicSimple

  override def xyScale(xOperand: Double, yOperand: Double): PolygonGraphicSimple
}

/** Companion object for the PolygonGraphicSimple trait, contains implicit instances for the 2D geometric transformation classes. */
object PolygonGraphicSimple
{
  implicit val slateImplicit: Slate[PolygonGraphicSimple] = (obj: PolygonGraphicSimple, offset: Vec2Like) => obj.slate(offset)
  implicit val scaleImplicit: Scale[PolygonGraphicSimple] = (obj: PolygonGraphicSimple, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[PolygonGraphicSimple] = (obj: PolygonGraphicSimple, angle: Angle) => obj.rotate(angle)
  implicit val XYScaleImplicit: XYScale[PolygonGraphicSimple] = (obj, xOperand, yOperand) => obj.xyScale(xOperand, yOperand)
  implicit val prolignImplicit: Prolign[PolygonGraphicSimple] = (obj, matrix) => obj.prolign(matrix)

  implicit val reflectAxesImplicit: ReflectAxes[PolygonGraphicSimple] = new ReflectAxes[PolygonGraphicSimple]
  { override def negYT(obj: PolygonGraphicSimple): PolygonGraphicSimple = obj.negY
    override def negXT(obj: PolygonGraphicSimple): PolygonGraphicSimple = obj.negX
  }
}

/** A pointable polygon without visual */
case class PolygonActive(shape: Polygon, pointerId: Any) extends GraphicAffineElem with GraphicClickable with PolygonGraphic
{ override type ThisT = PolygonActive
  override def fTrans(f: Pt2 => Pt2): PolygonActive = PolygonActive(shape.fTrans(f), pointerId)
  override def boundingRect = shape.boundingRect

  override def slateTo(newCen: Pt2): PolygonActive = ???
  override def ptInside(pt: Pt2): Boolean = shape.ptInside(pt)

  override def attribs: Arr[XmlAtt] = ???

  override def svgStr: String = ???
}