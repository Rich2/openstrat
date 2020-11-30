/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

/** A shape based graphic. */
trait ShapeGraphic extends BoundedGraphic
{ def shape: Shape
  override def boundingRect: BoundingRect = shape.boundingRect
  def attribs: Arr[XmlAtt]
  def svgStr: String
  def shapeAttribs: Arr[XmlAtt] = shape.attribs
  final def svgInline: String = SvgSvgElem(shape.boundingRect.minX, shape.boundingRect.minY, shape.boundingRect.width, shape.boundingRect.height,
    svgJustElem).out(0, 0, 150)
  def svgOut(indent: Int = 0, linePosn: Int = 0, lineLen: Int = 150): String = svgJustElem.out(indent, linePosn, lineLen)
  final def svgJustElem: SvgElem = svgElem(shape.boundingRect)
  def svgElem(bounds: BoundingRect): SvgElem

  /** Translate geometric transformation. */
  def slate(xOffset: Double, yOffset: Double): ShapeGraphic

  /** Translate geometric transformation. */
  def slate(offset: Vec2Like): ShapeGraphic

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  def scale(operand: Double): ShapeGraphic

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  def negY: ShapeGraphic

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  def negX: ShapeGraphic

  def prolign(matrix: ProlignMatrix): ShapeGraphic

  def rotate(angle: Angle): ShapeGraphic

  def reflect(lineLike: LineLike): ShapeGraphic

  override def xyScale(xOperand: Double, yOperand: Double): ShapeGraphic

  override def xCen: Double = shape.xCen
  override def yCen: Double = shape.yCen
  override def cen: Pt2 = shape.cen
}

/** Companion object for the ShapeGraphic class. */
object ShapeGraphic
{
  implicit class ArrImplicit(val thisArr: Arr[ShapeGraphic])
  {
    def svgList: String = thisArr.foldLeft("")(_ + "\n" + _.svgStr)

    def svgInline(indent: Int = 0, linePosn: Int = 0, lineLen: Int = 150): String =
    { val br = thisArr.foldLeft(thisArr.head.shape.boundingRect)(_ || _.shape.boundingRect)
      SvgSvgElem(br.minX, br.minY, br.width, br.height, thisArr.map(_.svgElem(br))).out(indent, linePosn, lineLen)
    }
  }
  
  implicit val slateImplicit: Slate[ShapeGraphic] = (obj: ShapeGraphic, dx: Double, dy: Double) => obj.slate(dx, dy)
  implicit val scaleImplicit: Scale[ShapeGraphic] = (obj: ShapeGraphic, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[ShapeGraphic] = (obj: ShapeGraphic, angle: Angle) => obj.rotate(angle)
  implicit val XYScaleImplicit: XYScale[ShapeGraphic] = (obj, xOperand, yOperand) => obj.xyScale(xOperand, yOperand)
  implicit val prolignImplicit: Prolign[ShapeGraphic] = (obj, matrix) => obj.prolign(matrix)

  implicit val reflectAxesImplicit: ReflectAxes[ShapeGraphic] = new ReflectAxes[ShapeGraphic]
  { override def negYT(obj: ShapeGraphic): ShapeGraphic = obj.negY
    override def negXT(obj: ShapeGraphic): ShapeGraphic = obj.negX
  }
}