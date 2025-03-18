/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait GeomLen2Elem
{
  /** Uniform 2D geometric scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves
   * [[Circle]]s and [[Square]]s. Use the xyScale method for differential scaling. The Return type will be narrowed in sub traits / classes. */
  def scale(operand: Double): GeomLen2Elem
}