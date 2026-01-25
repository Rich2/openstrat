/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb.*

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
  override def shearX(operand: Double): CanvElem
  override def shearY(operand: Double): CanvElem
  override def scaleXY(xOperand: Double, yOperand: Double): CanvElem
}

/** Companion object for the [[CanvElem]] trait. Contains Implicit instances for 2d geometrical transformation type-classes. */
object CanvElem
{ /** Implicit [[Slate2]] type class instance / evidence for [[CanvElem]]. */
  given slate2Ev: Slate2[CanvElem] = new Slate2[CanvElem]
  { override def slateT(obj: CanvElem, operand: VecPt2): CanvElem = obj.slate(operand)
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