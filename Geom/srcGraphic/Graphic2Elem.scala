/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb.*

/** This will be sealed in due course. A graphic element is either an element that can be rendered to a display (or printed) or is an active element in a
 * display or both. So I think the self type will force all [[Graphic2Elem]]s to extend [[CanvElem]] or [[NoCanvElem]]. */
trait Graphic2Elem extends Simil2Elem
{ /** Renders this functional immutable GraphicElem, using the imperative methods of the abstract [[pCanv.CanvasPlatform]] interface. */
  def rendToCanvas(cp: pgui.CanvasPlatform): Unit

  def svgElems: RArr[SvgOwnLine]
  
  override def slate(operand: VecPt2): Graphic2Elem
  override def slate(xOperand: Double, yOperand: Double): Graphic2Elem
  override def slateFrom(operand: VecPt2): Graphic2Elem
  override def slateFrom(xOperand: Double, yOperand: Double): Graphic2Elem
  override def slateX(xOperand: Double): Graphic2Elem
  override def slateY(yOperand: Double): Graphic2Elem
  override def scale(operand: Double): Graphic2Elem
  override def negX: Graphic2Elem
  override def negY: Graphic2Elem
  override def rotate90: Graphic2Elem
  override def rotate180: Graphic2Elem
  override def rotate270: Graphic2Elem
  override def prolign(matrix: AxlignMatrix): Graphic2Elem
  override def rotate(rotation: AngleVec): Graphic2Elem
  override def mirror(lineLike: LineLike): Graphic2Elem
}

/** Companion object for the DisplayElem trait. Contains Implicit instances for 2d geometrical transformation type-classes. */
object Graphic2Elem
{ /** Implicit [[Slate2]] type class instance / evidence for [[Graphic2Elem]]. */
  given slate2Ev: Slate2[Graphic2Elem] = new Slate2[Graphic2Elem]
  { override def slate(obj: Graphic2Elem, operand: VecPt2): Graphic2Elem = obj.slate(operand)
    override def slateXY(obj: Graphic2Elem, xOperand: Double, yOperand: Double): Graphic2Elem = obj.slate(xOperand, yOperand)
    override def slateFrom(obj: Graphic2Elem, operand: VecPt2): Graphic2Elem = obj.slateFrom(operand)
    override def slateFromXY(obj: Graphic2Elem, xOperand: Double, yOperand: Double): Graphic2Elem = obj.slateFrom(xOperand, yOperand)
    override def slateX(obj: Graphic2Elem, xOperand: Double): Graphic2Elem = obj.slateX(xOperand)
    override def slateY(obj: Graphic2Elem, yOperand: Double): Graphic2Elem = obj.slateY(yOperand)
  }

  /** Implicit [[Scale]] type class instance / evidence for [[Graphic2Elem]]. */
  given scaleEv: Scale[Graphic2Elem] = (obj: Graphic2Elem, operand: Double) => obj.scale(operand)

  /** Implicit [[Rotate]] type class instance / evidence for [[Graphic2Elem]]. */
  given rotateEv: Rotate[Graphic2Elem] = (obj: Graphic2Elem, angle: AngleVec) => obj.rotate(angle)

  /** Implicit [[ScaleXY]] type class instance / evidence for [[Graphic2Elem]]. */
  given scaleXYEv: ScaleXY[Graphic2Elem] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)

  /** Implicit [[Prolign]] type class instance / evidence for [[Graphic2Elem]]. */
  given prolignEv: Prolign[Graphic2Elem] = (obj, matrix) => obj.prolign(matrix)

  /** Implicit [[Mirror]] type class instance / evidence for [[Graphic2Elem]]. */
  given ReflectEv: Mirror[Graphic2Elem] = (obj, lineLike) => obj.mirror(lineLike)
  
  /** Implicit [[MirrorAxes]] type class instance / evidence for [[Graphic2Elem]]. */
  given transAxesEv: MirrorAxes[Graphic2Elem] = new MirrorAxes[Graphic2Elem]
  { override def negYT(obj: Graphic2Elem): Graphic2Elem = obj.negY
    override def negXT(obj: Graphic2Elem): Graphic2Elem = obj.negX
    override def rotate90(obj: Graphic2Elem): Graphic2Elem = obj.rotate90
    override def rotate180(obj: Graphic2Elem): Graphic2Elem = obj.rotate180
    override def rotate270(obj: Graphic2Elem): Graphic2Elem = obj.rotate270
  }

  /** Implicit [[Shear]] type class instance / evidence for [[Graphic2Elem]]. */
  given shearEv: Shear[Graphic2Elem] = new Shear[Graphic2Elem]
  { override def shearXT(obj: Graphic2Elem, yFactor: Double): Graphic2Elem = obj.shearX(yFactor)
    override def shearYT(obj: Graphic2Elem, xFactor: Double): Graphic2Elem = obj.shearY(xFactor)
  }
}

