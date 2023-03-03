/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pgui._, Colour.Black

/** To be phased out. */
case class ShapeGenDrawOld(shape: ShapeGenOld, lineColour: Colour = Black, lineWidth: Double = 2) extends CanvElem with AxisFree
{
  override type ThisT = ShapeGenDrawOld

  /** Renders this functional immutable GraphicElem, using the imperative methods of the abstract [[pCanv.CanvasPlatform]] interface. */
  override def rendToCanvas(cp: CanvasPlatform): Unit = { deb("Not implemented.")}

  /** Translate 2D geometric transformation on a ShapeGenDraw, returns a ShapeGenDraw. The Return type will be narrowed in sub traits / classes. */
  override def slateXY(xDelta: Double, yDelta: Double): ShapeGenDrawOld = ShapeGenDrawOld(shape.slateXY(xDelta, yDelta), lineColour, lineWidth)

  /** Uniform scaling 2D geometric transformation on a ShapeGenDraw, returns a ShapeGenDraw. The Return type will be narrowed in sub traits / classes.
   * The scale name was chosen for this operation as it is normally the desired operation and preserves [[Circle]]s and [[Square]]s. Use the xyScale
   * method for differential scaling on the X and Y axes. */
  override def scale(operand: Double): ShapeGenDrawOld = ShapeGenDrawOld(shape.scale(operand), lineColour, lineWidth)

  /** 2D geometric transformation using a [[ProlignMatrix]] on a ShapeGenDraw, returns a ShapeGenDraw. The Return type will be narrowed in sub traits /
   * classes. */
  override def prolign(matrix: ProlignMatrix): ShapeGenDrawOld = ShapeGenDrawOld(shape.prolign((matrix)), lineColour, lineWidth)

  /** Rotation 2D geometric transformation on a ShapeGenDraw taking the rotation as a scalar measured in radians, returns a ShapeGenDraw. The Return
   * type will be narrowed in sub traits / classes. */
  override def rotate(angle: AngleVec): ShapeGenDrawOld = ShapeGenDrawOld(shape.rotate(angle), lineColour, lineWidth)

  /** Reflect 2D geometric transformation across a line, line segment or ray on a ShapeGenDraw, returns a ShapeGenDraw. The Return type will be narrowed
   * in sub traits / classes. */
  override def reflect(lineLike: LineLike): ShapeGenDrawOld = ShapeGenDrawOld(shape.reflect(lineLike), lineColour, lineWidth)

  /** XY scaling 2D geometric transformation on a ShapeGenDraw, returns a GrpahicElem. This allows different scaling factors across X and Y dimensions.
   * The return type will be narrowed in sub classes and traits. */
  override def scaleXY(xOperand: Double, yOperand: Double): ShapeGenDrawOld = ShapeGenDrawOld(shape.scaleXY(xOperand, yOperand), lineColour, lineWidth)

  /** Shear 2D geometric transformation along the X Axis on a ShapeGenDraw, returns a ShapeGenDraw. The return type will be narrowed in sub classes and
   * traits. */
  override def shearX(operand: Double): ShapeGenDrawOld = ShapeGenDrawOld(shape.shearX(operand), lineColour, lineWidth)

  /** Shear 2D geometric transformation along the Y Axis on a ShapeGenDraw, returns a ShapeGenDraw. The return type will be narrowed in sub classes and
   * traits. */
  override def shearY(operand: Double): ShapeGenDrawOld = ShapeGenDrawOld(shape.shearY(operand), lineColour, lineWidth)
}