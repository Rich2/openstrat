/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pgui
import geom._

/** A canvas that is not divided up into panels. A panel is a portion of the canvas that has its own origin and is clipped. */
abstract class CanvasNoPanels(val title: String) extends CanvasUser with PanelLike
{
  override def width: Double = canv.width
  override def height: Double = canv.height
   
  canv.mouseUp = (posn, button) => mouseUp(button, actives.filter(_.ptInside(posn)).map(_.pointerId), posn)
      
  def refresh(): Unit =
  { canv.clear(backColour)
    actives = paintObjs(canvObjs)//paintObjs paints the objects to the screen and returns a list of active objects
  }

  /** Repaints the canvas takes repeat parameters of GraphicElem. */
  def repaints(elems: GraphicElem*): Unit = { canvObjs = elems.toArr; refresh() }

  /** Repaints the canvas, takes a Refs collection as parameter. */
  def repaint(elems: RArr[GraphicElem]): Unit = { canvObjs = elems; refresh() }

  /** This function is to create a display that changes over time. So you pass in a function from the time elapsed in milliseconds to the Arr of
   * [[GraphicElem]]s that you want to display at that time point. */
  def timedRepaint(f: Integer => GraphicElems): Unit =
  { val combinedF: Integer => Unit = elapsed => repaint(f(elapsed))
    canv.startFramePermanent(combinedF)
  }

  def timedRepaint1(f: Integer => GraphicElem): Unit =
  { val combinedF: Integer => Unit = elapsed => repaints(f(elapsed))
    canv.startFramePermanent(combinedF)
  }
}