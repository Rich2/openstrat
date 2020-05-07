/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** A geometric element to which 2 dimensional geometric transformations can be applied. Not all elements preserve their full properties under all
 * transformations. So for example a circle is no longer a Circle after a Shear transformation, but remains an Ellipse.  */
trait GeomElem extends Any
{ def fTrans(f: Vec2 => Vec2): GeomElem
  def slateNew(offset: Vec2): GeomElem = fTrans(_ + offset)
  def scaleNew(operand: Double): GeomElem = fTrans(_ *  operand)
 // def shear(xScale: Double, yScale: Double): GeomElem
  def rotateRadiansNew(radians: Double): GeomElem = fTrans(_.rotateRadians(radians))
  def mirrorNew(line: Line2): GeomElem = fTrans(_.mirror(line))
}

object GeomElem
{
  implicit def transImplicit: TransAll[GeomElem] = (obj, f) => obj.fTrans(f)
}
