/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pCanv._

object GraphicNone extends GraphicElem {
  /** Renders this functional immutable GraphicElem, using the imperative methods of the abstract [[pCanv.CanvasPlatform]] interface. */
  override def rendToCanvas(cp: CanvasPlatform): Unit = { }

  /** Translate 2D geometric transformation on a GraphicElem, returns a GraphicElem. The Return type will be narrowed in sub traits / classes. */
  override def slateXY(xDelta: Double, yDelta: Double): GraphicElem = GraphicNone

  /** Uniform scaling 2D geometric transformation on a GraphicElem, returns a GraphicElem. The Return type will be narrowed in sub traits / classes.
   * The scale name was chosen for this operation as it is normally the desired operation and preserves [[Circle]]s and [[Square]]s. Use the xyScale
   * method for differential scaling on the X and Y axes. */
  override def scale(operand: Double): GraphicElem = GraphicNone

  /** Mirror, reflection 2D geometric transformation across the X axis on a GraphicElem, returns a GraphicElem. The Return type will be narrowed in
   * sub traits / classes. */
  override def negY: GraphicElem = GraphicNone

  /** Mirror, reflection 2D geometric transformation across the X axis on a GraphicElem, returns a GraphicElem. The Return type will be narrowed in
   * sub traits / classes. */
  override def negX: GraphicElem = GraphicNone

  /** 2D geometric transformation using a [[ProlignMatrix]] on a GraphicElem, returns a GraphicElem. The Return type will be narrowed in sub traits /
   * classes. */
  override def prolign(matrix: ProlignMatrix): GraphicElem = GraphicNone

  /** Rotation positive or anti clockwise 90 degrees, 2D geometric transformation on a GraphicElem, returns a GraphicElem. The return type will be
   * narrowed in sub classes and traits. */
  override def rotate90: GraphicElem = GraphicNone

  /** Rotation positive or anti clockwise 180 degrees, 2D geometric transformation on a GraphicElem, returns a GraphicElem. The return type will be
   * narrowed in sub classes and traits. */
  override def rotate180: GraphicElem = GraphicNone

  /** Rotation positive or anti clockwise 270 degrees, 2D geometric transformation on a GraphicElem, returns a GraphicElem. The return type will be
   * narrowed in sub classes and traits. */
  override def rotate270: GraphicElem = GraphicNone

  /** Rotation 2D geometric transformation on a GraphicElem taking the rotation as a scalar measured in radians, returns a GraphicElem. The Return
   * type will be narrowed in sub traits / classes. */
  override def rotate(angle: AngleVec): GraphicElem = GraphicNone

  /** Reflect 2D geometric transformation across a line, line segment or ray on a GraphicElem, returns a GraphicElem. The Return type will be narrowed
   * in sub traits / classes. */
  override def reflect(lineLike: LineLike): GraphicElem = GraphicNone

  /** XY scaling 2D geometric transformation on a GraphicElem, returns a GrpahicElem. This allows different scaling factors across X and Y dimensions.
   * The return type will be narrowed in sub classes and traits. */
  override def scaleXY(xOperand: Double, yOperand: Double): GraphicElem = GraphicNone

  /** Shear 2D geometric transformation along the X Axis on a GraphicElem, returns a GraphicElem. The return type will be narrowed in sub classes and
   * traits. */
  override def shearX(operand: Double): GraphicElem = GraphicNone

  /** Shear 2D geometric transformation along the Y Axis on a GraphicElem, returns a GraphicElem. The return type will be narrowed in sub classes and
   * traits. */
  override def shearY(operand: Double): GraphicElem = GraphicNone
}