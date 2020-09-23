/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** This trait is for layout. For placing Display elements in rows and columns. It includes polygon and shape graphics but not line and curve
 *  graphics. */
trait GraphicBounded extends GraphicElem with BoundedElem
{ def slate(offset: Vec2): GraphicBounded
  def slate(xOffset: Double, yOffset: Double): GraphicBounded
  def scale(operand: Double): GraphicBounded
  def reflectYOffset(xOffset: Double): GraphicBounded
  def reflectXOffset(yOffset: Double): GraphicBounded
  def reflectX: GraphicBounded
  def reflectY: GraphicBounded
  def prolign(matrix: ProlignMatrix): GraphicBounded
  /*def rotate90: DisplayBounded
  def rotate180: DisplayBounded
  def rotate270: DisplayBounded*/
  def rotateRadians(radians: Double): GraphicBounded
  def reflect(line: LineSeg): GraphicBounded
  override def xyScale(xOperand: Double, yOperand: Double): GraphicBounded
}

/** Companion object for the DisplayBounded trait. Contains Implicit instances for 2d geometrical transformation type-classes. */
object GraphicBounded
{
  implicit val slateImplicit: Slate[GraphicBounded] = (obj: GraphicBounded, offset: Vec2) => obj.slate(offset)
  implicit val scaleImplicit: Scale[GraphicBounded] = (obj: GraphicBounded, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[GraphicBounded] = (obj: GraphicBounded, radians: Double) => obj.rotateRadians(radians)

  implicit val mirrorAxisImplicit: ReflectAxisOffset[GraphicBounded] = new ReflectAxisOffset[GraphicBounded]
  { /** Reflect, mirror across a line parallel to the X axis. */
    override def reflectXOffsetT(obj: GraphicBounded, yOffset: Double): GraphicBounded = obj.reflectXOffset(yOffset)

    /** Reflect, mirror across a line parallel to the Y axis. */
    override def reflectYOffsetT(obj: GraphicBounded, xOffset: Double): GraphicBounded = obj.reflectYOffset(xOffset)
  }

  /*implicit val rotateAxesImplicit: RotateAxes[DisplayBounded] = new RotateAxes[DisplayBounded]
  { /** Rotates object of type T, 90 degrees or Pi/2 radians anticlockwise. */
    override def rotateT90(obj: DisplayBounded): DisplayBounded = obj.rotate90

    /** Rotates object of type T, 180 degrees or Pi radians. */
    override def rotateT180(obj: DisplayBounded): DisplayBounded = obj.rotate180

    /** Rotates object of type T, 90 degrees or Pi/2 radians clockwise. */
    override def rotateT270(obj: DisplayBounded): DisplayBounded = obj.rotate270
  }*/

  implicit val prolignImplicit: Prolign[GraphicBounded] = (obj, matrix) => obj.prolign(matrix)
}


/** This trait is for layout. For placing Display elements in rows and columns. It includes polygon and shape graphics but not line and curve
 *  graphics. */
trait GraphicBoundedSimer extends GraphicSimElem with GraphicBounded
{ type ThisT <: GraphicBoundedSimer

  override def xyScale(xOperand: Double, yOperand: Double): ThisT
}

/** This trait is for layout. For placing Display elements in rows and columns. It includes polygon and shape graphics but not line and curve
 *  graphics. */
trait GraphicBoundedAffine extends GraphicBoundedSimer with GraphicAffineElem
{ type ThisT <: GraphicBoundedAffine
}