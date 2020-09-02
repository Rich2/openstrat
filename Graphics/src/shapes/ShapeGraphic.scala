/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

/** A shape based graphic. */
trait ShapeGraphic extends DisplayElem
{ def shape: Shape
  def facets: Arr[ShapeFacet]
  
  final def svgInline: String = SvgSvgElem(shape.boundingRect.minX, shape.boundingRect.minY, shape.boundingRect.width, shape.boundingRect.height,
    svgJustElem).out(0, 0, 150)
  
  /** The [[ShapeGraphic]] type will be widened at a later point. */
  def children: Arr[ShapeGraphic]
  
  def svgOut(indent: Int = 0, linePosn: Int = 0, lineLen: Int = 150): String = svgJustElem.out(indent, linePosn, lineLen)
  final def svgJustElem: SvgElem = svgElem(shape.boundingRect)
  def svgElem(bounds: BoundingRect): SvgElem
  /** Translate geometric transformation. */
  override def slate(offset: Vec2): ShapeGraphic

  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): ShapeGraphic

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): ShapeGraphic

  /** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
  override def reflectYOffset(xOffset: Double): ShapeGraphic

  /** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
  override def reflectXOffset(yOffset: Double): ShapeGraphic

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def reflectX: ShapeGraphic

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def reflectY: ShapeGraphic

  override def prolign(matrix: ProlignMatrix): ShapeGraphic

  /** Rotates 90 degrees or Pi/2 radians anticlockwise. */
  override def rotate90: ShapeGraphic

  /** Rotates 180 degrees or Pi radians. */
  override def rotate180: ShapeGraphic

  /** Rotates 90 degrees or Pi/2 radians clockwise. */
  override def rotate270: ShapeGraphic

  override def rotateRadians(radians: Double): ShapeGraphic

  override def reflect(line: Line): ShapeGraphic

  override def scaleXY(xOperand: Double, yOperand: Double): ShapeGraphic
  override def shearX(operand: Double): ShapeGraphic

  override def shearY(operand: Double): ShapeGraphic

  override def reflect(line: Sline): ShapeGraphic
}

object ShapeGraphic
{
  implicit class ShapeGraphicArrImplicit(val thisArr: Arr[ShapeGraphic])
  {
    def svgInline: String =
    { val br = thisArr.foldLeft(thisArr.head.shape.boundingRect)(_ || _.shape.boundingRect)
      SvgSvgElem(br.minX, br.minY, br.width, br.height, thisArr.map(_.svgElem(br))).out(0, 0, 150)
    }
  }

  implicit val slateImplicit: Slate[ShapeGraphic] = (obj: ShapeGraphic, offset: Vec2) => obj.slate(offset)
  implicit val scaleImplicit: Scale[ShapeGraphic] = (obj: ShapeGraphic, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[ShapeGraphic] = (obj: ShapeGraphic, radians: Double) => obj.rotateRadians(radians)

  implicit val reflectAxisImplicit: ReflectAxis[ShapeGraphic] = new ReflectAxis[ShapeGraphic]
  { /** Reflect, mirror across the X axis. */
    override def reflectXT(obj: ShapeGraphic): ShapeGraphic = obj.reflectX

    /** Reflect, mirror across the Y axis. */
    override def reflectYT(obj: ShapeGraphic): ShapeGraphic = obj.reflectY
  }
  
  implicit val reflectAxisOffsetImplicit: ReflectAxisOffset[ShapeGraphic] = new ReflectAxisOffset[ShapeGraphic]
  { /** Reflect, mirror across a line parallel to the X axis. */
    override def reflectXOffsetT(obj: ShapeGraphic, yOffset: Double): ShapeGraphic = obj.reflectXOffset(yOffset)

    /** Reflect, mirror across a line parallel to the Y axis. */
    override def reflectYOffsetT(obj: ShapeGraphic, xOffset: Double): ShapeGraphic = obj.reflectYOffset(xOffset)
  }

  implicit val rotateAxesImplicit: RotateAxes[ShapeGraphic] = new RotateAxes[ShapeGraphic]
  { /** Rotates object of type T, 90 degrees or Pi/2 radians anticlockwise. */
    override def rotateT90(obj: ShapeGraphic): ShapeGraphic = obj.rotate90

    /** Rotates object of type T, 180 degrees or Pi radians. */
    override def rotateT180(obj: ShapeGraphic): ShapeGraphic = obj.rotate180

    /** Rotates object of type T, 90 degrees or Pi/2 radians clockwise. */
    override def rotateT270(obj: ShapeGraphic): ShapeGraphic = obj.rotate270
  }

  implicit val prolignImplicit: Prolign[ShapeGraphic] = (obj, matrix) => obj.prolign(matrix)  
}