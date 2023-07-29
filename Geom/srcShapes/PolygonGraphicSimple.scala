/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pgui._, pWeb._

/** This trait may be removed. */
trait PolygonGraphicSimple extends PolygonGraphic with ShapeGraphicSimple
{
  override def boundingRect: Rect = shape.boundingRect
  override def svgElem: SvgElem = SvgPolygon(attribs)

  override def shearX(operand: Double): PolygonGraphicSimple

  override def shearY(operand: Double): PolygonGraphicSimple

  override def reflect(lineLike: LineLike): PolygonGraphicSimple

  //override def nonShapeAttribs: RArr[XmlAtt] = ???

  /** Translate geometric transformation. */
  override def slateXY(xDelta: Double, yDelta: Double): PolygonGraphicSimple

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

  override def rotate90: PolygonGraphicSimple
  override def rotate180: PolygonGraphicSimple
  override def rotate270: PolygonGraphicSimple

  override def rotate(angle: AngleVec): PolygonGraphicSimple

  override def scaleXY(xOperand: Double, yOperand: Double): PolygonGraphicSimple
}

/** Companion object for the PolygonGraphicSimple trait, contains implicit instances for the 2D geometric transformation classes. */
object PolygonGraphicSimple
{
  implicit val slateImplicit: Slate[PolygonGraphicSimple] = (obj: PolygonGraphicSimple, dx: Double, dy: Double) => obj.slateXY(dx, dy)
  implicit val scaleImplicit: Scale[PolygonGraphicSimple] = (obj: PolygonGraphicSimple, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[PolygonGraphicSimple] = (obj: PolygonGraphicSimple, angle: AngleVec) => obj.rotate(angle)
  implicit val XYScaleImplicit: ScaleXY[PolygonGraphicSimple] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)
  implicit val prolignImplicit: Prolign[PolygonGraphicSimple] = (obj, matrix) => obj.prolign(matrix)

  implicit val reflectAxesImplicit: TransAxes[PolygonGraphicSimple] = new TransAxes[PolygonGraphicSimple]
  { override def negYT(obj: PolygonGraphicSimple): PolygonGraphicSimple = obj.negY
    override def negXT(obj: PolygonGraphicSimple): PolygonGraphicSimple = obj.negX
    override def rotate90(obj: PolygonGraphicSimple): PolygonGraphicSimple = obj.rotate90
    override def rotate180(obj: PolygonGraphicSimple): PolygonGraphicSimple = obj.rotate180
    override def rotate270(obj: PolygonGraphicSimple): PolygonGraphicSimple = obj.rotate270
  }
}

/** A pointable polygon without visual. */
case class PolygonActive(shape: Polygon, pointerId: Any) extends GraphicAffineElem with GraphicClickable with PolygonGraphicSimple
{ override type ThisT = PolygonActive
  override def ptsTrans(f: Pt2 => Pt2): PolygonActive = PolygonActive(shape.vertsTrans(f), pointerId)
  override def boundingRect: Rect = shape.boundingRect

  /** Renders this functional immutable GraphicElem, using the imperative methods of the abstract [[pCanv.CanvasPlatform]] interface. */
  override def rendToCanvas(cp: CanvasPlatform): Unit = { deb("Not implemented.")}

  //override def slateTo(newCen: Pt2): PolygonActive = ???
  override def ptInside(pt: Pt2): Boolean = shape.ptInside(pt)

  override def nonShapeAttribs: RArr[XmlAtt] = ???

  override def svgStr: String = ???
}