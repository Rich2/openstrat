/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait GeomLen2Elem extends Any
{
  /** Translate 2 [[Length]] dimension geometric transformation [[GeomLen2Elem]]. The Return type will be narrowed in sub traits. There is a name overload
   *  taking the X and Y Deltas as separate parameters, slateX and slateY methods are also available. */
  def slate(operand: VecPtLen2): GeomLen2Elem

  /** Translate 2 [[Length]] dimension geometric transformation, taking the xDelta and yDelta as parameters on this [[GeomLen2Elem]] returning a
   * [[GeomLen2Elem]]. The Return type will be narrowed in sub traits. There is a name overload method taking a [[Pt2]] or [[Vec2]] as a parameter, slateX and
   * slateY methods are also available. */
  def slate(xDelta: Length, yDelta: Length): GeomLen2Elem

  /** Translate 2 [[Length]] dimension geometric transformation in the X dimension returning a [[GeomLen2Elem]]. The Return type will be narrowed in sub
   * traits. */
  def slateX(operand: Length): GeomLen2Elem

  /** Translate 2 [[Length]] dimension geometric transformation in the Y dimension returning a [[GeomLen2Elem]]. The Return type will be narrowed in sub
   * traits. */
  def slateY(operand: Length): GeomLen2Elem

  /** Uniform 2 [[Length]] dimension geometric scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and
   * preserves [[Circle]]s and [[Square]]s. The Return type will be narrowed in sub traits / classes. */
  def scale(operand: Double): GeomLen2Elem

  /** Divides by a [[Length]] to produce an equivlent [[Geom2Elem]] without [[Length]] units. */
  def mapScalars(operand: Length): Geom2Elem
}