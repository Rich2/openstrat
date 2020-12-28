/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A canvas element that can be rendered by the [[pCanv.CanvasPlatform]] API. */
trait CanvElem extends GraphicElem
{
  /** Renders this functional immutable CanvElem, using the imperative methods of the abstract [[pCanv.CanvasPlatform]] interface. */
  //def rendToCanvas(cp: pCanv.CanvasPlatform): Unit = {}

  /** Translate 2D geometric transformation on a CanvElem, returns a CanvElem. The Return type will be narrowed in sub traits / classes. */
  override def xySlate(xOffset: Double, yOffset: Double): CanvElem

  /** Uniform scaling 2D geometric transformation on a CanvElem, returns a CanvElem. The Return type will be narrowed in sub traits / classes.
   * The scale name was chosen for this operation as it is normally the desired operation and preserves [[Circle]]s and [[Square]]s. Use the xyScale
   *  method for differential scaling on the X and Y axes. */
  override def scale(operand: Double): CanvElem

  /** Mirror, reflection 2D geometric transformation across the X axis on a CanvElem, returns a CanvElem. The Return type will be narrowed in
   *  sub traits / classes. */
  override def negY: CanvElem

  /** Mirror, reflection 2D geometric transformation across the X axis on a CanvElem, returns a CanvElem. The Return type will be narrowed in
   *  sub traits / classes. */
  override def negX: CanvElem

  /** 2D geometric transformation using a [[ProlignMatrix]] on a CanvElem, returns a CanvElem. The Return type will be narrowed in sub traits /
   *  classes. */
  override def prolign(matrix: ProlignMatrix): CanvElem

  override def rotate90: CanvElem

  /** Rotation 2D geometric transformation on a CanvElem taking the rotation as a scalar measured in radians, returns a CanvElem. The Return
   *  type will be narrowed in sub traits / classes. */
  override def rotate(angle: AngleVec): CanvElem

  /** Reflect 2D geometric transformation across a line, line segment or ray on a CanvElem, returns a CanvElem. The Return type will be narrowed
   *  in sub traits / classes. */
  override def reflect(lineLike: LineLike): CanvElem

  /** XY scaling 2D geometric transformation on a CanvElem, returns a GrpahicElem. This allows different scaling factors across X and Y dimensions.
   *  The return type will be narrowed in sub classes and traits.*/
  override def xyScale(xOperand: Double, yOperand: Double): CanvElem

  /** Shear 2D geometric transformation along the X Axis on a CanvElem, returns a CanvElem. The return type will be narrowed in sub classes and
   * traits. */
  override def xShear(operand: Double): CanvElem

  /** Shear 2D geometric transformation along the Y Axis on a CanvElem, returns a CanvElem. The return type will be narrowed in sub classes and
   *  traits. */
  override def yShear(operand: Double): CanvElem
}

/** Companion object for the DisplayElem trait. Contains Implicit instances for 2d geometrical transformation type-classes. */
object CanvElem
{
  implicit val slateImplicit: Slate[CanvElem] = (obj: CanvElem, dx: Double, dy: Double) => obj.xySlate(dx, dy)
  implicit val scaleImplicit: Scale[CanvElem] = (obj: CanvElem, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[CanvElem] = (obj: CanvElem, angle: AngleVec) => obj.rotate(angle)
  implicit val XYScaleImplicit: XYScale[CanvElem] = (obj, xOperand, yOperand) => obj.xyScale(xOperand, yOperand)
  implicit val prolignImplicit: Prolign[CanvElem] = (obj, matrix) => obj.prolign(matrix)
  implicit val ReflectImplicit: Reflect[CanvElem] = (obj, lineLike) => obj.reflect(lineLike)

  implicit val reflectAxisImplicit: TransAxes[CanvElem] = new TransAxes[CanvElem]
  { override def negYT(obj: CanvElem): CanvElem = obj.negY
    override def negXT(obj: CanvElem): CanvElem = obj.negX
    override def rotate90(obj: CanvElem): CanvElem = obj.rotate90
  }
}
