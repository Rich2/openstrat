/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pCanv
import geom._

/** A canvas divided up into panels. Each panel is clipped and has its own origin. */
abstract class CanvasPanelled(title: String) extends CanvasUser(title)
{
  var panels: Arr[Panel] = Arr()
  
  def addPanel(clipPoly: Polygon, cover: Boolean = true): Panel =
  { val newPanel = Panel(this, clipPoly, cover)
    panels +-= newPanel
    newPanel
  }     
  
  canv.mouseUp = (v, b) => panels.find(_.clipPoly.ptInside(v)).foreach{ pan =>
      val ids: Arr[AnyRef] = pan.actives.filter(_.ptInside(v)).map(_.pointerId)
      pan.mouseUp(b, ids, v)
    }
    
  def refresh(): Unit = panels.foreach(refreshPanel)   
  
  /** This method creates a new frame for the panel. It clips the painting area. Paints the [[Panel]] with the back colour, translates the
   *  [[GraphicElem]]s from their positions relative to the [[Panel]]'s centre to their absolute positions on the canvas. It stores the active
   *  object shapes with their absolute positions on the panels active object list. Finally it unclips the painting area. */
  def refreshPanel(panel: Panel): Unit =
  { val clipPoly = panel.clipPoly
    canv.gcSave()
    canv.clip(clipPoly)
    canv.polygonFill(clipPoly.fill(panel.backColour))
    val movedObjs: Arr[GraphicElem] = panel.canvObjs.slate(panel.clipVec)
    panel.actives = paintObjs(movedObjs)
    canv.gcRestore()
  }   
}