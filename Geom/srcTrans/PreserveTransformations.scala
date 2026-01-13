/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A class that can preserve its type through all the [[Prolign]], proportionate XY axes aligned transformations, using a [[Pt2]] => [[Pt2]] function. These
 * are [[Slate2]], [[SlateXY]], [[Scale]] and negX and negY, the [[MirrorAxes]], transformations. */
trait ProlignPreserve extends Any, Aff2Elem
{ /** The most narrow type preserved in some 2d geometric transformations. */
  type ThisT <: ProlignPreserve

  /** A method to perform all the [[ProlignPreserve]] transformations with a function from PT2 => PT2. */
  def ptsTrans(f: Pt2 => Pt2): ThisT

  override def slate(operand: VecPt2): ThisT = ptsTrans(_.slate(operand))
  override def slate(xOperand: Double, yOperand: Double): ThisT = ptsTrans(_.slate(xOperand, yOperand))
  override def slateFrom(operand: VecPt2): ThisT = ptsTrans(_.slateFrom(operand))
  override def slateFrom(xOperand: Double, yOperand: Double): ThisT = ptsTrans(_.slateFrom(xOperand, yOperand))
  override def slateX(xOperand: Double): ThisT = ptsTrans(_.slateX(xOperand))
  override def slateY(yOperand: Double): ThisT = ptsTrans(_.slateY(yOperand))
  override def scale(operand: Double): ThisT = ptsTrans(_.scale(operand))
  override def negX: ThisT = ptsTrans(_.negX)
  override def negY: ThisT = ptsTrans(_.negY)
  override def prolign(matrix: AxlignMatrix): ThisT = ptsTrans(_.prolign(matrix))
}

/** All leaf classes of this type that will preserve their types for all the Similar 2D geometrical transformations. */
trait SimilarPreserve extends Any, ProlignPreserve
{ type ThisT <: SimilarPreserve
  override def rotate90: ThisT = ptsTrans(_.rotate90)
  override def rotate180: ThisT = ptsTrans(_.rotate180)
  override def rotate270: ThisT = ptsTrans(_.rotate270)
  override def mirror(lineLike: LineLike): ThisT = ptsTrans(_.mirror(lineLike))
  override def rotate(rotation: AngleVec): ThisT = ptsTrans(_.rotate(rotation))
}

/** A trait that preserves one type through all the similar 2D geometrical transformations and preserves a second type ThisT2 through the other affine
 * transformations. */
trait SimilarAffPreserve extends SimilarPreserve
{ type ThisT2 <: Aff2Elem
  def fTrans2(f: Pt2 => Pt2): ThisT2
  override def scaleXY(xOperand: Double, yOperand: Double): ThisT2 = fTrans2(_.xyScale(xOperand, yOperand))
  override def shearX(operand: Double): ThisT2 = fTrans2(_.xShear(operand))
  override def shearY(operand: Double): ThisT2 = fTrans2(_.yShear(operand))
}

/** A class that can transform itself in 2d geometry and can preserve its type across all affine transformations. This is a key trait, the object can be
 * transformed in 2-dimensional space. Leaf classes must implement the single method fTrans(f: Vec2 => Vec2): T. */
trait AffinePreserve extends Any, SimilarPreserve
{ type ThisT <: AffinePreserve
  override def scaleXY(xOperand: Double, yOperand: Double): ThisT = ptsTrans(_.xyScale(xOperand, yOperand))
  override def shearX(operand: Double): ThisT = ptsTrans(_.xShear(operand))
  override def shearY(operand: Double): ThisT = ptsTrans(_.yShear(operand))
}