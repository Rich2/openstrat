/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb.*

/** This will be sealed in due course. A graphic element is either an element that can be rendered to a display (or printed) or is an active element in a
 * display or both. So I think the self type will force all [[Graphic2Elem]]s to extend [[CanvElem]] or [[NoCanvElem]]. */
trait Graphic2Elem extends Aff2Elem
{ /** Renders this functional immutable GraphicElem, using the imperative methods of the abstract [[pCanv.CanvasPlatform]] interface. */
  def rendToCanvas(cp: pgui.CanvasPlatform): Unit

  override def slate(operand: VecPt2): Graphic2Elem  
  override def slate(xOperand: Double, yOperand: Double): Graphic2Elem
  override def slateX(xOperand: Double): Graphic2Elem
  override def slateY(yOperand: Double): Graphic2Elem
  override def scale(operand: Double): Graphic2Elem
  override def negX: Graphic2Elem
  override def negY: Graphic2Elem
  override def rotate90: Graphic2Elem
  override def rotate180: Graphic2Elem  
  override def rotate270: Graphic2Elem
  override def prolign(matrix: ProlignMatrix): Graphic2Elem
  override def rotate(rotation: AngleVec): Graphic2Elem
  override def reflect(lineLike: LineLike): Graphic2Elem
  override def scaleXY(xOperand: Double, yOperand: Double): Graphic2Elem
  override def shearX(operand: Double): Graphic2Elem
  override def shearY(operand: Double): Graphic2Elem

  def svgElems: RArr[SvgElem]
}

/** Companion object for the DisplayElem trait. Contains Implicit instances for 2d geometrical transformation type-classes. */
object Graphic2Elem
{ /** Implicit [[Slate]] type class instance / evidence for [[Graphic2Elem]]. */
  implicit val slateEv: Slate[Graphic2Elem] = (obj, operand) => obj.slate(operand)

  /** Implicit [[SlateXY]] type class instance / evidence for [[Graphic2Elem]]. */
  implicit val slateXYEv: SlateXY[Graphic2Elem] = (obj: Graphic2Elem, dx: Double, dy: Double) => obj.slate(dx, dy)

  /** Implicit [[Scale]] type class instance / evidence for [[Graphic2Elem]]. */
  implicit val scaleEv: Scale[Graphic2Elem] = (obj: Graphic2Elem, operand: Double) => obj.scale(operand)

  /** Implicit [[Rotate]] type class instance / evidence for [[Graphic2Elem]]. */
  implicit val rotateEv: Rotate[Graphic2Elem] = (obj: Graphic2Elem, angle: AngleVec) => obj.rotate(angle)

  /** Implicit [[ScaleXY]] type class instance / evidence for [[Graphic2Elem]]. */
  implicit val scaleXYEv: ScaleXY[Graphic2Elem] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)

  /** Implicit [[Prolign]] type class instance / evidence for [[Graphic2Elem]]. */
  implicit val prolignEv: Prolign[Graphic2Elem] = (obj, matrix) => obj.prolign(matrix)

  /** Implicit [[Reflect]] type class instance / evidence for [[Graphic2Elem]]. */
  implicit val ReflectEv: Reflect[Graphic2Elem] = (obj, lineLike) => obj.reflect(lineLike)
  
  /** Implicit [[TransAxes]] type class instance / evidence for [[Graphic2Elem]]. */
  implicit val transAxesEv: TransAxes[Graphic2Elem] = new TransAxes[Graphic2Elem]
  { override def negYT(obj: Graphic2Elem): Graphic2Elem = obj.negY
    override def negXT(obj: Graphic2Elem): Graphic2Elem = obj.negX
    override def rotate90(obj: Graphic2Elem): Graphic2Elem = obj.rotate90
    override def rotate180(obj: Graphic2Elem): Graphic2Elem = obj.rotate180
    override def rotate270(obj: Graphic2Elem): Graphic2Elem = obj.rotate270
  }

  /** Implicit [[Shear]] type class instance / evidence for [[Graphic2Elem]]. */
  implicit val shearEv: Shear[Graphic2Elem] = new Shear[Graphic2Elem]
  { override def shearXT(obj: Graphic2Elem, yFactor: Double): Graphic2Elem = obj.shearX(yFactor)
    override def shearYT(obj: Graphic2Elem, xFactor: Double): Graphic2Elem = obj.shearY(xFactor)
  }
}

/** A canvas element that can be rendered by the [[pgui.CanvasPlatform]] API. This trait is not sealed, but should not be extended outside of the library. */
trait CanvElem extends Graphic2Elem
{
  override def slate(operand: VecPt2): CanvElem
  override def slate(xOperand: Double, yOperand: Double): CanvElem
  override def scale(operand: Double): CanvElem
  override def negY: CanvElem
  override def negX: CanvElem
  override def prolign(matrix: ProlignMatrix): CanvElem
  override def rotate90: CanvElem
  override def rotate180: CanvElem
  override def rotate270: CanvElem
  override def rotate(rotation: AngleVec): CanvElem
  override def reflect(lineLike: LineLike): CanvElem
  override def scaleXY(xOperand: Double, yOperand: Double): CanvElem
  override def shearX(operand: Double): CanvElem
  override def shearY(operand: Double): CanvElem
}

/** Companion object for the [[CanvElem]] trait. Contains Implicit instances for 2d geometrical transformation type-classes. */
object CanvElem
{ /** Implicit [[Slate]] type class instance / evidence for [[CanvElem]]. */
  implicit val slateEv: Slate[CanvElem] = (obj, operand) => obj.slate(operand)

  /** Implicit [[SlateXY]] type class instance / evidence for [[CanvElem]]. */
  implicit val slateXYEv: SlateXY[CanvElem] = (obj: CanvElem, dx: Double, dy: Double) => obj.slate(dx, dy)

  /** Implicit [[Scale]] type class instance / evidence for [[CanvElem]]. */
  implicit val scaleEv: Scale[CanvElem] = (obj: CanvElem, operand: Double) => obj.scale(operand)

  /** Implicit [[Rotate]] type class instance / evidence for [[CanvElem]]. */
  implicit val rotateEv: Rotate[CanvElem] = (obj: CanvElem, angle: AngleVec) => obj.rotate(angle)

  /** Implicit [[ScaleXY]] type class instance / evidence for [[CanvElem]]. */
  implicit val scaleXYEv: ScaleXY[CanvElem] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)

  /** Implicit [[Prologn]] type class instance / evidence for [[CanvElem]]. */
  implicit val prolignEv: Prolign[CanvElem] = (obj, matrix) => obj.prolign(matrix)

  /** Implicit [[Reflect]] type class instance / evidence for [[CanvElem]]. */
  implicit val ReflectEv: Reflect[CanvElem] = (obj, lineLike) => obj.reflect(lineLike)
  
  /** Implicit [[TransAxes]] type class instance / evidence for [[CanvElem]]. */
  implicit val transAxisEv: TransAxes[CanvElem] = new TransAxes[CanvElem]
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