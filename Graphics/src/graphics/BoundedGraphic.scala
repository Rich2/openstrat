/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** This trait is for layout. For placing Display elements in rows and columns. It includes polygon and shape graphics but not line and curve
 *  graphics. */
trait BoundedGraphic extends GraphicElem with BoundedElem
{ def slate(offset: Vec2): BoundedGraphic
  def slate(xOffset: Double, yOffset: Double): BoundedGraphic
  def scale(operand: Double): BoundedGraphic
  def reflectYParallel(xOffset: Double): BoundedGraphic
  def reflectXParallel(yOffset: Double): BoundedGraphic
  def reflectX: BoundedGraphic
  def reflectY: BoundedGraphic
  def prolign(matrix: ProlignMatrix): BoundedGraphic
  /*def rotate90: DisplayBounded
  def rotate180: DisplayBounded
  def rotate270: DisplayBounded*/
  def rotateRadians(radians: Double): BoundedGraphic
  def reflect(line: LineSeg): BoundedGraphic
  override def xyScale(xOperand: Double, yOperand: Double): BoundedGraphic
}

/** Companion object for the DisplayBounded trait. Contains Implicit instances for 2d geometrical transformation type-classes. */
object BoundedGraphic
{
  implicit val slateImplicit: Slate[BoundedGraphic] = (obj: BoundedGraphic, offset: Vec2) => obj.slate(offset)
  implicit val scaleImplicit: Scale[BoundedGraphic] = (obj: BoundedGraphic, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[BoundedGraphic] = (obj: BoundedGraphic, radians: Double) => obj.rotateRadians(radians)

  implicit val mirrorAxisImplicit: ReflectAxisOffset[BoundedGraphic] = new ReflectAxisOffset[BoundedGraphic]
  { /** Reflect, mirror across a line parallel to the X axis. */
    override def reflectXOffsetT(obj: BoundedGraphic, yOffset: Double): BoundedGraphic = obj.reflectXParallel(yOffset)

    /** Reflect, mirror across a line parallel to the Y axis. */
    override def reflectYOffsetT(obj: BoundedGraphic, xOffset: Double): BoundedGraphic = obj.reflectYParallel(xOffset)
  }

  implicit val prolignImplicit: Prolign[BoundedGraphic] = (obj, matrix) => obj.prolign(matrix)
}


/** This trait is for layout. For placing Display elements in rows and columns. It includes polygon and shape graphics but not line and curve
 *  graphics. */
trait GraphicBoundedSimer extends GraphicSimElem with BoundedGraphic
{ type ThisT <: GraphicBoundedSimer

  override def xyScale(xOperand: Double, yOperand: Double): ThisT
}

/** This trait is for layout. For placing Display elements in rows and columns. It includes polygon and shape graphics but not line and curve
 *  graphics. */
trait GraphicBoundedAffine extends GraphicBoundedSimer with GraphicAffineElem
{ type ThisT <: GraphicBoundedAffine
}