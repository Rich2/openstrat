/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A GraphicElem is either an element that can be rendered to a display (or printed) or is an active element in a display or both. */
trait DisplayElem extends TransElem
{/** Translate geometric transformation. */
  def slate(offset: Vec2): DisplayElem

  /** Translate geometric transformation. */
  def slate(xOffset: Double, yOffset: Double): DisplayElem

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  def scale(operand: Double): DisplayElem

  /** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
  def mirrorYOffset(xOffset: Double): DisplayElem

  /** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
  def mirrorXOffset(yOffset: Double): DisplayElem

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  def mirrorX: DisplayElem

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  def mirrorY: DisplayElem

  def prolign(matrix: ProlignMatrix): DisplayElem

  /** Rotates 90 degrees or Pi/2 radians anticlockwise. */
  def rotate90: DisplayElem

  /** Rotates 180 degrees or Pi radians. */
  def rotate180: DisplayElem

  /** Rotates 90 degrees or Pi/2 radians clockwise. */
  def rotate270: DisplayElem

  def rotateRadians(radians: Double): DisplayElem

  def mirror(line: Line2): DisplayElem

  override def scaleXY(xOperand: Double, yOperand: Double): DisplayElem
}

/** Companion object for the DisplayElem trait. Contains Implicit instances for 2d geometrical transformation type-classes. */
object DisplayElem
{
  implicit val slateImplicit: Slate[DisplayElem] = (obj: DisplayElem, offset: Vec2) => obj.slate(offset)
  implicit val scaleImplicit: Scale[DisplayElem] = (obj: DisplayElem, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[DisplayElem] = (obj: DisplayElem, radians: Double) => obj.rotateRadians(radians)

  implicit val mirrorAxisImplicit: MirrorAxis[DisplayElem] = new MirrorAxis[DisplayElem]
  { /** Reflect, mirror across a line parallel to the X axis. */
    override def mirrorXOffset(obj: DisplayElem, yOffset: Double): DisplayElem = obj.mirrorXOffset(yOffset)

    /** Reflect, mirror across a line parallel to the Y axis. */
    override def mirrorYOffset(obj: DisplayElem, xOffset: Double): DisplayElem = obj.mirrorYOffset(xOffset)
  }

  implicit val prolignImplicit: Prolign[DisplayElem] = (obj, matrix) => obj.prolign(matrix)
}
/** This trait is slated for removal as is the TransSimer trait. */
trait DisplaySimer extends TransSimElem with DisplayElem
{ type ThisT <: DisplaySimer

  override def slate(offset: Vec2): ThisT

  override def slate(xOffset: Double, yOffset: Double): ThisT

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): ThisT

  /** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
  def mirrorYOffset(xOffset: Double): ThisT

  override def rotate90: ThisT
  override def rotate180: ThisT
  override def rotate270: ThisT
  override def rotateRadians(radians: Double): ThisT
  override def mirror(line: Line2): ThisT
  override def scaleXY(xOperand: Double, yOperand: Double): ThisT
}

/** Display element that returns the same type through all affine transformations. */
trait DisplayAffineElem extends DisplayElem with TransAffElem
{ type ThisT <: DisplayAffineElem

  override def slate(offset: Vec2): ThisT

  override def slate(xOffset: Double, yOffset: Double): ThisT

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): ThisT

  /** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
  override def mirrorYOffset(xOffset: Double): ThisT
}

/** This trait is for layout. For placing Graphic elements in rows and columns. It includes polygon and shape graphics but not line and curve
 *  graphics. */
trait DisplayBounded extends DisplaySimer
{ type ThisT <: DisplayBounded
  /** The bounding Rectangle provides an initial exclusion test as to whether the pointer is inside the polygon / shape */
  def boundingRect: BoundingRect
  def boundingWidth: Double = boundingRect.width
}

/** This trait is for layout. For placing Graphic elements in rows and columns. It includes polygon and shape graphics but not line and curve
 *  graphics. */
trait DisplayBoundedAffine extends DisplayBounded with DisplayAffineElem
{ type ThisT <: DisplayBoundedAffine
}