/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A GraphicElem is either an element that can be rendered to a display or printed. */
trait PaintElem extends GraphicElem
{
  /** Renders this functional immutable Graphic PaintElem, using the imperative methods of the abstract [[ostrat.pCanv.CanvasPlatform]] interface. */
  def rendToCanvas(cp: pCanv.CanvasPlatform): Unit

  /** Translate geometric transformation. */
  def slate(offset: Vec2): PaintElem

  /** Translate geometric transformation. */
  def slate(xOffset: Double, yOffset: Double): PaintElem

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  def scale(operand: Double): PaintElem
}

/** Companion object for PaintElem contains various implicit instances for the transformation type classes. */
object PaintElem
{ implicit val slateImplicit: Slate[PaintElem] = (obj: PaintElem, offset: Vec2) => obj.slate(offset)
  implicit val scaleImplicit: Scale[PaintElem] = (obj: PaintElem, operand: Double) => obj.scale(operand)
}

/** Trait to be removed. */
trait PaintElemOld extends GraphicElemOld with PaintElem
{ type AlignT <: PaintElemOld

  override def slate(offset: Vec2): AlignT

  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): AlignT

  def scale(operand: Double): AlignT
}
