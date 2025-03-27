/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait GeomLen2Elem extends Any
{ /** Translate 2 [[Length]] dimension geometric transformation [[GeomLen2Elem]]. The Return type will be narrowed in sub traits. There is a name overload
   *  taking the X and Y Deltas as separate parameters, slateX and slateY methods are also available. */
  def slate(operand: VecPtLen2): GeomLen2Elem

  /** Translate 2 [[Length]] dimension geometric transformation, taking the xDelta and yDelta as parameters on this [[GeomLen2Elem]] returning a
   * [[GeomLen2Elem]]. The Return type will be narrowed in sub traits. There is a name overload method taking a [[Pt2]] or [[Vec2]] as a parameter, slateX and
   * slateY methods are also available. */
  def slate(xOperand: Length, yOperand: Length): GeomLen2Elem

  /** Translate 2 [[Length]] dimension geometric transformation in the X dimension returning a [[GeomLen2Elem]]. The Return type will be narrowed in sub
   * traits. */
  def slateX(xOperand: Length): GeomLen2Elem

  /** Translate 2 [[Length]] dimension geometric transformation in the Y dimension returning a [[GeomLen2Elem]]. The Return type will be narrowed in sub
   * traits. */
  def slateY(yOperand: Length): GeomLen2Elem

  /** Uniform 2 [[Length]] dimension geometric scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and
   * preserves [[Circle]]s and [[Square]]s. The Return type will be narrowed in sub traits / classes. */
  def scale(operand: Double): GeomLen2Elem

  /** Divides by a [[Length]] to produce an equivalent [[Geom2Elem]] without [[Length]] units. */
  def mapGeom2(operand: Length): Geom2Elem
}

/** A graphical element in 2 [[Length]] dimension space. */
trait GraphicLen2Elem extends GeomLen2Elem
{ override def slate(operand: VecPtLen2): GraphicLen2Elem
  override def slate(xOperand: Length, yOperand: Length): GraphicLen2Elem
  override def slateX(xOperand: Length): GraphicLen2Elem
  override def slateY(yOperand: Length): GraphicLen2Elem
  override def scale(operand: Double): GraphicLen2Elem
  override def mapGeom2(operand: Length): Graphic2Elem
}

object GraphicLen2Elem
{ /** Implicit [[SlateLen2]] type class instance / evidence for [[GraphicLen2Elem]]. */
  implicit val slateLenEv: SlateLen2[GraphicLen2Elem] = (obj, operand) => obj.slate(operand)

  /** Implicit [[SlateLenXY]] type class instance / evidence for [[GraphicLen2Elem]]. */
  implicit val slateLenXYEv: SlateLenXY[GraphicLen2Elem] = (obj, xOp, yOp) => obj.slate(xOp, yOp)

  /** Implicit [[Scale]] type class instance / evidence for [[GraphicLen2Elem]]. */
  implicit val scaleEv: Scale[GraphicLen2Elem] = (obj, operand) => obj.scale(operand)

  /** Implicit [[MapGeom2]] type class instance / evidence for [[GraphicLen2Elem]] and [[Graphic2Elem]]. */
  implicit val mapGeomEv: MapGeom2[GraphicLen2Elem, Graphic2Elem] = (obj, operand) => obj.mapGeom2(operand)
}