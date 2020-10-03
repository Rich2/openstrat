/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A geometric element to which 2D geometric transformations can be applied. Not all elements preserve their full properties under all
 *  transformations. So for example a circle is no longer a Circle after a Shear transformation, but remains an Ellipse.  */
trait GeomElem extends Product with Serializable
{
  /** Translate 2D geometric transformation. This abstract method returns a [[GeomElem]]. The Return type will be narrowed in sub traits.  */
  def slate(offset: Vec2): GeomElem

  /** Translate 2D geometric transformation. This abstract method returns a [[GeomElem]]. The Return type will be narrowed in sub traits. */
  def slate(xOffset: Double, yOffset: Double): GeomElem

  /** Uniform 2D scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles
   *  and Squares. Use the xyScale method for differential scaling. */
  def scale(operand: Double): GeomElem

  /** Mirror, reflection transformation across the X axis by negating y. This method has been left abstract in GeomElemNew to allow the return type
   *  to be narrowed in sub classes. */
  def negY: GeomElem

  /** Mirror, reflection transformation across the Y axis by negating X. This method has been left abstract in GeomElemNew to allow the return type to
   *  be narrowed in sub classes. */
  def negX: GeomElem

  /** 2D Transformation using a [[ProlignMatrix]]. This method has been left abstract in GeomElemNew to allow the return type to be narrowed in sub
   *  classes and traits. */
  def prolign(matrix: ProlignMatrix): GeomElem

  /** Rotation 2D geometric transformation on a GeomElem. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   *  in sub classes and traits. */
  def rotateRadians(radians: Double): GeomElem

  /** Reflect 2D geometric transformation across a line, line segment or ray on a GeomElem. This method has been left abstract in GeomElemNew to
   *  allow the return type to be narrowed in sub classes and traits. */
  def reflect(lineLike: LineLike): GeomElem

  /** XY scaling 2D geometric transformation on a GeomElem. */
  def xyScale(xOperand: Double, yOperand: Double): GeomElem

  def xShear(operand: Double): GeomElem
  def yShear(operand: Double): GeomElem
}

/** Companion object for the [[GeomElem]] trait. Contains implicit instances of type GeomElem for all the 2d geometric transformation type
 *  classes. */
object GeomElem
{ implicit val slateImplicit: Slate[GeomElem] = (obj: GeomElem, offset: Vec2) => obj.slate(offset)
  implicit val scaleImplicit: Scale[GeomElem] = (obj: GeomElem, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[GeomElem] = (obj: GeomElem, radians: Double) => obj.rotateRadians(radians)
  implicit val prolignImplicit: Prolign[GeomElem] = (obj, matrix) => obj.prolign(matrix)
  implicit val XYScaleImplicit: XYScale[GeomElem] = (obj, xOperand, yOperand) => obj.xyScale(xOperand, yOperand)

  implicit val reflectAxesImplicit: ReflectAxes[GeomElem] = new ReflectAxes[GeomElem]
  { override def negYT(obj: GeomElem): GeomElem = obj.negY
    override def negXT(obj: GeomElem): GeomElem = obj.negX
  }

  implicit val shearImplicit: Shear[GeomElem] = new Shear[GeomElem]
  { override def xShearT(obj: GeomElem, yFactor: Double): GeomElem = obj.xShear(yFactor)
    override def yShearT(obj: GeomElem, xFactor: Double): GeomElem = obj.yShear(xFactor)
  }
}

/** This trait is for layout. For placing Graphic elements in rows and columns. It includes polygon and shape graphics but not line and curve
 *  graphics. */
trait BoundedElem extends GeomElem
{ /** The bounding Rectangle provides an initial exclusion test as to whether the pointer is inside the polygon / shape */
  def boundingRect: BoundingRect
  def boundingWidth: Double = boundingRect.width
  def boundingHeight: Double = boundingRect.height
}