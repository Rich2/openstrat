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
  def mirrorYOffset(xOffset: Double): TransElem

  /** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
  def mirrorXOffset(yOffset: Double): TransElem

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  def mirrorX: TransElem

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  def mirrorY: TransElem

  def prolign(matrix: ProlignMatrix): TransElem

  /** Rotates 90 degrees or Pi/2 radians anticlockwise. */
  def rotate90: TransElem
  
  /** Rotates 180 degrees or Pi radians. */
  def rotate180: TransElem

  /** Rotates 90 degrees or Pi/2 radians clockwise. */
  def rotate270: TransElem
}

object TransElem
{ implicit val slateImplicit: Slate[TransElem] = (obj: TransElem, offset: Vec2) => obj.slate(offset)
  implicit val scaleImplicit: Scale[TransElem] = (obj: TransElem, operand: Double) => obj.scale(operand)
  
  implicit val mirrorAxisImplicit: MirrorAxis[TransElem] = new MirrorAxis[TransElem]
  { /** Reflect, mirror across a line parallel to the X axis. */
    override def mirrorXOffset(obj: TransElem, yOffset: Double): TransElem = obj.mirrorXOffset(yOffset)

    /** Reflect, mirror across a line parallel to the Y axis. */
    override def mirrorYOffset(obj: TransElem, xOffset: Double): TransElem = obj.mirrorYOffset(xOffset)
  }
  
  implicit val prolignImplicit: Prolign[TransElem] = (obj, matrix) => obj.prolign(matrix)
}