/** A canvas element that can be rendered by the [[pgui.CanvasPlatform]] API. This trait is not sealed, but should not be extended outside of the library. */
trait CanvElem extends Graphic2Elem
{ override def slate(operand: VecPt2): CanvElem
  override def slate(xOperand: Double, yOperand: Double): CanvElem
  override def slateFrom(operand: VecPt2): CanvElem
  override def slateFrom(xOperand: Double, yOperand: Double): CanvElem
  override def slateX(xOperand: Double): CanvElem
  override def slateY(yOperand: Double): CanvElem
  override def scale(operand: Double): CanvElem
  override def negY: CanvElem
  override def negX: CanvElem
  override def prolign(matrix: AxlignMatrix): CanvElem
  override def rotate90: CanvElem
  override def rotate180: CanvElem
  override def rotate270: CanvElem
  override def rotate(rotation: AngleVec): CanvElem
  override def mirror(lineLike: LineLike): CanvElem
}

/** Companion object for the [[CanvElem]] trait. Contains Implicit instances for 2d geometrical transformation type-classes. */
object CanvElem
{ /** Implicit [[Slate2]] type class instance / evidence for [[CanvElem]]. */
  given slate2Ev: Slate2[CanvElem] = new Slate2[CanvElem]
  { override def slate(obj: CanvElem, operand: VecPt2): CanvElem = obj.slate(operand)
    override def slateXY(obj: CanvElem, xOperand: Double, yOperand: Double): CanvElem = obj.slate(xOperand, yOperand)
    override def slateFrom(obj: CanvElem, operand: VecPt2): CanvElem = obj.slateFrom(operand)
    override def slateFromXY(obj: CanvElem, xOperand: Double, yOperand: Double): CanvElem = obj.slateFrom(xOperand, yOperand)
    override def slateX(obj: CanvElem, xOperand: Double): CanvElem = obj.slateX(xOperand)
    override def slateY(obj: CanvElem, yOperand: Double): CanvElem = obj.slateY(yOperand)
  }

  /** Implicit [[Scale]] type class instance / evidence for [[CanvElem]]. */
  given scaleEv: Scale[CanvElem] = (obj: CanvElem, operand: Double) => obj.scale(operand)

  /** Implicit [[Rotate]] type class instance / evidence for [[CanvElem]]. */
  given rotateEv: Rotate[CanvElem] = (obj: CanvElem, angle: AngleVec) => obj.rotate(angle)

  /** Implicit [[ScaleXY]] type class instance / evidence for [[CanvElem]]. */
  given scaleXYEv: ScaleXY[CanvElem] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)

  /** Implicit [[Prologn]] type class instance / evidence for [[CanvElem]]. */
  given prolignEv: Prolign[CanvElem] = (obj, matrix) => obj.prolign(matrix)

  /** Implicit [[Mirror]] type class instance / evidence for [[CanvElem]]. */
  given ReflectEv: Mirror[CanvElem] = (obj, lineLike) => obj.mirror(lineLike)
  
  /** Implicit [[MirrorAxes]] type class instance / evidence for [[CanvElem]]. */
  given transAxisEv: MirrorAxes[CanvElem] = new MirrorAxes[CanvElem]
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