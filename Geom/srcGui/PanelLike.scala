/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pgui
import geom._

/** The purpose of this trait is to give common methods for Panels and Simple Canvases. A SimpleCanvas is like a Panel but not a Panel.*/
trait PanelLike extends RectCenlign
{ var backColour: Colour = Colour.White

  /** The active objects registered with this [[PanelLike]]. */
  var actives: RArr[GraphicActive] = RArr()

  var canvObjs: RArr[Graphic2Elem] = RArr()

  /** This method name is inconsistent with mouseUp on the canvas class*/
  var mouseUp: (MouseButton, RArr[Any], Pt2) => Unit = (_, _, _) => {}

  /** This method name is inconsistent with mousedown on the canvas class */
  var mouseDown: (Pt2, MouseButton, AnyRefs) => Unit = (v, b, s) => {}

  var fMouseMoved: (Pt2, MouseButton, AnyRefs) => Unit = (v, b, s) => {}
  var fMouseDragged: (Pt2, MouseButton, AnyRefs) => Unit = (v, b, s) => {}

  /** Just responds to when the Panel is clicked, ignores the position of the mose click or any potential active objects on the screen. */
  def setMouseSimplest(action: => Unit): Unit = mouseUp = (_, _, _) => action

  /** Just responds to the position of the mose click on the Panel, ignores any potential active objects on the screen. */
  def setMouseSimple(action: Pt2 => Unit): Unit = mouseUp = (_, _, v) => action(v)
}
