/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** This trait is for layout. For placing Display elements in rows and columns. It includes polygon and shape graphics but not line and curve
 *  graphics. */
trait DisplayBounded extends DisplayElem with BoundedElem
{ def slate(offset: Vec2): DisplayBounded
  def slate(xOffset: Double, yOffset: Double): DisplayBounded
  def scale(operand: Double): DisplayBounded
  def reflectYOffset(xOffset: Double): DisplayBounded
  def reflectXOffset(yOffset: Double): DisplayBounded
  def reflectX: DisplayBounded
  def reflectY: DisplayBounded
  def prolign(matrix: ProlignMatrix): DisplayBounded
  def rotate90: DisplayBounded
  def rotate180: DisplayBounded
  def rotate270: DisplayBounded
  def rotateRadians(radians: Double): DisplayBounded
  def reflect(line: Sline): DisplayBounded
  override def scaleXY(xOperand: Double, yOperand: Double): DisplayBounded
}

/** Companion object for the DisplayBounded trait. Contains Implicit instances for 2d geometrical transformation type-classes. */
object DisplayBounded
{
  implicit val slateImplicit: Slate[DisplayBounded] = (obj: DisplayBounded, offset: Vec2) => obj.slate(offset)
  implicit val scaleImplicit: Scale[DisplayBounded] = (obj: DisplayBounded, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[DisplayBounded] = (obj: DisplayBounded, radians: Double) => obj.rotateRadians(radians)

  implicit val mirrorAxisImplicit: ReflectAxis[DisplayBounded] = new ReflectAxis[DisplayBounded]
  { /** Reflect, mirror across a line parallel to the X axis. */
    override def reflectXOffsetT(obj: DisplayBounded, yOffset: Double): DisplayBounded = obj.reflectXOffset(yOffset)

    /** Reflect, mirror across a line parallel to the Y axis. */
    override def reflectYOffsetT(obj: DisplayBounded, xOffset: Double): DisplayBounded = obj.reflectYOffset(xOffset)
  }

  implicit val rotateAxesImplicit: RotateAxes[DisplayBounded] = new RotateAxes[DisplayBounded]
  { /** Rotates object of type T, 90 degrees or Pi/2 radians anticlockwise. */
    override def rotateT90(obj: DisplayBounded): DisplayBounded = obj.rotate90

    /** Rotates object of type T, 180 degrees or Pi radians. */
    override def rotateT180(obj: DisplayBounded): DisplayBounded = obj.rotate180

    /** Rotates object of type T, 90 degrees or Pi/2 radians clockwise. */
    override def rotateT270(obj: DisplayBounded): DisplayBounded = obj.rotate270
  }

  implicit val prolignImplicit: Prolign[DisplayBounded] = (obj, matrix) => obj.prolign(matrix)
}


/** This trait is for layout. For placing Display elements in rows and columns. It includes polygon and shape graphics but not line and curve
 *  graphics. */
trait DisplayBoundedSimer extends DisplaySimElem with DisplayBounded
{ type ThisT <: DisplayBoundedSimer

  override def scaleXY(xOperand: Double, yOperand: Double): ThisT
}

/** This trait is for layout. For placing Display elements in rows and columns. It includes polygon and shape graphics but not line and curve
 *  graphics. */
trait DisplayBoundedAffine extends DisplayBoundedSimer with DisplayAffineElem
{ type ThisT <: DisplayBoundedAffine
}