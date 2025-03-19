/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb._

/** This will be sealed in due course. A graphic element is either an element that can be rendered to a display (or printed) or is an active element in a
 * display or both. So I think the self type will force all [[Graphic2Elem]]s to extend [[CanvElem]] or [[NoCanvElem]]. */
trait Graphic2Elem extends Geom2Elem
{ /** Renders this functional immutable GraphicElem, using the imperative methods of the abstract [[pCanv.CanvasPlatform]] interface. */
  def rendToCanvas(cp: pgui.CanvasPlatform): Unit

  /** Translate 2D geometric transformation on a GraphicElem, returns a GraphicElem. The Return type will be narrowed in sub traits / classes. */
  override def slateXY(xDelta: Double, yDelta: Double): Graphic2Elem

  /** Uniform scaling 2D geometric transformation on a GraphicElem, returns a GraphicElem. The Return type will be narrowed in sub traits / classes. The scale
   * name was chosen for this operation as it is normally the desired operation and preserves [[Circle]]s and [[Square]]s. Use the xyScale method for
   * differential scaling on the X and Y axes. */
  override def scale(operand: Double): Graphic2Elem
  
  /** Mirror, reflection 2D geometric transformation across the X axis on a GraphicElem, returns a GraphicElem. The Return type will be narrowed in sub traits /
   * classes. */
  override def negY: Graphic2Elem

  /** Mirror, reflection 2D geometric transformation across the X axis on a GraphicElem, returns a GraphicElem. The Return type will be narrowed in sub traits /
   * classes. */
  override def negX: Graphic2Elem

  /** 2D geometric transformation using a [[ProlignMatrix]] on a GraphicElem, returns a GraphicElem. The Return type will be narrowed in sub traits /
   * classes. */
  override def prolign(matrix: ProlignMatrix): Graphic2Elem

  /** Rotation positive or anti-clockwise 90 degrees, 2D geometric transformation on a GraphicElem, returns a GraphicElem. The return type will be narrowed in
   * subclasses and traits. */
  override def rotate90: Graphic2Elem

  /** Rotation positive or anti-clockwise 180 degrees, 2D geometric transformation on a GraphicElem, returns a GraphicElem. The return type will be narrowed in
   * subclasses and traits. */
  override def rotate180: Graphic2Elem

  /** Rotation positive or anti-clockwise 270 degrees, 2D geometric transformation on a GraphicElem, returns a GraphicElem. The return type will be narrowed in
   * subclasses and traits. */
  override def rotate270: Graphic2Elem

  /** Rotation 2D geometric transformation on a GraphicElem taking the rotation as a scalar measured in radians, returns a GraphicElem. The Return type will be
   * narrowed in sub traits / classes. */
  override def rotate(angle: AngleVec): Graphic2Elem

  /** Reflect 2D geometric transformation across a line, line segment or ray on a GraphicElem, returns a GraphicElem. The Return type will be narrowed in sub
   * traits / classes. */
  override def reflect(lineLike: LineLike): Graphic2Elem

  /** XY scaling 2D geometric transformation on a GraphicElem, returns a GrpahicElem. This allows different scaling factors across X and Y dimensions. The
   * return type will be narrowed in subclasses and traits. */
  override def scaleXY(xOperand: Double, yOperand: Double): Graphic2Elem

  /** Shear 2D geometric transformation along the X Axis on a GraphicElem, returns a GraphicElem. The return type will be narrowed in subclasses and traits. */
  override def shearX(operand: Double): Graphic2Elem

  /** Shear 2D geometric transformation along the Y Axis on a GraphicElem, returns a GraphicElem. The return type will be narrowed in subclasses and traits. */
  override def shearY(operand: Double): Graphic2Elem

  def svgElems: RArr[SvgElem]
}

