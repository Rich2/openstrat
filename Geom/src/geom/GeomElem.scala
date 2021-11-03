/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A 2D geometric element to which 2D geometric transformations can be applied. Not all elements preserve their full properties under all
 *  transformations. So for example a [[Circle]] is no longer a [[Circle]] after a Shear transformation, but remains an [[Ellipse]]. [[GraphicElem]]
 *  inherits from GeomElem. A [[Circle]] is not a [[GraphicElem]] but if we add a fill colour to make a [[CircleFill]], or a line width and line
 *  colour to it, we have a [[GraphicElem]] a graphical element that can be displayed on a canvas or output to SVG. It is expected that all elements
 *  that inherit from GeomElem that are not [[GraphicElem]]s will be [[Drawable]] elements, but this has not been finalised. */
trait GeomElem
{
  /** Translate 2D geometric transformation, taking the xOffset and yOffset as parameters on this GeomElem returning a GeomElem. The Return type will
   *  be narrowed in sub traits. End users will often want to use the slate method taking a [[Pt2]] or [[Vec2]] as a parameter, the slateX or the
   *  slateY methods. These methods will be offered as extension methods using this method for their implementations. */
  def slateXY(xDelta: Double, yDelta: Double): GeomElem

  /** Uniform 2D geometric scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves
   *  [[Circle]]s and [[Square]]s. Use the xyScale method for differential scaling. The Return type will be narrowed in sub traits / classes. */
  def scale(operand: Double): GeomElem

  /** Mirror, reflection 2D geometric transformation across the X axis by negating y. The return type will be narrowed in sub traits / classes. */
  def negY: GeomElem

  /** Mirror, reflection 2D geometric transformation across the Y axis by negating X. The return type will be narrowed in sub traits / classes. */
  def negX: GeomElem

  /** 2D Transformation using a [[ProlignMatrix]]. The return type will be narrowed in sub classes / traits. */
  def prolign(matrix: ProlignMatrix): GeomElem

  /** Rotation positive or anti clockwise 90 degrees, 2D geometric transformation on a GeomElem, returns a GeomElem. The return type will be narrowed
   *  in sub classes and traits. */
  def rotate90: GeomElem

  /** Rotation of 180 degrees, 2D geometric transformation on a GeomElem, returns a GeomElem. The return type will be narrowed in sub classes and
   * traits. */
  def rotate180: GeomElem

  /** Rotation positive or anti clockwise 270 degrees, 2D geometric transformation on a GeomElem, returns a GeomElem. The return type will be narrowed
   *  in sub classes and traits. */
  def rotate270: GeomElem

  /** Rotation 2D geometric transformation on a GeomElem. The return type will be narrowed in sub classes and traits. */
  def rotate(angle: AngleVec): GeomElem

  /** Reflect 2D geometric transformation across a line, line segment or ray on a GeomElem. The return type will be narrowed in sub classes and
   *  traits. */
  def reflect(lineLike: LineLike): GeomElem

  /** Scaling X and Y axes independently, 2D geometric transformation on this [[GeomElem]], returns a new [[GeomElem]]. This allows different scaling
   *  factors across X and Y dimensions. The return type will be narrowed in sub classes and traits. This is an affine transformation but it is not a
   *  similar transformation. */
  def scaleXY(xOperand: Double, yOperand: Double): GeomElem

  /** Shear 2D geometric transformation along the X Axis on a GeomElem. The return type will be narrowed in sub classes and traits. This is an affine
   *  transformation but it is not a similar transformation. */
  def shearX(operand: Double): GeomElem

  /** Shear 2D geometric transformation along the Y Axis on a GeomElem. The return type will be narrowed in sub classes and traits. This is an affine
   *  transformation but it is not a similar transformation. */
  def shearY(operand: Double): GeomElem
}

/** Companion object for the [[GeomElem]] trait. Contains implicit instances of type GeomElem for all the 2D geometric transformation type classes. */
object GeomElem
{ implicit val slateImplicit: Slate[GeomElem] = (obj: GeomElem, dx: Double, dy: Double) => obj.slateXY(dx, dy)
  implicit val scaleImplicit: Scale[GeomElem] = (obj: GeomElem, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[GeomElem] = (obj: GeomElem, angle: AngleVec) => obj.rotate(angle)
  implicit val prolignImplicit: Prolign[GeomElem] = (obj, matrix) => obj.prolign(matrix)
  implicit val XYScaleImplicit: ScaleXY[GeomElem] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)
  implicit val ReflectImplicit: Reflect[GeomElem] = (obj, lineLike) => obj.reflect(lineLike)

  implicit val transAxesImplicit: TransAxes[GeomElem] = new TransAxes[GeomElem]
  { override def negYT(obj: GeomElem): GeomElem = obj.negY
    override def negXT(obj: GeomElem): GeomElem = obj.negX
    override def rotate90(obj: GeomElem): GeomElem = obj.rotate90
    override def rotate180(obj: GeomElem): GeomElem = obj.rotate180
    override def rotate270(obj: GeomElem): GeomElem = obj.rotate270
  }

  implicit val shearImplicit: Shear[GeomElem] = new Shear[GeomElem]
  { override def shearXT(obj: GeomElem, yFactor: Double): GeomElem = obj.shearX(yFactor)
    override def shearYT(obj: GeomElem, xFactor: Double): GeomElem = obj.shearY(xFactor)
  }
}