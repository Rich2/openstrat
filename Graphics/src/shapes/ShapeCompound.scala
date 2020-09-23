/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A shape based compound graphic. */
trait ShapeCompound extends ShapeGraphic
{ 
  def facets: Arr[GraphicFacet]
  
  final def svgInline: String = SvgSvgElem(shape.boundingRect.minX, shape.boundingRect.minY, shape.boundingRect.width, shape.boundingRect.height,
    svgJustElem).out(0, 0, 150)
  
  /** The [[ShapeCompound]] type will be widened at a later point. */
  def children: Arr[ShapeCompound]
  
  def svgOut(indent: Int = 0, linePosn: Int = 0, lineLen: Int = 150): String = svgJustElem.out(indent, linePosn, lineLen)
  final def svgJustElem: SvgElem = svgElem(shape.boundingRect)
  def svgElem(bounds: BoundingRect): SvgElem
  /** Translate geometric transformation. */
  override def slate(offset: Vec2): ShapeCompound

  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): ShapeCompound

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): ShapeCompound  
  
  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def reflectX: ShapeCompound

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def reflectY: ShapeCompound
  
  /** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
  override def reflectXOffset(yOffset: Double): ShapeCompound

  /** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
  override def reflectYOffset(xOffset: Double): ShapeCompound

  override def prolign(matrix: ProlignMatrix): ShapeCompound

  override def rotateRadians(radians: Double): ShapeCompound

  override def reflect(line: Line): ShapeCompound

  override def xyScale(xOperand: Double, yOperand: Double): ShapeCompound
  override def xShear(operand: Double): ShapeCompound

  override def yShear(operand: Double): ShapeCompound

  override def reflect(line: LineSeg): ShapeCompound
}

/** Companion object for the [[ShapeCompound]] trait, contains implicit instances for 2D geometric transoframtion type classes for common collection
 *  and other containner classes. */
object ShapeCompound
{
  implicit class ShapeGraphicArrImplicit(val thisArr: Arr[ShapeCompound])
  {
    def svgInline(indent: Int = 0, linePosn: Int = 0, lineLen: Int = 150): String =
    { val br = thisArr.foldLeft(thisArr.head.shape.boundingRect)(_ || _.shape.boundingRect)
      SvgSvgElem(br.minX, br.minY, br.width, br.height, thisArr.map(_.svgElem(br))).out(indent, linePosn, lineLen)
    }
  }

  implicit val slateImplicit: Slate[ShapeCompound] = (obj: ShapeCompound, offset: Vec2) => obj.slate(offset)
  implicit val scaleImplicit: Scale[ShapeCompound] = (obj: ShapeCompound, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[ShapeCompound] = (obj: ShapeCompound, radians: Double) => obj.rotateRadians(radians)

  implicit val reflectAxisImplicit: ReflectAxis[ShapeCompound] = new ReflectAxis[ShapeCompound]
  { /** Reflect, mirror across the X axis. */
    override def reflectXT(obj: ShapeCompound): ShapeCompound = obj.reflectX

    /** Reflect, mirror across the Y axis. */
    override def reflectYT(obj: ShapeCompound): ShapeCompound = obj.reflectY
  }
  
  implicit val reflectAxisOffsetImplicit: ReflectAxisOffset[ShapeCompound] = new ReflectAxisOffset[ShapeCompound]
  { /** Reflect, mirror across a line parallel to the X axis. */
    override def reflectXOffsetT(obj: ShapeCompound, yOffset: Double): ShapeCompound = obj.reflectXOffset(yOffset)

    /** Reflect, mirror across a line parallel to the Y axis. */
    override def reflectYOffsetT(obj: ShapeCompound, xOffset: Double): ShapeCompound = obj.reflectYOffset(xOffset)
  }

  implicit val prolignImplicit: Prolign[ShapeCompound] = (obj, matrix) => obj.prolign(matrix)  
}