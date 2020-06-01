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
}

/** Trait to be removed. */
trait PaintElemOld extends GraphicElemOld with PaintElem
{ type AlignT <: PaintElemOld

  override def slate(offset: Vec2): AlignT

  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): AlignT
}
