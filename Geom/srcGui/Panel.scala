/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pgui
import geom._

trait DispObj
sealed trait DispPhase
case class DispAnim(fAnim: Double => Unit, secs: Double) extends DispPhase
case class DispStill(fDisp: () => Unit) extends DispPhase

/** A virtual panel created from the Canvas implemented using the clip function. */
case class Panel(private val outerCanv: CanvasPanelled, clipPoly: Polygon, cover: Boolean = true) extends PanelLike
{


  override def unsafeArray: Array[Double] = ???

  override def toString: String = "Panel:" -- clipPoly.toString

  /** The position of the centre of the Panel on the underlying canvas. */
  val clipCen: Pt2 = clipPoly.cenPt
  val clipVec: Vec2 = clipPoly.cenVec
  def width = clipPoly.boundingWidth
  def height = clipPoly.boundingHeight

  def repaint(els: Arr[GraphicElem]): Unit = { canvObjs = els; outerCanv.refreshPanel(this) }
  def repaints(els: GraphicElem*): Unit = repaint(els.toArr)
}

case class MouseButtonCmd(cmd: MouseButton => Unit)
{ @inline def apply(mb: MouseButton): Any = cmd(mb)
}