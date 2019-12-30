/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pCanv
import geom._

abstract class CanvasMulti(title: String) extends CanvasUser(title)
{
  var panels: List[Panel] = Nil
  
  def addPanel(clipPoly: Polygon, cover: Boolean = true): Panel =
  { val newPanel = Panel(this, clipPoly, cover)
    panels :+= newPanel
    newPanel
  }     
  
  canv.mouseUp = (v, b) =>
    {
      panels.find(_.clipPoly.ptInPolygon(v)).foreach{ pan =>
        val objs: Refs[AnyRef] = pan.subjs.ptInObjs(v)
        pan.mouseUp(v, b, objs)//.toRefs)
    }
  }
    
  def refresh(): Unit = panels.foreach(refreshPanel)   
   
  def refreshPanel(panel: Panel): Unit =
  {
    val clipPoly = panel.clipPoly
    //panel.subjs = Nil
    canv.gcSave()
    canv.clip(clipPoly)
    canv.polyFill(clipPoly.fill(panel.backColour))
    val movedObjs: Refs[GraphicElem] = panel.canvObjs.slate(panel.cen)//.sortWith(_.zOrder < _.zOrder)
    panel.subjs = paintObjs(movedObjs.toArraySeq).toRefs
    canv.gcRestore()
  }   
}
