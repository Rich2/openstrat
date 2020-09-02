/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A geometric element to which 2 dimensional geometric transformations can be applied. Not all elements preserve their full properties under all
 * transformations. So for example a circle is no longer a Circle after a Shear transformation, but remains an Ellipse.  */
trait TransElem extends Product with Serializable
{ /** Translate geometric transformation. */
  def slate(offset: Vec2): TransElem

  /** Translate geometric transformation. */
  def slate(xOffset: Double, yOffset: Double): TransElem

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  def scale(operand: Double): TransElem

  /** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
  def reflectYOffset(xOffset: Double): TransElem

  /** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
  def reflectXOffset(yOffset: Double): TransElem

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  def reflectX: TransElem

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  def reflectY: TransElem

  def prolign(matrix: ProlignMatrix): TransElem

  /** Rotates 90 degrees or Pi/2 radians anticlockwise. */
  def rotate90: TransElem

  /** Rotates 180 degrees or Pi radians. */
  def rotate180: TransElem

  /** Rotates 90 degrees or Pi/2 radians clockwise. */
  def rotate270: TransElem

  def rotateRadians(radians: Double): TransElem

  def reflect(line: Line): TransElem
  def reflect(line: Sline): TransElem
  def scaleXY(xOperand: Double, yOperand: Double): TransElem

  def shearX(operand: Double): TransElem
  def shearY(operand: Double): TransElem
}

object TransElem
{ implicit val slateImplicit: Slate[TransElem] = (obj: TransElem, offset: Vec2) => obj.slate(offset)
  implicit val scaleImplicit: Scale[TransElem] = (obj: TransElem, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[TransElem] = (obj: TransElem, radians: Double) => obj.rotateRadians(radians)

  implicit val rotateAxesImplicit: RotateAxes[TransElem] = new RotateAxes[TransElem]
  { /** Rotates object of type T, 90 degrees or Pi/2 radians anticlockwise. */
    override def rotateT90(obj: TransElem): TransElem = obj.rotate90

    /** Rotates object of type T, 180 degrees or Pi radians. */
    override def rotateT180(obj: TransElem): TransElem = obj.rotate180

    /** Rotates object of type T, 90 degrees or Pi/2 radians clockwise. */
    override def rotateT270(obj: TransElem): TransElem = obj.rotate270
  }

  implicit val mirrorAxisImplicit: ReflectAxisOffset[TransElem] = new ReflectAxisOffset[TransElem]
  { /** Reflect, mirror across a line parallel to the X axis. */
    override def reflectXOffsetT(obj: TransElem, yOffset: Double): TransElem = obj.reflectXOffset(yOffset)

    /** Reflect, mirror across a line parallel to the Y axis. */
    override def reflectYOffsetT(obj: TransElem, xOffset: Double): TransElem = obj.reflectYOffset(xOffset)
  }
  
  implicit val prolignImplicit: Prolign[TransElem] = (obj, matrix) => obj.prolign(matrix)
}

/** This trait is for layout. For placing Graphic elements in rows and columns. It includes polygon and shape graphics but not line and curve
 *  graphics. */
trait BoundedElem extends TransElem
{ /** The bounding Rectangle provides an initial exclusion test as to whether the pointer is inside the polygon / shape */
  def boundingRect: BoundingRect
  def boundingWidth: Double = boundingRect.width
}
