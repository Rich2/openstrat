/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pCanv
import geom._

/** A simple use of the canvas with out splitting it up into Panels */
abstract class CanvasSimple(title: String) extends CanvasUser(title) with PanelLike 
{      
  override def width = canv.width
  override def height = canv.height
   
  canv.mouseUp = (posn, button) => mouseUp(posn, button, subjs.ptInObjsOld(posn))
      
  def refresh(): Unit =
  { canv.clear(backColour)
    subjs = paintObjs(canvObjs).toRefs//paintObjs paints the objects to the screen and returns a list of active objects
  }

  def repaints(els: GraphicElem*): Unit = { canvObjs = els.toArr; refresh() }
  def repaintOld(els: Arr[GraphicElem]): Unit = { canvObjs = els; refresh() }

  def timedRepaint(f: Integer => Arr[GraphicElem]): Unit =
  { val combinedF: Integer => Unit = elapsed => repaintOld(f(elapsed))
    canv.startFramePermanent(combinedF)
  }

  def timedRepaint1(f: Integer => GraphicElem): Unit =
  { val combinedF: Integer => Unit = elapsed => repaintOld(Arr(f(elapsed)))
    canv.startFramePermanent(combinedF)
  }
}