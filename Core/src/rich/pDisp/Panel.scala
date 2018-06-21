/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich
package pDisp
import geom._

trait DispObj
sealed trait DispPhase
case class DispAnim(fAnim: Double => Unit, secs: Double) extends DispPhase
case class DispStill(fDisp: () => Unit) extends DispPhase

/** A virtual panel created from the Canvas implemented using the clip function */
case class Panel(private val outerCanv: CanvasMulti, clipPoly: Vec2s, simple: Boolean = false, cover: Boolean = true) extends PanelLike
{
   override def toString: String = "Panel:" -- clipPoly.toString
   val cen: Vec2 = clipPoly.polyCentre
   def width = clipPoly.boundingWidth
   def height = clipPoly.boundingHeight
   def repaint(els: Seq[CanvObj[_]]): Unit =
   {
      canvObjs = els
      outerCanv.refreshPanel(this)
   }
}

case class M3Cmd(cmd: MouseButton => Unit)
{
   @inline def apply(mb: MouseButton): Unit = cmd(mb)
}

trait PanelLike extends RectGeom
{
   /** These are currently stored in reverse. I think this would be better in an Array */
   var subjs: List[ClickObj] = Nil
   var canvObjs: Seq[CanvObj[_]] = Seq()
   /** This method name is inconsistent with mouseup on the canvas class*/
   var mouseUp: (Vec2, MouseButton, List[AnyRef]) => Unit = (v, b, s) => {}
   /** This method name is inconsistent with mousedown on the canvas class */
   var mouseDown: (Vec2, MouseButton, List[AnyRef]) => Unit = (v, b, s) => {}
   var fMouseMoved: (Vec2, MouseButton, Seq[Any]) => Unit = (v, b, s) => {}
   var fMouseDragged: (Vec2, MouseButton, Seq[Any]) => Unit = (v, b, s) => {}  
}
