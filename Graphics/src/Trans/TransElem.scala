/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A geometric element to which 2D geometric transformations can be applied. Not all elements preserve their full properties under all
 *  transformations. So for example a circle is no longer a Circle after a Shear transformation, but remains an Ellipse.  */
trait TransElem extends Product with Serializable
{
  /** Translate 2D geometric transformation. This abstract method returns a [[TransElem]]. The Return type will be narrowed in sub traits.  */
  def slate(offset: Vec2): TransElem

  /** Translate 2D geometric transformation. This abstract method returns a [[TransElem]]. The Return type will be narrowed in sub traits. */
  def slate(xOffset: Double, yOffset: Double): TransElem

  /** Uniform 2D scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles
   *  and Squares. Use the xyScale method for differential scaling. */
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

  /** Transforms this TransElem using a [[ProlignMatrix]]. */
  def prolign(matrix: ProlignMatrix): TransElem

  def rotateRadians(radians: Double): TransElem

  def reflect(line: Line): TransElem
  def reflect(line: LineSeg): TransElem
  def xyScale(xOperand: Double, yOperand: Double): TransElem

  def xShear(operand: Double): TransElem
  def yShear(operand: Double): TransElem
}

/** Companion object for the [[TransElem]] trait. Contains implicit instances of type TransElem for all the 2d geometric transformation type
 *  classes. */
object TransElem
{ implicit val slateImplicit: Slate[TransElem] = (obj: TransElem, offset: Vec2) => obj.slate(offset)
  implicit val scaleImplicit: Scale[TransElem] = (obj: TransElem, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[TransElem] = (obj: TransElem, radians: Double) => obj.rotateRadians(radians)
  implicit val prolignImplicit: Prolign[TransElem] = (obj, matrix) => obj.prolign(matrix)
  implicit val XYScaleImplicit: XYScale[TransElem] = (obj, xOperand, yOperand) => obj.xyScale(xOperand, yOperand)
  
  implicit val mirrorAxisImplicit: ReflectAxisOffset[TransElem] = new ReflectAxisOffset[TransElem]
  { override def reflectXOffsetT(obj: TransElem, yOffset: Double): TransElem = obj.reflectXOffset(yOffset)
    override def reflectYOffsetT(obj: TransElem, xOffset: Double): TransElem = obj.reflectYOffset(xOffset)
  }
  
  implicit val shearImplicit: Shear[TransElem] = new Shear[TransElem]
  { override def xShearT(obj: TransElem, yFactor: Double): TransElem = obj.xShear(yFactor)
    override def yShearT(obj: TransElem, xFactor: Double): TransElem = obj.yShear(xFactor)
  }
}

/** This trait is for layout. For placing Graphic elements in rows and columns. It includes polygon and shape graphics but not line and curve
 *  graphics. */
trait BoundedElem extends TransElem
{ /** The bounding Rectangle provides an initial exclusion test as to whether the pointer is inside the polygon / shape */
  def boundingRect: BoundingRect
  def boundingWidth: Double = boundingRect.width
}