/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A 2 scalar dimension geometric object that can be the object of transformations that preserves alignment to the X and Y axes. This trait only exits to allow
 * classes like [[RectCompound]] and [[SqlignCompound]]. Because they take functions that rely on the alignment to the X and Y axes, the shape can not be
 * downgraded to a [[Quadrilateral]]. All the affine transformations can be applied to most objects, although for example some tranformations may downgrade a
 * [[Circle]] to an [[Ellipse]]. */
trait Axlign2Elem extends Any
{ /** Translate 2D geometric transformation, taking the xOffset and yOffset as parameters on this GeomElem returning a GeomElem. The Return type will be
 * narrowed in sub traits. End users will often want to use the slate method taking a [[Pt2]] or [[Vec2]] as a parameter, the slateX or the slateY methods.
 * These methods will be offered as extension methods using this method for their implementations. */
  def slate(operand: VecPt2): Axlign2Elem

  /** Translate 2D geometric transformation, taking the xOffset and yOffset as parameters on this GeomElem returning a GeomElem. The Return type will be
   * narrowed in sub traits. End users will often want to use the slate method taking a [[Pt2]] or [[Vec2]] as a parameter, the slateX or the slateY methods.
   * These methods will be offered as extension methods using this method for their implementations. */
  def slate(xOperand: Double, yOperand: Double): Axlign2Elem

  /** Translate 2D geometric transformation in the X dimension, returning a GeomElem. The Return type will be narrowed in sub traits. */
  def slateX(operand: Double): Axlign2Elem

  /** Translate 2D geometric transformation in the Y dimension, returning a GeomElem. The Return type will be narrowed in sub traits. */
  def slateY(operand: Double): Axlign2Elem

  /** Uniform 2D geometric scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves
   * [[Circle]]s and [[Square]]s. Use the xyScale method for differential scaling. The Return type will be narrowed in sub traits / classes. */
  def scale(operand: Double): Axlign2Elem

  /** Mirror, reflection 2D geometric transformation across the Y axis by negating X. The return type will be narrowed in sub traits / classes. */
  def negX: Axlign2Elem

  /** Mirror, reflection 2D geometric transformation across the X axis by negating y. The return type will be narrowed in sub traits / classes. */
  def negY: Axlign2Elem

  /** Rotation positive or anti-clockwise 90 degrees, 2D geometric transformation on a GeomElem, returns a GeomElem. The return type will be narrowed in
   * subclasses and traits. */
  def rotate90: Axlign2Elem

  /** Rotation of 180 degrees, 2D geometric transformation on a GeomElem, returns a GeomElem. The return type will be narrowed in subclasses and traits. */
  def rotate180: Axlign2Elem

  /** Rotation positive or anti-clockwise 270 degrees, 2D geometric transformation on a GeomElem, returns a GeomElem. The return type will be narrowed in
   * subclasses and traits. */
  def rotate270: Axlign2Elem

  /** 2D Transformation using a [[AxlignMatrix]]. The return type will be narrowed in subclasses / traits. */
  def prolign(matrix: AxlignMatrix): Axlign2Elem
}