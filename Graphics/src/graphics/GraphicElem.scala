/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A GraphicElem, graphic element is either an element that can be rendered to a display (or printed) or is an active element in a display or
 *  both. */
trait GraphicElem extends GeomElem
{
  /** Renders this functional immutable GraphicElem, using the imperative methods of the abstract [[pCanv.CanvasPlatform]] interface. */
   def rendToCanvas(cp: pCanv.CanvasPlatform): Unit
  
  /** Translate 2D geometric transformation on a GraphicElem, returns a GraphicElem. The Return type will be narrowed in sub traits / classes. This
   * overload might be removeable in Scala 3, but is necessary for the time being die to type inference problems. */
 // override def slate(offset: Vec2Like): GraphicElem

  /** Translate 2D geometric transformation on a GraphicElem, returns a GraphicElem. The Return type will be narrowed in sub traits / classes. */
  override def xySlate(xOffset: Double, yOffset: Double): GraphicElem

  /** Uniform scaling 2D geometric transformation on a GraphicElem, returns a GraphicElem. The Return type will be narrowed in sub traits / classes.
   * The scale name was chosen for this operation as it is normally the desired operation and preserves [[Circle]]s and [[Square]]s. Use the xyScale
   *  method for differential scaling on the X and Y axes. */
  override def scale(operand: Double): GraphicElem
  
  /** Mirror, reflection 2D geometric transformation across the X axis on a GraphicElem, returns a GraphicElem. The Return type will be narrowed in
   *  sub traits / classes. */
  override def negY: GraphicElem

  /** Mirror, reflection 2D geometric transformation across the X axis on a GraphicElem, returns a GraphicElem. The Return type will be narrowed in
   *  sub traits / classes. */
  override def negX: GraphicElem

  /** 2D geometric transformation using a [[ProlignMatrix]] on a GraphicElem, returns a GraphicElem. The Return type will be narrowed in sub traits /
   *  classes. */
  override def prolign(matrix: ProlignMatrix): GraphicElem

  /** Rotation 2D geometric transformation on a GraphicElem taking the rotation as a scalar measured in radians, returns a GraphicElem. The Return
   *  type will be narrowed in sub traits / classes. */
  override def rotate(angle: AngleVec): GraphicElem

  /** Reflect 2D geometric transformation across a line, line segment or ray on a GraphicElem, returns a GraphicElem. The Return type will be narrowed
   *  in sub traits / classes. */
  override def reflect(lineLike: LineLike): GraphicElem

  /** XY scaling 2D geometric transformation on a GraphicElem, returns a GrpahicElem. This allows different scaling factors across X and Y dimensions.
   *  The return type will be narrowed in sub classes and traits.*/
  override def xyScale(xOperand: Double, yOperand: Double): GraphicElem

  /** Shear 2D geometric transformation along the X Axis on a GraphicElem, returns a GraphicElem. The return type will be narrowed in sub classes and
   * traits. */
  override def xShear(operand: Double): GraphicElem

  /** Shear 2D geometric transformation along the Y Axis on a GraphicElem, returns a GraphicElem. The return type will be narrowed in sub classes and
   *  traits. */
  override def yShear(operand: Double): GraphicElem
}

/** Companion object for the DisplayElem trait. Contains Implicit instances for 2d geometrical transformation type-classes. */
object GraphicElem
{
  implicit val slateImplicit: Slate[GraphicElem] = (obj: GraphicElem, dx: Double, dy: Double) => obj.xySlate(dx, dy)
  implicit val scaleImplicit: Scale[GraphicElem] = (obj: GraphicElem, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[GraphicElem] = (obj: GraphicElem, angle: AngleVec) => obj.rotate(angle)
  implicit val XYScaleImplicit: XYScale[GraphicElem] = (obj, xOperand, yOperand) => obj.xyScale(xOperand, yOperand)
  implicit val prolignImplicit: Prolign[GraphicElem] = (obj, matrix) => obj.prolign(matrix)
  implicit val ReflectImplicit: Reflect[GraphicElem] = (obj, lineLike) => obj.reflect(lineLike)

  implicit val reflectAxisImplicit: ReflectAxes[GraphicElem] = new ReflectAxes[GraphicElem]
  { override def negYT(obj: GraphicElem): GraphicElem = obj.negY
    override def negXT(obj: GraphicElem): GraphicElem = obj.negX
  }
}