/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

/** A shape based graphic. */
trait ShapeGraphic extends GraphicElem
{ def shape: Shape
  def attribs: Arr[XmlAtt]
  def svgStr: String
  def shapeAttribs: Arr[XmlAtt] = shape.attribs
  final def svgInline: String = SvgSvgElem(shape.boundingRect.minX, shape.boundingRect.minY, shape.boundingRect.width, shape.boundingRect.height,
    svgJustElem).out(0, 0, 150)
  def svgOut(indent: Int = 0, linePosn: Int = 0, lineLen: Int = 150): String = svgJustElem.out(indent, linePosn, lineLen)
  final def svgJustElem: SvgElem = svgElem(shape.boundingRect)
  def svgElem(bounds: BoundingRect): SvgElem
  def cen: Vec2 = shape.cen

  /** Translate geometric transformation. */
  def slate(offset: Vec2): ShapeGraphic

  /** Translate geometric transformation. */
  def slate(xOffset: Double, yOffset: Double): ShapeGraphic

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  def scale(operand: Double): ShapeGraphic

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  def negY: ShapeGraphic

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  def negX: ShapeGraphic

  /** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
  def reflectXParallel(yOffset: Double): ShapeGraphic

  /** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
  def reflectYParallel(xOffset: Double): ShapeGraphic

  def prolign(matrix: ProlignMatrix): ShapeGraphic

  def rotateRadians(radians: Double): ShapeGraphic

  def reflect(line: Line): ShapeGraphic

  override def xyScale(xOperand: Double, yOperand: Double): ShapeGraphic  
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
  
  implicit val slateImplicit: Slate[ShapeGraphic] = (obj: ShapeGraphic, offset: Vec2) => obj.slate(offset)
  implicit val scaleImplicit: Scale[ShapeGraphic] = (obj: ShapeGraphic, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[ShapeGraphic] = (obj: ShapeGraphic, radians: Double) => obj.rotateRadians(radians)
  implicit val XYScaleImplicit: XYScale[ShapeGraphic] = (obj, xOperand, yOperand) => obj.xyScale(xOperand, yOperand)
  implicit val prolignImplicit: Prolign[ShapeGraphic] = (obj, matrix) => obj.prolign(matrix)

  implicit val reflectAxesImplicit: ReflectAxes[ShapeGraphic] = new ReflectAxes[ShapeGraphic]
  { override def negYT(obj: ShapeGraphic): ShapeGraphic = obj.negY
    override def negXT(obj: ShapeGraphic): ShapeGraphic = obj.negX
  }

  implicit val reflectAxisOffsetImplicit: ReflectAxesOffset[ShapeGraphic] = new ReflectAxesOffset[ShapeGraphic]
  { /** Reflect, mirror across a line parallel to the X axis. */
    override def reflectXOffsetT(obj: ShapeGraphic, yOffset: Double): ShapeGraphic = obj.reflectXParallel(yOffset)

    /** Reflect, mirror across a line parallel to the Y axis. */
    override def reflectYOffsetT(obj: ShapeGraphic, xOffset: Double): ShapeGraphic = obj.reflectYParallel(xOffset)
  }
}