/** Companion object for the DisplayElem trait. Contains Implicit instances for 2d geometrical transformation type-classes. */
object Graphic2Elem
{
  implicit val slateImplicit: Slate[Graphic2Elem] = (obj: Graphic2Elem, dx: Double, dy: Double) => obj.slateXY(dx, dy)
  implicit val scaleImplicit: Scale[Graphic2Elem] = (obj: Graphic2Elem, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[Graphic2Elem] = (obj: Graphic2Elem, angle: AngleVec) => obj.rotate(angle)
  implicit val XYScaleImplicit: ScaleXY[Graphic2Elem] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)
  implicit val prolignImplicit: Prolign[Graphic2Elem] = (obj, matrix) => obj.prolign(matrix)
  implicit val ReflectImplicit: Reflect[Graphic2Elem] = (obj, lineLike) => obj.reflect(lineLike)

  implicit val reflectAxisImplicit: TransAxes[Graphic2Elem] = new TransAxes[Graphic2Elem]
  { override def negYT(obj: Graphic2Elem): Graphic2Elem = obj.negY
    override def negXT(obj: Graphic2Elem): Graphic2Elem = obj.negX
    override def rotate90(obj: Graphic2Elem): Graphic2Elem = obj.rotate90
    override def rotate180(obj: Graphic2Elem): Graphic2Elem = obj.rotate180
    override def rotate270(obj: Graphic2Elem): Graphic2Elem = obj.rotate270
  }
}

/** A canvas element that can be rendered by the [[pgui.CanvasPlatform]] API. This trait is not sealed, but should not be extended outside of the library. */
trait CanvElem extends Graphic2Elem
{
  /** Translate 2D geometric transformation on a CanvElem, returns a CanvElem. The Return type will be narrowed in sub traits / classes. */
  override def slateXY(xDelta: Double, yDelta: Double): CanvElem

  /** Uniform scaling 2D geometric transformation on a CanvElem, returns a CanvElem. The Return type will be narrowed in sub traits / classes. The scale name
   * was chosen for this operation as it is normally the desired operation and preserves [[Circle]]s and [[Square]]s. Use the xyScale method for differential
   * scaling on the X and Y axes. */
  override def scale(operand: Double): CanvElem

  /** Mirror, reflection 2D geometric transformation across the X axis on a CanvElem, returns a CanvElem. The Return type will be narrowed in sub traits /
   * classes. */
  override def negY: CanvElem

  /** Mirror, reflection 2D geometric transformation across the X axis on a CanvElem, returns a CanvElem. The Return type will be narrowed in sub traits /
   * classes. */
  override def negX: CanvElem

  /** 2D geometric transformation using a [[ProlignMatrix]] on a CanvElem, returns a CanvElem. The Return type will be narrowed in sub traits / classes. */
  override def prolign(matrix: ProlignMatrix): CanvElem

  override def rotate90: CanvElem
  override def rotate180: CanvElem
  override def rotate270: CanvElem

  /** Rotation 2D geometric transformation on a CanvElem taking the rotation as a scalar measured in radians, returns a CanvElem. The Return type will be
   * narrowed in sub traits / classes. */
  override def rotate(angle: AngleVec): CanvElem

  /** Reflect 2D geometric transformation across a line, line segment or ray on a CanvElem, returns a CanvElem. The Return type will be narrowed in sub traits /
   *  classes. */
  override def reflect(lineLike: LineLike): CanvElem

  /** XY scaling 2D geometric transformation on a CanvElem, returns a GrpahicElem. This allows different scaling factors across X and Y dimensions. The return
   * type will be narrowed in subclasses and traits.*/
  override def scaleXY(xOperand: Double, yOperand: Double): CanvElem

  /** Shear 2D geometric transformation along the X Axis on a CanvElem, returns a CanvElem. The return type will be narrowed in subclasses and traits. */
  override def shearX(operand: Double): CanvElem

  /** Shear 2D geometric transformation along the Y Axis on a CanvElem, returns a CanvElem. The return type will be narrowed in subclasses and traits. */
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

/** A graphic element [[Graphic2Elem]] that is not one of the standard canvas elements [[CanvElem]], it must provide a conversion into those standard
 * elements. */
trait NoCanvElem extends Graphic2Elem
{ /** This method converts this non-standard graphic element into [[CanvElem]]s that can be processed by the [[pCanv.CanvasPlatform]]. */
  def canvElems: RArr[CanvElem]
}