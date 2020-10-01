/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A shape based compound graphic. */
trait ShapeCompound extends ShapeGraphic
{ 
  def facets: Arr[GraphicFacet]
  
  /** The [[ShapeCompound]] type will be widened at a later point. */
  def children: Arr[GraphicElem]

  /** Translate geometric transformation. */
  override def slate(offset: Vec2): ShapeCompound

  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): ShapeCompound

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): ShapeCompound  
  
  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negY: ShapeCompound

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negX: ShapeCompound
  
  /** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
  override def reflectXParallel(yOffset: Double): ShapeCompound

  /** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
  override def reflectYParallel(xOffset: Double): ShapeCompound

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

  implicit val slateImplicit: Slate[ShapeCompound] = (obj: ShapeCompound, offset: Vec2) => obj.slate(offset)
  implicit val scaleImplicit: Scale[ShapeCompound] = (obj: ShapeCompound, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[ShapeCompound] = (obj: ShapeCompound, radians: Double) => obj.rotateRadians(radians)

  implicit val reflectAxisImplicit: ReflectAxis[ShapeCompound] = new ReflectAxis[ShapeCompound]
  { /** Reflect, mirror across the X axis. */
    override def reflectXT(obj: ShapeCompound): ShapeCompound = obj.negY

    /** Reflect, mirror across the Y axis. */
    override def reflectYT(obj: ShapeCompound): ShapeCompound = obj.negX
  }
  
  implicit val reflectAxisOffsetImplicit: ReflectAxisOffset[ShapeCompound] = new ReflectAxisOffset[ShapeCompound]
  { /** Reflect, mirror across a line parallel to the X axis. */
    override def reflectXOffsetT(obj: ShapeCompound, yOffset: Double): ShapeCompound = obj.reflectXParallel(yOffset)

    /** Reflect, mirror across a line parallel to the Y axis. */
    override def reflectYOffsetT(obj: ShapeCompound, xOffset: Double): ShapeCompound = obj.reflectYParallel(xOffset)
  }

  implicit val prolignImplicit: Prolign[ShapeCompound] = (obj, matrix) => obj.prolign(matrix)  
}