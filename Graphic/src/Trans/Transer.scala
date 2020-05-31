/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A geometric element to which 2 dimensional geometric transformations can be applied. Not all elements preserve their full properties under all
 * transformations. So for example a circle is no longer a Circle after a Shear transformation, but remains an Ellipse.  */
trait Transer extends Product with Serializable
{ /** Translate geometric transformation. */
  def slate(offset: Vec2): Transer

  /** Translate geometric transformation. */
  def slate(xOffset: Double, yOffset: Double): Transer

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  def scale(operand: Double): Transer

  /** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
  def mirrorYOffset(xOffset: Double): Transer

  /** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
  def mirrorXOffset(yOffset: Double): Transer

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  def mirrorX: Transer

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  def mirrorY: Transer

  def prolign(matrix: ProlignMatrix): Transer
}

object Transer
{ implicit val slateImplicit: Slate[Transer] = (obj: Transer, offset: Vec2) => obj.slate(offset)
  implicit val scaleImplicit: Scale[Transer] = (obj: Transer, operand: Double) => obj.scale(operand)
  
  implicit val mirrorAxisImplicit: MirrorAxis[Transer] = new MirrorAxis[Transer] {
    /** Reflect, mirror across a line parallel to the X axis. */
    override def mirrorXOffset(obj: Transer, yOffset: Double): Transer = obj.mirrorXOffset(yOffset)

    /** Reflect, mirror across a line parallel to the Y axis. */
    override def mirrorYOffset(obj: Transer, xOffset: Double): Transer = obj.mirrorYOffset(xOffset)
  }
}