/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb._

/** A shape based graphic. */
trait ShapeGraphic extends GraphicBounded
{ def shape: Shape
  override def boundingRect: Rect = shape.boundingRect
  def attribs: RArr[XmlAtt]
  def shapeAttribs: RArr[XmlAtt] = shape.attribs

  def svgInline: HtmlSvg

  final def svgInlineStr: String = svgInline.out(0, 150)

  /** Translate geometric transformation. */
  def slateXY(xDelta: Double, yDelta: Double): ShapeGraphic

  /** Translate geometric transformation. */
  //def slate(offset: Vec2Like): ShapeGraphic

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  def scale(operand: Double): ShapeGraphic

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  def negY: ShapeGraphic

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  def negX: ShapeGraphic

  override def rotate90: ShapeGraphic
  override def rotate180: ShapeGraphic
  override def rotate270: ShapeGraphic

  def prolign(matrix: ProlignMatrix): ShapeGraphic

  def rotate(angle: AngleVec): ShapeGraphic

  def reflect(lineLike: LineLike): ShapeGraphic

  override def scaleXY(xOperand: Double, yOperand: Double): ShapeGraphic
}

/** Companion object for the ShapeGraphic class. */
object ShapeGraphic
{
  implicit class ArrImplicit(val thisArr: RArr[ShapeGraphic])
  {
    def svgInline(indent: Int = 0, linePosn: Int = 0, lineLen: Int = 150): String = ???
  }
  
  implicit val slateImplicit: Slate[ShapeGraphic] = (obj: ShapeGraphic, dx: Double, dy: Double) => obj.slateXY(dx, dy)
  implicit val scaleImplicit: Scale[ShapeGraphic] = (obj: ShapeGraphic, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[ShapeGraphic] = (obj: ShapeGraphic, angle: AngleVec) => obj.rotate(angle)
  implicit val XYScaleImplicit: ScaleXY[ShapeGraphic] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)
  implicit val prolignImplicit: Prolign[ShapeGraphic] = (obj, matrix) => obj.prolign(matrix)

  implicit val reflectAxesImplicit: TransAxes[ShapeGraphic] = new TransAxes[ShapeGraphic]
  { override def negYT(obj: ShapeGraphic): ShapeGraphic = obj.negY
    override def negXT(obj: ShapeGraphic): ShapeGraphic = obj.negX
    override def rotate90(obj: ShapeGraphic): ShapeGraphic = obj.rotate90
    override def rotate180(obj: ShapeGraphic): ShapeGraphic = obj.rotate180
    override def rotate270(obj: ShapeGraphic): ShapeGraphic = obj.rotate270
  }
}