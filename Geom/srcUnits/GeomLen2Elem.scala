/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait GeomLen2Elem
{
  /** Uniform 2D geometric scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves
   * [[Circle]]s and [[Square]]s. Use the xyScale method for differential scaling. The Return type will be narrowed in sub traits / classes. */
  def scale(operand: Double): GeomLen2Elem

  /** Translate 2D geometric transformation, taking the xOffset and yOffset as parameters on this GeomElem returning a GeomElem. The Return type will be
   * narrowed in sub traits. End users will often want to use the slate method taking a [[Pt2]] or [[Vec2]] as a parameter, the slateX or the slateY methods.
   * These methods will be offered as extension methods using this method for their implementations. */
  def slate(xDelta: Length, yDelta: Length): GeomLen2Elem
}