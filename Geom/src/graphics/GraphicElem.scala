/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** This will be sealed in due course. A graphic element is either an element that can be rendered to a display (or printed) or is an active element
 * in a display or both. So I think the self type will force all [[GraphicElem]]s to extend [[CanvElem]] or [[NoCanvElem]]. */
trait GraphicElem extends GeomElem
{
  /** Renders this functional immutable GraphicElem, using the imperative methods of the abstract [[pCanv.CanvasPlatform]] interface. */
   def rendToCanvas(cp: pgui.CanvasPlatform): Unit

  /** Translate 2D geometric transformation on a GraphicElem, returns a GraphicElem. The Return type will be narrowed in sub traits / classes. */
  override def slateXY(xDelta: Double, yDelta: Double): GraphicElem

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

  /** Rotation positive or anti clockwise 90 degrees, 2D geometric transformation on a GraphicElem, returns a GraphicElem. The return type will be
   *  narrowed in sub classes and traits. */
  override def rotate90: GraphicElem

  /** Rotation positive or anti clockwise 180 degrees, 2D geometric transformation on a GraphicElem, returns a GraphicElem. The return type will be
   *  narrowed in sub classes and traits. */
  override def rotate180: GraphicElem

  /** Rotation positive or anti clockwise 270 degrees, 2D geometric transformation on a GraphicElem, returns a GraphicElem. The return type will be
   *  narrowed in sub classes and traits. */
  override def rotate270: GraphicElem

  /** Rotation 2D geometric transformation on a GraphicElem taking the rotation as a scalar measured in radians, returns a GraphicElem. The Return
   *  type will be narrowed in sub traits / classes. */
  override def rotate(angle: AngleVec): GraphicElem

  /** Reflect 2D geometric transformation across a line, line segment or ray on a GraphicElem, returns a GraphicElem. The Return type will be narrowed
   *  in sub traits / classes. */
  override def reflect(lineLike: LineLike): GraphicElem

  /** XY scaling 2D geometric transformation on a GraphicElem, returns a GrpahicElem. This allows different scaling factors across X and Y dimensions.
   *  The return type will be narrowed in sub classes and traits.*/
  override def scaleXY(xOperand: Double, yOperand: Double): GraphicElem

  /** Shear 2D geometric transformation along the X Axis on a GraphicElem, returns a GraphicElem. The return type will be narrowed in sub classes and
   * traits. */
  override def shearX(operand: Double): GraphicElem

  /** Shear 2D geometric transformation along the Y Axis on a GraphicElem, returns a GraphicElem. The return type will be narrowed in sub classes and
   *  traits. */
  override def shearY(operand: Double): GraphicElem
}

/** Companion object for the DisplayElem trait. Contains Implicit instances for 2d geometrical transformation type-classes. */
object GraphicElem
{
  implicit val slateImplicit: Slate[GraphicElem] = (obj: GraphicElem, dx: Double, dy: Double) => obj.slateXY(dx, dy)
  implicit val scaleImplicit: Scale[GraphicElem] = (obj: GraphicElem, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[GraphicElem] = (obj: GraphicElem, angle: AngleVec) => obj.rotate(angle)
  implicit val XYScaleImplicit: ScaleXY[GraphicElem] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)
  implicit val prolignImplicit: Prolign[GraphicElem] = (obj, matrix) => obj.prolign(matrix)
  implicit val ReflectImplicit: Reflect[GraphicElem] = (obj, lineLike) => obj.reflect(lineLike)

  implicit val reflectAxisImplicit: TransAxes[GraphicElem] = new TransAxes[GraphicElem]
  { override def negYT(obj: GraphicElem): GraphicElem = obj.negY
    override def negXT(obj: GraphicElem): GraphicElem = obj.negX
    override def rotate90(obj: GraphicElem): GraphicElem = obj.rotate90
    override def rotate180(obj: GraphicElem): GraphicElem = obj.rotate180
    override def rotate270(obj: GraphicElem): GraphicElem = obj.rotate270
  }
}

/** A canvas element that can be rendered by the [[pgui.CanvasPlatform]] API. This trait is not sealed, but should not be extended outside of the
 * library. */
trait CanvElem extends GraphicElem
{
  /** Translate 2D geometric transformation on a CanvElem, returns a CanvElem. The Return type will be narrowed in sub traits / classes. */
  override def slateXY(xDelta: Double, yDelta: Double): CanvElem

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
  override def rotate180: CanvElem
  override def rotate270: CanvElem

  /** Rotation 2D geometric transformation on a CanvElem taking the rotation as a scalar measured in radians, returns a CanvElem. The Return
   *  type will be narrowed in sub traits / classes. */
  override def rotate(angle: AngleVec): CanvElem

  /** Reflect 2D geometric transformation across a line, line segment or ray on a CanvElem, returns a CanvElem. The Return type will be narrowed
   *  in sub traits / classes. */
  override def reflect(lineLike: LineLike): CanvElem

  /** XY scaling 2D geometric transformation on a CanvElem, returns a GrpahicElem. This allows different scaling factors across X and Y dimensions.
   *  The return type will be narrowed in sub classes and traits.*/
  override def scaleXY(xOperand: Double, yOperand: Double): CanvElem

  /** Shear 2D geometric transformation along the X Axis on a CanvElem, returns a CanvElem. The return type will be narrowed in sub classes and
   * traits. */
  override def shearX(operand: Double): CanvElem

  /** Shear 2D geometric transformation along the Y Axis on a CanvElem, returns a CanvElem. The return type will be narrowed in sub classes and
   *  traits. */
  override def shearY(operand: Double): CanvElem
}

/** Companion object for the DisplayElem trait. Contains Implicit instances for 2d geometrical transformation type-classes. */
object CanvElem
{
  implicit val slateImplicit: Slate[CanvElem] = (obj: CanvElem, dx: Double, dy: Double) => obj.slateXY(dx, dy)
  implicit val scaleImplicit: Scale[CanvElem] = (obj: CanvElem, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[CanvElem] = (obj: CanvElem, angle: AngleVec) => obj.rotate(angle)
  implicit val XYScaleImplicit: ScaleXY[CanvElem] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)
  implicit val prolignImplicit: Prolign[CanvElem] = (obj, matrix) => obj.prolign(matrix)
  implicit val ReflectImplicit: Reflect[CanvElem] = (obj, lineLike) => obj.reflect(lineLike)

  implicit val reflectAxisImplicit: TransAxes[CanvElem] = new TransAxes[CanvElem]
  { override def negYT(obj: CanvElem): CanvElem = obj.negY
    override def negXT(obj: CanvElem): CanvElem = obj.negX
    override def rotate90(obj: CanvElem): CanvElem = obj.rotate90
    override def rotate180(obj: CanvElem): CanvElem = obj.rotate180
    override def rotate270(obj: CanvElem): CanvElem = obj.rotate270
  }
}

/** A graphic element [[GraphicElem]] that is not one of the standard canvas elements [[CanvElem]], it must provide a conversion into those standard
 * elements. */
trait NoCanvElem extends GraphicElem
{ /** This method converts this non standard graphic element into [[CanvElem]]s that can be processed by the [[pCanv.CanvasPlatform]]. */
  def canvElems: Arr[CanvElem]
}