/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb.*

/** This will be sealed in due course. A graphic element is either an element that can be rendered to a display (or printed) or is an active element in a
 * display or both. So I think the self type will force all [[Graphic2Elem]]s to extend [[CanvElem]] or [[NoCanvElem]]. */
trait Graphic2Elem extends Geom2Elem
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
  override def scaleXY(xOperand: Double, yOperand: Double): Graphic2Elem
  override def shearX(operand: Double): Graphic2Elem
  override def shearY(operand: Double): Graphic2Elem
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