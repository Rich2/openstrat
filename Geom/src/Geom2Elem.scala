/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A 2D geometric element to which 2D geometric transformations can be applied. Not all elements preserve their full properties under all transformations. So
 * for example a [[Circle]] is no longer a [[Circle]] after a Shear transformation, but remains an [[Ellipse]]. [[Graphic2Elem]] inherits from GeomElem. A
 * [[Circle]] is not a [[Graphic2Elem]] but if we add a fill colour to make a [[CircleFill]], or a line width and line  colour to it, we have a [[Graphic2Elem]]
 * a graphical element that can be displayed on a canvas or output to SVG. It is expected that all elements that inherit from GeomElem that are not
 * [[Graphic2Elem]]s will be [[Drawable]] elements, but this has not been finalised. */
trait Geom2Elem extends Any
{ /** Translate 2D geometric transformation, taking the xOffset and yOffset as parameters on this GeomElem returning a GeomElem. The Return type will be
   * narrowed in sub traits. End users will often want to use the slate method taking a [[Pt2]] or [[Vec2]] as a parameter, the slateX or the slateY methods.
   * These methods will be offered as extension methods using this method for their implementations. */
  def slate(operand: VecPt2): Geom2Elem

  /** Translate 2D geometric transformation, taking the xOffset and yOffset as parameters on this GeomElem returning a GeomElem. The Return type will be
   * narrowed in sub traits. End users will often want to use the slate method taking a [[Pt2]] or [[Vec2]] as a parameter, the slateX or the slateY methods.
   * These methods will be offered as extension methods using this method for their implementations. */
  def slate(xOperand: Double, yOperand: Double): Geom2Elem

  /** Uniform 2D geometric scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves
   * [[Circle]]s and [[Square]]s. Use the xyScale method for differential scaling. The Return type will be narrowed in sub traits / classes. */
  def scale(operand: Double): Geom2Elem

  /** Mirror, reflection 2D geometric transformation across the Y axis by negating X. The return type will be narrowed in sub traits / classes. */
  def negX: Geom2Elem
  
  /** Mirror, reflection 2D geometric transformation across the X axis by negating y. The return type will be narrowed in sub traits / classes. */
  def negY: Geom2Elem  
  
  /** Rotation positive or anti-clockwise 90 degrees, 2D geometric transformation on a GeomElem, returns a GeomElem. The return type will be narrowed in
   * subclasses and traits. */
  def rotate90: Geom2Elem

  /** Rotation of 180 degrees, 2D geometric transformation on a GeomElem, returns a GeomElem. The return type will be narrowed in subclasses and traits. */
  def rotate180: Geom2Elem

  /** Rotation positive or anti-clockwise 270 degrees, 2D geometric transformation on a GeomElem, returns a GeomElem. The return type will be narrowed in
   * subclasses and traits. */
  def rotate270: Geom2Elem

  /** 2D Transformation using a [[ProlignMatrix]]. The return type will be narrowed in subclasses / traits. */
  def prolign(matrix: ProlignMatrix): Geom2Elem

  /** Rotation 2D geometric transformation on a GeomElem. The return type will be narrowed in subclasses and traits. */
  def rotate(angle: AngleVec): Geom2Elem

  /** Reflect 2D geometric transformation across a line, line segment or ray on a GeomElem. The return type will be narrowed in subclasses and traits. */
  def reflect(lineLike: LineLike): Geom2Elem

  /** Scaling X and Y axes independently, 2D geometric transformation on this [[Geom2Elem]], returns a new [[Geom2Elem]]. This allows different scaling factors
   * across X and Y dimensions. The return type will be narrowed in subclasses and traits. This is an affine transformation, but it is not a similar
   * transformation. */
  def scaleXY(xOperand: Double, yOperand: Double): Geom2Elem

  /** Shear 2D geometric transformation along the X Axis on a GeomElem. The return type will be narrowed in subclasses and traits. This is an affine
   *  transformation, but it is not a similar transformation. */
  def shearX(operand: Double): Geom2Elem

  /** Shear 2D geometric transformation along the Y Axis on a GeomElem. The return type will be narrowed in subclasses and traits. This is an affine
   * transformation, but it is not a similar transformation. */
  def shearY(operand: Double): Geom2Elem
}

/** Companion object for the [[Geom2Elem]] trait. Contains implicit instances of type GeomElem for all the 2D geometric transformation type classes. */
object Geom2Elem
{ /** Implicit [[Slate]] type class instance / evidence for [[Geom2Elem]]. */
  implicit val slateEv: Slate[Geom2Elem] = (obj, operand) => obj.slate(operand)

  /** Implicit [[SlateXY]] type class instance / evidence for [[Geom2Elem]]. */
  implicit val slateXYEv: SlateXY[Geom2Elem] = (obj: Geom2Elem, dx: Double, dy: Double) => obj.slate(dx, dy)

  /** Implicit [[Scale]] type class instance / evidence for [[Geom2Elem]]. */
  implicit val scaleEv: Scale[Geom2Elem] = (obj: Geom2Elem, operand: Double) => obj.scale(operand)

  /** Implicit [[Rotate]] type class instance / evidence for [[Geom2Elem]]. */
  implicit val rotateEv: Rotate[Geom2Elem] = (obj: Geom2Elem, angle: AngleVec) => obj.rotate(angle)

  /** Implicit [[Prolign]] type class instance / evidence for [[Geom2Elem]]. */
  implicit val prolignEv: Prolign[Geom2Elem] = (obj, matrix) => obj.prolign(matrix)

  /** Implicit [[ScaleXY]] type class instance / evidence for [[Geom2Elem]]. */
  implicit val scaleXYEv: ScaleXY[Geom2Elem] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)

  /** Implicit [[Reflect]] type class instance / evidence for [[Geom2Elem]]. */
  implicit val ReflectEv: Reflect[Geom2Elem] = (obj, lineLike) => obj.reflect(lineLike)

  /** Implicit [[TransAxes]] type class instance / evidence for [[Geom2Elem]]. */
  implicit val transAxesEv: TransAxes[Geom2Elem] = new TransAxes[Geom2Elem]
  { override def negYT(obj: Geom2Elem): Geom2Elem = obj.negY
    override def negXT(obj: Geom2Elem): Geom2Elem = obj.negX
    override def rotate90(obj: Geom2Elem): Geom2Elem = obj.rotate90
    override def rotate180(obj: Geom2Elem): Geom2Elem = obj.rotate180
    override def rotate270(obj: Geom2Elem): Geom2Elem = obj.rotate270
  }

  /** Implicit [[Shear]] type class instance / evidence for [[Geom2Elem]]. */
  implicit val shearEv: Shear[Geom2Elem] = new Shear[Geom2Elem]
  { override def shearXT(obj: Geom2Elem, yFactor: Double): Geom2Elem = obj.shearX(yFactor)
    override def shearYT(obj: Geom2Elem, xFactor: Double): Geom2Elem = obj.shearY(xFactor)
  }
}