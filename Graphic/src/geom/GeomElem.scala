/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** A geometric element to which 2 dimensional geometric transformations can be applied. Not all elements preserve their full properties under all
 * transformations. So for example a circle is no longer a Circle after a Shear transformation, but remains an Ellipse.  */
trait GeomElem extends Any
{
  def sillyMethod: String = "Silly"
}

object GeomElem
{
  implicit val transImplicit: TransAlign[GeomElem] = new TransAlign[GeomElem]
  {
    override def slate(obj: GeomElem, offset: Vec2): GeomElem = obj match
    { case ta: TransAligner => ta.slateOld(offset).asInstanceOf[GeomElem]
      case gea: GeomElemNew => gea.slate(offset)
    }

    override def scale(obj: GeomElem, operand: Double): GeomElem = obj match
    { case ta: TransAligner => ta.scaleOld(operand).asInstanceOf[GeomElem]
      case gea: GeomElemNew => gea.scale(operand)
    }
  }
}

/* A temporary element which will be merged with With GeomElem once GeomElemOld can be removed. */
trait GeomElemNew extends GeomElem
{ def fTrans(f: Vec2 => Vec2): GeomElemNew
  def slate(offset: Vec2): GeomElemNew = fTrans(_ + offset)
  def scale(operand: Double): GeomElem = fTrans(_ *  operand)
}
