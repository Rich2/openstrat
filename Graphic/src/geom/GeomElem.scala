/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A geometric element to which 2 dimensional geometric transformations can be applied. Not all elements preserve their full properties under all
 * transformations. So for example a circle is no longer a Circle after a Shear transformation, but remains an Ellipse.  */
trait GeomElem extends Any// with Product with Serializable

object GeomElem
{
  implicit def slateImplicit: Slate[GeomElem] = (obj: GeomElem, offset: Vec2) => obj match
  { case ta: TransAligner => ta.slateOld(offset).asInstanceOf[GeomElem]
    case gea: GeomElemNew => gea.slate(offset)
  }

  implicit def scaleImplicit: Scale[GeomElem] = (obj: GeomElem, operand: Double) => obj match
  { case ta: TransAligner => ta.scaleOld(operand).asInstanceOf[GeomElem]
    case gea: GeomElemNew => gea.scale(operand)
  }
}

/* A temporary element which will be merged with With GeomElem once GeomElemOld can be removed. */
trait GeomElemNew extends GeomElem
{ /** Geometric transformation by the function from a 2 dimensional Vector value to a 2 dimensional vector value. */
  def fTrans(f: Vec2 => Vec2): GeomElemNew

  /** Translate geometric transformation. */
  def slate(offset: Vec2): GeomElemNew = fTrans(_ + offset)

  /** Translate geometric transformation. */
  @inline final def slate(xOffset: Double, yOffset: Double): GeomElemNew = slate(xOffset, yOffset)

  def scale(operand: Double): GeomElemNew = fTrans(_ *  operand)
  def mirrorXOffset(yOffset: Double): GeomElemNew
  @inline final def mirrorX: GeomElemNew = mirrorXOffset(0)
  def mirrorYOffset(xOffset: Double): GeomElemNew
  @inline final def mirrorY: GeomElemNew = mirrorYOffset(0)

  def prolign(matrix: ProlignMatrix): GeomElemNew
}
